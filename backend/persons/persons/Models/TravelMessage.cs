using System.Collections.Generic;

namespace Persons.Models
{
    public class TravelMessage
    {
        public long SpaceshipId { get; set; }
        public long ToPlanetId { get; set; }
        public IEnumerable<long> PersonIds { get; set; }
    }
}
