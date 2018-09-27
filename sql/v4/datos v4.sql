-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: plumasdocentes
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `areas`
--

LOCK TABLES `areas` WRITE;
/*!40000 ALTER TABLE `areas` DISABLE KEYS */;
INSERT INTO `areas` VALUES ('BI.01.00','Ecología','BI',3097,'506/12','2001-12-31','2013-12-31',NULL),('BI.01.01','Ecología Básica','BI',427,'','2001-12-31','2001-12-31','BI.01.00'),('BI.01.02','Ecología Avanzada','BI',2675,'','2001-12-31','2001-12-31','BI.01.00'),('BI.02.00','Biología General','BI',2459,'506/12','2001-12-31','2013-12-31',NULL),('BI.03.00','Biología  Molecular y Microbiología','BI',1027,'506/12','2001-12-31','2013-12-31',NULL),('BI.03.01','Biología  Celular y Molecular','BI',948,'506/12','2001-12-31','2013-12-31','BI.03.00'),('BI.03.02','Microbiología','BI',3043,'','2001-12-31','2001-12-31','BI.03.00'),('BI.04.00','Genética y Evolución','BI',257,'506/12','2001-12-31','2013-12-31',NULL),('BI.05.00','Biología Vegetal','BI',268,'','2001-12-31','2013-12-31',NULL),('BI.06.00','Biología Animal','BI',2609,'506/12','2001-12-31','2013-12-31',NULL),('BI.06.01','Biología Animal Estructural','BI',1229,'','2001-12-31','2001-12-31','BI.06.00'),('BI.06.02','anulada','BI',2924,'','2001-12-31','2001-12-31','BI.06.00'),('BI.06.03','Fisiología','BI',521,'','2001-12-31','2001-12-31','BI.06.00'),('CO.01.00','Algoritmos y Lenguajes','CO',2653,'506/12','2001-12-31','2013-12-31',NULL),('CO.02.00','Arquitectura,Sistemas Operativos y Redes','CO',1193,'506/12','2001-12-31','2013-12-31',NULL),('CO.03.00','Ing.de Soft, Base de Dat. y Sist de Inf.','CO',1153,'506/12','2012-05-10','2013-12-31',NULL),('CO.03.01','Sistema de Información ','CO',2647,'','2001-12-31','2001-12-31','CO.03.00'),('CO.03.02','Base de Datos','CO',467,'','2012-05-10','2001-12-31','CO.03.00'),('CO.03.03','Proyectos de Investigación','CO',2125,'','2012-05-10','2001-12-31','CO.03.00'),('CO.04.00','Teoría de la Computación','CO',218,'506/12','2001-12-31','2013-12-31',NULL),('CO.05.00','Computación Aplicada','CO',2859,'506/12','2001-12-31','2013-12-31',NULL),('CO.06.00','Informática','CO',2407,'506/12','2001-12-31','2013-12-31',NULL),('ES.01.00','Estadística Socioeconómica','ES',1108,'506/12','2001-12-31','2013-12-31',NULL),('ES.02.00','Bioestadística','ES',2974,'506/12','2001-12-31','2013-12-31',NULL),('ES.03.00','Muestreo y Control de Procesos','ES',1221,'506/12','2001-12-31','2013-12-31',NULL),('ES.04.00','Modelos Avanzados Metod Cuali-Cuanti del','ES',2330,'506/12','2001-12-31','2013-12-31',NULL),('ES.05.00','Informática Básica','ES',318,'','2001-12-31','2001-12-31',NULL),('ES.06.00','Teoría de la Computación','ES',671,'CDDCB 111/2009','2009-04-01','2009-12-31',NULL),('ES.07.00','Biometría','ES',1358,'506/12','2001-12-31','2013-12-31',NULL),('FI.01.00','Física Básica','FI',2194,'506/12','2001-12-31','2013-12-31',NULL),('FI.02.00','Física Aplicada','FI',2067,'506/12','2001-12-31','2013-12-31',NULL),('MA.01.00','Matemática en las Ciencias Básicas','MA',2176,'CDDCB 041/2009','2009-02-26','2014-11-06',NULL),('MA.02.00','Matemática en las Ciencias Económicas ','MA',2786,'506/12','2001-12-31','2013-12-31',NULL),('MA.03.00','Mat. en la Agronomía y las Cs Soc. y Hum','MA',3049,'506/12','2001-12-31','2012-12-31',NULL),('QU.01.00','Química General e Inorgánica','QU',81,'506/12','2001-12-31','2013-12-31',NULL),('QU.02.00','Química Orgánica','QU',2592,'506/12','2001-12-31','2013-12-31',NULL),('QU.03.00','Química Biológica','QU',325,'506/12','2001-12-31','2013-12-31',NULL),('QU.04.00','Química Analítica','QU',1640,'506/12','2001-12-31','2013-12-31',NULL);
/*!40000 ALTER TABLE `areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `asignaturas`
--

LOCK TABLES `asignaturas` WRITE;
/*!40000 ALTER TABLE `asignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `asignaturasxcarreras`
--

LOCK TABLES `asignaturasxcarreras` WRITE;
/*!40000 ALTER TABLE `asignaturasxcarreras` DISABLE KEYS */;
/*!40000 ALTER TABLE `asignaturasxcarreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cargos`
--

LOCK TABLES `cargos` WRITE;
/*!40000 ALTER TABLE `cargos` DISABLE KEYS */;
INSERT INTO `cargos` VALUES (213,'Profesor Titular Exclusiva',40),(214,'Profesor Titular Semiexclusiva',20),(215,'Profesor Titular Simple',10),(216,'Profesor Asociado Exclusiva',40),(217,'Profesor Asociado Semiexclusiva',20),(218,'Profesor Asociado Simple',10),(219,'Profesor Adjunto Exclusiva',36),(220,'Profesor Adjunto Semiexclusiva',18),(221,'Profesor Adjunto Simple',9),(222,'Jefe de Trabajos Prácticos Exclusiva',36),(223,'Jefe de Trabajos Prácticos Semiexclusiva',18),(224,'Jefe de Trabajos Prácticos Simple',9),(225,'Ayudante de Primera Exclusiva',18),(226,'Ayudante de Primera Semiexclusiva',14),(227,'Ayudante de Primera Simple',9),(228,'Ayudante de Segunda',6),(638,'Bedel',4),(1213,'Profesor Titular Ad-Honorem',9),(1218,'Profesor Asociado Ad-Honorem',9),(1221,'Profesor Adjunto Ad-Honorem',9),(1224,'Jefe de Trabajos Prácticos Ad-Honorem',9),(1227,'Ayudante de Primera Ad-Honorem',9),(1228,'Ayudante de Segunda Ad-Honorem',9);
/*!40000 ALTER TABLE `cargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cargosdocentes`
--

LOCK TABLES `cargosdocentes` WRITE;
/*!40000 ALTER TABLE `cargosdocentes` DISABLE KEYS */;
INSERT INTO `cargosdocentes` VALUES (64,2407,'ES.04.00',1213,0,'507/29',NULL,NULL,NULL,NULL,NULL,21585.00,'2018-09-01',0),(65,2459,'ES.05.00',1218,0,'507/26',NULL,NULL,NULL,NULL,NULL,12596.66,'2018-09-01',0),(66,2459,'ES.06.00',1221,0,'507/31',NULL,NULL,NULL,NULL,NULL,12596.66,'2018-09-01',0),(67,2592,'ES.07.00',1224,0,'507/33',NULL,NULL,NULL,NULL,NULL,29216.22,'2018-09-01',0),(68,2592,'FI.01.00',1227,0,'507/32',NULL,NULL,NULL,NULL,NULL,50387.44,'2018-09-01',0),(69,2609,'FI.02.00',1228,0,'507/27',NULL,NULL,NULL,NULL,NULL,14607.89,'2018-09-01',0),(70,2609,'MA.01.00',213,0,'507/28',NULL,NULL,NULL,NULL,NULL,56382.22,'2018-09-01',0),(71,2647,'MA.02.00',214,0,'507/29',NULL,NULL,NULL,NULL,NULL,14095.33,'2018-09-01',0),(72,2647,'MA.03.00',215,0,'507/30',NULL,NULL,NULL,NULL,NULL,28191.09,'2018-09-01',0),(73,2653,'QU.01.00',216,0,'507/27',NULL,NULL,NULL,NULL,NULL,24309.69,'2018-09-01',0),(74,2653,'QU.02.00',217,0,'507/32',NULL,NULL,NULL,NULL,NULL,14095.33,'2018-09-01',0),(75,2675,'QU.03.00',218,0,'507/34',NULL,NULL,NULL,NULL,NULL,26909.67,'2018-09-01',0),(76,2675,'QU.04.00',219,0,'507/33',NULL,NULL,NULL,NULL,NULL,14095.33,'2018-09-01',0),(77,2786,'BI.01.00',220,0,'507/28',NULL,NULL,NULL,NULL,NULL,55869.65,'2018-09-01',0),(78,2786,'BI.01.01',221,0,'507/29',NULL,NULL,NULL,NULL,NULL,13967.20,'2018-09-01',0),(79,2859,'BI.01.02',222,0,'507/30',NULL,NULL,NULL,NULL,NULL,9749.73,'2018-09-01',0),(80,2859,'BI.02.00',223,0,'507/31',NULL,NULL,NULL,NULL,NULL,58432.48,'2018-09-01',0),(81,2924,'BI.03.00',224,0,'507/28',NULL,NULL,NULL,NULL,NULL,24309.69,'2018-09-01',0),(82,2924,'BI.03.01',225,0,'507/33',NULL,NULL,NULL,NULL,NULL,12154.67,'2018-09-01',0),(83,2974,'BI.03.02',226,0,'507/35',NULL,NULL,NULL,NULL,NULL,24309.69,'2018-09-01',0),(84,2974,'BI.04.00',227,0,'507/34',NULL,NULL,NULL,NULL,NULL,7428.37,'2018-09-01',0),(85,3043,'BI.05.00',228,0,'507/29',NULL,NULL,NULL,NULL,NULL,13454.64,'2018-09-01',0),(86,3043,'BI.06.00',638,0,'507/30',NULL,NULL,NULL,NULL,NULL,43171.00,'2018-09-01',0),(87,3049,'BI.06.01',1213,0,'507/31',NULL,NULL,NULL,NULL,NULL,49334.71,'2018-09-01',0),(88,3049,'BI.06.02',1218,0,'507/32',NULL,NULL,NULL,NULL,NULL,56382.22,'2018-09-01',0),(89,3097,'BI.06.03',1221,0,'507/29',NULL,NULL,NULL,NULL,NULL,18571.20,'2018-09-01',0),(90,3097,'CO.01.00',1224,0,'507/34',NULL,NULL,NULL,NULL,NULL,24309.69,'2018-09-01',0),(91,143191,'CO.02.00',1227,0,'507/36',NULL,NULL,NULL,NULL,NULL,9749.73,'2018-09-01',0),(92,143191,'CO.03.00',1228,0,'507/35',NULL,NULL,NULL,NULL,NULL,51256.56,'2018-09-01',0),(93,143112,'CO.03.01',1228,0,'507/30',NULL,NULL,NULL,NULL,NULL,12813.94,'2018-09-01',1),(100,143191,'CO.01.00',228,0,'507/18',NULL,NULL,NULL,NULL,NULL,9700.00,'2018-09-01',0),(102,1640,'QU.04.00',214,0,'507/23',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-09-01',0),(121,1221,'ES.03.00',213,0,'507/25',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-09-01',0),(500,2176,'MA.01.00',222,0,'507/24',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-09-01',0),(1301,143112,'FI.02.00',228,0,'507/19',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-09-01',0),(1400,1640,'QU.04.00',214,0,'507/20',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-09-01',0),(1500,2176,'MA.01.00',224,0,'507/21',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-09-01',0),(2000,1221,'ES.03.00',224,0,'507/22',NULL,NULL,NULL,NULL,NULL,4000.00,'2018-06-01',1),(2002,94,'ES.06.00',213,0,NULL,NULL,NULL,NULL,NULL,NULL,0.00,NULL,1),(2003,143191,'BI.03.00',228,0,'578/18','2018-08-09',NULL,'578/18','2018-08-16',NULL,6758.21,'2018-08-24',1),(2004,104,'BI.03.01',219,0,'824/18','2018-09-07',NULL,'826/18','2018-09-13',NULL,12590.10,'2018-09-13',0),(2005,57,'BI.04.00',225,0,NULL,NULL,NULL,NULL,NULL,NULL,24005.20,'2018-09-13',0);
/*!40000 ALTER TABLE `cargosdocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cargosfaltantes`
--

LOCK TABLES `cargosfaltantes` WRITE;
/*!40000 ALTER TABLE `cargosfaltantes` DISABLE KEYS */;
INSERT INTO `cargosfaltantes` VALUES (1,'Lopez','Julio',200,14559.61,'2018-09-02',0),(10,'Cabo Benítez','Jorge',1,14095.33,'2018-08-01',0),(10,'Cabo Benítez','Jorge',1,14095.33,'2018-08-25',0),(10,'Cabo Benítez','Jorge',2,77791.13,'2018-08-01',0),(10,'Cabo Benítez','Jorge',2,77791.13,'2018-08-25',0),(57,'Fernandez Perez','Julieta',3,28191.09,'2018-08-01',0),(57,'Fernandez Perez','Julieta',3,28191.09,'2018-08-25',0),(57,'Fernandez Perez','Julieta',4,15248.58,'2018-08-01',0),(57,'Fernandez Perez','Julieta',4,15248.58,'2018-08-25',0),(57,'Fernandez Perez','Julieta',94,15248.58,'2018-06-23',0),(81,'Vázquez Cabeza','Carmen Rosario',5,28191.09,'2018-08-01',0),(81,'Vázquez Cabeza','Carmen Rosario',5,28191.09,'2018-08-25',0),(81,'Vázquez Cabeza','Carmen Rosario',6,24309.69,'2018-08-01',0),(81,'Vázquez Cabeza','Carmen Rosario',6,24309.69,'2018-08-25',0),(81,'Vázquez Cabeza','Carmen Rosario',95,28191.09,'2018-06-23',0),(81,'Vázquez Cabeza','Carmen Rosario',96,24309.69,'2018-06-23',0),(94,'Cisneros','Guillermo',7,52597.41,'2018-08-01',0),(94,'Cisneros','Guillermo',7,52597.41,'2018-08-25',0),(94,'Cisneros','Guillermo',8,15248.58,'2018-08-01',0),(94,'Cisneros','Guillermo',8,15248.58,'2018-08-25',0),(94,'Cisneros','Guillermo',97,52597.41,'2018-06-23',0),(94,'Cisneros','Guillermo',98,15248.58,'2018-06-23',0),(94,'Cisneros','Guillermo',2002,0.00,'2018-08-01',1),(94,'Cisneros','Guillermo',2002,0.00,'2018-08-25',1),(104,'Aramburu','Graciela',9,15248.58,'2018-08-01',0),(104,'Aramburu','Graciela',9,15248.58,'2018-08-25',0),(104,'Aramburu','Graciela',10,26298.66,'2018-06-23',0),(104,'Aramburu','Graciela',10,26298.66,'2018-08-01',0),(104,'Aramburu','Graciela',10,26298.66,'2018-08-25',0),(104,'Aramburu','Graciela',99,15248.58,'2018-06-23',0),(107,'Urritiaga','Daniel Alfonso',11,32072.46,'2018-06-23',0),(107,'Urritiaga','Daniel Alfonso',11,32072.46,'2018-08-01',0),(107,'Urritiaga','Daniel Alfonso',11,32072.46,'2018-08-25',0),(107,'Urritiaga','Daniel Alfonso',12,48619.45,'2018-06-23',0),(107,'Urritiaga','Daniel Alfonso',12,48619.45,'2018-08-01',0),(107,'Urritiaga','Daniel Alfonso',12,48619.45,'2018-08-25',0),(218,'González','Adán',13,14095.33,'2018-06-23',0),(218,'González','Adán',13,14095.33,'2018-08-01',0),(218,'González','Adán',13,14095.33,'2018-08-25',0),(218,'González','Adán',14,56382.22,'2018-06-23',0),(218,'González','Adán',14,56382.22,'2018-08-01',0),(218,'González','Adán',14,56382.22,'2018-08-25',0),(257,'Lopes Martínez','Ana',15,14095.33,'2018-06-23',0),(257,'Lopes Martínez','Ana',15,14095.33,'2018-08-01',0),(257,'Lopes Martínez','Ana',15,14095.33,'2018-08-25',0),(257,'Lopes Martínez','Ana',16,14095.33,'2018-06-23',0),(257,'Lopes Martínez','Ana',16,14095.33,'2018-08-01',0),(257,'Lopes Martínez','Ana',16,14095.33,'2018-08-25',0),(268,'Lafuente','Dolores',17,56382.22,'2018-06-23',0),(268,'Lafuente','Dolores',17,56382.22,'2018-08-01',0),(268,'Lafuente','Dolores',17,56382.22,'2018-08-25',0),(268,'Lafuente','Dolores',18,56382.22,'2018-06-23',0),(268,'Lafuente','Dolores',18,56382.22,'2018-08-01',0),(268,'Lafuente','Dolores',18,56382.22,'2018-08-25',0),(318,'Cabo Benítez','Jorge',19,18784.76,'2018-06-23',0),(318,'Cabo Benítez','Jorge',19,18784.76,'2018-08-01',0),(318,'Cabo Benítez','Jorge',19,18784.76,'2018-08-25',0),(318,'Cabo Benítez','Jorge',20,28191.09,'2018-06-23',0),(318,'Cabo Benítez','Jorge',20,28191.09,'2018-08-01',0),(318,'Cabo Benítez','Jorge',20,28191.09,'2018-08-25',0),(325,'Peralta San Juan','Leonardo',21,10214.01,'2018-06-23',0),(325,'Peralta San Juan','Leonardo',21,10214.01,'2018-08-01',0),(325,'Peralta San Juan','Leonardo',21,10214.01,'2018-08-25',0),(325,'Peralta San Juan','Leonardo',22,56382.22,'2018-06-23',0),(325,'Peralta San Juan','Leonardo',22,56382.22,'2018-08-01',0),(325,'Peralta San Juan','Leonardo',22,56382.22,'2018-08-25',0),(427,'Peralta','Ramón',23,14095.33,'2018-06-23',0),(427,'Peralta','Ramón',23,14095.33,'2018-08-01',0),(427,'Peralta','Ramón',23,14095.33,'2018-08-25',0),(427,'Peralta','Ramón',24,49724.44,'2018-06-23',0),(427,'Peralta','Ramón',24,49724.44,'2018-08-01',0),(427,'Peralta','Ramón',24,49724.44,'2018-08-25',0),(467,'Castaño','Juan',25,12430.91,'2018-06-23',0),(467,'Castaño','Juan',25,12430.91,'2018-08-01',0),(467,'Castaño','Juan',25,12430.91,'2018-08-25',0),(467,'Castaño','Juan',26,24309.69,'2018-06-23',0),(467,'Castaño','Juan',26,24309.69,'2018-08-01',0),(467,'Castaño','Juan',26,24309.69,'2018-08-25',0),(469,'Gonzalez','Julián',27,28191.09,'2018-06-23',0),(469,'Gonzalez','Julián',27,28191.09,'2018-08-01',0),(469,'Gonzalez','Julián',27,28191.09,'2018-08-25',0),(469,'Gonzalez','Julián',28,24309.69,'2018-06-23',0),(469,'Gonzalez','Julián',28,24309.69,'2018-08-01',0),(469,'Gonzalez','Julián',28,24309.69,'2018-08-25',0),(480,'Alcaraz','Marcelo Fernando',29,12154.67,'2018-06-23',0),(480,'Alcaraz','Marcelo Fernando',29,12154.67,'2018-08-01',0),(480,'Alcaraz','Marcelo Fernando',29,12154.67,'2018-08-25',0),(480,'Alcaraz','Marcelo Fernando',30,24309.69,'2018-06-23',0),(480,'Alcaraz','Marcelo Fernando',30,24309.69,'2018-08-01',0),(480,'Alcaraz','Marcelo Fernando',30,24309.69,'2018-08-25',0),(521,'Lozano Peralta','Guillermo Javier',31,24309.69,'2018-06-23',0),(521,'Lozano Peralta','Guillermo Javier',31,24309.69,'2018-08-01',0),(521,'Lozano Peralta','Guillermo Javier',31,24309.69,'2018-08-25',0),(521,'Lozano Peralta','Guillermo Javier',32,60995.31,'2018-06-23',0),(521,'Lozano Peralta','Guillermo Javier',32,60995.31,'2018-08-01',0),(521,'Lozano Peralta','Guillermo Javier',32,60995.31,'2018-08-25',0),(671,'Lozano','Carmen Rosario',33,7037.00,'2018-06-23',0),(671,'Lozano','Carmen Rosario',33,7037.00,'2018-08-01',0),(671,'Lozano','Carmen Rosario',33,7037.00,'2018-08-25',0),(671,'Lozano','Carmen Rosario',34,14095.33,'2018-06-23',0),(671,'Lozano','Carmen Rosario',34,14095.33,'2018-08-01',0),(671,'Lozano','Carmen Rosario',34,14095.33,'2018-08-25',0),(948,'Del Moral','Francisco Guillermo',35,14095.33,'2018-06-23',0),(948,'Del Moral','Francisco Guillermo',35,14095.33,'2018-08-01',0),(948,'Del Moral','Francisco Guillermo',35,14095.33,'2018-08-25',0),(948,'Del Moral','Francisco Guillermo',36,12154.67,'2018-06-23',0),(948,'Del Moral','Francisco Guillermo',36,12154.67,'2018-08-01',0),(948,'Del Moral','Francisco Guillermo',36,12154.67,'2018-08-25',0),(1027,'Benítez López','Pilar Laura',37,48619.45,'2018-06-23',0),(1027,'Benítez López','Pilar Laura',37,48619.45,'2018-08-01',0),(1027,'Benítez López','Pilar Laura',37,48619.45,'2018-08-25',0),(1027,'Benítez López','Pilar Laura',38,7428.37,'2018-06-23',0),(1027,'Benítez López','Pilar Laura',38,7428.37,'2018-08-01',0),(1027,'Benítez López','Pilar Laura',38,7428.37,'2018-08-25',0),(1108,'González Muñoz','Jorge Carlos',39,56382.22,'2018-06-23',0),(1108,'González Muñoz','Jorge Carlos',39,56382.22,'2018-08-01',0),(1108,'González Muñoz','Jorge Carlos',39,56382.22,'2018-08-25',0),(1108,'González Muñoz','Jorge Carlos',40,20428.32,'2018-06-23',0),(1108,'González Muñoz','Jorge Carlos',40,20428.32,'2018-08-01',0),(1108,'González Muñoz','Jorge Carlos',40,20428.32,'2018-08-25',0),(1153,'Cabeza Algaba','Vicente',41,28191.09,'2018-06-23',0),(1153,'Cabeza Algaba','Vicente',41,28191.09,'2018-08-01',0),(1153,'Cabeza Algaba','Vicente',41,28191.09,'2018-08-25',0),(1153,'Cabeza Algaba','Vicente',42,24309.69,'2018-06-23',0),(1153,'Cabeza Algaba','Vicente',42,24309.69,'2018-08-01',0),(1153,'Cabeza Algaba','Vicente',42,24309.69,'2018-08-25',0),(1193,'Benítez García','Francisco Jorge',43,28191.09,'2018-06-23',0),(1193,'Benítez García','Francisco Jorge',43,28191.09,'2018-08-01',0),(1193,'Benítez García','Francisco Jorge',43,28191.09,'2018-08-25',0),(1193,'Benítez García','Francisco Jorge',44,48619.45,'2018-06-23',0),(1193,'Benítez García','Francisco Jorge',44,48619.45,'2018-08-01',0),(1193,'Benítez García','Francisco Jorge',44,48619.45,'2018-08-25',0),(1221,'González Gutiérrez','Vicente',45,26298.66,'2018-06-23',0),(1221,'González Gutiérrez','Vicente',45,26298.66,'2018-08-01',0),(1221,'González Gutiérrez','Vicente',45,26298.66,'2018-08-25',0),(1221,'González Gutiérrez','Vicente',46,14095.33,'2018-06-23',0),(1221,'González Gutiérrez','Vicente',46,14095.33,'2018-08-01',0),(1221,'González Gutiérrez','Vicente',46,14095.33,'2018-08-25',0),(1221,'González Gutiérrez','Vicente',121,4000.00,'2018-06-23',1),(1221,'González Gutiérrez','Vicente',121,4000.00,'2018-09-02',0),(1221,'González Gutiérrez','Vicente',2000,4000.00,'2018-06-23',1),(1229,'Miranda','Martín',47,20428.32,'2018-06-23',0),(1229,'Miranda','Martín',47,20428.32,'2018-08-01',0),(1229,'Miranda','Martín',47,20428.32,'2018-08-25',0),(1229,'Miranda','Martín',48,20428.32,'2018-06-23',0),(1229,'Miranda','Martín',48,20428.32,'2018-08-01',0),(1229,'Miranda','Martín',48,20428.32,'2018-08-25',0),(1358,'Sainz Álvarez','Leonardo',49,20428.32,'2018-06-23',0),(1358,'Sainz Álvarez','Leonardo',49,20428.32,'2018-08-01',0),(1358,'Sainz Álvarez','Leonardo',49,20428.32,'2018-08-25',0),(1358,'Sainz Álvarez','Leonardo',50,24309.69,'2018-06-23',0),(1358,'Sainz Álvarez','Leonardo',50,24309.69,'2018-08-01',0),(1358,'Sainz Álvarez','Leonardo',50,24309.69,'2018-08-25',0),(1640,'Benítez','Elías Adán',51,48619.45,'2018-06-23',0),(1640,'Benítez','Elías Adán',51,48619.45,'2018-08-01',0),(1640,'Benítez','Elías Adán',51,48619.45,'2018-08-25',0),(1640,'Benítez','Elías Adán',52,12154.67,'2018-06-23',0),(1640,'Benítez','Elías Adán',52,12154.67,'2018-08-01',0),(1640,'Benítez','Elías Adán',52,12154.67,'2018-08-25',0),(1640,'Benítez','Elías Adán',102,51256.60,'2018-08-01',1),(1640,'Benítez','Elías Adán',102,51256.60,'2018-08-25',1),(1640,'Benítez','Elías Adán',102,4000.00,'2018-09-02',0),(1640,'Benítez','Elías Adán',1400,4000.00,'2018-06-23',1),(1640,'Benítez','Elías Adán',1400,4000.00,'2018-09-02',0),(2067,'García','Julián',53,28191.09,'2018-06-23',0),(2067,'García','Julián',53,28191.09,'2018-08-01',0),(2067,'García','Julián',53,28191.09,'2018-08-25',0),(2067,'García','Julián',54,10214.01,'2018-06-23',0),(2067,'García','Julián',54,10214.01,'2018-08-01',0),(2067,'García','Julián',54,10214.01,'2018-08-25',0),(2125,'Sainz Cabeza','Dolores Ana',55,14095.33,'2018-06-23',0),(2125,'Sainz Cabeza','Dolores Ana',55,14095.33,'2018-08-01',0),(2125,'Sainz Cabeza','Dolores Ana',55,14095.33,'2018-08-25',0),(2125,'Sainz Cabeza','Dolores Ana',56,28191.09,'2018-06-23',0),(2125,'Sainz Cabeza','Dolores Ana',56,28191.09,'2018-08-01',0),(2125,'Sainz Cabeza','Dolores Ana',56,28191.09,'2018-08-25',0),(2176,'Lozano Benítez','Carmen María',57,14095.33,'2018-06-23',0),(2176,'Lozano Benítez','Carmen María',57,14095.33,'2018-08-01',0),(2176,'Lozano Benítez','Carmen María',57,14095.33,'2018-08-25',0),(2176,'Lozano Benítez','Carmen María',58,56382.22,'2018-06-23',0),(2176,'Lozano Benítez','Carmen María',58,56382.22,'2018-08-01',0),(2176,'Lozano Benítez','Carmen María',58,56382.22,'2018-08-25',0),(2176,'Lozano Benítez','Carmen María',500,4000.00,'2018-06-23',1),(2176,'Lozano Benítez','Carmen María',500,4000.00,'2018-09-02',0),(2176,'Lozano Benítez','Carmen María',1500,4000.00,'2018-06-23',1),(2176,'Lozano Benítez','Carmen María',1500,4000.00,'2018-09-02',0),(2194,'Iborra Mate','Rosario',59,14095.33,'2018-06-23',0),(2194,'Iborra Mate','Rosario',59,14095.33,'2018-08-01',0),(2194,'Iborra Mate','Rosario',59,14095.33,'2018-08-25',0),(2194,'Iborra Mate','Rosario',60,14095.33,'2018-06-23',0),(2194,'Iborra Mate','Rosario',60,14095.33,'2018-08-01',0),(2194,'Iborra Mate','Rosario',60,14095.33,'2018-08-25',0),(2330,'Peralta Castaño','Juan Nicolás',61,115221.00,'2018-06-23',0),(2330,'Peralta Castaño','Juan Nicolás',61,115221.00,'2018-08-01',0),(2330,'Peralta Castaño','Juan Nicolás',61,115221.00,'2018-08-25',0),(2330,'Peralta Castaño','Juan Nicolás',62,48619.45,'2018-06-23',0),(2330,'Peralta Castaño','Juan Nicolás',62,48619.45,'2018-08-01',0),(2330,'Peralta Castaño','Juan Nicolás',62,48619.45,'2018-08-25',0),(2407,'Pérez','Óscar',63,56382.22,'2018-06-23',0),(2407,'Pérez','Óscar',63,56382.22,'2018-08-01',0),(2407,'Pérez','Óscar',63,56382.22,'2018-08-25',0),(2407,'Pérez','Óscar',64,21585.00,'2018-09-02',0),(2459,'Manzano','María',65,12596.66,'2018-09-02',0),(2459,'Manzano','María',66,12596.66,'2018-09-02',0),(2592,'Peralta Sánchez','Nicolás',67,29216.22,'2018-09-02',0),(2592,'Peralta Sánchez','Nicolás',68,50387.44,'2018-09-02',0),(2609,'Montserrat','Martín Javier',69,14607.89,'2018-09-02',0),(2609,'Montserrat','Martín Javier',70,56382.22,'2018-09-02',0),(2647,'López','Adán Antonio',71,14095.33,'2018-09-02',0),(2647,'López','Adán Antonio',72,28191.09,'2018-09-02',0),(2653,'Peralta Fernández','Ezequiel José',73,24309.69,'2018-09-02',0),(2653,'Peralta Fernández','Ezequiel José',74,14095.33,'2018-09-02',0),(2675,'Mate','Cristina',75,26909.67,'2018-09-02',0),(2675,'Mate','Cristina',76,14095.33,'2018-09-02',0),(2786,'Pérez','Rosa',77,55869.65,'2018-09-02',0),(2786,'Pérez','Rosa',78,13967.20,'2018-09-02',0),(2859,'López Lafuente','Martín',79,9749.73,'2018-09-02',0),(2859,'López Lafuente','Martín',80,58432.48,'2018-09-02',0),(2924,'Herrero Rubio','Nuria',81,24309.69,'2018-09-02',0),(2924,'Herrero Rubio','Nuria',82,12154.67,'2018-09-02',0),(2924,'Herrero Rubio','Nuria',150,12154.67,'2018-09-02',0),(2974,'Rubio','Leonardo',83,24309.69,'2018-09-02',0),(2974,'Rubio','Leonardo',84,7428.37,'2018-09-02',0),(3043,'Gutiérrez Pérez','Óscar',85,13454.64,'2018-09-02',0),(3043,'Gutiérrez Pérez','Óscar',86,43171.00,'2018-09-02',0),(3049,'Peralta García','Óscar',87,49334.71,'2018-09-02',0),(3049,'Peralta García','Óscar',88,56382.22,'2018-09-02',0),(3097,'Martínez Mate','Laura',89,18571.20,'2018-09-02',0),(3097,'Martínez Mate','Laura',90,24309.69,'2018-09-02',0),(143112,'Marazzo','Leonardo José',93,12813.94,'2018-09-02',0),(143112,'Marazzo','Leonardo José',1301,4000.00,'2018-06-23',1),(143112,'Marazzo','Leonardo José',1301,4000.00,'2018-09-02',0),(143191,'Juran','Martín Tomás',1,9749.73,'2018-06-23',0),(143191,'Juran','Martín Tomás',91,9749.73,'2018-09-02',0),(143191,'Juran','Martín Tomás',92,51256.56,'2018-08-01',0),(143191,'Juran','Martín Tomás',92,51256.56,'2018-08-25',0),(143191,'Juran','Martín Tomás',92,51256.56,'2018-09-02',0),(143191,'Juran','Martín Tomás',100,12813.90,'2018-08-01',1),(143191,'Juran','Martín Tomás',100,12813.90,'2018-08-25',1),(143191,'Juran','Martín Tomás',100,4000.00,'2018-09-02',0);
/*!40000 ALTER TABLE `cargosfaltantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `carreras`
--

LOCK TABLES `carreras` WRITE;
/*!40000 ALTER TABLE `carreras` DISABLE KEYS */;
/*!40000 ALTER TABLE `carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `categoriasinvestigacion`
--

LOCK TABLES `categoriasinvestigacion` WRITE;
/*!40000 ALTER TABLE `categoriasinvestigacion` DISABLE KEYS */;
INSERT INTO `categoriasinvestigacion` VALUES (1,'I'),(2,'II'),(3,'III'),(4,'IV'),(0,'No categorizado'),(5,'V');
/*!40000 ALTER TABLE `categoriasinvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `columnas`
--

LOCK TABLES `columnas` WRITE;
/*!40000 ALTER TABLE `columnas` DISABLE KEYS */;
INSERT INTO `columnas` VALUES (0,1,'Horas de docencia','CARGOS.CARGAHORARIA','','SUM',0,7,'FLOAT'),(0,1,'Cantidad Cargos','CARGOSDOCENTES.CODIGO','','COUNT',0,4,'INTEGER'),(0,1,'Costo de planta','CARGOSDOCENTES.ULTIMOCOSTO','','SUM',0,6,'FLOAT'),(0,1,'Legajo','DOCENTES.LEGAJO','','',0,0,'INTEGER'),(0,0,'Estado Cargo','ESTADOSCARGOS.DESCRIPCION','= Activo','',0,5,'STRING'),(0,0,'Estado Docente','ESTADOSDOCENTES.DESCRIPCION','= Activo','',0,1,'STRING'),(0,1,'Horas de investigación','INTEGRANTES.HORASSEMANALES','','SUM',0,9,'FLOAT'),(0,1,'Proyectos en los que participa','INTEGRANTES.ID','','COUNT',0,8,'INTEGER'),(0,1,'Apellido','PERSONAS.APELLIDO','','',0,2,'STRING'),(0,1,'Nombre','PERSONAS.NOMBRE','','',0,3,'STRING'),(1,1,'Código Área','AREAS.CODIGO','','GROUP_CONCAT',0,3,'STRING'),(1,1,'Área','AREAS.DESCRIPCION','','GROUP_CONCAT',0,4,'STRING'),(1,1,'Mail Director','CONTACTOS.VALOR','','GROUP_CONCAT',0,11,'STRING'),(1,1,'Código División','DIVISIONES.CODIGO','','GROUP_CONCAT',0,1,'STRING'),(1,1,'División','DIVISIONES.DESCRIPCION','','GROUP_CONCAT',0,2,'STRING'),(1,1,'Legajo Director','DOCENTES.LEGAJO','','',0,8,'INTEGER'),(1,1,'Apellido Director','PERSONAS.APELLIDO','','',0,9,'STRING'),(1,1,'Nombre Director','PERSONAS.NOMBRE','','',0,10,'STRING'),(1,1,'Prórroga','PRORROGAS.FECHAFIN','','MAX',0,7,'DATE'),(1,1,'Fecha inicio','PROYECTOS.FECHAINICIO','','',0,5,'DATE'),(1,1,'Fecha fin','PROYECTOS.FECHA_FIN','','',0,6,'DATE'),(1,1,'Nombre Proyecto','PROYECTOS.NOMBRE','','',0,0,'STRING'),(3,1,'Horas de docencia','CARGOS.CARGAHORARIA',NULL,'SUM',0,7,'FLOAT'),(3,1,'Cantidad Cargos','CARGOSDOCENTES.CODIGO',NULL,'COUNT',0,4,'INTEGER'),(3,1,'Costo de planta','CARGOSDOCENTES.ULTIMOCOSTO',NULL,'SUM',0,6,'FLOAT'),(3,1,'Legajo','DOCENTES.LEGAJO','>= 2500',NULL,0,0,'INTEGER'),(3,0,'Estado Cargo','ESTADOSCARGOS.DESCRIPCION','= Activo',NULL,0,5,'STRING'),(3,1,'Estado Docente','ESTADOSDOCENTES.DESCRIPCION','',NULL,0,1,'STRING'),(3,1,'Horas de investigación','INTEGRANTES.HORASSEMANALES',NULL,'SUM',0,9,'FLOAT'),(3,1,'Proyectos en los que participa','INTEGRANTES.ID',NULL,'COUNT',0,8,'INTEGER'),(3,1,'Apellido','PERSONAS.APELLIDO',NULL,NULL,0,2,'STRING'),(3,1,'Nombre','PERSONAS.NOMBRE',NULL,NULL,0,3,'STRING');
/*!40000 ALTER TABLE `columnas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `comisiones`
--

LOCK TABLES `comisiones` WRITE;
/*!40000 ALTER TABLE `comisiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `comisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `contactos`
--

LOCK TABLES `contactos` WRITE;
/*!40000 ALTER TABLE `contactos` DISABLE KEYS */;
INSERT INTO `contactos` VALUES (0,0,'11242536',0,'semint2018@gmail.com'),(0,0,'12675920',0,'semint2018@gmail.com'),(0,0,'15603702',0,'semint2018@gmail.com'),(0,0,'16405290',0,'semint2018@gmail.com'),(0,0,'17200893',0,'tomasjuran96@gmail.com'),(1,0,'39586150',0,'tomasjuran96@gmail.com'),(2,0,'39586150',4,'01140853127'),(3,0,'37479560',0,'mleonardoa@gmail.com'),(4,0,'18178625',0,'hrnuria@unlu.edu.ar'),(5,0,'395830',0,'asjakds@mail.com');
/*!40000 ALTER TABLE `contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `costos`
--

LOCK TABLES `costos` WRITE;
/*!40000 ALTER TABLE `costos` DISABLE KEYS */;
INSERT INTO `costos` VALUES (1,64,21585.00,'2018-06-23'),(1,65,12596.66,'2018-06-23'),(1,66,12596.66,'2018-06-23'),(1,67,29216.22,'2018-06-23'),(1,68,50387.44,'2018-06-23'),(1,69,14607.89,'2018-06-23'),(1,70,56382.22,'2018-06-23'),(1,71,14095.33,'2018-06-23'),(1,72,28191.09,'2018-06-23'),(1,73,24309.69,'2018-06-23'),(1,74,14095.33,'2018-06-23'),(1,75,26909.67,'2018-06-23'),(1,76,14095.33,'2018-06-23'),(1,77,55869.65,'2018-06-23'),(1,78,13967.20,'2018-06-23'),(1,79,9749.73,'2018-06-23'),(1,80,58432.48,'2018-06-23'),(1,81,24309.69,'2018-06-23'),(1,82,12154.67,'2018-06-23'),(1,83,24309.69,'2018-06-23'),(1,84,7428.37,'2018-06-23'),(1,85,13454.64,'2018-06-23'),(1,86,43171.00,'2018-06-23'),(1,87,49334.71,'2018-06-23'),(1,88,56382.22,'2018-06-23'),(1,89,18571.20,'2018-06-23'),(1,90,24309.69,'2018-06-23'),(1,91,14095.33,'2018-06-23'),(1,92,77791.13,'2018-06-23'),(1,93,28191.09,'2018-06-23'),(1,100,4000.00,'2018-06-01'),(1,102,51256.56,'2018-06-23'),(1,121,4000.00,'2018-06-01'),(1,500,4000.00,'2018-06-01'),(1,1301,4000.00,'2018-06-01'),(1,1400,4000.00,'2018-06-01'),(1,1500,4000.00,'2018-06-01'),(1,2000,4000.00,'2018-06-01'),(1,2002,0.00,NULL),(1,2003,6758.21,'2018-08-24'),(1,2004,12590.10,'2018-09-13'),(1,2005,24005.20,'2018-09-13'),(2,64,21585.00,'2018-08-25'),(2,65,12596.66,'2018-08-25'),(2,66,12596.66,'2018-08-25'),(2,67,29216.22,'2018-08-25'),(2,68,50387.44,'2018-08-25'),(2,69,14607.89,'2018-08-25'),(2,70,56382.22,'2018-08-25'),(2,71,14095.33,'2018-08-25'),(2,72,28191.09,'2018-08-25'),(2,73,24309.69,'2018-08-25'),(2,74,14095.33,'2018-08-25'),(2,75,26909.67,'2018-08-25'),(2,76,14095.33,'2018-08-25'),(2,77,55869.65,'2018-08-25'),(2,78,13967.20,'2018-08-25'),(2,79,9749.73,'2018-08-25'),(2,80,58432.48,'2018-08-25'),(2,81,24309.69,'2018-08-25'),(2,82,12154.67,'2018-08-25'),(2,83,24309.69,'2018-08-25'),(2,84,7428.37,'2018-08-25'),(2,85,13454.64,'2018-08-25'),(2,86,43171.00,'2018-08-25'),(2,87,49334.71,'2018-08-25'),(2,88,56382.22,'2018-08-25'),(2,89,18571.20,'2018-08-25'),(2,90,24309.69,'2018-08-25'),(2,91,9749.73,'2018-08-25'),(2,92,77791.10,'2018-06-23'),(2,93,12813.94,'2018-08-25'),(2,100,4000.00,'2018-06-01'),(2,102,4000.00,'2018-09-01'),(2,121,4000.00,'2018-09-01'),(2,500,4000.00,'2018-09-01'),(2,1301,4000.00,'2018-09-01'),(2,1400,4000.00,'2018-09-01'),(2,1500,4000.00,'2018-09-01'),(2,2002,0.00,NULL),(2,2003,6758.21,'2018-08-24'),(3,64,21585.00,'2018-08-25'),(3,65,12596.66,'2018-08-25'),(3,66,12596.66,'2018-08-25'),(3,67,29216.22,'2018-08-25'),(3,68,50387.44,'2018-08-25'),(3,69,14607.89,'2018-08-25'),(3,70,56382.22,'2018-08-25'),(3,71,14095.33,'2018-08-25'),(3,72,28191.09,'2018-08-25'),(3,73,24309.69,'2018-08-25'),(3,74,14095.33,'2018-08-25'),(3,75,26909.67,'2018-08-25'),(3,76,14095.33,'2018-08-25'),(3,77,55869.65,'2018-08-25'),(3,78,13967.20,'2018-08-25'),(3,79,9749.73,'2018-08-25'),(3,80,58432.48,'2018-08-25'),(3,81,24309.69,'2018-08-25'),(3,82,12154.67,'2018-08-25'),(3,83,24309.69,'2018-08-25'),(3,84,7428.37,'2018-08-25'),(3,85,13454.64,'2018-08-25'),(3,86,43171.00,'2018-08-25'),(3,87,49334.71,'2018-08-25'),(3,88,56382.22,'2018-08-25'),(3,89,18571.20,'2018-08-25'),(3,90,24309.69,'2018-08-25'),(3,91,9749.73,'2018-08-25'),(3,92,7779.10,'2018-06-23'),(3,93,12813.94,'2018-08-25'),(3,100,12813.94,'2018-06-23'),(3,102,4000.00,'2018-09-01'),(3,1301,4000.00,'2018-09-01'),(4,64,21585.00,'2018-08-01'),(4,65,12596.66,'2018-08-01'),(4,66,12596.66,'2018-08-01'),(4,67,29216.22,'2018-08-01'),(4,68,50387.44,'2018-08-01'),(4,69,14607.89,'2018-08-01'),(4,70,56382.22,'2018-08-01'),(4,71,14095.33,'2018-08-01'),(4,72,28191.09,'2018-08-01'),(4,73,24309.69,'2018-08-01'),(4,74,14095.33,'2018-08-01'),(4,75,26909.67,'2018-08-01'),(4,76,14095.33,'2018-08-01'),(4,77,55869.65,'2018-08-01'),(4,78,13967.20,'2018-08-01'),(4,79,9749.73,'2018-08-01'),(4,80,58432.48,'2018-08-01'),(4,81,24309.69,'2018-08-01'),(4,82,12154.67,'2018-08-01'),(4,83,24309.69,'2018-08-01'),(4,84,7428.37,'2018-08-01'),(4,85,13454.64,'2018-08-01'),(4,86,43171.00,'2018-08-01'),(4,87,49334.71,'2018-08-01'),(4,88,56382.22,'2018-08-01'),(4,89,18571.20,'2018-08-01'),(4,90,24309.69,'2018-08-01'),(4,91,9749.73,'2018-08-01'),(4,92,51256.56,'2018-08-25'),(4,93,12813.94,'2018-08-01'),(4,100,12813.90,'2018-06-23'),(4,102,4000.00,'2018-09-01'),(4,1301,4000.00,'2018-09-01'),(5,64,21585.00,'2018-08-25'),(5,65,12596.66,'2018-08-25'),(5,66,12596.66,'2018-09-01'),(5,67,29216.22,'2018-09-01'),(5,68,50387.44,'2018-09-01'),(5,69,14607.89,'2018-09-01'),(5,70,56382.22,'2018-09-01'),(5,71,14095.33,'2018-09-01'),(5,72,28191.09,'2018-09-01'),(5,73,24309.69,'2018-09-01'),(5,74,14095.33,'2018-09-01'),(5,75,26909.67,'2018-09-01'),(5,76,14095.33,'2018-09-01'),(5,77,55869.65,'2018-09-01'),(5,78,13967.20,'2018-09-01'),(5,79,9749.73,'2018-09-01'),(5,80,58432.48,'2018-09-01'),(5,81,24309.69,'2018-09-01'),(5,82,12154.67,'2018-09-01'),(5,83,24309.69,'2018-09-01'),(5,84,7428.37,'2018-09-01'),(5,85,13454.64,'2018-09-01'),(5,86,43171.00,'2018-09-01'),(5,87,49334.71,'2018-09-01'),(5,88,56382.22,'2018-09-01'),(5,89,18571.20,'2018-09-01'),(5,90,24309.69,'2018-09-01'),(5,91,9749.73,'2018-09-01'),(5,92,51256.56,'2018-08-25'),(5,93,12813.94,'2018-09-01'),(5,100,12813.90,'2018-06-23'),(6,64,21585.00,'2018-09-01'),(6,65,12596.66,'2018-09-01'),(6,66,12596.66,'2018-09-01'),(6,67,29216.22,'2018-09-01'),(6,68,50387.44,'2018-09-01'),(6,69,14607.89,'2018-09-01'),(6,70,56382.22,'2018-09-01'),(6,71,14095.33,'2018-09-01'),(6,72,28191.09,'2018-09-01'),(6,73,24309.69,'2018-09-01'),(6,74,14095.33,'2018-09-01'),(6,75,26909.67,'2018-09-01'),(6,76,14095.33,'2018-09-01'),(6,77,55869.65,'2018-09-01'),(6,78,13967.20,'2018-09-01'),(6,79,9749.73,'2018-09-01'),(6,80,58432.48,'2018-09-01'),(6,81,24309.69,'2018-09-01'),(6,82,12154.67,'2018-09-01'),(6,83,24309.69,'2018-09-01'),(6,84,7428.37,'2018-09-01'),(6,85,13454.64,'2018-09-01'),(6,86,43171.00,'2018-09-01'),(6,87,49334.71,'2018-09-01'),(6,88,56382.22,'2018-09-01'),(6,89,18571.20,'2018-09-01'),(6,90,24309.69,'2018-09-01'),(6,91,9749.73,'2018-09-01'),(6,92,51256.56,'2018-08-01'),(6,93,12813.94,'2018-09-01'),(6,100,12813.90,'2018-06-23'),(7,64,21585.00,'2018-09-01'),(7,65,12596.66,'2018-09-01'),(7,66,12596.66,'2018-09-01'),(7,67,29216.22,'2018-09-01'),(7,68,50387.44,'2018-09-01'),(7,69,14607.89,'2018-09-01'),(7,70,56382.22,'2018-09-01'),(7,71,14095.33,'2018-09-01'),(7,72,28191.09,'2018-09-01'),(7,73,24309.69,'2018-09-01'),(7,74,14095.33,'2018-09-01'),(7,75,26909.67,'2018-09-01'),(7,76,14095.33,'2018-09-01'),(7,77,55869.65,'2018-09-01'),(7,78,13967.20,'2018-09-01'),(7,79,9749.73,'2018-09-01'),(7,80,58432.48,'2018-09-01'),(7,81,24309.69,'2018-09-01'),(7,82,12154.67,'2018-09-01'),(7,83,24309.69,'2018-09-01'),(7,84,7428.37,'2018-09-01'),(7,85,13454.64,'2018-09-01'),(7,86,43171.00,'2018-09-01'),(7,87,49334.71,'2018-09-01'),(7,88,56382.22,'2018-09-01'),(7,89,18571.20,'2018-09-01'),(7,90,24309.69,'2018-09-01'),(7,91,9749.73,'2018-09-01'),(7,92,51256.56,'2018-08-01'),(7,93,12813.94,'2018-09-01'),(7,100,9700.00,'2018-09-01'),(8,64,21585.00,'2018-09-01'),(8,65,12596.66,'2018-09-01'),(8,91,9749.73,'2018-09-01'),(8,92,51256.56,'2018-08-01'),(8,93,12813.94,'2018-09-01'),(8,100,9700.00,'2018-09-01'),(9,91,9749.73,'2018-09-01'),(9,92,51256.56,'2018-08-01'),(9,100,9700.00,'2018-09-01'),(10,91,9749.73,'2018-09-01'),(10,92,51256.56,'2018-08-01'),(10,100,9700.00,'2018-09-01'),(11,91,9749.73,'2018-09-01'),(11,92,51256.56,'2018-08-01'),(11,100,9700.00,'2018-09-01'),(12,92,51256.56,'2018-08-01'),(12,100,9700.00,'2018-09-01'),(13,92,51256.56,'2018-08-01'),(13,100,9700.00,'2018-09-01'),(14,92,51256.56,'2018-08-01'),(14,100,9700.00,'2018-09-01'),(15,92,51256.56,'2018-08-01'),(15,100,9700.00,'2018-09-01'),(16,92,51256.56,'2018-08-01'),(17,92,51256.56,'2018-09-01'),(18,92,51256.56,'2018-09-01'),(19,92,51256.56,'2018-09-01'),(20,92,51256.56,'2018-09-01'),(21,92,51256.56,'2018-09-01'),(22,92,51256.56,'2018-09-01'),(23,92,51256.56,'2018-09-01'),(24,92,51256.56,'2018-09-01'),(25,92,51256.56,'2018-09-01'),(26,92,51256.56,'2018-09-01'),(27,92,51256.56,'2018-09-01'),(28,92,51256.56,'2018-09-01'),(29,92,51256.56,'2018-09-01');
/*!40000 ALTER TABLE `costos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `divisiones`
--

LOCK TABLES `divisiones` WRITE;
/*!40000 ALTER TABLE `divisiones` DISABLE KEYS */;
INSERT INTO `divisiones` VALUES ('BI','Biología',469,'DD 04/2006','2006-02-14','2009-12-20'),('CO','Computación',94,'DD 13/2012','2006-02-14','2009-12-20'),('ES','Estadísticas y Sistemas',107,'DD 02/2006','2006-02-14','2009-12-20'),('FI','Física',480,'DD 03/2006','2006-02-14','2009-12-20'),('MA','Matemática',57,'DD 05/2006','2006-02-14','2009-12-20'),('QU','Química',104,'DD 06/2006','2006-03-01','2009-12-20');
/*!40000 ALTER TABLE `divisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `docentes`
--

LOCK TABLES `docentes` WRITE;
/*!40000 ALTER TABLE `docentes` DISABLE KEYS */;
INSERT INTO `docentes` VALUES (57,0,'16405290','Jefa de la división Matemática',1,0),(81,0,'9550991','Observaciones de Vázquez Cabeza',1,0),(94,0,'17200893','Jefe de la división Computación',1,0),(104,0,'15603702','Jefa de la división Química',1,0),(107,0,'11242536','Jefe de la división Estadísticas y Sistemas',1,0),(218,0,'9834195','Observaciones de González',1,0),(257,0,'9849483','Observaciones de Lopes Martínez',1,0),(268,0,'9945503','Observaciones de Lafuente',1,0),(318,0,'10117993','Observaciones de Cabo Benítez',1,0),(325,0,'10662538','Observaciones de Peralta San Juan',1,0),(427,0,'10928479','Observaciones de Peralta',1,0),(467,0,'10284846','Observaciones de Castaño',1,0),(469,0,'9675920','Jefe de la división Biología',1,0),(480,0,'12675920','Jefe de la división Física',1,0),(521,0,'10071374','Observaciones de Lozano Peralta',1,0),(671,0,'11915775','Observaciones de Lozano',1,0),(948,0,'12506506','Observaciones de Del Moral',1,0),(1027,0,'12976600','Observaciones de Benítez López',1,0),(1108,0,'12329525','Observaciones de González Muñoz',1,0),(1153,0,'12147287','Observaciones de Cabeza Algaba',1,0),(1193,0,'12579600','Observaciones de Benítez García',1,0),(1221,0,'13522037','Observaciones de González Gutiérrez',1,0),(1229,0,'13582584','Observaciones de Miranda',1,0),(1358,0,'13959331','Observaciones de Sainz Álvarez',1,0),(1640,0,'14871880','Observaciones de Benítez',1,0),(2067,0,'15491157','Observaciones de García',1,0),(2125,0,'16647413','Observaciones de Sainz Cabeza',1,0),(2176,0,'16015748','Observaciones de Lozano Benítez',1,0),(2194,0,'16314292','Observaciones de Iborra Mate',1,0),(2330,0,'16677483','Observaciones de Peralta Castaño',1,0),(2407,0,'17032691','Observaciones de Pérez',1,0),(2459,0,'17645568','Observaciones de Manzano',1,0),(2592,0,'17924559','Observaciones de Peralta Sánchez',1,0),(2609,0,'17112281','Observaciones de Montserrat',1,0),(2647,0,'17504187','Observaciones de López',1,0),(2653,0,'17457547','Observaciones de Peralta Fernández',1,0),(2675,0,'17875356','Observaciones de Mate',1,0),(2786,0,'18661346','Observaciones de Pérez',1,0),(2859,0,'18373319','Observaciones de López Lafuente',1,0),(2924,0,'18178625','Observaciones de Herrero Rubio',3,0),(2974,0,'18663341','Observaciones de Rubio',1,0),(3043,0,'19113505','Observaciones de Gutiérrez Pérez',1,0),(3049,0,'19751064','Observaciones de Peralta García',1,0),(3097,0,'19732040','Observaciones de Martínez Mate',1,0),(143112,0,'40455634','Observaciones de Marazzo',1,0),(143191,0,'39586150','Observaciones de Juran',1,1);
/*!40000 ALTER TABLE `docentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `domicilios`
--

LOCK TABLES `domicilios` WRITE;
/*!40000 ALTER TABLE `domicilios` DISABLE KEYS */;
INSERT INTO `domicilios` VALUES (1,0,'39586150','Buenos Aires','Garín','1619','Lamberti 862'),(2,0,'37479560','Buenos Aires','Luján','6700','Saavedra 994'),(3,0,'18178625','Buenos Aires','Luján','6700','San Martín 802'),(4,0,'395830','Córdoba','Córdoba','2993','jasja 92');
/*!40000 ALTER TABLE `domicilios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `equiposdocentes`
--

LOCK TABLES `equiposdocentes` WRITE;
/*!40000 ALTER TABLE `equiposdocentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `equiposdocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `estadoscargos`
--

LOCK TABLES `estadoscargos` WRITE;
/*!40000 ALTER TABLE `estadoscargos` DISABLE KEYS */;
INSERT INTO `estadoscargos` VALUES (0,'Activo'),(1,'Inactivo');
/*!40000 ALTER TABLE `estadoscargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `estadosdocentes`
--

LOCK TABLES `estadosdocentes` WRITE;
/*!40000 ALTER TABLE `estadosdocentes` DISABLE KEYS */;
INSERT INTO `estadosdocentes` VALUES (0,'Activo'),(5,'Fallecido'),(1,'Inactivo'),(2,'Jubilado'),(6,'Licencia'),(4,'Renuncia'),(3,'Retiro');
/*!40000 ALTER TABLE `estadosdocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `estadospersonas`
--

LOCK TABLES `estadospersonas` WRITE;
/*!40000 ALTER TABLE `estadospersonas` DISABLE KEYS */;
INSERT INTO `estadospersonas` VALUES (0,'Activa'),(1,'Inactiva');
/*!40000 ALTER TABLE `estadospersonas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `estadosprogramas`
--

LOCK TABLES `estadosprogramas` WRITE;
/*!40000 ALTER TABLE `estadosprogramas` DISABLE KEYS */;
INSERT INTO `estadosprogramas` VALUES (0,'Activo'),(1,'Inactivo');
/*!40000 ALTER TABLE `estadosprogramas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `estadosproyectos`
--

LOCK TABLES `estadosproyectos` WRITE;
/*!40000 ALTER TABLE `estadosproyectos` DISABLE KEYS */;
INSERT INTO `estadosproyectos` VALUES (0,'Activo'),(1,'Inactivo');
/*!40000 ALTER TABLE `estadosproyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `examenes`
--

LOCK TABLES `examenes` WRITE;
/*!40000 ALTER TABLE `examenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `examenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `incentivos`
--

LOCK TABLES `incentivos` WRITE;
/*!40000 ALTER TABLE `incentivos` DISABLE KEYS */;
INSERT INTO `incentivos` VALUES (1221,'2018');
/*!40000 ALTER TABLE `incentivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `integrantes`
--

LOCK TABLES `integrantes` WRITE;
/*!40000 ALTER TABLE `integrantes` DISABLE KEYS */;
INSERT INTO `integrantes` VALUES (1,100,'',NULL,NULL,'UNLu',15,1),(2,102,NULL,NULL,NULL,'UNLu',48,2),(3,121,NULL,NULL,NULL,'UNLu',20,3),(4,500,NULL,NULL,NULL,'UNLu',11,4),(5,1301,NULL,NULL,NULL,'UNLu',10,5),(6,1400,NULL,NULL,NULL,'UNLu',5,6),(7,1500,NULL,NULL,NULL,'UNLu',13,7),(8,2000,NULL,NULL,NULL,'UNLu',22,8),(9,2000,NULL,NULL,NULL,'UNLu',24,9),(10,1500,NULL,NULL,NULL,'UNLu',5,10),(11,1301,NULL,NULL,NULL,'UNLu',18,11),(12,500,NULL,NULL,NULL,'UNLu',48,12),(13,100,NULL,NULL,NULL,'UNLu',11,13),(14,1400,NULL,NULL,NULL,'UNLu',11,14),(15,121,NULL,NULL,NULL,'UNLu',20,15),(16,64,NULL,NULL,NULL,'UNLu',20,1),(17,2004,'Aramburu','Graciela','Profesor Adjunto Exclusiva','UNLu',5,5);
/*!40000 ALTER TABLE `integrantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `modulos`
--

LOCK TABLES `modulos` WRITE;
/*!40000 ALTER TABLE `modulos` DISABLE KEYS */;
INSERT INTO `modulos` VALUES (0,'General'),(1,'Estación'),(2,'Roles'),(3,'Usuarios'),(4,'Cargos'),(5,'Docentes'),(6,'Investigación'),(7,'Informes'),(8,'Personas'),(9,'Proyectos'),(10,'Programas');
/*!40000 ALTER TABLE `modulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` VALUES (188,2,1,0,1,1,0),(189,2,1,0,1,1,1),(190,2,0,0,0,0,2),(191,2,0,0,0,0,3),(192,2,1,0,1,1,4),(193,2,1,0,1,1,5),(194,2,1,0,1,1,6),(195,2,1,0,1,1,7),(196,2,1,0,1,1,8),(197,2,1,0,1,1,9),(198,2,1,0,1,1,10),(210,0,1,1,1,1,0),(211,0,1,1,1,1,1),(212,0,1,1,1,1,2),(213,0,1,1,1,1,3),(214,0,1,1,1,1,4),(215,0,1,1,1,1,5),(216,0,1,1,1,1,6),(217,0,1,1,1,1,7),(218,0,1,1,1,1,8),(219,0,1,1,1,1,9),(220,0,1,1,1,1,10),(232,3,0,0,0,0,0),(233,3,0,0,0,0,1),(234,3,0,0,0,0,2),(235,3,0,0,0,0,3),(236,3,0,0,0,1,4),(237,3,0,0,0,1,5),(238,3,0,0,0,1,6),(239,3,0,0,0,1,7),(240,3,0,0,0,1,8),(241,3,0,0,0,1,9),(242,3,0,0,0,1,10),(243,1,1,1,1,1,0),(244,1,1,1,1,1,1),(245,1,1,1,1,1,2),(246,1,1,1,1,1,3),(247,1,1,1,1,1,4),(248,1,1,1,1,1,5),(249,1,1,1,1,1,6),(250,1,1,1,1,1,7),(251,1,1,1,1,1,8),(252,1,1,1,1,1,9),(253,1,1,1,1,1,10),(254,0,1,1,1,1,0),(255,0,1,1,1,1,1),(256,0,1,1,1,1,2),(257,0,1,1,1,1,3),(258,0,1,1,1,1,4),(259,0,1,1,1,1,5),(260,0,1,1,1,1,6),(261,0,1,1,1,1,7),(262,0,1,1,1,1,8),(263,0,1,1,1,1,9),(264,0,1,1,1,1,10),(265,0,1,1,1,1,0),(266,0,1,1,1,1,1),(267,0,1,1,1,1,2),(268,0,1,1,1,1,3),(269,0,1,1,1,1,4),(270,0,1,1,1,1,5),(271,0,1,1,1,1,6),(272,0,1,1,1,1,7),(273,0,1,1,1,1,8),(274,0,1,1,1,1,9),(275,0,1,1,1,1,10),(276,0,1,1,1,1,0),(277,0,1,1,1,1,1),(278,0,1,1,1,1,2),(279,0,1,1,1,1,3),(280,0,1,1,1,1,4),(281,0,1,1,1,1,5),(282,0,1,1,1,1,6),(283,0,1,1,1,1,7),(284,0,1,1,1,1,8),(285,0,1,1,1,1,9),(286,0,1,1,1,1,10);
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` VALUES (0,'0','s','u','2018-03-10',1),(0,'10071374','Lozano Peralta','Guillermo Javier','1950-12-31',0),(0,'10117993','Cabo Benítez','Jorge','1948-08-06',0),(0,'10284846','Castaño','Juan','1949-07-10',0),(0,'10662538','Peralta San Juan','Leonardo','1948-01-11',0),(0,'10928479','Peralta','Ramón','1949-09-04',0),(0,'11242536','Urritiaga','Daniel Alfonso','1951-05-12',0),(0,'11915775','Lozano','Carmen Rosario','1951-08-01',0),(0,'12147287','Cabeza Algaba','Vicente','1956-09-09',0),(0,'12329525','González Muñoz','Jorge Carlos','1956-08-14',0),(0,'12506506','Del Moral','Francisco Guillermo','1954-12-29',0),(0,'12579600','Benítez García','Francisco Jorge','1956-09-04',0),(0,'12675920','Alcaraz','Marcelo Fernando','1955-11-08',0),(0,'12976600','Benítez López','Pilar Laura','1955-06-12',0),(0,'13522037','González Gutiérrez','Vicente','1957-07-30',0),(0,'13582584','Miranda','Martín','1957-09-29',0),(0,'13959331','Sainz Álvarez','Leonardo','1958-02-25',0),(0,'14871880','Benítez','Elías Adán','1961-12-06',0),(0,'15491157','García','Julián','1965-05-11',0),(0,'15603702','Aramburu','Graciela','1959-07-10',0),(0,'16015748','Lozano Benítez','Carmen María','1966-02-11',0),(0,'16314292','Iborra Mate','Rosario','1966-12-31',0),(0,'16405290','Fernandez Perez','Julieta','1964-02-28',0),(0,'16647413','Sainz Cabeza','Dolores Ana','1966-01-21',0),(0,'16677483','Peralta Castaño','Juan Nicolás','1968-11-03',0),(0,'17032691','Pérez','Óscar','1969-09-21',0),(0,'17112281','Montserrat','Martín Javier','1971-05-13',0),(0,'17200893','Cisneros','Guillermo','1967-10-02',0),(0,'17457547','Peralta Fernández','Ezequiel José','1971-08-13',0),(0,'17504187','López','Adán Antonio','1971-05-02',0),(0,'17645568','Manzano','María','1969-02-04',0),(0,'17875356','Mate','Cristina','1971-10-09',0),(0,'17924559','Peralta Sánchez','Nicolás','1970-04-09',0),(0,'18178625','Herrero Rubio','Nuria','1974-05-01',0),(0,'18373319','López Lafuente','Martín','1973-10-27',0),(0,'18661346','Pérez','Rosa','1972-06-30',0),(0,'18663341','Rubio','Leonardo','1974-05-22',0),(0,'19113505','Gutiérrez Pérez','Óscar','1975-05-26',0),(0,'19732040','Martínez Mate','Laura','1975-07-04',0),(0,'19751064','Peralta García','Óscar','1975-06-30',0),(0,'2960429','Lopez','Julio','1975-09-19',NULL),(0,'37479560','Marchetti','Leonardo Andrés','1993-05-17',NULL),(0,'395830','Lopez','Julio','2018-09-22',NULL),(0,'39586150','Juran','Martín Tomás','1996-06-29',0),(0,'40455634','Marazzo','Leonardo José','1997-06-22',0),(0,'4819239','Lopez','Julio',NULL,NULL),(0,'9550991','Vázquez Cabeza','Carmen Rosario','1945-01-25',0),(0,'9675920','Gonzalez','Julián','1945-03-29',0),(0,'9834195','González','Adán','1947-02-21',0),(0,'9849483','Lopes Martínez','Ana','1947-09-09',0),(0,'9945503','Lafuente','Dolores','1945-10-17',0);
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `programasasignaturas`
--

LOCK TABLES `programasasignaturas` WRITE;
/*!40000 ALTER TABLE `programasasignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `programasasignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `programasinvestigacion`
--

LOCK TABLES `programasinvestigacion` WRITE;
/*!40000 ALTER TABLE `programasinvestigacion` DISABLE KEYS */;
INSERT INTO `programasinvestigacion` VALUES (1,'Ciencias Biologicas',143112,NULL,NULL,NULL,NULL,0),(2,'Las Tecnologías de la Información y la Comunicación',57,NULL,NULL,NULL,NULL,0),(3,'Estudios de física',325,NULL,NULL,NULL,NULL,0),(4,'Quimica',671,NULL,NULL,NULL,NULL,0),(5,'Analisis matematicos',521,NULL,NULL,NULL,NULL,0),(6,'PROGRAMA DE INVESTIGACIÓN EN ECOTOXICOLOGÍA',2786,1358,NULL,NULL,NULL,0),(7,'PROGRAMA DE ESTUDIOS DE POLITICA, HISTORIA Y DERECHO (EPHyD)',268,257,NULL,NULL,NULL,0),(8,'PROGRAMA DE INVESTIGACIONES EN ECOLOGÍA TERRESTRE',2194,2176,NULL,NULL,NULL,0),(9,'Programa de Estudios sobre Fundamentos Teórico-Metodológicos del Trabajo Social',1193,2609,NULL,NULL,NULL,0),(10,'Programa De Ecofisiología Aplicada',521,480,NULL,NULL,NULL,0),(11,'DIAGNÓSTICO INTEGRAL DEL ÁREA DE INFLUENCIA DE LA UNIVERSIDAD NACIONAL DE LUJÁN',2459,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `programasinvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `prorrogas`
--

LOCK TABLES `prorrogas` WRITE;
/*!40000 ALTER TABLE `prorrogas` DISABLE KEYS */;
INSERT INTO `prorrogas` VALUES (1,'608/12','2012-03-19'),(2,'127/13','2013-04-13'),(2,'201/14','2014-02-08'),(3,'120/10','2018-11-09'),(7,'378/12','2012-02-22'),(10,'409/10','2010-12-04'),(14,'607/12','2012-09-27');
/*!40000 ALTER TABLE `prorrogas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `proyectos`
--

LOCK TABLES `proyectos` WRITE;
/*!40000 ALTER TABLE `proyectos` DISABLE KEYS */;
INSERT INTO `proyectos` VALUES (1,'Cultivo in vitro de especies leñosas nativas',NULL,'2008-06-22',NULL,NULL,94,NULL,'2008-09-15','2010-03-20',1,0),(2,'Morfogénesis in vitro de Handroanthus impetiginosus',NULL,'2009-07-15',NULL,NULL,107,NULL,'2009-10-30','2011-12-21',1,0),(3,'El aprendizaje basado en problemas como técnicas didáctica.',NULL,'2015-02-14',NULL,NULL,469,NULL,'2016-05-01','2018-02-13',1,0),(4,'P.A.M.P.A.',NULL,'2010-05-30',NULL,NULL,107,NULL,'2010-06-14','2012-01-10',2,0),(5,'Decisiones inteligentes en sistemas complejos',NULL,'2011-02-01',NULL,NULL,257,NULL,'2011-04-13','2012-12-10',2,0),(6,'Estudio multitemporal y monitoreo de los glaciares de Patagonia Sur y del Mar Weddell Sur ',NULL,'2011-03-01',NULL,NULL,3049,NULL,'2012-02-01','2014-03-31',2,0),(7,'Medición de la Radiación Fotosintéticamente activa (PAR) en los planos con diferentes orientaciones, mediante el desarrollo y ensayo de un dispositivo ad-hoc',NULL,'2010-07-26',NULL,NULL,2609,NULL,'2010-08-26','2011-08-23',3,0),(8,'Red solarimétrica Regional',NULL,'2017-02-01',NULL,NULL,2647,NULL,'2017-06-15',NULL,3,0),(9,'Medición y modelo la componente directa de la radiación solar: aplicaciones',NULL,'2018-03-06',NULL,NULL,3043,NULL,NULL,NULL,3,0),(10,'Síntesis y estudio de la estereoquímica de Tetralinas e Indanos sustituidos',NULL,'2006-06-24',NULL,NULL,2609,NULL,'2006-10-24','2007-12-15',4,0),(11,'Formulación de complejos entre ciclodextrinas y compuestos destinados a mejorar la calidad de los alimentos y su aplicación\n',NULL,'2006-07-28',NULL,NULL,2609,NULL,'2006-11-01','2008-10-30',4,0),(12,'Propiedades de oxo – hidróxidos comunes en suelos. Su actividad como inmovilizantes de iones pesados y posibles uso en remediación',NULL,'2016-04-22',NULL,NULL,104,NULL,'2017-11-22',NULL,4,0),(13,'Los estilos de Aprendizaje y la Matemática',NULL,'2009-11-22',NULL,NULL,218,NULL,'2011-02-22','2013-04-19',5,0),(14,'Los errores mas frecuentes en el aprendizaje de Matemática I',NULL,'2007-08-19',NULL,NULL,257,NULL,'2009-04-19','2010-11-07',5,0),(15,'Un estudio sobre la comprensión en Matemática Superior. Consecuencias para la enseñanza',NULL,'2005-12-12',NULL,NULL,427,NULL,'2007-01-19','2009-05-23',5,0),(16,' Genotoxic Evidences of Glyphosate and Chlorpyriphos on Eisenia fetida Coelomocytes',NULL,'2011-08-30',NULL,NULL,427,NULL,NULL,NULL,6,0),(17,' The ecotoxicity of binary mixtures of Imazamox and ionized ammonia on freshwater copepods: Implications for environmental risk assessment in groundwater bodies',NULL,'2014-07-11',NULL,NULL,1193,NULL,NULL,NULL,6,0),(18,'Evidences of oxidative stress during hydrogen photoproduction by means of sulfur-deprived Chlamydomonas reinhardtii cultures.',NULL,'2008-11-18',NULL,NULL,2609,NULL,NULL,NULL,6,0),(19,'Toxicity assessment of Cyfluthrin commercial formulation on growth, photosynthesis and catalase activity of green algae. Pesticide Biochemistry and Physiology.',NULL,'2012-01-28',NULL,NULL,1640,NULL,NULL,NULL,6,0),(20,'Effects of a commercial formulation of Cypermethrin used in biotech soybean crops on growth and antioxidant enzymes of freshwater algae. International Journal of Environmental Protection',NULL,'2011-11-18',NULL,NULL,1640,NULL,NULL,NULL,6,0),(21,'Sociedad civil y Salud: de reclamos y memorias.',NULL,'2016-06-22',NULL,NULL,427,NULL,NULL,NULL,7,0),(22,'Los Modelos en Trabajo Social y implicancia en el ejercicio profesional',NULL,'2016-06-22',NULL,NULL,107,NULL,NULL,NULL,7,0),(23,'Del Estado Benefactor al Estado Punitivo. El poder de policía: conceptos, normas y prácticas políticas aplicadas en el aglomerado Gran Buenos Aires entre 1946 y1968.',NULL,'2007-04-06',NULL,NULL,3049,NULL,NULL,NULL,7,0),(24,'Necesidades e infancias',NULL,'2008-07-14',NULL,NULL,143191,NULL,NULL,NULL,7,0),(25,'Análisis de un gradiente de disturbio: cambios en la velocidad de descomposición e incorporación de la materia orgánica y se relación con la meso y la macro fauna.',NULL,'2013-08-05',NULL,NULL,1640,NULL,NULL,NULL,8,0),(26,'Indicadores de sustentabilidad del suelo basados en la estructura y funcionamiento de la fauna edáfica. ',NULL,'2017-04-29',NULL,NULL,143191,NULL,NULL,NULL,8,0),(27,'Relaciones interespecíficas entre los parasitoides larvales Dineulophus phtherimaeae De Santis y Pseudiapanteles dignus. Implicancias para el control biológico de Tuta absoluta. CDD-CB 006-16',NULL,'2010-05-19',NULL,NULL,3049,NULL,NULL,NULL,8,0),(28,'Degradación de suelos agrícolas medida a través de sus efectos sobre la estructura y el funcionamiento del ecosistema edáfico',NULL,'2010-09-26',NULL,NULL,1193,NULL,NULL,NULL,8,0),(29,'Las intervenciones sociales impulsadas por las iglesias protestantes entre 1870-1930 en Argentina y sus vinculaciones con el proceso de institucionalización del Trabajo Social',NULL,'2013-03-21',NULL,NULL,2194,NULL,NULL,NULL,8,0),(30,'El debate actual en Ciencias Sociales. Análisis de las influencias de las categorías de realidad, sujeto y contemporaneidad en la obra de Pierre Bourdieu y Michel Foucault sobre el trabajo social argentino en el período 1994-2004',NULL,'2011-08-13',NULL,NULL,3049,NULL,NULL,NULL,9,0),(31,'Fundamentos y estrategias de intervención de los actores institucionales en el abordaje de la niñez: Estudio de caso en los municipios de Luján y Gral. Rodríguez',NULL,'2011-08-13',NULL,NULL,3049,NULL,NULL,NULL,9,0),(32,'El ejercicio profesional del Trabajo Social en las actuales relaciones Estado-Sociedad',NULL,'2007-05-30',NULL,NULL,1640,NULL,NULL,NULL,9,0),(33,'Implicancias de los cambios teóricos y legislativos en el abordaje a la niñez',NULL,'2015-06-01',NULL,NULL,3049,NULL,NULL,NULL,9,0),(34,'Effect of ibuprofeno thes swimming pattern of Cyprinus carpio',NULL,'2015-06-01',NULL,NULL,3049,NULL,NULL,NULL,9,0),(35,'Respuesta de biomarcadores bioquímicos, morfológicos y comportamentales de la carpa común, Cyprinus carpio, por exposición a muestras ambientales.',NULL,'2015-08-27',NULL,NULL,2675,NULL,NULL,NULL,10,0),(36,'Cadmium Toxicity assessment in juveniles of he Austral South America amphipod Hyalella curvispina.',NULL,'2014-02-10',NULL,NULL,2675,NULL,NULL,NULL,10,0),(37,' Energy balance of juvenile Cyprinus carpio after a short-term exposure to sublethal water-borne cadmium.',NULL,'2009-06-12',NULL,NULL,143191,NULL,NULL,NULL,10,0),(38,' Cadmium toxicity in tadpoles of Rhinella arenarum in relation to calcium and humic acids.',NULL,'2010-06-23',NULL,NULL,2653,NULL,NULL,NULL,10,0),(39,'Investigación y desarrollo territorial (3ra etapa) - Agendas ambientales y gestión participada de riesgos: componentes de relevancia para una ordenación ambiental desde multicriterios',NULL,'2014-07-14',NULL,NULL,1108,NULL,NULL,NULL,10,0),(40,'Observatorio de los graduados del Centro Regional Campana. Dinámica profesional y vinculación con el medio. (Continua)',NULL,'2010-08-03',NULL,NULL,268,NULL,NULL,NULL,11,0),(41,'Evolución, alcance e impacto de los servicios de vinculación tecnológica de la UNLu en su zona de influencia.',NULL,'2013-01-05',NULL,NULL,427,NULL,NULL,NULL,11,0),(42,'Taller de muestreo y diseño de encuestas',NULL,'2013-03-08',NULL,NULL,318,NULL,NULL,NULL,11,0),(43,'Los Graduados de la Universidad Nacional de Luján (UNLu) y su Inserción en el Contexto del Área de Influencia:',NULL,'2012-07-28',NULL,NULL,107,NULL,NULL,NULL,11,0);
/*!40000 ALTER TABLE `proyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rendiciones`
--

LOCK TABLES `rendiciones` WRITE;
/*!40000 ALTER TABLE `rendiciones` DISABLE KEYS */;
INSERT INTO `rendiciones` VALUES (1,2,2018,'2018-09-28',2000.00,NULL),(2,2,2018,'2018-09-14',950.00,NULL);
/*!40000 ALTER TABLE `rendiciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (0,'su','Oculto'),(1,'Administrador del sistema','Rol Administrador del sistema'),(2,'usuario','Usuario común'),(3,'invitado','Invitado');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rolesxusuario`
--

LOCK TABLES `rolesxusuario` WRITE;
/*!40000 ALTER TABLE `rolesxusuario` DISABLE KEYS */;
INSERT INTO `rolesxusuario` VALUES ('su',0),('admin',1),('usuario',2),('invitado',3),('usuario 1',3);
/*!40000 ALTER TABLE `rolesxusuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sedes`
--

LOCK TABLES `sedes` WRITE;
/*!40000 ALTER TABLE `sedes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sedes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subsidios`
--

LOCK TABLES `subsidios` WRITE;
/*!40000 ALTER TABLE `subsidios` DISABLE KEYS */;
INSERT INTO `subsidios` VALUES (2,2018,'',2950.00,'Subsidio 1');
/*!40000 ALTER TABLE `subsidios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tiposcargos`
--

LOCK TABLES `tiposcargos` WRITE;
/*!40000 ALTER TABLE `tiposcargos` DISABLE KEYS */;
INSERT INTO `tiposcargos` VALUES (1,'Interino'),(0,'Ordinario');
/*!40000 ALTER TABLE `tiposcargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tiposcontactos`
--

LOCK TABLES `tiposcontactos` WRITE;
/*!40000 ALTER TABLE `tiposcontactos` DISABLE KEYS */;
INSERT INTO `tiposcontactos` VALUES (0,'Mail Laboral'),(1,'Mail Personal'),(4,'Teléfono Celular'),(2,'Teléfono Laboral'),(3,'Teléfono Particular');
/*!40000 ALTER TABLE `tiposcontactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tiposdocumentos`
--

LOCK TABLES `tiposdocumentos` WRITE;
/*!40000 ALTER TABLE `tiposdocumentos` DISABLE KEYS */;
INSERT INTO `tiposdocumentos` VALUES (3,'CI'),(4,'CUIT'),(0,'DNI'),(1,'LC'),(2,'LE');
/*!40000 ALTER TABLE `tiposdocumentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tiposinformes`
--

LOCK TABLES `tiposinformes` WRITE;
/*!40000 ALTER TABLE `tiposinformes` DISABLE KEYS */;
INSERT INTO `tiposinformes` VALUES (0,'Impacto Docente','Lista el costo de los docentes activos y su carga horaria total',0,'DOCENTES INNER JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO LEFT JOIN CARGOSDOCENTES ON DOCENTES.LEGAJO = CARGOSDOCENTES.LEGAJO INNER JOIN CARGOS ON CARGOSDOCENTES.CARGO = CARGOS.CODIGO LEFT JOIN INTEGRANTES ON CARGOSDOCENTES.CODIGO = INTEGRANTES.CARGODOCENTE INNER JOIN ESTADOSDOCENTES ON DOCENTES.ESTADO = ESTADOSDOCENTES.ID INNER JOIN ESTADOSCARGOS ON CARGOSDOCENTES.ESTADOCARGO = ESTADOSCARGOS.IDESTADOCARGO','DOCENTES.LEGAJO, ESTADOSDOCENTES.DESCRIPCION, PERSONAS.APELLIDO, PERSONAS.NOMBRE, ESTADOSCARGOS.DESCRIPCION'),(1,'Resumen de Proyectos de Investigación','Lista un resumen de cada Proyecto de Investigación',0,'PROYECTOS LEFT JOIN INTEGRANTES ON PROYECTOS.ID = INTEGRANTES.PROYECTO LEFT JOIN CARGOSDOCENTES ON INTEGRANTES.CARGODOCENTE = CARGOSDOCENTES.CODIGO LEFT JOIN AREAS ON CARGOSDOCENTES.AREA = AREAS.CODIGO LEFT JOIN DIVISIONES ON AREAS.DIVISION = DIVISIONES.CODIGO LEFT JOIN DOCENTES ON PROYECTOS.DIRECTOR = DOCENTES.LEGAJO LEFT JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO LEFT JOIN CONTACTOS ON PERSONAS.NRODOCUMENTO = CONTACTOS.NRODOCUMENTO LEFT JOIN PRORROGAS ON PROYECTOS.ID = PRORROGAS.PROYECTO','PROYECTOS.NOMBRE, PROYECTOS.FECHAINICIO, PROYECTOS.FECHA_FIN, PRORROGAS.FECHAFIN, DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE'),(3,'Impacto Docente 3','Lista el costo de los docentes activos y su carga horaria total',1,'DOCENTES INNER JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO LEFT JOIN CARGOSDOCENTES ON DOCENTES.LEGAJO = CARGOSDOCENTES.LEGAJO INNER JOIN CARGOS ON CARGOSDOCENTES.CARGO = CARGOS.CODIGO LEFT JOIN INTEGRANTES ON CARGOSDOCENTES.CODIGO = INTEGRANTES.CARGODOCENTE INNER JOIN ESTADOSDOCENTES ON DOCENTES.ESTADO = ESTADOSDOCENTES.ID INNER JOIN ESTADOSCARGOS ON CARGOSDOCENTES.ESTADOCARGO = ESTADOSCARGOS.IDESTADOCARGO','DOCENTES.LEGAJO, ESTADOSDOCENTES.DESCRIPCION, PERSONAS.APELLIDO, PERSONAS.NOMBRE, ESTADOSCARGOS.DESCRIPCION');
/*!40000 ALTER TABLE `tiposinformes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `titulos`
--

LOCK TABLES `titulos` WRITE;
/*!40000 ALTER TABLE `titulos` DISABLE KEYS */;
INSERT INTO `titulos` VALUES (1,0,'37479560','Analista de Sistemas',1),(2,0,'18178625','Licenciada en Biología',1),(3,0,'395830','Mg. Ciencias de la Computación',1);
/*!40000 ALTER TABLE `titulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('admin','YWRtaW4=','m9+f6TFQBUQUmw6igGvGpw==',0,'39586150','El usuario Administrador'),('invitado','aW52aXRhZG8=','/maLD/W5PDOsXHZ9MXeYxQ==',0,'37479560','Invitado'),('su','c2VtaW50MjAxOA==','Ve3nYAXwigF2BfAU0H+1+Q==',0,'0','Oculto'),('usuario','dXN1YXJpbw==','ZLTSypFJqEvJPKWDPfwb2g==',0,'40455634','Usuario común'),('usuario 1','dXN1YXJpbw==','m2n2++/t/m79hjuYb9oLXw==',0,'10662538','Usuario de prueba');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'plumasdocentes'
--

--
-- Dumping routines for database 'plumasdocentes'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-27 15:22:50
