import { PersonService } from './person.service.js';

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
        <h1>Person</h1>
        <ul>
        </ul>
    </section>
    `;

class PersonComponent extends HTMLElement {
    constructor() {
        super();
        this._shadowRoot = this.attachShadow({ 'mode': 'open' });
        this._shadowRoot.appendChild(template.content.cloneNode(true));

        this.$personList = this._shadowRoot.querySelector('ul');
        this.$personList.addEventListener('click', e => this._selectPerson(e));
        this._loadPersons();
    }

    async _loadPersons(planetId) {
        const personService = new PersonService();
        
        if (planetId) {
            this.persons = await personService.getPersonsOnPlanet(planetId);            
        } else {
            this.persons = await personService.getAllPersons();
        }
    }

    _selectPerson(event) {
        const selectedElement = event.path.find(x => x.tagName === 'LI');
        if (selectedElement) {
            const id = Number(selectedElement.id);
            if (id !== NaN) {
                this._selectedPerson = id;
                this.$personList.querySelectorAll('li').forEach(x => x.classList.remove('selected'));
                selectedElement.classList.add('selected');
                this.dispatchEvent(new CustomEvent('onSelectedPerson', { detail: id, bubbles: true, composed: true }));            
            }
        }
    }

    _createPerson(person) {
        const { id, name, image } = person;
        return `
            <li id="${id}">
                ${image !== undefined && `<img src="${image}">`}
                <div>
                    <span>${name}</span><br>
                </div>
            </li>
        `;
    }

    _render() {
        this.$personList.innerHTML = this._persons.map(person => {
            return this._createPerson(person);
        }).join('');
    }

    set persons(value) {
        this._persons = value;
        this._render();
    }

    get persons() {
        return this._persons;
    }

    get selectedPerson() {
        return this._selectedPerson;
    }

    set selectedPerson(id) {
        this._selectedPerson = id; 
    }

    get planetId() {
        return this._planetId;
    }

    set planetId(id) {
        this._planetId = id;
        this._loadPersons(id);
        this._render();
    }
}

customElements.define('pw-person', PersonComponent);
