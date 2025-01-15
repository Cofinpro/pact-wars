
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Persons.Models;
using Persons.Repositories;

namespace Persons.Tests
{
    class MockPersonRepository : IPersonRepository
    {
        public List<Person> Persons { get; set; } = new List<Person>();

        public Task<Person> findPersonAsync(long id) => Task.FromResult(Persons.FirstOrDefault(x => x.Id == id));

        public Task<IEnumerable<Person>> findPersonsOnPlanetAsync(long planetId) => Task.FromResult(Persons.AsEnumerable());

        public Task<IEnumerable<Person>> getAllPersonsAsync() => Task.FromResult(Persons.AsEnumerable());

    }
}
