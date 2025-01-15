
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Persons.Models;

namespace Persons.Repositories
{
    public class PersonRepository : IPersonRepository
    {
        private readonly PersonContext _context;

        public PersonRepository(PersonContext context)
        {
            _context = context;
        }

        public async Task<Person> findPersonAsync(long id)
        {
            return await _context.Persons.FindAsync(id);
        }

        public async Task<IEnumerable<Person>> findPersonsOnPlanetAsync(long planetId)
        {
            return await _context.Persons.Where(x => x.OnPlanetId.Equals(planetId)).ToListAsync();
        }

        public async Task<IEnumerable<Person>> getAllPersonsAsync()
        {
            return await _context.Persons.ToListAsync();
        }
    }
}
