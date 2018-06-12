ALTER TABLE `Docentes` 
DROP FOREIGN KEY `fk_docentes_2`;
ALTER TABLE `Docentes` 
DROP INDEX `fk_docentes_2_idx` ;

ALTER TABLE `EstadosDocentes` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ;

ALTER TABLE `Docentes` 
ADD INDEX `fk_docentes_2_idx` (`Estado` ASC);
ALTER TABLE `Docentes` 
ADD CONSTRAINT `fk_docentes_2`
  FOREIGN KEY (`Estado`)
  REFERENCES `EstadosDocentes` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `CargoDocente` 
RENAME TO  `CargosDocentes` ;



ALTER TABLE `Integrantes` 
DROP FOREIGN KEY `fk_Integrante_1`;
ALTER TABLE `Integrantes` 
DROP COLUMN `NroDocumento`,
DROP COLUMN `TipoDocumento`,
CHANGE COLUMN `Cargo` `Cargo` VARCHAR(45) NULL DEFAULT NULL ,
CHANGE COLUMN `Institucion` `Institucion` VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN `HorasSemanales` `HorasSemanales` INT(11) NULL DEFAULT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`),
DROP INDEX `fk_Integrante_1_idx` ;

ALTER TABLE `Integrantes` 
ADD COLUMN `CodigoCargo` INT NULL DEFAULT NULL AFTER `Legajo`;

ALTER TABLE `Integrantes` 
ADD INDEX `fk_docentes_idx` (`Legajo` ASC),
ADD INDEX `fk_cargosdocentes_idx` (`CodigoCargo` ASC);
ALTER TABLE `Integrantes` 
ADD CONSTRAINT `fk_docentes`
  FOREIGN KEY (`Legajo`)
  REFERENCES `Docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_cargosdocentes`
  FOREIGN KEY (`CodigoCargo`)
  REFERENCES `CargosDocentes` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `Divisiones` 
DROP FOREIGN KEY `jefe`;
ALTER TABLE `Divisiones` 
CHANGE COLUMN `Jefe` `Jefe` INT(11) NULL DEFAULT NULL ;
ALTER TABLE `Divisiones` 
ADD CONSTRAINT `jefe`
  FOREIGN KEY (`Jefe`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;
  
ALTER TABLE `Divisiones` 
DROP FOREIGN KEY `jefe`;
ALTER TABLE `Divisiones` 
ADD CONSTRAINT `jefe`
  FOREIGN KEY (`Jefe`)
  REFERENCES `Docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `Areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `Areas` 
CHANGE COLUMN `Responsable` `Responsable` INT(11) NULL ;
ALTER TABLE `Areas` 
ADD CONSTRAINT `responsable`
  FOREIGN KEY (`Responsable`)
  REFERENCES `Docentes` (`Legajo`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;
  
ALTER TABLE `Areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `Areas` 
ADD CONSTRAINT `responsable`
  FOREIGN KEY (`Responsable`)
  REFERENCES `Docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `Areas` 
DROP FOREIGN KEY `responsable`;
ALTER TABLE `Areas` 
DROP INDEX `responsable_idx` ;




