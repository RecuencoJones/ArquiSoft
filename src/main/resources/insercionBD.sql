INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (1);
INSERT INTO Publicante (tipoPublicante) VALUES (1);
INSERT INTO Publicante (tipoPublicante) VALUES (1);
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais) VALUES (1,'Foo','Bar','foo@bar.com',
	'1234','567548674','Zaragoza','Spain');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais) VALUES (2,'Manolo','el del bombo',
	'coso@cosito.com','1234','2476474765','Huesca','Spain');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais) VALUES (3,'Ana','Gomez',
	'herp@derp.com','1234','45764356','Teruel','Spain');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion) VALUES (4,'Arctic Monkeys',2001,
	'Grupo entre el rock y el indie buscando su lugar en el mundo de la musica');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion) VALUES (5,'Foo Fighters',2000,
	'Somos unos chicos que estan empezando');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion) VALUES (6,'Los solfamidas',1992,'Barney tiene un vozarron');
insert into publicacion (fecha,contenido,publicante_uuid) values (234324123,'soy mu tonto y lo digo por aqui porque no tengo vida',1);
insert into aptitud (nombre) values ('guitarra');
insert into aptitud (nombre) values ('zambomba');
insert into aptitud (nombre) values ('bajo');
insert into es_integrante (uuid_p,uuid_g) values (1,5);
insert into es_integrante (uuid_p,uuid_g) values (1,6);
insert into es_integrante (uuid_p,uuid_g) values (2,6);
insert into tiene_aptitud (UUID_P,idAptitud) values (3,1);
insert into tiene_aptitud (UUID_P,idAptitud) values (3,2);
insert into tiene_aptitud (UUID_P,idAptitud) values (1,1);
insert into tiene_aptitud (UUID_P,idAptitud) values (2,3);