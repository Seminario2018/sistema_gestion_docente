ALTER TABLE `docentes` 
DROP FOREIGN KEY `fk_docentes_2`;
ALTER TABLE `docentes` 
DROP INDEX `fk_docentes_2_idx` ;

ALTER TABLE `estadosdocentes` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ;

ALTER TABLE `docentes` 
ADD INDEX `fk_docentes_2_idx` (`Estado` ASC);
ALTER TABLE `docentes` 
ADD CONSTRAINT `fk_docentes_2`
  FOREIGN KEY (`Estado`)
  REFERENCES `estadosdocentes` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `cargodocente` 
RENAME TO  `cargosdocentes` ;



ALTER TABLE `integrantes` 
DROP FOREIGN KEY `fk_Integrante_1`;
ALTER TABLE `integrantes` 
DROP COLUMN `NroDocumento`,
DROP COLUMN `TipoDocumento`,
CHANGE COLUMN `Cargo` `Cargo` VARCHAR(45) NULL DEFAULT NULL ,
CHANGE COLUMN `Institucion` `Institucion` VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN `HorasSemanales` `HorasSemanales` INT(11) NULL DEFAULT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`),
DROP INDEX `fk_Integrante_1_idx` ;

ALTER TABLE `integrantes` 
ADD COLUMN `CodigoCargo` INT NULL DEFAULT NULL AFTER `Legajo`;

ALTER TABLE `integrantes` 
ADD INDEX `fk_docentes_idx` (`Legajo` ASC),
ADD INDEX `fk_cargosdocentes_idx` (`CodigoCargo` ASC);
ALTER TABLE `integrantes` 
ADD CONSTRAINT `fk_docentes`
  FOREIGN KEY (`Legajo`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_cargosdocentes`
  FOREIGN KEY (`CodigoCargo`)
  REFERENCES `cargosdocentes` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `divisiones` 
DROP FOREIGN KEY `jefe`;
ALTER TABLE `divisiones` 
CHANGE COLUMN `Jefe` `Jefe` INT(11) NULL DEFAULT NULL ;
ALTER TABLE `divisiones` 
ADD CONSTRAINT `jefe`
  FOREIGN KEY (`Jefe`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;
  
ALTER TABLE `divisiones` 
DROP FOREIGN KEY `jefe`;
ALTER TABLE `divisiones` 
ADD CONSTRAINT `jefe`
  FOREIGN KEY (`Jefe`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `areas` 
CHANGE COLUMN `Responsable` `Responsable` INT(11) NULL ;
ALTER TABLE `areas` 
ADD CONSTRAINT `responsable`
  FOREIGN KEY (`Responsable`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;
  
ALTER TABLE `areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `areas` 
ADD CONSTRAINT `responsable`
  FOREIGN KEY (`Responsable`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `areas` 
DROP INDEX `responsable_idx` ;




