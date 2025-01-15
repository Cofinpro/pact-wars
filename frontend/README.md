# PACT Wars - Frontend

The Frontend is build with Web Components. Each Domain (persons, planets, spaceships) has it's own Web Component responsible for loading and selecting data. 

The `main.js` is responsible for the business logik and routing between the seperate domains.


## Instal dependencies

You need nodejs and npm installed on your machine https://nodejs.org/en/.

Install frontend dependencies via node package manager: `npm i`

## Start local development server

`npx http-server -p 8080 -P http://localhost:8000`

The server connects via a proxy through the backend and runs on port 8080.

## Run PACT Tests

The used test framework is jest: https://jestjs.io/

Run the tests with: `npm test`

For the pact tests PACT.JS ist used see:

https://docs.pact.io/implementation_guides/javascript/

https://github.com/pact-foundation/pact-js

## Font Source

https://www.starwarsfont.com/
