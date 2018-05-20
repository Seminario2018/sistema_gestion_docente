

ALTER TABLE `planta` 
DROP FOREIGN KEY `estado`;
ALTER TABLE `planta` 
DROP INDEX `estado_idx` ;

ALTER TABLE `planta` 
CHANGE COLUMN `Estado_Cargo` `Estado_Cargo` INT NOT NULL ;

ALTER TABLE `planta` 
ADD INDEX `estado_idx` (`Estado_Cargo` ASC);
ALTER TABLE `planta` 
ADD CONSTRAINT `estado`
  FOREIGN KEY (`Estado_Cargo`)
  REFERENCES `estadocargo` (`idestadocargo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `Persona` 
CHANGE COLUMN `Apellido` `Apellido` VARCHAR(50) NOT NULL ,
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(45) NOT NULL ;


ALTER TABLE `areas` 
DROP FOREIGN KEY `disp`,
DROP FOREIGN KEY `division`,
DROP FOREIGN KEY `responsable`,
DROP FOREIGN KEY `subarea`;
ALTER TABLE `areas` 
CHANGE COLUMN `Descripcion` `Descripcion` VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN `Division` `Division` VARCHAR(2) NULL DEFAULT NULL ,
CHANGE COLUMN `Responsable` `Responsable` INT(11) NULL DEFAULT NULL ,
CHANGE COLUMN `Disposicion` `Disposicion` VARCHAR(15) NULL DEFAULT NULL ,
CHANGE COLUMN `Desde` `Desde` DATETIME NULL DEFAULT NULL ,
CHANGE COLUMN `Hasta` `Hasta` DATETIME NULL DEFAULT NULL ,
CHANGE COLUMN `Subarea_De` `SubAreaDe` VARCHAR(8) NULL DEFAULT NULL ;
ALTER TABLE `areas` 
ADD CONSTRAINT `disp`
  FOREIGN KEY (`Disposicion`)
  REFERENCES `disposiciones` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `division`
  FOREIGN KEY (`Division`)
  REFERENCES `divisiones` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `responsable`
  FOREIGN KEY (`Responsable`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `subarea`
  FOREIGN KEY (`SubAreaDe`)
  REFERENCES `areas` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `areas`
DROP FOREIGN KEY `disp`;
ALTER TABLE `areas` 
DROP INDEX `disposicion_idx` ;


ALTER TABLE `divisiones` 
DROP FOREIGN KEY `disposicion`;
ALTER TABLE `divisiones` 
DROP INDEX `disposicion_idx` ;

ALTER TABLE `planta` 
DROP FOREIGN KEY `dispDoc`;
ALTER TABLE `planta` 
DROP INDEX `dispDoc_idx` ;

ALTER TABLE `programainvestigacion` 
DROP FOREIGN KEY `disp_pro`;
ALTER TABLE `programainvestigacion` 
DROP INDEX `disp_idx` ;

ALTER TABLE `programasAsignaturas` 
DROP FOREIGN KEY `dispo`;
ALTER TABLE `programasAsignaturas` 
DROP INDEX `dispo_idx` ;

ALTER TABLE `prorroga` 
DROP FOREIGN KEY `dispopro`;
ALTER TABLE `prorroga` 
DROP INDEX `dispo_idx` ;

ALTER TABLE `prorroga` 
CHANGE COLUMN `Disposicion` `Disposicion` VARCHAR(15) NULL DEFAULT NULL ,
CHANGE COLUMN `Fecha_inicio` `Fecha_inicio` DATE NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`proyecto`, `Fecha_inicio`);

ALTER TABLE `subsidio` 
DROP FOREIGN KEY `disposub`;
ALTER TABLE `subsidio` 
CHANGE COLUMN `disposicion` `disposicion` VARCHAR(15) NULL DEFAULT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`proyecto`, `anio`),
DROP INDEX `disposub_idx` ;

DROP TABLE `disposiciones`;




ALTER TABLE `integrante` 
DROP FOREIGN KEY `fk_Integrante_1`;
ALTER TABLE `integrante` 
DROP COLUMN `NroDocumento`,
DROP COLUMN `TipoDocumento`,
CHANGE COLUMN `Proyecto` `Proyecto` INT(11) NOT NULL FIRST,
CHANGE COLUMN `idnew_table` `idintegrante` INT(11) NOT NULL ,
CHANGE COLUMN `Institucion` `Institucion` VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN `Cargo` `Cargo` VARCHAR(45) NULL DEFAULT NULL ,
CHANGE COLUMN `HorasSemanales` `HorasSemanales` DOUBLE NULL DEFAULT NULL ,
ADD COLUMN `Legajo` INT NULL DEFAULT NULL AFTER `idintegrante`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Proyecto`, `idintegrante`),
ADD INDEX `fk_legajo_idx` (`Legajo` ASC),
DROP INDEX `fk_Integrante_1_idx` ;
ALTER TABLE `integrante` 
ADD CONSTRAINT `fk_legajo`
  FOREIGN KEY (`Legajo`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `docentes` 
DROP FOREIGN KEY `fk_docentes_2`;
ALTER TABLE `docentes` 
DROP INDEX `fk_docentes_2_idx` ;

ALTER TABLE `estadodocente` 
CHANGE COLUMN `idEstadoDocente` `idEstadoDocente` INT(11) NOT NULL ;

ALTER TABLE `docentes` 
ADD INDEX `fk_estado_idx` (`Estado` ASC);
ALTER TABLE `docentes` 
ADD CONSTRAINT `fk_estado`
  FOREIGN KEY (`Estado`)
  REFERENCES `estadodocente` (`idEstadoDocente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `areas` 
DROP FOREIGN KEY `division`,
DROP FOREIGN KEY `responsable`;
ALTER TABLE `areas` 
CHANGE COLUMN `Descripcion` `Descripcion` VARCHAR(50) NOT NULL ,
CHANGE COLUMN `Division` `Division` VARCHAR(2) NOT NULL ,
CHANGE COLUMN `Responsable` `Responsable` INT(11) NOT NULL ;
ALTER TABLE `areas` 
ADD CONSTRAINT `division`
  FOREIGN KEY (`Division`)
  REFERENCES `divisiones` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `responsable`
  FOREIGN KEY (`Responsable`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `areas` 
DROP INDEX `responsable_idx` ;


ALTER TABLE `docentes` 
DROP FOREIGN KEY `fk_docentes_3`;
ALTER TABLE `docentes` 
CHANGE COLUMN `CategoriaInvestigacion` `CategoriaInvestigacion` INT(11) NULL DEFAULT 0 ;
ALTER TABLE `docentes` 
ADD CONSTRAINT `fk_docentes_3`
  FOREIGN KEY (`CategoriaInvestigacion`)
  REFERENCES `categoriainvestigacion` (`idCategoriaInvestigacion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `Persona` 
CHANGE COLUMN `FechaNacimiento` `FechaNacimiento` DATE NULL DEFAULT NULL ;
