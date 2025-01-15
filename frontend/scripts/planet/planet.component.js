import { PlanetService } from './planet.service.js';

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

        ul {
            overflow-x: hidden;
            overflow-y: scroll;
            height: 100%;
            padding-inline-start: 0;
            margin-block-start: 0;
            scrollbar-width: none;
        }

        ul::-webkit-scrollbar { 
            display: none;
        }

        li {
            list-style-type: none;
            background: var(--white-color);
            color: var(--black-color);
            display: flex;
            border: 1px solid;
            margin: 1em 1em 1em 0;
        }

        li.selected {
            background: var(--primary-color);
        }

        li img, li div {
            padding: 1em;
        }

        img {
            height: 6em;
        }
    </style>
    <section>
        <h1>Planet</h1>
        <ul>
        </ul>
    </section>
    `;

class PlanetComponent extends HTMLElement {
    constructor() {
        super();
        this._shadowRoot = this.attachShadow({ 'mode': 'open' });
        this._shadowRoot.appendChild(template.content.cloneNode(true));

        this.$planetList = this._shadowRoot.querySelector('ul');
        this.$planetList.addEventListener('click', e => this._selectPlanet(e));

        this._init();
    }

    async _init() {
        const planetService = new PlanetService();
        this.planets = await planetService.getAllPlanets();
    }

    _selectPlanet(event) {
        const selectedElement = event.path.find(x => x.tagName === 'LI');
        if (selectedElement) {
            const id = Number(selectedElement.id);
            if (id !== NaN) {
                this._selectedPlanet = id;
                this.$planetList.querySelectorAll('li').forEach(x => x.classList.remove('selected'));
                selectedElement.classList.add('selected');
                this.dispatchEvent(new CustomEvent('onSelectedPlanet', { detail: id, bubbles: true, composed: true }));            
            }
        }
    }

    _createplanet(planet) {
        const { id, name, description, image } = planet;
        return `
            <li id="${id}">
                ${image !== undefined && `<img src="${image}">`}
                <div>
                    <details>
                        <summary>${name}</summary>
                        ${description}
                    </details>
                </div>
            </li>
        `;
    }

    _render() {
        this.$planetList.innerHTML = this._planets.map(planet => {
            return this._createplanet(planet);
        }).join('');
    }

    set planets(value) {
        this._planets = value;
        this._render();
    }

    get planets() {
        return this._planets;
    }

    get selectedPlanet() {
        return this._selectedPlanet;
    }

    set selectedPlanet(id) {
        this._selectedPlanet = id; 
    }
}

customElements.define('pw-planet', PlanetComponent);
