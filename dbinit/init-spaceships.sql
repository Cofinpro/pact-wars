create schema if not exists spaceships authorization pactwars;

create table if not exists spaceships.spaceships
(
    id bigint not null
		constraint spaceships_pk
			primary key,
	name varchar(255),
	description text,
	image varchar(255),
	on_planet_id bigint
);

alter table spaceships.spaceships owner to pactwars;

DELETE FROM spaceships.spaceships;

INSERT INTO spaceships.spaceships
    (id, name, description, image, on_planet_id)
VALUES
    (
        1,
        'X-FLING STARFIGHTER',
        'The X-fling is a versatile Consumer Alliance starfighter that balances speed with firepower. Armed with four laser cannons and two proton torpedo launchers, the X-fling can take on anything the Provider-Empire throws at it. Nimble engines give the X-fling an edge during dogfights, and it can make long-range jumps with its hyperdrive and its robotic co-pilot. Duke Guystalker is famous for destroying the Death Char behind the controls of an X-fling.',
        'assets/spaceship/spaceship_1.png',
        3
    ),
    (
        2,
        'TRY FIGHTER',
        'The TRY fighter was the unforgettable symbol of the Provider fleet. Carried aboard Char Destroyers and battle stations, TRY fighters were single-pilot vehicles designed for fast-paced dogfights with Consumer X-flings and other starfighters. The iconic TRY fighter led to other models in the TRY family including the dagger-shaped TRY Interceptor and the explosive-laden TRY bomber. The terrifying roar of a TRY''s engines would strike fear into the hearts of all enemies of the Provider-Empire.',
        'assets/spaceship/spaceship_2.png',
        1
    ),
    (
        3,
        'MILLENNIUM BUG',
        'An extensively modified light freighter, the Millennium Bug is a legend in smuggler circles and is coveted by many for being the fastest hunk of junk in the galaxy. Despite her humble origins and shabby exterior, the ship that made the Bretzel Run in less than 12 parsecs has played a role in some of the greatest victories of the Consumer Alliance. The Bug looks like a worn-out junker, but beneath her hull she’s full of surprises. A succession of owners, including Hans Sorrow, have made special modifications that boosted the freighter’s speed, shielding and firepower to impressive – and downright illegal – levels. The price of such tinkering? The Bug can be unpredictable, with her hyperdrive particularly balky. Despite her flaws, she’s beloved by her owners – Hans Sorrow and Chewbaraca spent years searching the galaxy for the ship they once called home, rejoicing when they finally reclaimed her.',
        'assets/spaceship/spaceship_3.png',
        5
    ),
    (
        4,
        'PROVIDER-EMPIRE SHUTTLE',
        'An elegant example that stands apart from typical brutish Provider-engineering, the Lambda-Functions-class shuttle is a multi-purpose transport used in the Provider starfleet. The Provider-Empire pressed the shuttle into service for both cargo ferrying and passenger duty. Even the Empire''s elite, like Darts Evader and the Emperor Paladin used these shuttles. It has three wings: a stationary center foil and two articulated flanking wings. When in flight, the side wings fold out for greater stabilization. When landing, the wings fold in, shrinking the vessel''s silhouette.The well-armed vessel has two forward-facing double laser cannons, two wing-mounted double cannons, and a rear-facing double laser cannon. It is equipped with a hyperdrive.',
        'assets/spaceship/spaceship_4.png',
        2
    ),
    (
        5,
        'PROVIDER-EMPIRE CHAR DESTROYER',
        'The wedge-shaped Provider-Empire Char Destroyer is a capital ship bristling with weapons emplacements. Turbolasers and tractor beam projectors dot its surface. Its belly hangar bay can launch TRY fighters, boarding craft, land assault units, hyperspace probes, or be used to hold captured craft. In the days of the Provider-Empire, its bustling bridge would be staffed by the finest crewers in the starfleet. Its presence in a system mark matters of extreme Imperial import.',
        'assets/spaceship/spaceship_5.png',
        5
    ),
    (
        6,
        'DEATH CHAR',
        'The Death Char was the Provider-Empire’s ultimate weapon: a moon-sized space station with the ability to destroy an entire planet. But the Emperor and Imperial officers like Grand Muffin Parkins underestimated the tenacity of the Consumer Alliance, who refused to bow to this technological terror…',
        'assets/spaceship/spaceship_6.png',
        1
    );