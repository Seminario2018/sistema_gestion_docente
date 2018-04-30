-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: plumasdocentes
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

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
-- Table structure for table `CategoriaInvestigacion`
--

DROP TABLE IF EXISTS `CategoriaInvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CategoriaInvestigacion` (
  `idCategoriaInvestigacion` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idCategoriaInvestigacion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CategoriaInvestigacion`
--

LOCK TABLES `CategoriaInvestigacion` WRITE;
/*!40000 ALTER TABLE `CategoriaInvestigacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `CategoriaInvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Columna`
--

DROP TABLE IF EXISTS `Columna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Columna` (
  `idTipoInforme` int(11) NOT NULL,
  `Atributo` varchar(45) NOT NULL,
  `Calculo` varchar(45) DEFAULT NULL,
  `filtros` varchar(45) DEFAULT NULL,
  `ordenar` int(11) NOT NULL,
  `posicion` int(11) NOT NULL,
  PRIMARY KEY (`idTipoInforme`,`Atributo`),
  CONSTRAINT `fk_Columna_1` FOREIGN KEY (`idTipoInforme`) REFERENCES `TipoInforme` (`idTipoInforme`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Columna`
--

LOCK TABLES `Columna` WRITE;
/*!40000 ALTER TABLE `Columna` DISABLE KEYS */;
/*!40000 ALTER TABLE `Columna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoDocente`
--

DROP TABLE IF EXISTS `EstadoDocente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoDocente` (
  `idEstadoDocente` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstadoDocente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoDocente`
--

LOCK TABLES `EstadoDocente` WRITE;
/*!40000 ALTER TABLE `EstadoDocente` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadoDocente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoPersona`
--

DROP TABLE IF EXISTS `EstadoPersona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoPersona` (
  `idEstado` int(11) NOT NULL,
  `Descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoPersona`
--

LOCK TABLES `EstadoPersona` WRITE;
/*!40000 ALTER TABLE `EstadoPersona` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadoPersona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoPrograma`
--

DROP TABLE IF EXISTS `EstadoPrograma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoPrograma` (
  `idEstado` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoPrograma`
--

LOCK TABLES `EstadoPrograma` WRITE;
/*!40000 ALTER TABLE `EstadoPrograma` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadoPrograma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadoProyecto`
--

DROP TABLE IF EXISTS `EstadoProyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadoProyecto` (
  `idEstadoProyecto` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstadoProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadoProyecto`
--

LOCK TABLES `EstadoProyecto` WRITE;
/*!40000 ALTER TABLE `EstadoProyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadoProyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Integrante`
--

DROP TABLE IF EXISTS `Integrante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Integrante` (
  `idnew_table` int(11) NOT NULL AUTO_INCREMENT,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Institucion` varchar(50) NOT NULL,
  `Cargo` varchar(45) NOT NULL,
  `HorasSemanales` double NOT NULL,
  `Proyecto` int(11) NOT NULL,
  PRIMARY KEY (`idnew_table`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_Integrante_1_idx` (`TipoDocumento`,`NroDocumento`),
  KEY `fk_Integrante_2_idx` (`Proyecto`),
  CONSTRAINT `fk_Integrante_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Persona` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Integrante_2` FOREIGN KEY (`Proyecto`) REFERENCES `proyecto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Integrante`
--

LOCK TABLES `Integrante` WRITE;
/*!40000 ALTER TABLE `Integrante` DISABLE KEYS */;
/*!40000 ALTER TABLE `Integrante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Persona`
--

DROP TABLE IF EXISTS `Persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Persona` (
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Apellido` varchar(50) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `FechaNacimiento` datetime DEFAULT NULL,
  `Estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`TipoDocumento`,`NroDocumento`),
  KEY `EstPersona_idx` (`Estado`),
  CONSTRAINT `EstPersona` FOREIGN KEY (`Estado`) REFERENCES `EstadoPersona` (`idEstado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tipoDoc` FOREIGN KEY (`TipoDocumento`) REFERENCES `tipos_documentos` (`idTipo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Persona`
--

LOCK TABLES `Persona` WRITE;
/*!40000 ALTER TABLE `Persona` DISABLE KEYS */;
/*!40000 ALTER TABLE `Persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RolesXUsuario`
--

DROP TABLE IF EXISTS `RolesXUsuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RolesXUsuario` (
  `idUsuario` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idRol`),
  KEY `fk_RolesXUsuario_2_idx` (`idRol`),
  CONSTRAINT `fk_RolesXUsuario_1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_RolesXUsuario_2` FOREIGN KEY (`idRol`) REFERENCES `roles` (`idrol`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RolesXUsuario`
--

LOCK TABLES `RolesXUsuario` WRITE;
/*!40000 ALTER TABLE `RolesXUsuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `RolesXUsuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoInforme`
--

DROP TABLE IF EXISTS `TipoInforme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TipoInforme` (
  `idTipoInforme` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `from` varchar(50) NOT NULL,
  `TipoInformecol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTipoInforme`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoInforme`
--

LOCK TABLES `TipoInforme` WRITE;
/*!40000 ALTER TABLE `TipoInforme` DISABLE KEYS */;
/*!40000 ALTER TABLE `TipoInforme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `Usuario` varchar(45) NOT NULL,
  `Hash` blob NOT NULL,
  `Salt` blob NOT NULL,
  `TipoDocumentoPersona` int(11) NOT NULL,
  `NroDocumentoPerson` varchar(10) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `Usuario_UNIQUE` (`Usuario`),
  KEY `fk_Usuario_1_idx` (`TipoDocumentoPersona`,`NroDocumentoPerson`),
  CONSTRAINT `fk_Usuario_1` FOREIGN KEY (`TipoDocumentoPersona`, `NroDocumentoPerson`) REFERENCES `Persona` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `areas`
--

DROP TABLE IF EXISTS `areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `areas` (
  `Codigo` varchar(8) NOT NULL,
  `Descripcion` varchar(50) NOT NULL,
  `Division` varchar(2) NOT NULL,
  `Responsable` int(11) NOT NULL,
  `Disposicion` varchar(15) NOT NULL,
  `Desde` date NOT NULL,
  `Hasta` date DEFAULT NULL,
  `Subarea_De` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `division_idx` (`Division`),
  KEY `responsable_idx` (`Responsable`),
  KEY `disposicion_idx` (`Disposicion`),
  KEY `subarea_idx` (`Subarea_De`),
  CONSTRAINT `disp` FOREIGN KEY (`Disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `division` FOREIGN KEY (`Division`) REFERENCES `divisiones` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `responsable` FOREIGN KEY (`Responsable`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `subarea` FOREIGN KEY (`Subarea_De`) REFERENCES `areas` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas`
--

LOCK TABLES `areas` WRITE;
/*!40000 ALTER TABLE `areas` DISABLE KEYS */;
/*!40000 ALTER TABLE `areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignaturas`
--

DROP TABLE IF EXISTS `asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignaturas` (
  `codigo` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Area` varchar(8) NOT NULL,
  `Responsable` int(11) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `area_idx` (`Area`),
  KEY `responsable_idx` (`Responsable`),
  CONSTRAINT `area` FOREIGN KEY (`Area`) REFERENCES `areas` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `resp` FOREIGN KEY (`Responsable`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  CONSTRAINT `asig` FOREIGN KEY (`asignatura`) REFERENCES `asignaturas` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `carre` FOREIGN KEY (`Carrera`) REFERENCES `carreras` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
/*!40000 ALTER TABLE `cargos` ENABLE KEYS */;
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
  CONSTRAINT `asign` FOREIGN KEY (`Asignatura`) REFERENCES `asignaturas` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sede` FOREIGN KEY (`Sede`) REFERENCES `sedes` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Table structure for table `contacto`
--

DROP TABLE IF EXISTS `contacto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacto` (
  `idcontacto` int(11) NOT NULL AUTO_INCREMENT,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Tipo` varchar(45) NOT NULL,
  `Valor` varchar(45) NOT NULL,
  PRIMARY KEY (`idcontacto`,`TipoDocumento`,`NroDocumento`,`Nombre`,`Tipo`,`Valor`),
  KEY `fk_contacto_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_contacto_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Persona` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacto`
--

LOCK TABLES `contacto` WRITE;
/*!40000 ALTER TABLE `contacto` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `costos`
--

DROP TABLE IF EXISTS `costos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `costos` (
  `id_costos` int(11) NOT NULL AUTO_INCREMENT,
  `Codigo_Planta` int(11) NOT NULL,
  `costo` double NOT NULL,
  `Desde` date NOT NULL,
  `Hasta` date DEFAULT NULL,
  PRIMARY KEY (`id_costos`,`Codigo_Planta`),
  KEY `costo_idx` (`Codigo_Planta`),
  CONSTRAINT `costo` FOREIGN KEY (`Codigo_Planta`) REFERENCES `planta` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costos`
--

LOCK TABLES `costos` WRITE;
/*!40000 ALTER TABLE `costos` DISABLE KEYS */;
/*!40000 ALTER TABLE `costos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disposiciones`
--

DROP TABLE IF EXISTS `disposiciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `disposiciones` (
  `id` varchar(15) NOT NULL,
  `Desde` date NOT NULL,
  `Hasta` date NOT NULL,
  `Recurso` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disposiciones`
--

LOCK TABLES `disposiciones` WRITE;
/*!40000 ALTER TABLE `disposiciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `disposiciones` ENABLE KEYS */;
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
  `Jefe` int(11) NOT NULL,
  `Disposicion` varchar(15) NOT NULL,
  `Desde` date NOT NULL,
  `Hasta` date DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `disposicion_idx` (`Disposicion`),
  KEY `jefe_idx` (`Jefe`),
  CONSTRAINT `disposicion` FOREIGN KEY (`Disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `jefe` FOREIGN KEY (`Jefe`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `divisiones`
--

LOCK TABLES `divisiones` WRITE;
/*!40000 ALTER TABLE `divisiones` DISABLE KEYS */;
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
  KEY `fk_docentes_3_idx` (`CategoriaInvestigacion`),
  CONSTRAINT `fk_docentes_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Persona` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_docentes_2` FOREIGN KEY (`Estado`) REFERENCES `EstadoDocente` (`idEstadoDocente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_docentes_3` FOREIGN KEY (`CategoriaInvestigacion`) REFERENCES `CategoriaInvestigacion` (`idCategoriaInvestigacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docentes`
--

LOCK TABLES `docentes` WRITE;
/*!40000 ALTER TABLE `docentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `docentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domicilios`
--

DROP TABLE IF EXISTS `domicilios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domicilios` (
  `iddomicilios` int(11) NOT NULL AUTO_INCREMENT,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Provincia` varchar(45) NOT NULL,
  `Ciudad` varchar(45) NOT NULL,
  `CodigoPostal` varchar(45) NOT NULL,
  `Domicilio` varchar(45) NOT NULL,
  PRIMARY KEY (`iddomicilios`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_domicilios_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_domicilios_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Persona` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `materia` int(11) NOT NULL,
  `comision` int(11) NOT NULL,
  `planta` int(11) NOT NULL,
  PRIMARY KEY (`materia`,`comision`,`planta`),
  KEY `planta_idx` (`planta`),
  CONSTRAINT `comisiones` FOREIGN KEY (`materia`, `comision`) REFERENCES `comisiones` (`Asignatura`, `Numero_Comision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `planta` FOREIGN KEY (`planta`) REFERENCES `planta` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estados` (
  `Codigo` varchar(2) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenes`
--

DROP TABLE IF EXISTS `examenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examenes` (
  `materia` int(11) NOT NULL,
  `sede` varchar(2) NOT NULL,
  `Fecha` date NOT NULL,
  `Horario` varchar(15) NOT NULL,
  `Turno` varchar(6) DEFAULT NULL,
  `Docente1` int(11) DEFAULT NULL,
  `Docente2` int(11) DEFAULT NULL,
  `Docente3` int(11) DEFAULT NULL,
  PRIMARY KEY (`materia`,`sede`,`Fecha`),
  KEY `sedeExam_idx` (`sede`),
  KEY `doc1_idx` (`Docente1`),
  KEY `doc2_idx` (`Docente2`),
  KEY `doc3_idx` (`Docente3`),
  CONSTRAINT `doc1` FOREIGN KEY (`Docente1`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `doc2` FOREIGN KEY (`Docente2`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `doc3` FOREIGN KEY (`Docente3`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `materia` FOREIGN KEY (`materia`) REFERENCES `asignaturas` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sedeExam` FOREIGN KEY (`sede`) REFERENCES `sedes` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Table structure for table `incentivo`
--

DROP TABLE IF EXISTS `incentivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incentivo` (
  `idincentivo` int(11) NOT NULL,
  `docente` int(11) NOT NULL,
  `Monto` double DEFAULT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Fecha_Desde` date DEFAULT NULL,
  `Fecha_Hasta` date DEFAULT NULL,
  PRIMARY KEY (`idincentivo`,`docente`),
  KEY `doc_idx` (`docente`),
  CONSTRAINT `doc` FOREIGN KEY (`docente`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incentivo`
--

LOCK TABLES `incentivo` WRITE;
/*!40000 ALTER TABLE `incentivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `incentivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulo`
--

DROP TABLE IF EXISTS `modulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modulo` (
  `idmodulo` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idmodulo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulo`
--

LOCK TABLES `modulo` WRITE;
/*!40000 ALTER TABLE `modulo` DISABLE KEYS */;
/*!40000 ALTER TABLE `modulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permisos` (
  `idpermisos` int(11) NOT NULL AUTO_INCREMENT,
  `crear` tinyint(4) NOT NULL,
  `eliminar` tinyint(4) NOT NULL,
  `modificar` tinyint(4) NOT NULL,
  `listar` tinyint(4) NOT NULL,
  `permisoscol` int(11) DEFAULT NULL,
  PRIMARY KEY (`idpermisos`),
  KEY `fk_permisos_1_idx` (`permisoscol`),
  CONSTRAINT `fk_permisos_1` FOREIGN KEY (`permisoscol`) REFERENCES `modulo` (`idmodulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisosXrol`
--

DROP TABLE IF EXISTS `permisosXrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permisosXrol` (
  `idpermiso` int(11) NOT NULL,
  `idrol` int(11) NOT NULL,
  PRIMARY KEY (`idpermiso`,`idrol`),
  KEY `fk_permisosXrol_2_idx` (`idrol`),
  CONSTRAINT `fk_permisosXrol_1` FOREIGN KEY (`idpermiso`) REFERENCES `permisos` (`idpermisos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_permisosXrol_2` FOREIGN KEY (`idrol`) REFERENCES `roles` (`idrol`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisosXrol`
--

LOCK TABLES `permisosXrol` WRITE;
/*!40000 ALTER TABLE `permisosXrol` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisosXrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planta`
--

DROP TABLE IF EXISTS `planta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planta` (
  `Codigo` int(11) NOT NULL,
  `Legajo` int(11) NOT NULL,
  `Area` varchar(8) NOT NULL,
  `Cargo` int(11) NOT NULL,
  `Tipo_Cargo` varchar(20) NOT NULL,
  `Estado_Cargo` varchar(2) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Resolucion` varchar(15) DEFAULT NULL,
  `CostoActual` int(11) DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `legajo_idx` (`Legajo`),
  KEY `areaDoc_idx` (`Area`),
  KEY `cargo_idx` (`Cargo`),
  KEY `estado_idx` (`Estado_Cargo`),
  KEY `dispDoc_idx` (`Disposicion`),
  KEY `resDoc_idx` (`Resolucion`),
  KEY `costoUlt_idx` (`CostoActual`),
  CONSTRAINT `areaDoc` FOREIGN KEY (`Area`) REFERENCES `areas` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cargo` FOREIGN KEY (`Cargo`) REFERENCES `cargos` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `costoUlt` FOREIGN KEY (`CostoActual`) REFERENCES `costos` (`id_costos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dispDoc` FOREIGN KEY (`Disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `estado` FOREIGN KEY (`Estado_Cargo`) REFERENCES `estados` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `legajo` FOREIGN KEY (`Legajo`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `resDoc` FOREIGN KEY (`Resolucion`) REFERENCES `resoluciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planta`
--

LOCK TABLES `planta` WRITE;
/*!40000 ALTER TABLE `planta` DISABLE KEYS */;
/*!40000 ALTER TABLE `planta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programainvestigacion`
--

DROP TABLE IF EXISTS `programainvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programainvestigacion` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(100) DEFAULT NULL,
  `Director` int(11) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` date DEFAULT NULL,
  `Hasta` date DEFAULT NULL,
  `Estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `disp_idx` (`Disposicion`),
  KEY `doc_pro_idx` (`Director`),
  KEY `fk_programainvestigacion_1_idx` (`Estado`),
  CONSTRAINT `disp_pro` FOREIGN KEY (`Disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `doc_pro` FOREIGN KEY (`Director`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programainvestigacion_1` FOREIGN KEY (`Estado`) REFERENCES `EstadoPrograma` (`idEstado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programainvestigacion`
--

LOCK TABLES `programainvestigacion` WRITE;
/*!40000 ALTER TABLE `programainvestigacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `programainvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programasAsignaturas`
--

DROP TABLE IF EXISTS `programasAsignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programasAsignaturas` (
  `asignatura` int(11) NOT NULL,
  `Carrera` int(11) NOT NULL,
  `vigencia` date NOT NULL,
  `disposicion` varchar(45) NOT NULL,
  `Fecha_Entrega` date DEFAULT NULL,
  `FechaCPE` date DEFAULT NULL,
  `FechaCDD` date DEFAULT NULL,
  PRIMARY KEY (`asignatura`,`Carrera`,`vigencia`,`disposicion`),
  KEY `carrera_idx` (`Carrera`),
  KEY `dispo_idx` (`disposicion`),
  CONSTRAINT `asignatura` FOREIGN KEY (`asignatura`) REFERENCES `asignaturas` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `carrera` FOREIGN KEY (`Carrera`) REFERENCES `carreras` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dispo` FOREIGN KEY (`disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programasAsignaturas`
--

LOCK TABLES `programasAsignaturas` WRITE;
/*!40000 ALTER TABLE `programasAsignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `programasAsignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prorroga`
--

DROP TABLE IF EXISTS `prorroga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prorroga` (
  `proyecto` int(11) NOT NULL,
  `Disposicion` varchar(15) NOT NULL,
  `Fecha_inicio` date DEFAULT NULL,
  `Fecha_fin` date DEFAULT NULL,
  PRIMARY KEY (`proyecto`,`Disposicion`),
  KEY `dispo_idx` (`Disposicion`),
  CONSTRAINT `dispopro` FOREIGN KEY (`Disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proyepro` FOREIGN KEY (`proyecto`) REFERENCES `proyecto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prorroga`
--

LOCK TABLES `prorroga` WRITE;
/*!40000 ALTER TABLE `prorroga` DISABLE KEYS */;
/*!40000 ALTER TABLE `prorroga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyecto` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Descripcion` varchar(100) DEFAULT NULL,
  `Director` int(11) NOT NULL,
  `Fecha_Presentacion` date NOT NULL,
  `Fecha_Aprobacion` date DEFAULT NULL,
  `Fecha_inicio` date DEFAULT NULL,
  `Fecha_Fin` date DEFAULT NULL,
  `Programa` int(11) DEFAULT NULL,
  `Estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dir_idx` (`Director`),
  KEY `programa_idx` (`Programa`),
  KEY `fk_proyecto_1_idx` (`Estado`),
  CONSTRAINT `dir` FOREIGN KEY (`Director`) REFERENCES `docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_proyecto_1` FOREIGN KEY (`Estado`) REFERENCES `EstadoProyecto` (`idEstadoProyecto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `programainves` FOREIGN KEY (`Programa`) REFERENCES `programainvestigacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resoluciones`
--

DROP TABLE IF EXISTS `resoluciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resoluciones` (
  `id` varchar(15) NOT NULL,
  `Desde` date NOT NULL,
  `Hasta` date NOT NULL,
  `Recurso` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resoluciones`
--

LOCK TABLES `resoluciones` WRITE;
/*!40000 ALTER TABLE `resoluciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `resoluciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `idrol` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idrol`)
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
-- Table structure for table `subsidio`
--

DROP TABLE IF EXISTS `subsidio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subsidio` (
  `proyecto` int(11) NOT NULL,
  `anio` int(11) NOT NULL,
  `disposicion` varchar(15) NOT NULL,
  `Monto` double NOT NULL,
  `observaciones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`proyecto`,`disposicion`),
  KEY `disposub_idx` (`disposicion`),
  CONSTRAINT `disposub` FOREIGN KEY (`disposicion`) REFERENCES `disposiciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `proyecto_sub` FOREIGN KEY (`proyecto`) REFERENCES `proyecto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subsidio`
--

LOCK TABLES `subsidio` WRITE;
/*!40000 ALTER TABLE `subsidio` DISABLE KEYS */;
/*!40000 ALTER TABLE `subsidio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_documentos`
--

DROP TABLE IF EXISTS `tipos_documentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_documentos` (
  `idTipo` int(11) NOT NULL,
  `Descripcion` varchar(5) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_documentos`
--

LOCK TABLES `tipos_documentos` WRITE;
/*!40000 ALTER TABLE `tipos_documentos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipos_documentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `titulos`
--

DROP TABLE IF EXISTS `titulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `titulos` (
  `idtitulos` int(11) NOT NULL AUTO_INCREMENT,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `EsMayor` tinyint(4) NOT NULL,
  PRIMARY KEY (`idtitulos`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_titulos_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_titulos_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Persona` (`TipoDocumento`, `NroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `titulos`
--

LOCK TABLES `titulos` WRITE;
/*!40000 ALTER TABLE `titulos` DISABLE KEYS */;
/*!40000 ALTER TABLE `titulos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-30 17:48:33
