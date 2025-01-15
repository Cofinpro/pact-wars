using System.Collections.Generic;
using System.Threading.Tasks;
using Persons.Models;

namespace Persons.Repositories
{
    public interface IPersonRepository {
        Task<IEnumerable<Person>> getAllPersonsAsync();
        Task<IEnumerable<Person>> findPersonsOnPlanetAsync(long planetId);
        Task<Person> findPersonAsync(long id);
    }
}
