const { pactWith } = require('jest-pact');
const { Matchers } = require('@pact-foundation/pact');

global.fetch = require('node-fetch');

import { Planet } from './planet.model.js';
import { PlanetService } from './planet.service.js';

pactWith({ consumer: 'frontend-rest', provider: 'planets-rest' }, provider => {
    describe('Planets API', () => {

        describe('given there are planets', () => {
            describe('when a call to the API is made', () => {

                const PLANET = {
                    id: 1,
                    name: 'test',
                    description: 'test',
                    image: Matchers.term({
                        generate: 'https://testhost.de/image.png',
                        matcher: 'https?:\/\/(www\.)?.*'
                    })};

                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have a list of planets',
                        uponReceiving: 'a request for planets',
                        withRequest: {
                            method: 'GET',
                            path: '/planets'
                        },
                        willRespondWith: {
                            status: 200,
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: Matchers.eachLike(PLANET)
                        },
                    })
                });

                it('will receive the list of planets', async () => {
                    const planetService = new PlanetService(provider.mockService.baseUrl);

                    const planets = await planetService.getAllPlanets();
                    expect(planets).toEqual([new Planet(PLANET.id, PLANET.name, PLANET.description, PLANET.image.getValue())]);
                });
            });
        });

        describe('given there are NO planets', () => {
            describe('when a call to the API is made', () => {

                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have no planets',
                        uponReceiving: 'a request for planets',
                        withRequest: {
                            method: 'GET',
                            path: '/planets'
                        },
                        willRespondWith: {
                            status: 200,   // Expect success with empty list, not 404
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: []   // Expect an empty list
                        },
                    })
                });

                it('will receive an empty list', async () => {
                    const planetService = new PlanetService(provider.mockService.baseUrl);

                    const planets = await planetService.getAllPlanets();
                    expect(planets).toEqual([]);
                });
            });
        });

        describe('given a valid travel', () => {
            describe('when a call to the travel API is made', () => {

                const TRAVEL = {
                    spaceshipId: 2,
                    toPlanetId: 3,
                    personId: 5
                };

                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have planet 1 with spaceship 2 and person 5',
                        uponReceiving: 'a post request to travel',
                        withRequest: {
                            method: 'POST',
                            path: '/planets/1/travel',
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: TRAVEL
                        },
                        willRespondWith: {
                            status: 200
                        },
                    })
                });

                it('will successfully post a travel', async () => {
                    const planetService = new PlanetService(provider.mockService.baseUrl);

                    const result = await planetService.travel(1, TRAVEL);
                    expect(result).toHaveProperty('status', 200);
                });
            });
        });

        describe('given an invalid travel', () => {
            describe('when a call to the travel API is made', () => {

                const TRAVEL = {
                    spaceshipId: 2,
                    toPlanetId: 3,
                    personIds: 5
                };

                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have planet 1 without spaceship 2 or person 5',
                        uponReceiving: 'a post request to travel',
                        withRequest: {
                            method: 'POST',
                            path: '/planets/1/travel',
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: TRAVEL
                        },
                        willRespondWith: {
                            status: 400   // Expect a bad request if something/someone not on planet
                        },
                    })
                });

                it('will fail when posting a travel', async () => {
                    const planetService = new PlanetService(provider.mockService.baseUrl);

                    const result = await planetService.travel(1, TRAVEL);
                    expect(result).toHaveProperty('status', 400);
                });
            });
        });

    });
});
