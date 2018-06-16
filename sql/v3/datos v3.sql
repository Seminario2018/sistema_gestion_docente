delete from CargosDocentes;
delete from Areas where Areas.SubAreaDe is not null;
delete from Areas;
delete from Divisiones;
delete from Docentes;
delete from Personas;
delete from Cargos;
delete from CategoriasInvestigacion;
delete from EstadosCargos;
delete from EstadosDocentes;
delete from EstadosPersonas;
delete from TiposDocumentos;
delete from TiposCargos;
delete from TiposContactos;


insert into TiposCargos values
(0, 'Ordinario'),
(1, 'Interino');


insert into TiposDocumentos values
(0, 'DNI');


insert into TiposContactos values
(0, 'Mail Laboral'),
(1, 'Mail Personal'),
(2, 'Teléfono Laboral'),
(3, 'Teléfono Particular'),
(4, 'Teléfono Celular');


insert into EstadosPersonas values 
(0, 'Activa'),
(1, 'Inactiva');


insert into EstadosDocentes values 
(0, 'Activo'),
(1, 'Inactivo'),
(2, 'Jubilado'),
(3, 'Retiro'),
(4, 'Renuncia'),
(5, 'Fallecido'),
(6, 'Licencia');
    

insert into EstadosCargos values 
(0, 'Activo'),
(1, 'Inactivo');


insert into CategoriasInvestigacion values
(0, 'No categorizado'),
(1, 'I'),
(2, 'II'),
(3, 'III'),
(4, 'IV'),
(5, 'V');
    

insert into Cargos values
(213,'Profesor Titular Exclusiva',40.0),
(214,'Profesor Titular Semiexclusiva',20.0),
(215,'Profesor Titular Simple',10.0),
(216,'Profesor Asociado Exclusiva',40.0),
(217,'Profesor Asociado Semiexclusiva',20.0),
(218,'Profesor Asociado Simple',10.0),
(219,'Profesor Adjunto Exclusiva',0.0),
(220,'Profesor Adjunto Semiexclusiva',0.0),
(221,'Profesor Adjunto Simple',0.0),
(222,'Jefe de Trabajos Prácticos Exclusiva',0.0),
(223,'Jefe de Trabajos Prácticos Semiexclusiva',18.0),
(224,'Jefe de Trabajos Prácticos Simple',9.0),
(225,'Ayudante de Primera Exclusiva',18.0),
(226,'Ayudante de Primera Semiexclusiva',0.0),
(227,'Ayudante de Primera Simple',9.0),
(228,'Ayudante de Segunda',0.0),
(638,'Bedel',0.0),
(1213,'Profesor Titular Ad-Honorem',9.0),
(1218,'Profesor Asociado Ad-Honorem',9.0),
(1221,'Profesor Adjunto Ad-Honorem',9.0),
(1224,'Jefe de Trabajos Prácticos Ad-Honorem',9.0),
(1227,'Ayudante de Primera Ad-Honorem',9.0),
(1228,'Ayudante de Segunda Ad-Honorem',9.0);


# Jefes de División
insert into Personas values 
(0, 9675920, 'Gonzalez', 'Julián', '1945-03-29', 0),
(0, 17200893, 'Cisneros', 'Guillermo', '1967-10-02', 0),
(0, 11242536, 'Urritiaga', 'Daniel Alfonso', '1951-05-12', 0),
(0, 12675920, 'Alcaraz', 'Marcelo Fernando', '1955-11-08', 0),
(0, 16405290, 'Fernandez Perez', 'Julieta', '1964-02-28', 0),
(0, 15603702, 'Aramburu', 'Graciela', '1959-07-10', 0),

# Responsables de Área
(0, 19732040, 'Martínez Mate', 'Laura', '1975-07-04', 0),
(0, 10928479, 'Peralta', 'Ramón', '1949-09-04', 0),
(0, 17875356, 'Mate', 'Cristina', '1971-10-09', 0),
(0, 17645568, 'Manzano', 'María', '1969-02-04', 0),
(0, 12976600, 'Benítez López', 'Pilar Laura', '1955-06-12', 0),
(0, 12506506, 'Del Moral', 'Francisco Guillermo', '1954-12-29', 0),
(0, 19113505, 'Gutiérrez Pérez', 'Óscar', '1975-05-26', 0),
(0, 9849483, 'Lopes Martínez', 'Ana', '1947-09-09', 0),
(0, 9945503, 'Lafuente', 'Dolores', '1945-10-17', 0),
(0, 17112281, 'Montserrat', 'Martín Javier', '1971-05-13', 0),
(0, 13582584, 'Miranda', 'Martín', '1957-09-29', 0),
(0, 18178625, 'Herrero Rubio', 'Nuria', '1974-05-01', 0),
(0, 10071374, 'Lozano Peralta', 'Guillermo Javier', '1950-12-31', 0),
(0, 17457547, 'Peralta Fernández', 'Ezequiel José', '1971-08-13', 0),
(0, 12579600, 'Benítez García', 'Francisco Jorge', '1956-09-04', 0),
(0, 12147287, 'Cabeza Algaba', 'Vicente', '1956-09-09', 0),
(0, 17504187, 'López', 'Adán Antonio', '1971-05-02', 0),
(0, 10284846, 'Castaño', 'Juan', '1949-07-10', 0),
(0, 16647413, 'Sainz Cabeza', 'Dolores Ana', '1966-01-21', 0),
(0, 9834195, 'González', 'Adán', '1947-02-21', 0),
(0, 18373319, 'López Lafuente', 'Martín', '1973-10-27', 0),
(0, 17032691, 'Pérez', 'Óscar', '1969-09-21', 0),
(0, 12329525, 'González Muñoz', 'Jorge Carlos', '1956-08-14', 0),
(0, 18663341, 'Rubio', 'Leonardo', '1974-05-22', 0),
(0, 13522037, 'González Gutiérrez', 'Vicente', '1957-07-30', 0),
(0, 16677483, 'Peralta Castaño', 'Juan Nicolás', '1968-11-03', 0),
(0, 10117993, 'Cabo Benítez', 'Jorge', '1948-08-06', 0),
(0, 11915775, 'Lozano', 'Carmen Rosario', '1951-08-01', 0),
(0, 13959331, 'Sainz Álvarez', 'Leonardo', '1958-02-25', 0),
(0, 16314292, 'Iborra Mate', 'Rosario', '1966-12-31', 0),
(0, 15491157, 'García', 'Julián', '1965-05-11', 0),
(0, 16015748, 'Lozano Benítez', 'Carmen María', '1966-02-11', 0),
(0, 18661346, 'Pérez', 'Rosa', '1972-06-30', 0),
(0, 19751064, 'Peralta García', 'Óscar', '1975-06-30', 0),
(0, 9550991, 'Vázquez Cabeza', 'Carmen Rosario', '1945-01-25', 0),
(0, 17924559, 'Peralta Sánchez', 'Nicolás', '1970-04-09', 0),
(0, 10662538, 'Peralta San Juan', 'Leonardo', '1948-01-11', 0),
(0, 14871880, 'Benítez', 'Elías Adán', '1961-12-06', 0),

# Equipo
(0, 39586150, 'Juran', 'Martín Tomás', '1996-06-29', 0);



insert into Contactos values
# Jefes de División
(0, 0, 9675920, 0, 'semint2018@gmail.com'),
(0, 0, 17200893, 0, 'tomasjuran96@gmail.com'),
(0, 0, 11242536, 0, 'semint2018@gmail.com'),
(0, 0, 12675920, 0, 'semint2018@gmail.com'),
(0, 0, 16405290, 0, 'semint2018@gmail.com'),
(0, 0, 15603702, 0, 'semint2018@gmail.com');



# Jefes de División
insert into Docentes values 
(469, 0, 9675920, 'Jefe de la división Biología', 1, 0),
(94, 0, 17200893, 'Jefe de la división Computación', 1, 0),
(107, 0, 11242536, 'Jefe de la división Estadísticas y Sistemas', 1, 0),
(480, 0, 12675920, 'Jefe de la división Física', 1, 0),
(57, 0, 16405290, 'Jefa de la división Matemática', 1, 0),
(104, 0, 15603702, 'Jefa de la división Química', 1, 0),

# Responsables de Área
(3097, 0, 19732040, 'Observaciones de Martínez Mate', 1, 0),
(427, 0, 10928479, 'Observaciones de Peralta', 1, 0),
(2675, 0, 17875356, 'Observaciones de Mate', 1, 0),
(2459, 0, 17645568, 'Observaciones de Manzano', 1, 0),
(1027, 0, 12976600, 'Observaciones de Benítez López', 1, 0),
(948, 0, 12506506, 'Observaciones de Del Moral', 1, 0),
(3043, 0, 19113505, 'Observaciones de Gutiérrez Pérez', 1, 0),
(257, 0, 9849483, 'Observaciones de Lopes Martínez', 1, 0),
(268, 0, 9945503, 'Observaciones de Lafuente', 1, 0),
(2609, 0, 17112281, 'Observaciones de Montserrat', 1, 0),
(1229, 0, 13582584, 'Observaciones de Miranda', 1, 0),
(2924, 0, 18178625, 'Observaciones de Herrero Rubio', 1, 0),
(521, 0, 10071374, 'Observaciones de Lozano Peralta', 1, 0),
(2653, 0, 17457547, 'Observaciones de Peralta Fernández', 1, 0),
(1193, 0, 12579600, 'Observaciones de Benítez García', 1, 0),
(1153, 0, 12147287, 'Observaciones de Cabeza Algaba', 1, 0),
(2647, 0, 17504187, 'Observaciones de López', 1, 0),
(467, 0, 10284846, 'Observaciones de Castaño', 1, 0),
(2125, 0, 16647413, 'Observaciones de Sainz Cabeza', 1, 0),
(218, 0, 9834195, 'Observaciones de González', 1, 0),
(2859, 0, 18373319, 'Observaciones de López Lafuente', 1, 0),
(2407, 0, 17032691, 'Observaciones de Pérez', 1, 0),
(1108, 0, 12329525, 'Observaciones de González Muñoz', 1, 0),
(2974, 0, 18663341, 'Observaciones de Rubio', 1, 0),
(1221, 0, 13522037, 'Observaciones de González Gutiérrez', 1, 0),
(2330, 0, 16677483, 'Observaciones de Peralta Castaño', 1, 0),
(318, 0, 10117993, 'Observaciones de Cabo Benítez', 1, 0),
(671, 0, 11915775, 'Observaciones de Lozano', 1, 0),
(1358, 0, 13959331, 'Observaciones de Sainz Álvarez', 1, 0),
(2194, 0, 16314292, 'Observaciones de Iborra Mate', 1, 0),
(2067, 0, 15491157, 'Observaciones de García', 1, 0),
(2176, 0, 16015748, 'Observaciones de Lozano Benítez', 1, 0),
(2786, 0, 18661346, 'Observaciones de Pérez', 1, 0),
(3049, 0, 19751064, 'Observaciones de Peralta García', 1, 0),
(81, 0, 9550991, 'Observaciones de Vázquez Cabeza', 1, 0),
(2592, 0, 17924559, 'Observaciones de Peralta Sánchez', 1, 0),
(325, 0, 10662538, 'Observaciones de Peralta San Juan', 1, 0),
(1640, 0, 14871880, 'Observaciones de Benítez', 1, 0),

# Equipo
(143191, 0, 39586150, 'Observaciones de Juran', 1, 0);


insert into Divisiones values
('BI','Biología',469,'DD 04/2006','2006-02-14','2009-12-20'),
('CO','Computación',94,'DD 13/2012','2006-02-14','2009-12-20'),
('ES','Estadísticas y Sistemas',107,'DD 02/2006','2006-02-14','2009-12-20'),
('FI','Física',480,'DD 03/2006','2006-02-14','2009-12-20'),
('MA','Matemática',57,'DD 05/2006','2006-02-14','2009-12-20'),
('QU','Química',104,'DD 06/2006','2006-03-01','2009-12-20');


insert into Areas values
('BI.01.00','Ecología','BI',3097,'506/12','2001-12-31','2013-12-31',null),
('BI.01.01','Ecología Básica','BI',427,'','2001-12-31','2001-12-31','BI.01.00'),
('BI.01.02','Ecología Avanzada','BI',2675,'','2001-12-31','2001-12-31','BI.01.00'),
('BI.02.00','Biología General','BI',2459,'506/12','2001-12-31','2013-12-31',null),
('BI.03.00','Biología  Molecular y Microbiología','BI',1027,'506/12','2001-12-31','2013-12-31',null),
('BI.03.01','Biología  Celular y Molecular','BI',948,'506/12','2001-12-31','2013-12-31','BI.03.00'),
('BI.03.02','Microbiología','BI',3043,'','2001-12-31','2001-12-31','BI.03.00'),
('BI.04.00','Genética y Evolución','BI',257,'506/12','2001-12-31','2013-12-31',null),
('BI.05.00','Biología Vegetal','BI',268,'','2001-12-31','2013-12-31',null),
('BI.06.00','Biología Animal','BI',2609,'506/12','2001-12-31','2013-12-31',null),
('BI.06.01','Biología Animal Estructural','BI',1229,'','2001-12-31','2001-12-31','BI.06.00'),
('BI.06.02','anulada','BI',2924,'','2001-12-31','2001-12-31','BI.06.00'),
('BI.06.03','Fisiología','BI',521,'','2001-12-31','2001-12-31','BI.06.00'),
('CO.01.00','Algoritmos y Lenguajes','CO',2653,'506/12','2001-12-31','2013-12-31',null),
('CO.02.00','Arquitectura,Sistemas Operativos y Redes','CO',1193,'506/12','2001-12-31','2013-12-31',null),
('CO.03.00','Ing.de Soft, Base de Dat. y Sist de Inf.','CO',1153,'506/12','2012-05-10','2013-12-31',null),
('CO.03.01','Sistema de Información ','CO',2647,'','2001-12-31','2001-12-31','CO.03.00'),
('CO.03.02','Base de Datos','CO',467,'','2012-05-10','2001-12-31','CO.03.00'),
('CO.03.03','Proyectos de Investigación','CO',2125,'','2012-05-10','2001-12-31','CO.03.00'),
('CO.04.00','Teoría de la Computación','CO',218,'506/12','2001-12-31','2013-12-31',null),
('CO.05.00','Computación Aplicada','CO',2859,'506/12','2001-12-31','2013-12-31',null),
('CO.06.00','Informática','CO',2407,'506/12','2001-12-31','2013-12-31',null),
('ES.01.00','Estadística Socioeconómica','ES',1108,'506/12','2001-12-31','2013-12-31',null),
('ES.02.00','Bioestadística','ES',2974,'506/12','2001-12-31','2013-12-31',null),
('ES.03.00','Muestreo y Control de Procesos','ES',1221,'506/12','2001-12-31','2013-12-31',null),
('ES.04.00','Modelos Avanzados Metod Cuali-Cuanti del','ES',2330,'506/12','2001-12-31','2013-12-31',null),
('ES.05.00','Informática Básica','ES',318,'','2001-12-31','2001-12-31',null),
('ES.06.00','Teoría de la Computación','ES',671,'CDDCB 111/2009','2009-04-01','2009-12-31',null),
('ES.07.00','Biometría','ES',1358,'506/12','2001-12-31','2013-12-31',null),
('FI.01.00','Física Básica','FI',2194,'506/12','2001-12-31','2013-12-31',null),
('FI.02.00','Física Aplicada','FI',2067,'506/12','2001-12-31','2013-12-31',null),
('MA.01.00','Matemática en las Ciencias Básicas','MA',2176,'CDDCB 041/2009','2009-02-26','2014-11-06',null),
('MA.02.00','Matemática en las Ciencias Económicas ','MA',2786,'506/12','2001-12-31','2013-12-31',null),
('MA.03.00','Mat. en la Agronomía y las Cs Soc. y Hum','MA',3049,'506/12','2001-12-31','2012-12-31',null),
('QU.01.00','Química General e Inorgánica','QU',81,'506/12','2001-12-31','2013-12-31',null),
('QU.02.00','Química Orgánica','QU',2592,'506/12','2001-12-31','2013-12-31',null),
('QU.03.00','Química Biológica','QU',325,'506/12','2001-12-31','2013-12-31',null),
('QU.04.00','Química Analítica','QU',1640,'506/12','2001-12-31','2013-12-31',null);


insert into CargosDocentes values
(1, 143191, 'CO.01.00', 228, 0, '507/18', null, null, null, null, null, null, 4000.0, '2018-06-1');