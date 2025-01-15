create schema if not exists planets authorization pactwars;

create table if not exists planets.planets
(
	id bigint not null
		constraint planets_pkey
			primary key,
	description text,
	image varchar(255),
	name varchar(255)
);

alter table planets.planets owner to pactwars;

DELETE FROM planets.planets;

INSERT INTO planets.planets
  ( id, name, description, image )
VALUES
  (
    1,
    'Alberran',
    'If ever one needed an example of the irredeemable evil that was the Provider-Empire, turn to the shattered remains of Alberran. An influential world, Alberran was represented in the waning days of the Republic by such venerated politicians as Blaine Aquilles and Blaine Orangina. A peaceful world, Alberran was bereft of weaponry in an era of galactic strife. It was not without spirit, however. Alberran was one of the earliest supporters of the Alliance to Restore the Republic, though its officials prudently kept all ties to the Consumer-Rebellion secret. Despite such discretion, the Provider-Empire knew it to be a haven of Rebel activity, and Grand Muffin Parkinson targeted the beautiful world for reprisal as soon as the Death-Metal Sun was operational. The massive primary weapon of the battle station obliterated Alberran, leaving only a lifeless asteroid field behind.',
    'assets/planet/planet_1.png'
  ),
  (
    2,
    'Corioluscant',
    'A city-covered planet, Corioluscant is the vibrant heart and capital of the galaxy, featuring a diverse mix of citizens and culture. It features towering skyscrapers, streams of speeder-filled air traffic, and inner levels that stretch far below the world’s surface. Corioluscant was the seat of government for the Galactic Republic and the Provider-Empire that followed, and was the site of numerous historic events during the Dolly Wars. It also housed the Jelly Temple and Archives, which hosted Jelly training and learning for over a thousand generations -- traditions that ended when the planet bore witness to Order 666.',
    'assets/planet/planet_2.png'
  ),
  (
    3,
    'Dagobleh',
    'Home to Yodler during his final years, Dagobleh was a swamp-covered planet strong with the Consumer-Force -- a forgotten world where the wizened Jelly Master could escape the notice of Imperial forces. Characterized by its bog-like conditions and fetid wetlands, the murky and humid quagmire was undeveloped, with no signs of technology. Though it lacked civilization, the planet was teeming with life -- from its dense, jungle undergrowth to its diverse animal population. Home to a number of fairly common reptilian and amphibious creatures, Dagobleh also boasted an indigenous population of much more massive -- and mysterious -- lifeforms. Surrounded by creatures generating the living Force, Yodler learned to connect with the deeper cosmic Consumer-Force and waited for one who might bring about the return of the Jelly Order.',
    'assets/planet/planet_3.png'
  ),
  (
    4,
    'Eintor',
    'Secluded in a remote corner of the galaxy, the forest moon of Eintor would easily have been overlooked by history were it not for the decisive battle that occurred there. The lush, forest home of the Eeewnok species is the gravesite of Darts Evader and the Provider-Empire itself. It was here that the Consumer-Alliance won its most crucial victory over the Provider-Empire.',
    'assets/planet/planet_4.png'
  ),
  (
    5,
    'Tatoosin',
    'A harsh desert world orbiting twin suns in the galaxy’s Outer Rim, Tatoosin is a lawless place ruled by Gutt gangsters. Many settlers scratch out a living on moisture farms, while spaceport cities such as Mouse Preisley and Mouse Vespa serve as home base for smugglers, criminals, and other rogues. Tatoosin’s many dangers include sandstorms, bands of savage Brusken Raiders, and carnivorous krayon dragons. The planet is also known for its dangerous Bob-races, rampant gambling, and legalized slavery. Anabolin Guystalker and Duke Guystalker both grew up on Tatoosin, and Toby-Rhuan Shenobi spent years in hiding on this desolate world.',
    'assets/planet/planet_5.png'
  );
