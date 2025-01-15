import { Spaceship } from './spaceship.model.js';

export class SpaceshipService {
    
    constructor(baseURL = window.location.origin) {
        this.baseURL = baseURL;
    }

    getAllSpaceships() {
        return this._getSpaceships(`${this.baseURL}/spaceships`);
    }

    getSpaceshipsOnPlanet(planetId) {
        return this._getSpaceships(`${this.baseURL}/spaceships?planetId=${planetId}`);
    }

    _getSpaceships(url) {
        return fetch(url)
            .then(response => response.json())
            .then(json => json.map(spaceship => {
                const { id, name, description, image } = spaceship;
                return new Spaceship(id, name, description, image);
            }))
            .catch(console.error);
    }
}
