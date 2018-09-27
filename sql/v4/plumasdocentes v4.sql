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
  `UltimoCosto` float(11,2) DEFAULT NULL,
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
  `UltimoCosto` float(11,2) DEFAULT NULL,
  `FechaUltimoCosto` varchar(45) NOT NULL,
  `Tipo` int(11) NOT NULL,
  PRIMARY KEY (`Legajo`,`Cargo`,`FechaUltimoCosto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `columnas`
--

DROP TABLE IF EXISTS `columnas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `columnas` (
  `TipoInforme` int(11) NOT NULL,
  `Visible` tinyint(4) DEFAULT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Atributo` varchar(100) NOT NULL,
  `Filtros` varchar(100) DEFAULT NULL,
  `Calculo` varchar(45) DEFAULT NULL,
  `Ordenar` int(11) NOT NULL,
  `Posicion` int(11) NOT NULL,
  `Tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TipoInforme`,`Atributo`),
  CONSTRAINT `fk_columnas_1` FOREIGN KEY (`TipoInforme`) REFERENCES `tiposinformes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_Contactos_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Contactos_2` FOREIGN KEY (`Tipo`) REFERENCES `tiposcontactos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `costos`
--

DROP TABLE IF EXISTS `costos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `costos` (
  `id` int(11) NOT NULL,
  `CodigoCargo` int(11) NOT NULL,
  `Costo` float(11,2) NOT NULL,
  `Fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`,`CodigoCargo`),
  KEY `fk_Costos_1_idx` (`CodigoCargo`),
  CONSTRAINT `fk_Costos_1` FOREIGN KEY (`CodigoCargo`) REFERENCES `cargosdocentes` (`Codigo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_domicilios_1` FOREIGN KEY (`TipoDocumento`, `NroDocumento`) REFERENCES `personas` (`TipoDocumento`, `NroDocumento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `programasinvestigacion`
--

DROP TABLE IF EXISTS `programasinvestigacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programasinvestigacion` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(400) NOT NULL,
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
-- Table structure for table `proyectos`
--

DROP TABLE IF EXISTS `proyectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyectos` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(500) NOT NULL,
  `Resumen` varchar(10000) DEFAULT NULL,
  `FechaPresentacion` date NOT NULL,
  `FechaAprobacion` date DEFAULT NULL,
  `Descripcion` varchar(1000) DEFAULT NULL,
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
  `Monto` float(11,2) NOT NULL,
  `Observaciones` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`Proyecto`,`YearSubsidio`),
  KEY `fk_Rendiciones_1_idx` (`Proyecto`,`YearSubsidio`),
  CONSTRAINT `fk_Rendiciones_1` FOREIGN KEY (`Proyecto`, `YearSubsidio`) REFERENCES `subsidios` (`Proyecto`, `Year`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `subsidios`
--

DROP TABLE IF EXISTS `subsidios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subsidios` (
  `Proyecto` int(11) NOT NULL,
  `Year` int(11) NOT NULL,
  `Disposicion` varchar(15) DEFAULT NULL,
  `MontoTotal` float(11,2) NOT NULL,
  `Observaciones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Proyecto`,`Year`),
  CONSTRAINT `fk_Subsidios_1` FOREIGN KEY (`Proyecto`) REFERENCES `proyectos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `tiposinformes`
--

DROP TABLE IF EXISTS `tiposinformes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposinformes` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(200) NOT NULL,
  `Descripcion` varchar(1000) DEFAULT NULL,
  `Editable` tinyint(4) NOT NULL DEFAULT '0',
  `FromString` varchar(1000) DEFAULT NULL,
  `GroupByString` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
 1 AS `Codigo`,
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
 1 AS `NroDocumento`,
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
-- Temporary view structure for view `viewprogramas`
--

DROP TABLE IF EXISTS `viewprogramas`;
/*!50001 DROP VIEW IF EXISTS `viewprogramas`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewprogramas` AS SELECT 
 1 AS `id`,
 1 AS `Nombre`*/;
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
-- Temporary view structure for view `viewproyectosdocentes`
--

DROP TABLE IF EXISTS `viewproyectosdocentes`;
/*!50001 DROP VIEW IF EXISTS `viewproyectosdocentes`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `viewproyectosdocentes` AS SELECT 
 1 AS `Nombre Proyecto`,
 1 AS `Area`,
 1 AS `Cargo`,
 1 AS `CargoDocente`,
 1 AS `HorasSemanales`,
 1 AS `Legajo`*/;
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
 1 AS `Usuario`,
 1 AS `NroDocumento`,
 1 AS `Nombre`,
 1 AS `Apellido`*/;
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
/*!50001 VIEW `viewcargodocente` AS select `d`.`Legajo` AS `Legajo`,`p`.`Nombre` AS `Nombre`,`p`.`Apellido` AS `Apellido`,`cd`.`Area` AS `Area`,`cd`.`Codigo` AS `Codigo`,`c`.`Descripcion` AS `descripcion` from (((`personas` `p` join `docentes` `d` on(((`d`.`TipoDocumento` = `p`.`TipoDocumento`) and (`d`.`NroDocumento` = `p`.`NroDocumento`)))) left join `cargosdocentes` `cd` on((`cd`.`Legajo` = `d`.`Legajo`))) left join `cargos` `c` on((`cd`.`Cargo` = `c`.`Codigo`))) order by isnull(`d`.`Legajo`),`d`.`Legajo` */;
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
/*!50001 VIEW `viewdocente` AS select `docentes`.`Legajo` AS `Legajo`,`personas`.`NroDocumento` AS `NroDocumento`,`personas`.`Nombre` AS `Nombre`,`personas`.`Apellido` AS `Apellido` from (`personas` left join `docentes` on(((`docentes`.`TipoDocumento` = `personas`.`TipoDocumento`) and (`docentes`.`NroDocumento` = `personas`.`NroDocumento`)))) where (`personas`.`NroDocumento` <> '0') order by isnull(`docentes`.`Legajo`),`docentes`.`Legajo` */;
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
/*!50001 VIEW `viewpersona` AS select `p`.`NroDocumento` AS `NroDocumento`,`p`.`Nombre` AS `Nombre`,`p`.`Apellido` AS `Apellido` from `personas` `p` where (`p`.`NroDocumento` <> 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `viewprogramas`
--

/*!50001 DROP VIEW IF EXISTS `viewprogramas`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewprogramas` AS select `programasinvestigacion`.`id` AS `id`,`programasinvestigacion`.`Nombre` AS `Nombre` from `programasinvestigacion` */;
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
-- Final view structure for view `viewproyectosdocentes`
--

/*!50001 DROP VIEW IF EXISTS `viewproyectosdocentes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `viewproyectosdocentes` AS select `p`.`Nombre` AS `Nombre Proyecto`,`a`.`Descripcion` AS `Area`,`i`.`Cargo` AS `Cargo`,`cd`.`Codigo` AS `CargoDocente`,`i`.`HorasSemanales` AS `HorasSemanales`,`d`.`Legajo` AS `Legajo` from (`proyectos` `p` left join (`integrantes` `i` left join ((`cargosdocentes` `cd` join `areas` `a` on((`cd`.`Area` = `a`.`Codigo`))) join `docentes` `d` on((`cd`.`Legajo` = `d`.`Legajo`))) on((`i`.`CargoDocente` = `cd`.`Codigo`))) on((`i`.`Proyecto` = `p`.`id`))) */;
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
/*!50001 VIEW `viewrol` AS select `roles`.`id` AS `id`,`roles`.`nombre` AS `nombre` from `roles` where (`roles`.`id` <> 0) */;
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
/*!50001 VIEW `viewusuario` AS select `usuarios`.`Usuario` AS `Usuario`,`personas`.`NroDocumento` AS `NroDocumento`,`personas`.`Nombre` AS `Nombre`,`personas`.`Apellido` AS `Apellido` from (`personas` left join `usuarios` on(((`usuarios`.`TipoDocumentoPersona` = `personas`.`TipoDocumento`) and (`personas`.`NroDocumento` = `usuarios`.`NroDocumentoPerson`)))) where (`personas`.`NroDocumento` <> '0') order by isnull(`usuarios`.`Usuario`),`usuarios`.`Usuario` */;
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

-- Dump completed on 2018-09-27 15:23:26
