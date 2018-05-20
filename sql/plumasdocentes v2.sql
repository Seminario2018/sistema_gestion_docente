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
-- Table structure for table `Areas`
--

DROP TABLE IF EXISTS `Areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Areas` (
  `Codigo` varchar(8) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Division` varchar(2) NOT NULL,
  `Responsable` int(11) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` datetime DEFAULT NULL,
  `Hasta` datetime DEFAULT NULL,
  `SubAreaDe` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `division_idx` (`Division`),
  KEY `responsable_idx` (`Responsable`),
  KEY `subarea_idx` (`SubAreaDe`),
  CONSTRAINT `Division` FOREIGN KEY (`Division`) REFERENCES `Divisiones` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Areas_1` FOREIGN KEY (`SubAreaDe`) REFERENCES `Areas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `responsable` FOREIGN KEY (`Responsable`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Areas`
--

LOCK TABLES `Areas` WRITE;
/*!40000 ALTER TABLE `Areas` DISABLE KEYS */;
/*!40000 ALTER TABLE `Areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Asignaturas`
--

DROP TABLE IF EXISTS `Asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Asignaturas` (
  `Codigo` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Area` varchar(8) NOT NULL,
  `Responsable` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `area_idx` (`Area`),
  KEY `responsable_idx` (`Responsable`),
  CONSTRAINT `area` FOREIGN KEY (`Area`) REFERENCES `Areas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `resp` FOREIGN KEY (`Responsable`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Asignaturas`
--

LOCK TABLES `Asignaturas` WRITE;
/*!40000 ALTER TABLE `Asignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `Asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AsignaturasXCarreras`
--

DROP TABLE IF EXISTS `AsignaturasXCarreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AsignaturasXCarreras` (
  `asignatura` int(11) NOT NULL,
  `Carrera` int(11) NOT NULL,
  PRIMARY KEY (`asignatura`,`Carrera`),
  KEY `carre_idx` (`Carrera`),
  CONSTRAINT `asig` FOREIGN KEY (`asignatura`) REFERENCES `Asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `carre` FOREIGN KEY (`Carrera`) REFERENCES `Carreras` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AsignaturasXCarreras`
--

LOCK TABLES `AsignaturasXCarreras` WRITE;
/*!40000 ALTER TABLE `AsignaturasXCarreras` DISABLE KEYS */;
/*!40000 ALTER TABLE `AsignaturasXCarreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CargoDocente`
--

DROP TABLE IF EXISTS `CargoDocente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CargoDocente` (
  `Codigo` int(11) NOT NULL,
  `Legajo` int(11) NOT NULL,
  `Area` varchar(8) NOT NULL,
  `Cargo` int(11) NOT NULL,
  `TipoCargo` varchar(20) NOT NULL,
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
  CONSTRAINT `areaDoc` FOREIGN KEY (`Area`) REFERENCES `Areas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `cargo` FOREIGN KEY (`Cargo`) REFERENCES `Cargos` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `estado` FOREIGN KEY (`EstadoCargo`) REFERENCES `EstadosCargos` (`idestadocargo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `legajo` FOREIGN KEY (`Legajo`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CargoDocente`
--

LOCK TABLES `CargoDocente` WRITE;
/*!40000 ALTER TABLE `CargoDocente` DISABLE KEYS */;
/*!40000 ALTER TABLE `CargoDocente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cargos`
--

DROP TABLE IF EXISTS `Cargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cargos` (
  `Codigo` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  `CargaHoraria` float NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cargos`
--

LOCK TABLES `Cargos` WRITE;
/*!40000 ALTER TABLE `Cargos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Carreras`
--

DROP TABLE IF EXISTS `Carreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Carreras` (
  `Codigo` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Carreras`
--

LOCK TABLES `Carreras` WRITE;
/*!40000 ALTER TABLE `Carreras` DISABLE KEYS */;
/*!40000 ALTER TABLE `Carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CategoriasInvestigacion`
--

DROP TABLE IF EXISTS `CategoriasInvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CategoriasInvestigacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CategoriasInvestigacion`
--

LOCK TABLES `CategoriasInvestigacion` WRITE;
/*!40000 ALTER TABLE `CategoriasInvestigacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `CategoriasInvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Columnas`
--

DROP TABLE IF EXISTS `Columnas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Columnas` (
  `TipoInforme` int(11) NOT NULL,
  `Visible` int(11) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Atributo` varchar(45) NOT NULL,
  `Filtros` varchar(100) DEFAULT NULL,
  `Calculo` varchar(45) DEFAULT NULL,
  `Ordenar` int(11) NOT NULL,
  `Posicion` int(11) NOT NULL,
  PRIMARY KEY (`TipoInforme`,`Atributo`),
  CONSTRAINT `fk_Columna_1` FOREIGN KEY (`TipoInforme`) REFERENCES `TiposInformes` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Columnas`
--

LOCK TABLES `Columnas` WRITE;
/*!40000 ALTER TABLE `Columnas` DISABLE KEYS */;
/*!40000 ALTER TABLE `Columnas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comisiones`
--

DROP TABLE IF EXISTS `Comisiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comisiones` (
  `Asignatura` int(11) NOT NULL,
  `Numero_Comision` int(11) NOT NULL,
  `Sede` varchar(2) NOT NULL,
  `DiasHoras` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Asignatura`,`Numero_Comision`),
  KEY `sede_idx` (`Sede`),
  CONSTRAINT `asign` FOREIGN KEY (`Asignatura`) REFERENCES `Asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sede` FOREIGN KEY (`Sede`) REFERENCES `Sedes` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comisiones`
--

LOCK TABLES `Comisiones` WRITE;
/*!40000 ALTER TABLE `Comisiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `Comisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contactos`
--

DROP TABLE IF EXISTS `Contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contactos` (
  `idcontacto` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Tipo` int(11) NOT NULL,
  `Valor` varchar(45) NOT NULL,
  PRIMARY KEY (`idcontacto`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_contacto_1_idx` (`TipoDocumento`,`NroDocumento`),
  KEY `fk_Contacto_2_idx` (`Tipo`),
  CONSTRAINT `fk_Contactos_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contactos_2` FOREIGN KEY (`Tipo`) REFERENCES `TiposContactos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contactos`
--

LOCK TABLES `Contactos` WRITE;
/*!40000 ALTER TABLE `Contactos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Divisiones`
--

DROP TABLE IF EXISTS `Divisiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Divisiones` (
  `Codigo` varchar(2) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  `Jefe` int(11) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` date DEFAULT NULL,
  `Hasta` date DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `jefe_idx` (`Jefe`),
  CONSTRAINT `jefe` FOREIGN KEY (`Jefe`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Divisiones`
--

LOCK TABLES `Divisiones` WRITE;
/*!40000 ALTER TABLE `Divisiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `Divisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Docentes`
--

DROP TABLE IF EXISTS `Docentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Docentes` (
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
  CONSTRAINT `fk_docentes_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_docentes_2` FOREIGN KEY (`Estado`) REFERENCES `EstadosDocentes` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_docentes_3` FOREIGN KEY (`CategoriaInvestigacion`) REFERENCES `CategoriasInvestigacion` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Docentes`
--

LOCK TABLES `Docentes` WRITE;
/*!40000 ALTER TABLE `Docentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `Docentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Domicilios`
--

DROP TABLE IF EXISTS `Domicilios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Domicilios` (
  `iddomicilios` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Provincia` varchar(45) NOT NULL,
  `Ciudad` varchar(45) NOT NULL,
  `CodigoPostal` varchar(45) NOT NULL,
  `Direccion` varchar(45) NOT NULL,
  PRIMARY KEY (`iddomicilios`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_domicilios_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_domicilios_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Domicilios`
--

LOCK TABLES `Domicilios` WRITE;
/*!40000 ALTER TABLE `Domicilios` DISABLE KEYS */;
/*!40000 ALTER TABLE `Domicilios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EquiposDocentes`
--

DROP TABLE IF EXISTS `EquiposDocentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EquiposDocentes` (
  `Materia` int(11) NOT NULL,
  `Comision` int(11) NOT NULL,
  `planta` int(11) NOT NULL,
  PRIMARY KEY (`Materia`,`Comision`,`planta`),
  KEY `planta_idx` (`planta`),
  CONSTRAINT `comisiones` FOREIGN KEY (`Materia`, `Comision`) REFERENCES `Comisiones` (`Asignatura`, `Numero_Comision`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `planta` FOREIGN KEY (`planta`) REFERENCES `CargoDocente` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EquiposDocentes`
--

LOCK TABLES `EquiposDocentes` WRITE;
/*!40000 ALTER TABLE `EquiposDocentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `EquiposDocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadosCargos`
--

DROP TABLE IF EXISTS `EstadosCargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadosCargos` (
  `idestadocargo` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idestadocargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadosCargos`
--

LOCK TABLES `EstadosCargos` WRITE;
/*!40000 ALTER TABLE `EstadosCargos` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadosCargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadosDocentes`
--

DROP TABLE IF EXISTS `EstadosDocentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadosDocentes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadosDocentes`
--

LOCK TABLES `EstadosDocentes` WRITE;
/*!40000 ALTER TABLE `EstadosDocentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadosDocentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadosPersonas`
--

DROP TABLE IF EXISTS `EstadosPersonas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadosPersonas` (
  `idEstado` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstado`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadosPersonas`
--

LOCK TABLES `EstadosPersonas` WRITE;
/*!40000 ALTER TABLE `EstadosPersonas` DISABLE KEYS */;
INSERT INTO `EstadosPersonas` VALUES (0,'ACTIVO');
/*!40000 ALTER TABLE `EstadosPersonas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadosProgramas`
--

DROP TABLE IF EXISTS `EstadosProgramas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadosProgramas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadosProgramas`
--

LOCK TABLES `EstadosProgramas` WRITE;
/*!40000 ALTER TABLE `EstadosProgramas` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadosProgramas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EstadosProyectos`
--

DROP TABLE IF EXISTS `EstadosProyectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EstadosProyectos` (
  `idEstadoProyecto` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstadoProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EstadosProyectos`
--

LOCK TABLES `EstadosProyectos` WRITE;
/*!40000 ALTER TABLE `EstadosProyectos` DISABLE KEYS */;
/*!40000 ALTER TABLE `EstadosProyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Examenes`
--

DROP TABLE IF EXISTS `Examenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Examenes` (
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
  CONSTRAINT `doc1` FOREIGN KEY (`Docente1`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `doc2` FOREIGN KEY (`Docente2`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `doc3` FOREIGN KEY (`Docente3`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `materia` FOREIGN KEY (`Materia`) REFERENCES `Asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `sedeExam` FOREIGN KEY (`sede`) REFERENCES `Sedes` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Examenes`
--

LOCK TABLES `Examenes` WRITE;
/*!40000 ALTER TABLE `Examenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `Examenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Incentivos`
--

DROP TABLE IF EXISTS `Incentivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Incentivos` (
  `Legajo` int(11) NOT NULL,
  `Fecha` varchar(4) NOT NULL,
  PRIMARY KEY (`Legajo`,`Fecha`),
  CONSTRAINT `fk_Incentivos_1` FOREIGN KEY (`Legajo`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Incentivos`
--

LOCK TABLES `Incentivos` WRITE;
/*!40000 ALTER TABLE `Incentivos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Incentivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Integrantes`
--

DROP TABLE IF EXISTS `Integrantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Integrantes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Legajo` int(11) DEFAULT NULL,
  `Apellido` varchar(45) DEFAULT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Cargo` varchar(45) NOT NULL,
  `Institucion` varchar(50) NOT NULL,
  `HorasSemanales` int(11) NOT NULL,
  `Proyecto` int(11) NOT NULL,
  PRIMARY KEY (`id`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_Integrante_1_idx` (`TipoDocumento`,`NroDocumento`),
  KEY `fk_Integrante_2_idx` (`Proyecto`),
  CONSTRAINT `fk_Integrante_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Integrante_2` FOREIGN KEY (`Proyecto`) REFERENCES `Proyectos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Integrantes`
--

LOCK TABLES `Integrantes` WRITE;
/*!40000 ALTER TABLE `Integrantes` DISABLE KEYS */;
/*!40000 ALTER TABLE `Integrantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Modulos`
--

DROP TABLE IF EXISTS `Modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Modulos` (
  `idmodulo` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idmodulo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modulos`
--

LOCK TABLES `Modulos` WRITE;
/*!40000 ALTER TABLE `Modulos` DISABLE KEYS */;
INSERT INTO `Modulos` VALUES (0,'GENERAL'),(1,'ESTACION'),(2,'GRUPOS'),(3,'USUARIOS'),(4,'CARGOS'),(5,'DOCENTES'),(6,'INVESTIGACION'),(7,'VER_INFORMES'),(8,'EDIT_INFORMES');
/*!40000 ALTER TABLE `Modulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permisos`
--

DROP TABLE IF EXISTS `Permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permisos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Rol` varchar(45) NOT NULL,
  `Crear` tinyint(4) NOT NULL,
  `Eliminar` tinyint(4) NOT NULL,
  `Modificar` tinyint(4) NOT NULL,
  `Listar` tinyint(4) NOT NULL,
  `Modulo` int(11) NOT NULL,
  PRIMARY KEY (`id`,`Rol`),
  KEY `fk_permisos_2_idx` (`Rol`),
  KEY `fk_permisos_1_idx` (`Modulo`),
  CONSTRAINT `fk_Permiso_1` FOREIGN KEY (`Rol`) REFERENCES `Roles` (`nombre`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Permiso_2` FOREIGN KEY (`Modulo`) REFERENCES `Modulos` (`idmodulo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permisos`
--

LOCK TABLES `Permisos` WRITE;
/*!40000 ALTER TABLE `Permisos` DISABLE KEYS */;
INSERT INTO `Permisos` VALUES (1,'su',0,0,0,0,0),(2,'su',0,0,0,0,1),(3,'su',0,0,0,0,2),(4,'su',0,0,0,0,3),(5,'su',0,0,0,0,4),(6,'su',0,0,0,0,5),(7,'su',0,0,0,0,6),(8,'su',0,0,0,0,7),(9,'su',0,0,0,0,8),(10,'su',1,1,1,1,4),(11,'su',0,0,0,0,0),(12,'su',0,0,0,0,1),(13,'su',0,0,0,0,2),(14,'su',0,0,0,0,3),(15,'su',0,0,0,0,4),(16,'su',0,0,0,0,5),(17,'su',0,0,0,0,6),(18,'su',0,0,0,0,7),(19,'su',0,0,0,0,8),(20,'su',1,1,1,1,4);
/*!40000 ALTER TABLE `Permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Personas`
--

DROP TABLE IF EXISTS `Personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Personas` (
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Apellido` varchar(50) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `Estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`TipoDocumento`,`NroDocumento`),
  KEY `EstPersona_idx` (`Estado`),
  CONSTRAINT `EstPersona` FOREIGN KEY (`Estado`) REFERENCES `EstadosPersonas` (`idEstado`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `tipoDoc` FOREIGN KEY (`TipoDocumento`) REFERENCES `TiposDocumentos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Personas`
--

LOCK TABLES `Personas` WRITE;
/*!40000 ALTER TABLE `Personas` DISABLE KEYS */;
INSERT INTO `Personas` VALUES (0,'40455634','Marazzo','Leonardo','1997-06-22',0);
/*!40000 ALTER TABLE `Personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProgramasAsignaturas`
--

DROP TABLE IF EXISTS `ProgramasAsignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProgramasAsignaturas` (
  `asignatura` int(11) NOT NULL,
  `Carrera` int(11) NOT NULL,
  `vigencia` date NOT NULL,
  `disposicion` varchar(45) NOT NULL,
  `Fecha_Entrega` date DEFAULT NULL,
  `FechaCPE` date DEFAULT NULL,
  `FechaCDD` date DEFAULT NULL,
  PRIMARY KEY (`asignatura`,`Carrera`,`vigencia`,`disposicion`),
  KEY `carrera_idx` (`Carrera`),
  CONSTRAINT `asignatura` FOREIGN KEY (`asignatura`) REFERENCES `Asignaturas` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `carrera` FOREIGN KEY (`Carrera`) REFERENCES `Carreras` (`Codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProgramasAsignaturas`
--

LOCK TABLES `ProgramasAsignaturas` WRITE;
/*!40000 ALTER TABLE `ProgramasAsignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProgramasAsignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProgramasInvestigacion`
--

DROP TABLE IF EXISTS `ProgramasInvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProgramasInvestigacion` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Director` int(11) NOT NULL,
  `Codirector` int(11) DEFAULT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `Desde` date DEFAULT NULL,
  `Hasta` date DEFAULT NULL,
  `Estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_programainvestigacion_1_idx` (`Estado`),
  KEY `doc_pro_idx` (`Director`,`Codirector`),
  KEY `fk_ProgramasInvestigacion_2_idx` (`Codirector`),
  CONSTRAINT `fk_ProgramasInvestigacion_1` FOREIGN KEY (`Director`) REFERENCES `Docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProgramasInvestigacion_2` FOREIGN KEY (`Codirector`) REFERENCES `Docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programainvestigacion_1` FOREIGN KEY (`Estado`) REFERENCES `EstadosProgramas` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProgramasInvestigacion`
--

LOCK TABLES `ProgramasInvestigacion` WRITE;
/*!40000 ALTER TABLE `ProgramasInvestigacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProgramasInvestigacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Prorrogas`
--

DROP TABLE IF EXISTS `Prorrogas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Prorrogas` (
  `Proyecto` int(11) NOT NULL,
  `Disposicion` varchar(15) NOT NULL,
  `Fecha_inicio` date DEFAULT NULL,
  `Fecha_fin` date DEFAULT NULL,
  PRIMARY KEY (`Proyecto`,`Disposicion`),
  CONSTRAINT `fk_Prorrogas_1` FOREIGN KEY (`Proyecto`) REFERENCES `Proyectos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prorrogas`
--

LOCK TABLES `Prorrogas` WRITE;
/*!40000 ALTER TABLE `Prorrogas` DISABLE KEYS */;
/*!40000 ALTER TABLE `Prorrogas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Proyectos`
--

DROP TABLE IF EXISTS `Proyectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Proyectos` (
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
  KEY `fk_proyecto_1_idx` (`Estado`),
  KEY `fk_Proyectos_1_idx` (`Codirector`),
  CONSTRAINT `dir` FOREIGN KEY (`Director`) REFERENCES `Docentes` (`Legajo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Proyectos_1` FOREIGN KEY (`Codirector`) REFERENCES `Docentes` (`Legajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_proyecto_1` FOREIGN KEY (`Estado`) REFERENCES `EstadosProyectos` (`idEstadoProyecto`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `programainves` FOREIGN KEY (`Programa`) REFERENCES `ProgramasInvestigacion` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proyectos`
--

LOCK TABLES `Proyectos` WRITE;
/*!40000 ALTER TABLE `Proyectos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Proyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rendisiones`
--

DROP TABLE IF EXISTS `Rendisiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rendisiones` (
  `id` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Monto` float NOT NULL,
  `Observaciones` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rendisiones`
--

LOCK TABLES `Rendisiones` WRITE;
/*!40000 ALTER TABLE `Rendisiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rendisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles` (
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles`
--

LOCK TABLES `Roles` WRITE;
/*!40000 ALTER TABLE `Roles` DISABLE KEYS */;
INSERT INTO `Roles` VALUES ('su');
/*!40000 ALTER TABLE `Roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RolesXUsuario`
--

DROP TABLE IF EXISTS `RolesXUsuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RolesXUsuario` (
  `Usuario` varchar(45) NOT NULL,
  `Rol` varchar(45) NOT NULL,
  PRIMARY KEY (`Usuario`,`Rol`),
  KEY `fk_RolesXUsuario_2_idx` (`Rol`),
  CONSTRAINT `fk_RolesXUsuario_1` FOREIGN KEY (`Usuario`) REFERENCES `Usuarios` (`Usuario`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_RolesXUsuario_2` FOREIGN KEY (`Rol`) REFERENCES `Roles` (`nombre`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RolesXUsuario`
--

LOCK TABLES `RolesXUsuario` WRITE;
/*!40000 ALTER TABLE `RolesXUsuario` DISABLE KEYS */;
INSERT INTO `RolesXUsuario` VALUES ('leomarazzo','su');
/*!40000 ALTER TABLE `RolesXUsuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sedes`
--

DROP TABLE IF EXISTS `Sedes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sedes` (
  `Codigo` varchar(2) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sedes`
--

LOCK TABLES `Sedes` WRITE;
/*!40000 ALTER TABLE `Sedes` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sedes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subsidios`
--

DROP TABLE IF EXISTS `Subsidios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subsidios` (
  `Proyecto` int(11) NOT NULL,
  `Year` int(11) NOT NULL,
  `Disposicion` varchar(15) NOT NULL,
  `MontoTotal` double NOT NULL,
  `Observaciones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Proyecto`,`Disposicion`),
  CONSTRAINT `fk_Subsidios_1` FOREIGN KEY (`Proyecto`) REFERENCES `Proyectos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subsidios`
--

LOCK TABLES `Subsidios` WRITE;
/*!40000 ALTER TABLE `Subsidios` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subsidios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TiposCargos`
--

DROP TABLE IF EXISTS `TiposCargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TiposCargos` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TiposCargos`
--

LOCK TABLES `TiposCargos` WRITE;
/*!40000 ALTER TABLE `TiposCargos` DISABLE KEYS */;
/*!40000 ALTER TABLE `TiposCargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TiposContactos`
--

DROP TABLE IF EXISTS `TiposContactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TiposContactos` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TiposContactos`
--

LOCK TABLES `TiposContactos` WRITE;
/*!40000 ALTER TABLE `TiposContactos` DISABLE KEYS */;
/*!40000 ALTER TABLE `TiposContactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TiposDocumentos`
--

DROP TABLE IF EXISTS `TiposDocumentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TiposDocumentos` (
  `id` int(11) NOT NULL,
  `Descripcion` varchar(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Descripcion_UNIQUE` (`Descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TiposDocumentos`
--

LOCK TABLES `TiposDocumentos` WRITE;
/*!40000 ALTER TABLE `TiposDocumentos` DISABLE KEYS */;
INSERT INTO `TiposDocumentos` VALUES (0,'DNI');
/*!40000 ALTER TABLE `TiposDocumentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TiposInformes`
--

DROP TABLE IF EXISTS `TiposInformes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TiposInformes` (
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
-- Dumping data for table `TiposInformes`
--

LOCK TABLES `TiposInformes` WRITE;
/*!40000 ALTER TABLE `TiposInformes` DISABLE KEYS */;
/*!40000 ALTER TABLE `TiposInformes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Titulos`
--

DROP TABLE IF EXISTS `Titulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Titulos` (
  `id` int(11) NOT NULL,
  `TipoDocumento` int(11) NOT NULL,
  `NroDocumento` varchar(10) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `EsMayor` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`,`TipoDocumento`,`NroDocumento`),
  KEY `fk_titulos_1_idx` (`TipoDocumento`,`NroDocumento`),
  CONSTRAINT `fk_titulos_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `Personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Titulos`
--

LOCK TABLES `Titulos` WRITE;
/*!40000 ALTER TABLE `Titulos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Titulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuarios`
--

DROP TABLE IF EXISTS `Usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuarios` (
  `Usuario` varchar(45) NOT NULL,
  `Hash` varchar(200) NOT NULL,
  `Salt` varchar(200) NOT NULL,
  `TipoDocumentoPersona` int(11) NOT NULL,
  `NroDocumentoPerson` varchar(10) NOT NULL,
  `Descripcion` varchar(50) NOT NULL,
  PRIMARY KEY (`Usuario`),
  UNIQUE KEY `Usuario_UNIQUE` (`Usuario`),
  KEY `fk_Usuario_1_idx` (`TipoDocumentoPersona`,`NroDocumentoPerson`),
  CONSTRAINT `fk_Usuario_1` FOREIGN KEY (`TipoDocumentoPersona`, `NroDocumentoPerson`) REFERENCES `Personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuarios`
--

LOCK TABLES `Usuarios` WRITE;
/*!40000 ALTER TABLE `Usuarios` DISABLE KEYS */;
INSERT INTO `Usuarios` VALUES ('leomarazzo','bGVvbmFyZG9tYXJhenpv','uh/hLn0MRvsXRvolUxE0Bw==',0,'40455634','Leonardo');
/*!40000 ALTER TABLE `Usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-20  3:54:38
