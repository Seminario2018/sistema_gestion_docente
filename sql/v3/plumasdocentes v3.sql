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
-- Table structure for table `areas`
--

DROP TABLE IF EXISTS `areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `areas` (
  `Codigo` varchar(8) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Division` varchar(2) NOT NULL,
  `Responsable` int(11) DEFAULT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` date DEFAULT NULL,
  `Hasta` date DEFAULT NULL,
  `SubAreaDe` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `division_idx` (`Division`),
  KEY `subarea_idx` (`SubAreaDe`),
  CONSTRAINT `Division` FOREIGN KEY (`Division`) REFERENCES `divisiones` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Areas_1` FOREIGN KEY (`SubAreaDe`) REFERENCES `areas` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas`
--

LOCK TABLES `areas` WRITE;
/*!40000 ALTER TABLE `areas` DISABLE KEYS */;
INSERT INTO `areas` VALUES ('BI.01.00','Ecología','BI',3097,'506/12','2001-12-31','2013-12-31',NULL),('BI.01.01','Ecología Básica','BI',427,'','2001-12-31','2001-12-31','BI.01.00'),('BI.01.02','Ecología Avanzada','BI',2675,'','2001-12-31','2001-12-31','BI.01.00'),('BI.02.00','Biología General','BI',2459,'506/12','2001-12-31','2013-12-31',NULL),('BI.03.00','Biología  Molecular y Microbiología','BI',1027,'506/12','2001-12-31','2013-12-31',NULL),('BI.03.01','Biología  Celular y Molecular','BI',948,'506/12','2001-12-31','2013-12-31','BI.03.00'),('BI.03.02','Microbiología','BI',3043,'','2001-12-31','2001-12-31','BI.03.00'),('BI.04.00','Genética y Evolución','BI',257,'506/12','2001-12-31','2013-12-31',NULL),('BI.05.00','Biología Vegetal','BI',268,'','2001-12-31','2013-12-31',NULL),('BI.06.00','Biología Animal','BI',2609,'506/12','2001-12-31','2013-12-31',NULL),('BI.06.01','Biología Animal Estructural','BI',1229,'','2001-12-31','2001-12-31','BI.06.00'),('BI.06.02','anulada','BI',2924,'','2001-12-31','2001-12-31','BI.06.00'),('BI.06.03','Fisiología','BI',521,'','2001-12-31','2001-12-31','BI.06.00'),('CO.01.00','Algoritmos y Lenguajes','CO',2653,'506/12','2001-12-31','2013-12-31',NULL),('CO.02.00','Arquitectura,Sistemas Operativos y Redes','CO',1193,'506/12','2001-12-31','2013-12-31',NULL),('CO.03.00','Ing.de Soft, Base de Dat. y Sist de Inf.','CO',1153,'506/12','2012-05-10','2013-12-31',NULL),('CO.03.01','Sistema de Información ','CO',2647,'','2001-12-31','2001-12-31','CO.03.00'),('CO.03.02','Base de Datos','CO',467,'','2012-05-10','2001-12-31','CO.03.00'),('CO.03.03','Proyectos de Investigación','CO',2125,'','2012-05-10','2001-12-31','CO.03.00'),('CO.04.00','Teoría de la Computación','CO',218,'506/12','2001-12-31','2013-12-31',NULL),('CO.05.00','Computación Aplicada','CO',2859,'506/12','2001-12-31','2013-12-31',NULL),('CO.06.00','Informática','CO',2407,'506/12','2001-12-31','2013-12-31',NULL),('ES.01.00','Estadística Socioeconómica','ES',1108,'506/12','2001-12-31','2013-12-31',NULL),('ES.02.00','Bioestadística','ES',2974,'506/12','2001-12-31','2013-12-31',NULL),('ES.03.00','Muestreo y Control de Procesos','ES',1221,'506/12','2001-12-31','2013-12-31',NULL),('ES.04.00','Modelos Avanzados Metod Cuali-Cuanti del','ES',2330,'506/12','2001-12-31','2013-12-31',NULL),('ES.05.00','Informática Básica','ES',318,'','2001-12-31','2001-12-31',NULL),('ES.06.00','Teoría de la Computación','ES',671,'CDDCB 111/2009','2009-04-01','2009-12-31',NULL),('ES.07.00','Biometría','ES',1358,'506/12','2001-12-31','2013-12-31',NULL),('FI.01.00','Física Básica','FI',2194,'506/12','2001-12-31','2013-12-31',NULL),('FI.02.00','Física Aplicada','FI',2067,'506/12','2001-12-31','2013-12-31',NULL),('MA.01.00','Matemática en las Ciencias Básicas','MA',2176,'CDDCB 041/2009','2009-02-26','2014-11-06',NULL),('MA.02.00','Matemática en las Ciencias Económicas ','MA',2786,'506/12','2001-12-31','2013-12-31',NULL),('MA.03.00','Mat. en la Agronomía y las Cs Soc. y Hum','MA',3049,'506/12','2001-12-31','2012-12-31',NULL),('QU.01.00','Química General e Inorgánica','QU',81,'506/12','2001-12-31','2013-12-31',NULL),('QU.02.00','Química Orgánica','QU',2592,'506/12','2001-12-31','2013-12-31',NULL),('QU.03.00','Química Biológica','QU',325,'506/12','2001-12-31','2013-12-31',NULL),('QU.04.00','Química Analítica','QU',1640,'506/12','2001-12-31','2013-12-31',NULL);
/*!40000 ALTER TABLE `areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignaturas`
--

DROP TABLE IF EXISTS `asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignaturas` (
  `Codigo` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Area` varchar(8) NOT NULL,
  `Responsable` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `area_idx` (`Area`),
  KEY `responsable_idx` (`Responsable`),
  CONSTRAINT `area` FOREIGN KEY (`Area`) REFERENCES `areas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `resp` FOREIGN KEY (`Responsable`) REFERENCES `docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturas`
--

LOCK TABLES `asignaturas` WRITE;
/*!40000 ALTER TABLE `asignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignaturasxcarreras`
--

DROP TABLE IF EXISTS `asignaturasxcarreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignaturasxcarreras` (
  `asignatura` int(11) NOT NULL,
  `Carrera` int(11) NOT NULL,
  PRIMARY KEY (`asignatura`,`Carrera`),
  KEY `carre_idx` (`Carrera`),
  CONSTRAINT `asig` FOREIGN KEY (`asignatura`) REFERENCES `asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `carre` FOREIGN KEY (`Carrera`) REFERENCES `carreras` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturasxcarreras`
--

LOCK TABLES `asignaturasxcarreras` WRITE;
/*!40000 ALTER TABLE `asignaturasxcarreras` DISABLE KEYS */;
/*!40000 ALTER TABLE `asignaturasxcarreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargos`
--

DROP TABLE IF EXISTS `cargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargos` (
  `Codigo` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  `CargaHoraria` float NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargos`
--

LOCK TABLES `cargos` WRITE;
/*!40000 ALTER TABLE `cargos` DISABLE KEYS */;
INSERT INTO `cargos` VALUES (213,'Profesor Titular Exclusiva',40),(214,'Profesor Titular Semiexclusiva',20),(215,'Profesor Titular Simple',10),(216,'Profesor Asociado Exclusiva',40),(217,'Profesor Asociado Semiexclusiva',20),(218,'Profesor Asociado Simple',10),(219,'Profesor Adjunto Exclusiva',0),(220,'Profesor Adjunto Semiexclusiva',0),(221,'Profesor Adjunto Simple',0),(222,'Jefe de Trabajos Prácticos Exclusiva',0),(223,'Jefe de Trabajos Prácticos Semiexclusiva',18),(224,'Jefe de Trabajos Prácticos Simple',9),(225,'Ayudante de Primera Exclusiva',18),(226,'Ayudante de Primera Semiexclusiva',0),(227,'Ayudante de Primera Simple',9),(228,'Ayudante de Segunda',0),(638,'Bedel',0),(1213,'Profesor Titular Ad-Honorem',9),(1218,'Profesor Asociado Ad-Honorem',9),(1221,'Profesor Adjunto Ad-Honorem',9),(1224,'Jefe de Trabajos Prácticos Ad-Honorem',9),(1227,'Ayudante de Primera Ad-Honorem',9),(1228,'Ayudante de Segunda Ad-Honorem',9);
/*!40000 ALTER TABLE `cargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargosdocentes`
--

DROP TABLE IF EXISTS `cargosdocentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargosdocentes` (
  `Codigo` int(11) NOT NULL,
  `Legajo` int(11) NOT NULL,
  `Area` varchar(8) NOT NULL,
  `Cargo` int(11) NOT NULL,
  `TipoCargo` int(11) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `DispDesde` date DEFAULT NULL,
  `DispHasta` date DEFAULT NULL,
  `Resolucion` varchar(15) DEFAULT NULL,
  `ResDesde` date DEFAULT NULL,
  `ResHasta` date DEFAULT NULL,
  `UltimoCosto` float DEFAULT NULL,
  `FechaUltimoCosto` varchar(45) DEFAULT NULL,
  `EstadoCargo` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `legajo_idx` (`Legajo`),
  KEY `areaDoc_idx` (`Area`),
  KEY `cargo_idx` (`Cargo`),
  KEY `estado_idx` (`EstadoCargo`),
  KEY `fk_CargosDocentes_1_idx` (`TipoCargo`),
  CONSTRAINT `areaDoc` FOREIGN KEY (`Area`) REFERENCES `areas` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cargo` FOREIGN KEY (`Cargo`) REFERENCES `cargos` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `estado` FOREIGN KEY (`EstadoCargo`) REFERENCES `estadoscargos` (`idestadocargo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CargosDocentes_1` FOREIGN KEY (`TipoCargo`) REFERENCES `tiposcargos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `legajo` FOREIGN KEY (`Legajo`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargosdocentes`
--

LOCK TABLES `cargosdocentes` WRITE;
/*!40000 ALTER TABLE `cargosdocentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `cargosdocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargosfaltantes`
--

DROP TABLE IF EXISTS `cargosfaltantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargosfaltantes` (
  `Legajo` int(11) NOT NULL,
  `Apellido` varchar(50) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Cargo` int(11) NOT NULL,
  `UltimoCosto` varchar(45) DEFAULT NULL,
  `FechaUltimoCosto` varchar(45) DEFAULT NULL,
  `Tipo` int(11) NOT NULL,
  PRIMARY KEY (`Legajo`,`Cargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargosfaltantes`
--

LOCK TABLES `cargosfaltantes` WRITE;
/*!40000 ALTER TABLE `cargosfaltantes` DISABLE KEYS */;
/*!40000 ALTER TABLE `cargosfaltantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carreras`
--

DROP TABLE IF EXISTS `carreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carreras` (
  `Codigo` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carreras`
--

LOCK TABLES `carreras` WRITE;
/*!40000 ALTER TABLE `carreras` DISABLE KEYS */;
/*!40000 ALTER TABLE `carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriasinvestigacion`
--

DROP TABLE IF EXISTS `categoriasinvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriasinvestigacion` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriasinvestigacion`
--

LOCK TABLES `categoriasinvestigacion` WRITE;
/*!40000 ALTER TABLE `categoriasinvestigacion` DISABLE KEYS */;
INSERT INTO `categoriasinvestigacion` VALUES (1,'I'),(2,'II'),(3,'III'),(4,'IV'),(0,'No categorizado'),(5,'V');
/*!40000 ALTER TABLE `categoriasinvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `columnas`
--

DROP TABLE IF EXISTS `columnas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `columnas` (
  `TipoInforme` int(11) NOT NULL,
  `Visible` int(11) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Atributo` varchar(45) NOT NULL,
  `Filtros` varchar(100) DEFAULT NULL,
  `Calculo` varchar(45) DEFAULT NULL,
  `Ordenar` int(11) NOT NULL,
  `Posicion` int(11) NOT NULL,
  PRIMARY KEY (`TipoInforme`,`Atributo`),
  CONSTRAINT `fk_Columna_1` FOREIGN KEY (`TipoInforme`) REFERENCES `tiposinformes` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `columnas`
--

LOCK TABLES `columnas` WRITE;
/*!40000 ALTER TABLE `columnas` DISABLE KEYS */;
/*!40000 ALTER TABLE `columnas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comisiones`
--

DROP TABLE IF EXISTS `comisiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comisiones` (
  `Asignatura` int(11) NOT NULL,
  `Numero_Comision` int(11) NOT NULL,
  `Sede` varchar(2) NOT NULL,
  `DiasHoras` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Asignatura`,`Numero_Comision`),
  KEY `sede_idx` (`Sede`),
  CONSTRAINT `asign` FOREIGN KEY (`Asignatura`) REFERENCES `asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sede` FOREIGN KEY (`Sede`) REFERENCES `sedes` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comisiones`
--

LOCK TABLES `comisiones` WRITE;
/*!40000 ALTER TABLE `comisiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `comisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactos`
--

DROP TABLE IF EXISTS `contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactos` (
  `idcontacto` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Tipo` int(11) NOT NULL,
  `Valor` varchar(45) NOT NULL,
  PRIMARY KEY (`idcontacto`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_contacto_1_idx` (`TipoDocumento`,`NroDocumento`),
  KEY `fk_Contacto_2_idx` (`Tipo`),
  CONSTRAINT `fk_Contactos_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contactos_2` FOREIGN KEY (`Tipo`) REFERENCES `tiposcontactos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactos`
--

LOCK TABLES `contactos` WRITE;
/*!40000 ALTER TABLE `contactos` DISABLE KEYS */;
INSERT INTO `contactos` VALUES (0,0,'11242536',0,'semint2018@gmail.com'),(0,0,'12675920',0,'semint2018@gmail.com'),(0,0,'15603702',0,'semint2018@gmail.com'),(0,0,'16405290',0,'semint2018@gmail.com'),(0,0,'17200893',0,'tomasjuran96@gmail.com'),(0,0,'9675920',0,'semint2018@gmail.com');
/*!40000 ALTER TABLE `contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `divisiones`
--

DROP TABLE IF EXISTS `divisiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `divisiones` (
  `Codigo` varchar(2) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  `Jefe` int(11) DEFAULT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` date DEFAULT NULL,
  `Hasta` date DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `jefe_idx` (`Jefe`),
  CONSTRAINT `jefe` FOREIGN KEY (`Jefe`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `divisiones`
--

LOCK TABLES `divisiones` WRITE;
/*!40000 ALTER TABLE `divisiones` DISABLE KEYS */;
INSERT INTO `divisiones` VALUES ('BI','Biología',469,'DD 04/2006','2006-02-14','2009-12-20'),('CO','Computación',94,'DD 13/2012','2006-02-14','2009-12-20'),('ES','Estadísticas y Sistemas',107,'DD 02/2006','2006-02-14','2009-12-20'),('FI','Física',480,'DD 03/2006','2006-02-14','2009-12-20'),('MA','Matemática',57,'DD 05/2006','2006-02-14','2009-12-20'),('QU','Química',104,'DD 06/2006','2006-03-01','2009-12-20');
/*!40000 ALTER TABLE `divisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docentes`
--

DROP TABLE IF EXISTS `docentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docentes` (
  `Legajo` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Observaciones` varchar(100) DEFAULT NULL,
  `CategoriaInvestigacion` int(11) DEFAULT NULL,
  `Estado` int(11) NOT NULL,
  PRIMARY KEY (`Legajo`),
  KEY `fk_docentes_1_idx` (`TipoDocumento`,`NroDocumento`),
  KEY `fk_docentes_2_idx` (`Estado`),
  KEY `fk_docentes_3` (`CategoriaInvestigacion`),
  CONSTRAINT `fk_docentes_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_docentes_2` FOREIGN KEY (`Estado`) REFERENCES `estadosdocentes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_docentes_3` FOREIGN KEY (`CategoriaInvestigacion`) REFERENCES `categoriasinvestigacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docentes`
--

LOCK TABLES `docentes` WRITE;
/*!40000 ALTER TABLE `docentes` DISABLE KEYS */;
INSERT INTO `docentes` VALUES (57,0,'16405290','Jefa de la división Matemática',1,0),(81,0,'9550991','Observaciones de Vázquez Cabeza',1,0),(94,0,'17200893','Jefe de la división Computación',1,0),(104,0,'15603702','Jefa de la división Química',1,0),(107,0,'11242536','Jefe de la división Estadísticas y Sistemas',1,0),(218,0,'9834195','Observaciones de González',1,0),(257,0,'9849483','Observaciones de Lopes Martínez',1,0),(268,0,'9945503','Observaciones de Lafuente',1,0),(318,0,'10117993','Observaciones de Cabo Benítez',1,0),(325,0,'10662538','Observaciones de Peralta San Juan',1,0),(427,0,'10928479','Observaciones de Peralta',1,0),(467,0,'10284846','Observaciones de Castaño',1,0),(469,0,'9675920','Jefe de la división Biología',1,0),(480,0,'12675920','Jefe de la división Física',1,0),(521,0,'10071374','Observaciones de Lozano Peralta',1,0),(671,0,'11915775','Observaciones de Lozano',1,0),(948,0,'12506506','Observaciones de Del Moral',1,0),(1027,0,'12976600','Observaciones de Benítez López',1,0),(1108,0,'12329525','Observaciones de González Muñoz',1,0),(1153,0,'12147287','Observaciones de Cabeza Algaba',1,0),(1193,0,'12579600','Observaciones de Benítez García',1,0),(1221,0,'13522037','Observaciones de González Gutiérrez',1,0),(1229,0,'13582584','Observaciones de Miranda',1,0),(1358,0,'13959331','Observaciones de Sainz Álvarez',1,0),(1640,0,'14871880','Observaciones de Benítez',1,0),(2067,0,'15491157','Observaciones de García',1,0),(2125,0,'16647413','Observaciones de Sainz Cabeza',1,0),(2176,0,'16015748','Observaciones de Lozano Benítez',1,0),(2194,0,'16314292','Observaciones de Iborra Mate',1,0),(2330,0,'16677483','Observaciones de Peralta Castaño',1,0),(2407,0,'17032691','Observaciones de Pérez',1,0),(2459,0,'17645568','Observaciones de Manzano',1,0),(2592,0,'17924559','Observaciones de Peralta Sánchez',1,0),(2609,0,'17112281','Observaciones de Montserrat',1,0),(2647,0,'17504187','Observaciones de López',1,0),(2653,0,'17457547','Observaciones de Peralta Fernández',1,0),(2675,0,'17875356','Observaciones de Mate',1,0),(2786,0,'18661346','Observaciones de Pérez',1,0),(2859,0,'18373319','Observaciones de López Lafuente',1,0),(2924,0,'18178625','Observaciones de Herrero Rubio',1,0),(2974,0,'18663341','Observaciones de Rubio',1,0),(3043,0,'19113505','Observaciones de Gutiérrez Pérez',1,0),(3049,0,'19751064','Observaciones de Peralta García',1,0),(3097,0,'19732040','Observaciones de Martínez Mate',1,0),(143191,0,'39586150','Observaciones de Juran',1,0);
/*!40000 ALTER TABLE `docentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domicilios`
--

DROP TABLE IF EXISTS `domicilios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domicilios` (
  `iddomicilios` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Provincia` varchar(45) NOT NULL,
  `Ciudad` varchar(45) NOT NULL,
  `CodigoPostal` varchar(45) NOT NULL,
  `Direccion` varchar(45) NOT NULL,
  PRIMARY KEY (`iddomicilios`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_domicilios_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_domicilios_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domicilios`
--

LOCK TABLES `domicilios` WRITE;
/*!40000 ALTER TABLE `domicilios` DISABLE KEYS */;
/*!40000 ALTER TABLE `domicilios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equiposdocentes`
--

DROP TABLE IF EXISTS `equiposdocentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equiposdocentes` (
  `Materia` int(11) NOT NULL,
  `Comision` int(11) NOT NULL,
  `planta` int(11) NOT NULL,
  PRIMARY KEY (`Materia`,`Comision`,`planta`),
  KEY `planta_idx` (`planta`),
  CONSTRAINT `comisiones` FOREIGN KEY (`Materia`, `Comision`) REFERENCES `comisiones` (`Asignatura`, `Numero_Comision`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `planta` FOREIGN KEY (`planta`) REFERENCES `cargosdocentes` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equiposdocentes`
--

LOCK TABLES `equiposdocentes` WRITE;
/*!40000 ALTER TABLE `equiposdocentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `equiposdocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadoscargos`
--

DROP TABLE IF EXISTS `estadoscargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadoscargos` (
  `idestadocargo` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idestadocargo`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadoscargos`
--

LOCK TABLES `estadoscargos` WRITE;
/*!40000 ALTER TABLE `estadoscargos` DISABLE KEYS */;
INSERT INTO `estadoscargos` VALUES (0,'Activo'),(1,'Inactivo');
/*!40000 ALTER TABLE `estadoscargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadosdocentes`
--

DROP TABLE IF EXISTS `estadosdocentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadosdocentes` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadosdocentes`
--

LOCK TABLES `estadosdocentes` WRITE;
/*!40000 ALTER TABLE `estadosdocentes` DISABLE KEYS */;
INSERT INTO `estadosdocentes` VALUES (0,'Activo'),(5,'Fallecido'),(1,'Inactivo'),(2,'Jubilado'),(6,'Licencia'),(4,'Renuncia'),(3,'Retiro');
/*!40000 ALTER TABLE `estadosdocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadospersonas`
--

DROP TABLE IF EXISTS `estadospersonas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadospersonas` (
  `idEstado` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstado`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadospersonas`
--

LOCK TABLES `estadospersonas` WRITE;
/*!40000 ALTER TABLE `estadospersonas` DISABLE KEYS */;
INSERT INTO `estadospersonas` VALUES (0,'Activa'),(1,'Inactiva');
/*!40000 ALTER TABLE `estadospersonas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadosprogramas`
--

DROP TABLE IF EXISTS `estadosprogramas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadosprogramas` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadosprogramas`
--

LOCK TABLES `estadosprogramas` WRITE;
/*!40000 ALTER TABLE `estadosprogramas` DISABLE KEYS */;
/*!40000 ALTER TABLE `estadosprogramas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadosproyectos`
--

DROP TABLE IF EXISTS `estadosproyectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadosproyectos` (
  `idEstadoProyecto` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstadoProyecto`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadosproyectos`
--

LOCK TABLES `estadosproyectos` WRITE;
/*!40000 ALTER TABLE `estadosproyectos` DISABLE KEYS */;
/*!40000 ALTER TABLE `estadosproyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenes`
--

DROP TABLE IF EXISTS `examenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examenes` (
  `Materia` int(11) NOT NULL,
  `sede` varchar(2) NOT NULL,
  `Fecha` date NOT NULL,
  `Horario` varchar(15) NOT NULL,
  `Turno` varchar(6) DEFAULT NULL,
  `Docente1` int(11) DEFAULT NULL,
  `Docente2` int(11) DEFAULT NULL,
  `Docente3` int(11) DEFAULT NULL,
  PRIMARY KEY (`Materia`,`sede`,`Fecha`),
  KEY `sedeExam_idx` (`sede`),
  KEY `doc1_idx` (`Docente1`),
  KEY `doc2_idx` (`Docente2`),
  KEY `doc3_idx` (`Docente3`),
  CONSTRAINT `doc1` FOREIGN KEY (`Docente1`) REFERENCES `docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `doc2` FOREIGN KEY (`Docente2`) REFERENCES `docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `doc3` FOREIGN KEY (`Docente3`) REFERENCES `docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `materia` FOREIGN KEY (`Materia`) REFERENCES `asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sedeExam` FOREIGN KEY (`sede`) REFERENCES `sedes` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examenes`
--

LOCK TABLES `examenes` WRITE;
/*!40000 ALTER TABLE `examenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `examenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incentivos`
--

DROP TABLE IF EXISTS `incentivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incentivos` (
  `Legajo` int(11) NOT NULL,
  `Fecha` varchar(4) NOT NULL,
  PRIMARY KEY (`Legajo`,`Fecha`),
  CONSTRAINT `fk_Incentivos_1` FOREIGN KEY (`Legajo`) REFERENCES `docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incentivos`
--

LOCK TABLES `incentivos` WRITE;
/*!40000 ALTER TABLE `incentivos` DISABLE KEYS */;
/*!40000 ALTER TABLE `incentivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `integrantes`
--

DROP TABLE IF EXISTS `integrantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `integrantes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CargoDocente` int(11) DEFAULT NULL,
  `Apellido` varchar(45) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Cargo` varchar(45) DEFAULT NULL,
  `Institucion` varchar(50) DEFAULT NULL,
  `HorasSemanales` int(11) DEFAULT NULL,
  `Proyecto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Integrante_2_idx` (`Proyecto`),
  KEY `fk_Integrantes_1_idx` (`CargoDocente`),
  CONSTRAINT `fk_Integrante_2` FOREIGN KEY (`Proyecto`) REFERENCES `proyectos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Integrantes_1` FOREIGN KEY (`CargoDocente`) REFERENCES `cargosdocentes` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `integrantes`
--

LOCK TABLES `integrantes` WRITE;
/*!40000 ALTER TABLE `integrantes` DISABLE KEYS */;
/*!40000 ALTER TABLE `integrantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulos`
--

DROP TABLE IF EXISTS `modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modulos` (
  `idmodulo` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idmodulo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulos`
--

LOCK TABLES `modulos` WRITE;
/*!40000 ALTER TABLE `modulos` DISABLE KEYS */;
INSERT INTO `modulos` VALUES (0,'GENERAL'),(1,'ESTACION'),(2,'GRUPOS'),(3,'USUARIOS'),(4,'CARGOS'),(5,'DOCENTES'),(6,'INVESTIGACION'),(7,'VER_INFORMES'),(8,'EDIT_INFORMES');
/*!40000 ALTER TABLE `modulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permisos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Rol` int(11) NOT NULL,
  `Crear` tinyint(4) NOT NULL,
  `Eliminar` tinyint(4) NOT NULL,
  `Modificar` tinyint(4) NOT NULL,
  `Listar` tinyint(4) NOT NULL,
  `Modulo` int(11) NOT NULL,
  PRIMARY KEY (`id`,`Rol`),
  KEY `fk_permisos_1_idx` (`Modulo`),
  KEY `fk_Permiso_1_idx` (`Rol`),
  CONSTRAINT `fk_Permiso_1` FOREIGN KEY (`Rol`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Permiso_2` FOREIGN KEY (`Modulo`) REFERENCES `modulos` (`idmodulo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personas` (
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Apellido` varchar(50) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `Estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`TipoDocumento`,`NroDocumento`),
  KEY `EstPersona_idx` (`Estado`),
  CONSTRAINT `EstPersona` FOREIGN KEY (`Estado`) REFERENCES `estadospersonas` (`idEstado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tipoDoc` FOREIGN KEY (`TipoDocumento`) REFERENCES `tiposdocumentos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` VALUES (0,'10071374','Lozano Peralta','Guillermo Javier','1950-12-31',0),(0,'10117993','Cabo Benítez','Jorge','1948-08-06',0),(0,'10284846','Castaño','Juan','1949-07-10',0),(0,'10662538','Peralta San Juan','Leonardo','1948-01-11',0),(0,'10928479','Peralta','Ramón','1949-09-04',0),(0,'11242536','Urritiaga','Daniel Alfonso','1951-05-12',0),(0,'11915775','Lozano','Carmen Rosario','1951-08-01',0),(0,'12147287','Cabeza Algaba','Vicente','1956-09-09',0),(0,'12329525','González Muñoz','Jorge Carlos','1956-08-14',0),(0,'12506506','Del Moral','Francisco Guillermo','1954-12-29',0),(0,'12579600','Benítez García','Francisco Jorge','1956-09-04',0),(0,'12675920','Alcaraz','Marcelo Fernando','1955-11-08',0),(0,'12976600','Benítez López','Pilar Laura','1955-06-12',0),(0,'13522037','González Gutiérrez','Vicente','1957-07-30',0),(0,'13582584','Miranda','Martín','1957-09-29',0),(0,'13959331','Sainz Álvarez','Leonardo','1958-02-25',0),(0,'14871880','Benítez','Elías Adán','1961-12-06',0),(0,'15491157','García','Julián','1965-05-11',0),(0,'15603702','Aramburu','Graciela','1959-07-10',0),(0,'16015748','Lozano Benítez','Carmen María','1966-02-11',0),(0,'16314292','Iborra Mate','Rosario','1966-12-31',0),(0,'16405290','Fernandez Perez','Julieta','1964-02-28',0),(0,'16647413','Sainz Cabeza','Dolores Ana','1966-01-21',0),(0,'16677483','Peralta Castaño','Juan Nicolás','1968-11-03',0),(0,'17032691','Pérez','Óscar','1969-09-21',0),(0,'17112281','Montserrat','Martín Javier','1971-05-13',0),(0,'17200893','Cisneros','Guillermo','1967-10-02',0),(0,'17457547','Peralta Fernández','Ezequiel José','1971-08-13',0),(0,'17504187','López','Adán Antonio','1971-05-02',0),(0,'17645568','Manzano','María','1969-02-04',0),(0,'17875356','Mate','Cristina','1971-10-09',0),(0,'17924559','Peralta Sánchez','Nicolás','1970-04-09',0),(0,'18178625','Herrero Rubio','Nuria','1974-05-01',0),(0,'18373319','López Lafuente','Martín','1973-10-27',0),(0,'18661346','Pérez','Rosa','1972-06-30',0),(0,'18663341','Rubio','Leonardo','1974-05-22',0),(0,'19113505','Gutiérrez Pérez','Óscar','1975-05-26',0),(0,'19732040','Martínez Mate','Laura','1975-07-04',0),(0,'19751064','Peralta García','Óscar','1975-06-30',0),(0,'39586150','Juran','Martín Tomás','1996-06-29',0),(0,'9550991','Vázquez Cabeza','Carmen Rosario','1945-01-25',0),(0,'9675920','Gonzalez','Julián','1945-03-29',0),(0,'9834195','González','Adán','1947-02-21',0),(0,'9849483','Lopes Martínez','Ana','1947-09-09',0),(0,'9945503','Lafuente','Dolores','1945-10-17',0);
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programasasignaturas`
--

DROP TABLE IF EXISTS `programasasignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programasasignaturas` (
  `asignatura` int(11) NOT NULL,
  `Carrera` int(11) NOT NULL,
  `vigencia` date NOT NULL,
  `disposicion` varchar(45) NOT NULL,
  `Fecha_Entrega` date DEFAULT NULL,
  `FechaCPE` date DEFAULT NULL,
  `FechaCDD` date DEFAULT NULL,
  PRIMARY KEY (`asignatura`,`Carrera`,`vigencia`,`disposicion`),
  KEY `carrera_idx` (`Carrera`),
  CONSTRAINT `asignatura` FOREIGN KEY (`asignatura`) REFERENCES `asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `carrera` FOREIGN KEY (`Carrera`) REFERENCES `carreras` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programasasignaturas`
--

LOCK TABLES `programasasignaturas` WRITE;
/*!40000 ALTER TABLE `programasasignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `programasasignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programasinvestigacion`
--

DROP TABLE IF EXISTS `programasinvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programasinvestigacion` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Director` int(11) NOT NULL,
  `Codirector` int(11) DEFAULT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` date DEFAULT NULL,
  `Hasta` date DEFAULT NULL,
  `Estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `doc_pro_idx` (`Director`,`Codirector`),
  KEY `fk_ProgramasInvestigacion_2_idx` (`Codirector`),
  KEY `fk_ProgramasInvestigacion_3_idx` (`Estado`),
  CONSTRAINT `fk_ProgramasInvestigacion_1` FOREIGN KEY (`Director`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProgramasInvestigacion_2` FOREIGN KEY (`Codirector`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProgramasInvestigacion_3` FOREIGN KEY (`Estado`) REFERENCES `estadosprogramas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programasinvestigacion`
--

LOCK TABLES `programasinvestigacion` WRITE;
/*!40000 ALTER TABLE `programasinvestigacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `programasinvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prorrogas`
--

DROP TABLE IF EXISTS `prorrogas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prorrogas` (
  `Proyecto` int(11) NOT NULL,
  `Disposicion` varchar(15) NOT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`Proyecto`,`Disposicion`),
  CONSTRAINT `fk_Prorrogas_1` FOREIGN KEY (`Proyecto`) REFERENCES `proyectos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prorrogas`
--

LOCK TABLES `prorrogas` WRITE;
/*!40000 ALTER TABLE `prorrogas` DISABLE KEYS */;
/*!40000 ALTER TABLE `prorrogas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyectos`
--

DROP TABLE IF EXISTS `proyectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyectos` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Resumen` varchar(100) DEFAULT NULL,
  `FechaPresentacion` date NOT NULL,
  `FechaAprobacion` date DEFAULT NULL,
  `Descripcion` varchar(100) DEFAULT NULL,
  `Director` int(11) NOT NULL,
  `Codirector` int(11) DEFAULT NULL,
  `FechaInicio` date DEFAULT NULL,
  `Fecha_Fin` date DEFAULT NULL,
  `Programa` int(11) DEFAULT NULL,
  `Estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dir_idx` (`Director`),
  KEY `programa_idx` (`Programa`),
  KEY `fk_Proyectos_1_idx` (`Codirector`),
  KEY `fk_Proyectos_2_idx` (`Estado`),
  CONSTRAINT `dir` FOREIGN KEY (`Director`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Proyectos_1` FOREIGN KEY (`Codirector`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Proyectos_2` FOREIGN KEY (`Estado`) REFERENCES `estadosproyectos` (`idEstadoProyecto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `programainves` FOREIGN KEY (`Programa`) REFERENCES `programasinvestigacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyectos`
--

LOCK TABLES `proyectos` WRITE;
/*!40000 ALTER TABLE `proyectos` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rendiciones`
--

DROP TABLE IF EXISTS `rendiciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendiciones` (
  `id` int(11) NOT NULL,
  `Proyecto` int(11) NOT NULL,
  `YearSubsidio` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Monto` float NOT NULL,
  `Observaciones` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`Proyecto`,`YearSubsidio`),
  KEY `fk_Rendiciones_1_idx` (`Proyecto`,`YearSubsidio`),
  CONSTRAINT `fk_Rendiciones_1` FOREIGN KEY (`Proyecto`, `YearSubsidio`) REFERENCES `subsidios` (`Proyecto`, `Year`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rendiciones`
--

LOCK TABLES `rendiciones` WRITE;
/*!40000 ALTER TABLE `rendiciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `rendiciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rolesxusuario`
--

DROP TABLE IF EXISTS `rolesxusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rolesxusuario` (
  `Usuario` varchar(45) NOT NULL,
  `Rol` int(11) NOT NULL,
  PRIMARY KEY (`Usuario`,`Rol`),
  KEY `fk_rolesxusuario_2_idx` (`Rol`),
  CONSTRAINT `fk_RolesXUsuario_1` FOREIGN KEY (`Usuario`) REFERENCES `usuarios` (`Usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rolesxusuario_2` FOREIGN KEY (`Rol`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rolesxusuario`
--

LOCK TABLES `rolesxusuario` WRITE;
/*!40000 ALTER TABLE `rolesxusuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `rolesxusuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sedes`
--

DROP TABLE IF EXISTS `sedes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sedes` (
  `Codigo` varchar(2) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sedes`
--

LOCK TABLES `sedes` WRITE;
/*!40000 ALTER TABLE `sedes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sedes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subsidios`
--

DROP TABLE IF EXISTS `subsidios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subsidios` (
  `Proyecto` int(11) NOT NULL,
  `Year` int(11) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `MontoTotal` double NOT NULL,
  `Observaciones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Proyecto`,`Year`),
  CONSTRAINT `fk_Subsidios_1` FOREIGN KEY (`Proyecto`) REFERENCES `proyectos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subsidios`
--

LOCK TABLES `subsidios` WRITE;
/*!40000 ALTER TABLE `subsidios` DISABLE KEYS */;
/*!40000 ALTER TABLE `subsidios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposcargos`
--

DROP TABLE IF EXISTS `tiposcargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposcargos` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposcargos`
--

LOCK TABLES `tiposcargos` WRITE;
/*!40000 ALTER TABLE `tiposcargos` DISABLE KEYS */;
INSERT INTO `tiposcargos` VALUES (1,'Interino'),(0,'Ordinario');
/*!40000 ALTER TABLE `tiposcargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposcontactos`
--

DROP TABLE IF EXISTS `tiposcontactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposcontactos` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposcontactos`
--

LOCK TABLES `tiposcontactos` WRITE;
/*!40000 ALTER TABLE `tiposcontactos` DISABLE KEYS */;
INSERT INTO `tiposcontactos` VALUES (0,'Mail Laboral'),(1,'Mail Personal'),(4,'Teléfono Celular'),(2,'Teléfono Laboral'),(3,'Teléfono Particular');
/*!40000 ALTER TABLE `tiposcontactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposdocumentos`
--

DROP TABLE IF EXISTS `tiposdocumentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposdocumentos` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposdocumentos`
--

LOCK TABLES `tiposdocumentos` WRITE;
/*!40000 ALTER TABLE `tiposdocumentos` DISABLE KEYS */;
INSERT INTO `tiposdocumentos` VALUES (0,'DNI');
/*!40000 ALTER TABLE `tiposdocumentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposinformes`
--

DROP TABLE IF EXISTS `tiposinformes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposinformes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(45) DEFAULT NULL,
  `Editable` int(11) NOT NULL,
  `FromString` varchar(50) DEFAULT NULL,
  `GroupByString` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposinformes`
--

LOCK TABLES `tiposinformes` WRITE;
/*!40000 ALTER TABLE `tiposinformes` DISABLE KEYS */;
/*!40000 ALTER TABLE `tiposinformes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `titulos`
--

DROP TABLE IF EXISTS `titulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `titulos` (
  `id` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `EsMayor` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_titulos_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_titulos_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `titulos`
--

LOCK TABLES `titulos` WRITE;
/*!40000 ALTER TABLE `titulos` DISABLE KEYS */;
/*!40000 ALTER TABLE `titulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `Usuario` varchar(45) NOT NULL,
  `Hash` varchar(200) NOT NULL,
  `Salt` varchar(200) NOT NULL,
  `TipoDocumentoPersona` int(11) NOT NULL,
  `NroDocumentoPerson` varchar(10) NOT NULL,
  `Descripcion` varchar(50) NOT NULL,
  PRIMARY KEY (`Usuario`),
  UNIQUE KEY `Usuario_UNIQUE` (`Usuario`),
  KEY `fk_Usuario_1_idx` (`TipoDocumentoPersona`,`NroDocumentoPerson`),
  CONSTRAINT `fk_Usuario_1` FOREIGN KEY (`TipoDocumentoPersona`, `NroDocumentoPerson`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `viewarea`
--

DROP TABLE IF EXISTS `viewarea`;
/*!50001 DROP VIEW IF EXISTS `viewarea`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewarea` AS SELECT 
 1 AS `Codigo`,
 1 AS `Descripcion`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewcargo`
--

DROP TABLE IF EXISTS `viewcargo`;
/*!50001 DROP VIEW IF EXISTS `viewcargo`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewcargo` AS SELECT 
 1 AS `Descripcion`,
 1 AS `Codigo`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewcargodocente`
--

DROP TABLE IF EXISTS `viewcargodocente`;
/*!50001 DROP VIEW IF EXISTS `viewcargodocente`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewcargodocente` AS SELECT 
 1 AS `Legajo`,
 1 AS `Nombre`,
 1 AS `Apellido`,
 1 AS `Area`,
 1 AS `descripcion`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewdocente`
--

DROP TABLE IF EXISTS `viewdocente`;
/*!50001 DROP VIEW IF EXISTS `viewdocente`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewdocente` AS SELECT 
 1 AS `Legajo`,
 1 AS `Nombre`,
 1 AS `Apellido`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewpersona`
--

DROP TABLE IF EXISTS `viewpersona`;
/*!50001 DROP VIEW IF EXISTS `viewpersona`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewpersona` AS SELECT 
 1 AS `NroDocumento`,
 1 AS `Nombre`,
 1 AS `Apellido`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewproyecto`
--

DROP TABLE IF EXISTS `viewproyecto`;
/*!50001 DROP VIEW IF EXISTS `viewproyecto`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewproyecto` AS SELECT 
 1 AS `id`,
 1 AS `Nombre`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewrol`
--

DROP TABLE IF EXISTS `viewrol`;
/*!50001 DROP VIEW IF EXISTS `viewrol`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewrol` AS SELECT 
 1 AS `id`,
 1 AS `nombre`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `viewusuario`
--

DROP TABLE IF EXISTS `viewusuario`;
/*!50001 DROP VIEW IF EXISTS `viewusuario`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewusuario` AS SELECT 
 1 AS `Usuario`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'plumasdocentes'
--

--
-- Dumping routines for database 'plumasdocentes'
--

--
-- Final view structure for view `viewarea`
--

/*!50001 DROP VIEW IF EXISTS `viewarea`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewarea` AS select `areas`.`Codigo` AS `Codigo`,`areas`.`Descripcion` AS `Descripcion` from `areas` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewcargo`
--

/*!50001 DROP VIEW IF EXISTS `viewcargo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewcargo` AS select `cargos`.`Descripcion` AS `Descripcion`,`cargos`.`Codigo` AS `Codigo` from `cargos` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewcargodocente`
--

/*!50001 DROP VIEW IF EXISTS `viewcargodocente`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewcargodocente` AS select `cd`.`Legajo` AS `Legajo`,`p`.`Nombre` AS `Nombre`,`p`.`Apellido` AS `Apellido`,`cd`.`Area` AS `Area`,`c`.`Descripcion` AS `descripcion` from ((`cargosdocentes` `cd` join (`docentes` `d` join `personas` `p` on(((`d`.`TipoDocumento` = `p`.`TipoDocumento`) and (`d`.`NroDocumento` = `p`.`NroDocumento`)))) on((`cd`.`Legajo` = `d`.`Legajo`))) join `cargos` `c` on((`cd`.`Cargo` = `c`.`Codigo`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewdocente`
--

/*!50001 DROP VIEW IF EXISTS `viewdocente`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewdocente` AS select `docentes`.`Legajo` AS `Legajo`,`personas`.`Nombre` AS `Nombre`,`personas`.`Apellido` AS `Apellido` from (`docentes` join `personas` on(((`docentes`.`TipoDocumento` = `personas`.`TipoDocumento`) and (`docentes`.`NroDocumento` = `personas`.`NroDocumento`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewpersona`
--

/*!50001 DROP VIEW IF EXISTS `viewpersona`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewpersona` AS select `p`.`NroDocumento` AS `NroDocumento`,`p`.`Nombre` AS `Nombre`,`p`.`Apellido` AS `Apellido` from `personas` `p` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewproyecto`
--

/*!50001 DROP VIEW IF EXISTS `viewproyecto`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewproyecto` AS select `proyectos`.`id` AS `id`,`proyectos`.`Nombre` AS `Nombre` from `proyectos` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewrol`
--

/*!50001 DROP VIEW IF EXISTS `viewrol`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewrol` AS select `roles`.`id` AS `id`,`roles`.`nombre` AS `nombre` from `roles` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewusuario`
--

/*!50001 DROP VIEW IF EXISTS `viewusuario`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewusuario` AS select `usuarios`.`Usuario` AS `Usuario` from `usuarios` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-12 11:24:26
