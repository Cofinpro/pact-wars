import { PlanetService } from './planet.service.js';
import { Travel } from './travel.model.js';

const template = document.createElement('template');
template.innerHTML = `
    <style>
        :host {
            display: block;
        }

        section {
            scroll-snap-align: center;
            display: inline-block;
            height: 70vh;
            min-width: 500px;
            border-radius: 3px;
            margin-right: 3rem;
        }

        h1 {
            font-family: var(--star-jedi-font);
            color: var(--primary-color);
        }

        button {
            background: var(--primary-color);
            border: none;
            width: 100%;
            font-weight: bold;
        }

        button.go {
            height: 100%;
            font-size: 80px;
        }

        button.new-travel {
            height: 3em;
        }
    </style>
    <section>
        <h1>Travel</h1>
        <button class="go">GO</button>
        <img />
        <button class="new-travel">NEW TRAVEL</button>
    </section>
    `;

class TravelComponent extends HTMLElement {
    constructor() {
        super();
        this._shadowRoot = this.attachShadow({ 'mode': 'open' });
        this._shadowRoot.appendChild(template.content.cloneNode(true));

        this.$goButton = this._shadowRoot.querySelector('button.go');
        this.$goButton.addEventListener('click', () => this._travel());

        this.$newTravel = this._shadowRoot.querySelector('button.new-travel');
        this.$newTravel.addEventListener('click', () => {
            this.dispatchEvent(new CustomEvent('onNewTravel', { bubbles: true, composed: true }));
        });
        this.$newTravel.style.display = 'none';

        this.$image = this._shadowRoot.querySelector('img');
    }

    async _travel() {
        console.log('start travel');
        const { from, person, spaceship, to } = this._travelInformation;
        const planetService = new PlanetService();
        planetService.travel(from, new Travel(person, spaceship, to)).then(response => {
            this._getImageElement(response.ok);
            this.$newTravel.style.display = 'block';
        });
    }

    _getImageElement(success) {
        this.$goButton.remove();
        this.$image.src = success ? '/assets/travel.gif' : '/assets/explosion.gif';
    }

    get travelInformation() {
        return this._travelInformation;
    }

    set travelInformation(travelInformation) {
        this._travelInformation = travelInformation;
    }
}

customElements.define('pw-travel', TravelComponent);
