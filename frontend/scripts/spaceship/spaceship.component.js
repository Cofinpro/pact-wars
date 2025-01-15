import { SpaceshipService } from './spaceship.service.js';

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
        <h1>spaceship</h1>
        <ul>
        </ul>
    </section>
    `;

class SpaceshipComponent extends HTMLElement {
    constructor() {
        super();
        this._shadowRoot = this.attachShadow({ 'mode': 'open' });
        this._shadowRoot.appendChild(template.content.cloneNode(true));

        this.$spaceshipList = this._shadowRoot.querySelector('ul');
        this.$spaceshipList.addEventListener('click', e => this._selectSpaceship(e));

        this._loadSpaceships();
    }

    async _loadSpaceships(planetId) {
        const spaceshipService = new SpaceshipService();
        
        if (planetId) {
            this.spaceships = await spaceshipService.getSpaceshipsOnPlanet(planetId);
        } else {
            this.spaceships = await spaceshipService.getAllSpaceships();
        }
    }

    _selectSpaceship(event) {
        const selectedElement = event.path.find(x => x.tagName === 'LI');
        if (selectedElement) {
            const id = Number(selectedElement.id);
            if (id !== NaN) {
                this._selectedSpaceship = id;
                this.$spaceshipList.querySelectorAll('li').forEach(x => x.classList.remove('selected'));
                selectedElement.classList.add('selected');
                this.dispatchEvent(new CustomEvent('onSelectedSpaceship', { detail: id, bubbles: true, composed: true }));            
            }
        }
    }

    _createSpaceship(spaceship) {
        const { id, name, description, image } = spaceship;
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
        this.$spaceshipList.innerHTML = this._spaceships.map(spaceship => {
            return this._createSpaceship(spaceship);
        }).join('');
    }

    set spaceships(value) {
        this._spaceships = value;
        this._render();
    }

    get spaceships() {
        return this._spaceships;
    }

    get selectedSpaceship() {
        return this._selectedSpaceship;
    }

    set selectedSpaceship(id) {
        this._selectedSpaceship = id; 
    }

    get planetId() {
        return this._planetId;
    }

    set planetId(id) {
        this._planetId = id;
        this._loadSpaceships(id);
        this._render();
    }
}

customElements.define('pw-spaceship', SpaceshipComponent);
