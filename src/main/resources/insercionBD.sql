INSERT INTO Publicante (email,tipoPublicante) VALUES ('foo@bar.com',0);
INSERT INTO Publicante (email,tipoPublicante) VALUES ('coso@cosito.com',0);
INSERT INTO Publicante (email,tipoPublicante) VALUES ('herp@derp.com',0);
INSERT INTO Publicante (email,tipoPublicante) VALUES ('prueba1@g.com',1);
INSERT INTO Publicante (email,tipoPublicante) VALUES ('prueba2@g.com',1);
INSERT INTO Publicante (email,tipoPublicante) VALUES ('prueba3@g.com',1);
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,password,fechaNacimiento,ciudad,pais) VALUES (1,'Foo','Bar','1234','1992-03-01','Zaragoza','Spain');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,password,fechaNacimiento,ciudad,pais) VALUES (2,'Manolo','el del bombo','1234','1991-03-01','Huesca','Spain');
INSERT INTO Persona (Publicante_UUID,nombre,apellidos,password,fechaNacimiento,ciudad,pais) VALUES (3,'Ana','Gomez','1234','1993-03-01','Teruel','Spain');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion) VALUES (4,'Arctic Monkeys',2001,'Grupo entre el rock y el indie buscando su lugar en el mundo de la musica');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion) VALUES (5,'Foo Fighters',2000,'Somos unos chicos que estan empezando');
INSERT INTO Grupo (Publicante_UUID,nombre,anyoFundacion,descripcion) VALUES (6,'Los solfamidas',1992,'Barney tiene un vozarron');
   