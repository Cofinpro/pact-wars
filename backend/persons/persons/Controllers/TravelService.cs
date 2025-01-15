using Microsoft.Extensions.Hosting;
using System.Threading;
using System.Threading.Tasks;
using System;
using RabbitMQ.Client.Events;
using RabbitMQ.Client;
using Persons.Models;
using System.Text.Json;
using Microsoft.Extensions.DependencyInjection;
using Polly;
using RabbitMQ.Client.Exceptions;
using Microsoft.Extensions.Logging;

namespace Persons.Controllers
{
    public class TravelService : BackgroundService
    {
        readonly string QUEUE_NAME = "person-service";
        readonly string EXCHANGE_NAME = "travels";

        private readonly ILogger _logger;
        
        private readonly IServiceProvider _serviceProvider;
        private IConnection _connection;
        private IModel _channel;

        public TravelService(IServiceProvider serviceProvider, ILogger<TravelService> logger)
        {
            _serviceProvider = serviceProvider;

            var factory = new ConnectionFactory() { HostName = "rabbitmq" };

            var retryRabbitMqPolicy =
                    Policy
                        .Handle<BrokerUnreachableException>()
                        .WaitAndRetry(6, retryAttempt => TimeSpan.FromSeconds(10), (exception, retryCount) =>
                        {
                            logger.LogError(exception, "An exception occurred while connecting to the rabbitmq server.");
                        });

            retryRabbitMqPolicy.Execute(() =>
            {
                _connection = factory.CreateConnection();
                _channel = _connection.CreateModel();

                _channel.ExchangeDeclare(exchange: EXCHANGE_NAME, type: ExchangeType.Fanout);
                _channel.QueueDeclare(queue: QUEUE_NAME);

                _channel.QueueBind(queue: QUEUE_NAME,
                                        exchange: EXCHANGE_NAME,
                                        routingKey: "");

                _channel.BasicQos(prefetchSize: 0, prefetchCount: 1, global: false);
            });
        }

        protected override Task ExecuteAsync(CancellationToken cancellationToken)
        {
            cancellationToken.ThrowIfCancellationRequested();

            var consumer = new EventingBasicConsumer(_channel);

            consumer.Received += async (sender, ea) => await HandelMessage(ea);

            _channel.BasicConsume(queue: QUEUE_NAME,
                                 autoAck: false,
                                 consumer: consumer);

            return Task.CompletedTask;
        }

        private async Task HandelMessage(BasicDeliverEventArgs ea)
        {
            var travelMessage = JsonSerializer.Deserialize<TravelMessage>(
                new ReadOnlySpan<byte>(ea.Body.ToArray()),
                new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true,
                });

            Console.WriteLine(" [x] Received {0}", travelMessage.ToString());

            using (IServiceScope scope = _serviceProvider.CreateScope())
            {
                var context = scope.ServiceProvider.GetRequiredService<PersonContext>();
                foreach (long personId in travelMessage.PersonIds)
                {
                    var person = await context.Persons.FindAsync(personId);
                    person.OnPlanetId = travelMessage.ToPlanetId;
                }

                await context.SaveChangesAsync();
            }

            _channel.BasicAck(deliveryTag: ea.DeliveryTag, multiple: false);
        }

        public override void Dispose()
        {
            _channel.Close();
            _connection.Close();
            base.Dispose();
        }
    }
}
