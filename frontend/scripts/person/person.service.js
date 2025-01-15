import { Person } from './person.model.js';

export class PersonService {

    constructor(baseURL = window.location.origin) {
        this.baseURL = baseURL;
    }

    getAllPersons() {
        return this._getPersons(`${this.baseURL}/persons`);
    }

    getPersonsOnPlanet(planetId) {
        return this._getPersons(`${this.baseURL}/persons?planetId=${planetId}`);
    }

    _getPersons(url) {
        return fetch(url)
            .then(response => response.json())
            .then(json => json.map(person => {
                const { id, name, description, image } = person;
                return new Person(id, name, description, image);
            }))
            .catch(console.error);
    }
}
