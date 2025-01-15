const $inputElements = document.getElementById('input-elements');

const $fromPlanet = document.createElement('pw-planet');
$fromPlanet.addEventListener('onSelectedPlanet', e => {
    console.log('from planet: ', e.detail);

    try {
        $inputElements.removeChild($withSpaceship);
        $inputElements.removeChild($toPlanet);
        $inputElements.removeChild($travel);
    } catch { }


    $withPerson.planetId = e.detail;
    $withSpaceship.planetId = e.detail;
    $inputElements.appendChild($withPerson);
    $withPerson.scrollIntoView();
});

const $withPerson = document.createElement('pw-person');
$withPerson.addEventListener('onSelectedPerson', e => {
    console.log('with person: ', e.detail);

    try {
        $inputElements.removeChild($toPlanet);
        $inputElements.removeChild($travel);
    } catch { }

    $inputElements.appendChild($withSpaceship);
    $withSpaceship.scrollIntoView();
});

const $withSpaceship = document.createElement('pw-spaceship');
$withSpaceship.addEventListener('onSelectedSpaceship', e => {
    console.log('with spaceship: ', e.detail);

    try {
        $inputElements.removeChild($travel);
    } catch { }

    $inputElements.appendChild($toPlanet);
    $toPlanet.scrollIntoView();
});

const $toPlanet = document.createElement('pw-planet');
$toPlanet.addEventListener('onSelectedPlanet', e => {
    console.log('to planet: ', e.detail);

    const travel = {
        from: $fromPlanet.selectedPlanet,
        person: $withPerson.selectedPerson,
        spaceship: $withSpaceship.selectedSpaceship,
        to: $toPlanet.selectedPlanet
    }

    console.log(JSON.stringify(travel));

    $travel.travelInformation = travel;
    $inputElements.appendChild($travel);
    $travel.scrollIntoView();
});

const $travel = document.createElement('pw-travel');
$travel.addEventListener('onNewTravel', () => {
    window.location.reload();
});

customElements.whenDefined('pw-planet').then(() => {
    $inputElements.appendChild($fromPlanet);
});