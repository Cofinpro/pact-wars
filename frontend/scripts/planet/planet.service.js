import { Planet } from './planet.model.js';

export class PlanetService {
    
    constructor(baseURL = window.location.origin) {
        this.baseURL = baseURL;
    }

    getAllPlanets() {
        return fetch(`${this.baseURL}/planets`)
            .then(response => response.json())
            .then(json => json.map(planet => {
                const { id, name, description, image } = planet;
                return new Planet(id, name, description, image);
            }))
            .catch(console.error);
    }

    travel(fromPlanetId, travel) {
        return fetch(`${this.baseURL}/planets/${fromPlanetId}/travel`, {
            method: 'post',
            headers: {'Content-Type' : 'application/json'},
            body: JSON.stringify(travel)
        });
    }

}
