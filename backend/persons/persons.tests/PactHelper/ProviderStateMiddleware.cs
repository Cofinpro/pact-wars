using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using Persons.Models;
using Persons.Repositories;
using static System.String;

namespace Persons.Tests.PactHelper
{
    public class ProviderStateMiddleware
    {
        private readonly MockPersonRepository _personRepository;
        private const string ConsumerName = "frontend-rest";
        private readonly RequestDelegate _next;
        private readonly IDictionary<string, Action> _providerStates;

        public ProviderStateMiddleware(RequestDelegate next, IPersonRepository personRepository)
        {
            _next = next;
            _personRepository = personRepository as MockPersonRepository;

            _providerStates = new Dictionary<string, Action>
            {
                {
                    "i have a list of persons",
                    ListOfPersons
                },
                {
                    "i have no persons",
                    NoPersons
                }
            };
        }

        private void ListOfPersons()
        {
            _personRepository.Persons =
                new List<Person> {
                    new Person {
                        Id = 1,
                        Name = "test",
                        Image = "https://testhost.de/image.png"
                    },
                };
        }

        private void NoPersons()
        {
            _personRepository.Persons = new List<Person>();
        }

        public async Task InvokeAsync(HttpContext context)
        {
            if (context.Request.Path.Value == "/provider-states")
            {
                context.Response.StatusCode = (int)HttpStatusCode.OK;

                if (context.Request.Method == HttpMethod.Post.ToString() &&
                    context.Request.Body != null)
                {
                    string jsonRequestBody = String.Empty;
                    using (var reader = new StreamReader(context.Request.Body, Encoding.UTF8))
                    {
                        jsonRequestBody = await reader.ReadToEndAsync();
                    }

                    var providerState = JsonConvert.DeserializeObject<ProviderState>(jsonRequestBody);

                    //A null or empty provider state key must be handled
                    if (providerState != null &&
                        !IsNullOrEmpty(providerState.State) &&
                        providerState.Consumer == ConsumerName)
                    {
                        _providerStates[providerState.State].Invoke();
                    }

                    await context.Response.WriteAsync(Empty);
                }
            }
            else
            {
                await _next.Invoke(context);
            }
        }
    }
}