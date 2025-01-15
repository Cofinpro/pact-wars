const { pactWith } = require('jest-pact');
const { Matchers } = require('@pact-foundation/pact');

global.fetch = require('node-fetch');

import { Spaceship } from './spaceship.model.js';
import { SpaceshipService } from './spaceship.service.js';

pactWith({ consumer: 'frontend-rest', provider: 'spaceships-rest' }, provider => {
    describe('Spaceships API', () => {
        describe('given there are spaceships', () => {

            const SPACESHIP = {
                id: 1,
                name: 'test',
                description: 'test',
                image: Matchers.term({
                    generate: 'https://testhost.de/image.png',
                    matcher: 'https?:\/\/(www\.)?.*'
                })};

            describe('when a call to the API is made', () => {
                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have a list of spaceships',
                        uponReceiving: 'a request for spaceships',
                        withRequest: {
                            method: 'GET',
                            path: '/spaceships'
                        },
                        willRespondWith: {
                            status: 200,
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: Matchers.eachLike(SPACESHIP)
                        },
                    })
                });

                it('will receive the list of spaceships', async () => {
                    const spaceshipService = new SpaceshipService(provider.mockService.baseUrl);

                    const spaceships = await spaceshipService.getAllSpaceships();
                    expect(spaceships).toEqual([new Spaceship(SPACESHIP.id, SPACESHIP.name, SPACESHIP.description, SPACESHIP.image.getValue())]);
                });
            });

            describe('when a call to the API is made with query param planetId', () => {
                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have a list of spaceships',
                        uponReceiving: 'a request for spaceships on planetId',
                        withRequest: {
                            method: 'GET',
                            path: '/spaceships',
                            query: {
                                planetId: Matchers.term({
                                    generate: '1',
                                    matcher: '\d*'
                                })
                            }
                        },
                        willRespondWith: {
                            status: 200,
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: Matchers.eachLike(SPACESHIP)
                        },
                    })
                });

                it('will receive a list of persons on a planet', async () => {
                    const spaceshipService = new SpaceshipService(provider.mockService.baseUrl);

                    const spaceships = await spaceshipService.getSpaceshipsOnPlanet(1);
                    expect(spaceships).toEqual([new Spaceship(SPACESHIP.id, SPACESHIP.name, SPACESHIP.description, SPACESHIP.image.getValue())]);
                });
            });
        });

        describe('given there are NO spaceships', () => {
            describe('when a call to the API is made', () => {

                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have no spaceships',
                        uponReceiving: 'a request for spaceships',
                        withRequest: {
                            method: 'GET',
                            path: '/spaceships'
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
                    const spaceshipService = new SpaceshipService(provider.mockService.baseUrl);

                    const spaceships = await spaceshipService.getAllSpaceships();
                    expect(spaceships).toEqual([]);
                });
            });
        });
    });
});
