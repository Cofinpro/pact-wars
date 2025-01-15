using System;
using System.Collections.Generic;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using PactNet;
using PactNet.Infrastructure.Outputters;
using Xunit;
using Xunit.Abstractions;
using Persons.Tests.PactHelper;

// https://github.com/pact-foundation/pact-workshop-dotnet-core-v1

namespace Persons.Tests
{
    public class PersonControllerPactTests : IDisposable
    {
        private readonly ITestOutputHelper _output;
        private readonly string _serviceUri = "http://localhost:5001";
        private readonly IWebHost _webHost;
        private bool _disposed;

        public PersonControllerPactTests(ITestOutputHelper output)
        {
            _output = output;

            // Configure the person web server
            _webHost = WebHost.CreateDefaultBuilder()
                .UseUrls(_serviceUri)
                .UseStartup<TestStartup>()
                .Build();
        }

        [Fact]
        public void TestPersonProvider()
        {
            var config = new PactVerifierConfig
            {
                // Output wrapper for XUnit for outputting to console
                Outputters = new List<IOutput> {
                    new XUnitOutput(_output)
                },
                Verbose = true
            };

            // Start the configured person web server
            using (_webHost.StartAsync()) 
            {
                new PactVerifier(config)
                    .ProviderState($"{_serviceUri}/provider-states")
                    .ServiceProvider("persons-rest", _serviceUri)
                    .HonoursPactWith("frontend-rest")
                    .PactBroker("http://localhost:8005/")
                    //.PactUri(@"../../../frontend/pact/pacts/frontend-rest-persons-rest.json")
                    .Verify();
            }
        }

        protected virtual void Dispose(bool disposing)
        {
            if (!_disposed)
            {
                if (disposing)
                {
                    _webHost.StopAsync().GetAwaiter().GetResult();
                    _webHost.Dispose();
                }

                _disposed = true;
            }
        }

        public void Dispose()
        {
            Dispose(disposing: true);
        }
    }
}
