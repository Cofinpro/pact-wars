using System.Collections.Generic;
using System.Net.Mime;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Persons.Models;
using Persons.Repositories;

namespace Persons.Controllers
{

    [Route("persons")]
    [ApiController]
    public class PersonController : ControllerBase
    {

        private readonly IPersonRepository _personRepository;

        public PersonController(IPersonRepository personRepository)
        {
            _personRepository = personRepository;
        }

        [HttpGet]
        [Produces(MediaTypeNames.Application.Json)]
        public async Task<ActionResult<IEnumerable<Person>>> GetPersons([FromQuery] long? planetId)
        {
            IEnumerable<Person> persons = null;
            if (planetId.HasValue)
            {
                persons = await _personRepository.findPersonsOnPlanetAsync(planetId.Value);
            }
            else
            {
                persons = await _personRepository.getAllPersonsAsync();
            }

            if (persons == null)
            {
                return NotFound();
            }

            return Ok(persons);
        }

        [HttpGet("{id}")]
        [Produces(MediaTypeNames.Application.Json)]
        public async Task<ActionResult<Person>> GetPerson(long id)
        {
            var person = await _personRepository.findPersonAsync(id);

            if (person == null)
            {
                return NotFound();
            }

            return person;
        }

    }
}
