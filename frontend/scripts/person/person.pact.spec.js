const { pactWith } = require('jest-pact');
const { Matchers } = require('@pact-foundation/pact');

global.fetch = require('node-fetch');

import { Person } from './person.model.js';
import { PersonService } from './person.service.js';

pactWith({ consumer: 'frontend-rest', provider: 'persons-rest' }, provider => {
    describe('Persons API', () => {
        describe('given there are persons', () => {
            const PERSON = {
                id: 1,
                name: 'test',
                image: Matchers.term({
                    generate: 'https://testhost.de/image.png',
                    matcher: 'https?:\/\/(www\.)?.*'
                })};

            describe('when a call to the API is made', () => {
                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have a list of persons',
                        uponReceiving: 'a request for persons',
                        withRequest: {
                            method: 'GET',
                            path: '/persons'
                        },
                        willRespondWith: {
                            status: 200,
                            headers: {
                                'Content-Type': 'application/json; charset=utf-8',
                            },
                            body: Matchers.eachLike(PERSON)
                        },
                    })
                });

                it('will receive the list of persons', async () => {
                    const personService = new PersonService(provider.mockService.baseUrl);

                    const persons = await personService.getAllPersons();
                    expect(persons).toEqual([new Person(PERSON.id, PERSON.name, PERSON.description, PERSON.image.getValue())]);
                });
            });

            describe('when a call to the API is made with query param planetId', () => {
                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have a list of persons',
                        uponReceiving: 'a request for persons on planetId',
                        withRequest: {
                            method: 'GET',
                            path: '/persons',
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
                                'Content-Type': 'application/json; charset=utf-8',
                            },
                            body: Matchers.eachLike(PERSON)
                        },
                    })
                });

                it('will receive a list of persons on a planet', async () => {
                    const personService = new PersonService(provider.mockService.baseUrl);

                    const persons = await personService.getPersonsOnPlanet(1);
                    expect(persons).toEqual([new Person(PERSON.id, PERSON.name, PERSON.description, PERSON.image.getValue())]);
                });
            });
        });

        describe('given there are NO persons', () => {
            describe('when a call to the API is made', () => {

                beforeEach(() => {
                    return provider.addInteraction({
                        state: 'i have no persons',
                        uponReceiving: 'a request for persons',
                        withRequest: {
                            method: 'GET',
                            path: '/persons'
                        },
                        willRespondWith: {
                            status: 200,   // Expect success with empty list, not 404
                            headers: {
                                'Content-Type': 'application/json; charset=utf-8',
                            },
                            body: []   // Expect an empty list
                        },
                    })
                });

                it('will receive an empty list', async () => {
                    const personService = new PersonService(provider.mockService.baseUrl);

                    const persons = await personService.getAllPersons();
                    expect(persons).toEqual([]);
                });
            });
        });
    });
});
