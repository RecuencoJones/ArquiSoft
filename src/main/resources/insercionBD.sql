INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (0);
INSERT INTO Publicante (tipoPublicante) VALUES (1);
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais,avatar) VALUES (1,'Lars Ulrich','','lars@metallica.com',
	'metallica','9999999','Dinamarca','Dinamarca','img/lars.jpg');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais,avatar) VALUES (2,'James Hetfield','',
	'james@metallica.com','metallica','9999999','California','USA','img/james.jpg');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais,avatar) VALUES (3,'Cliff Burton','',
	'cliff@metallica.com','metallica','9999999','Castro Valley','USA','img/cliff.jpg');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,email,password,fechaNacimiento,ciudad,pais,avatar) VALUES (4,'Kirk Hammett','',
	'kirk@metallica.com','metallica','9999999','San Francisco','USA','img/kirk.jpg');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion,avatar) VALUES (5,'Metallica',1986,
	'Thrash and speed metal band','img/metallica.jpg');
insert into es_integrante (uuid_p,uuid_g) values (1,5);
insert into es_integrante (uuid_p,uuid_g) values (2,5);
insert into es_integrante (uuid_p,uuid_g) values (3,5);
insert into es_integrante (uuid_p,uuid_g) values (4,5);