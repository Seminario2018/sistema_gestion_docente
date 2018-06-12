ALTER TABLE `Integrantes` 
DROP FOREIGN KEY `fk_docentes`;
ALTER TABLE `Integrantes` 
CHANGE COLUMN `Legajo` `CargoDocente` INT(11) NULL DEFAULT NULL ,
DROP INDEX `fk_docentes_idx` ;

ALTER TABLE `Integrantes` 
ADD INDEX `fk_Integrantes_1_idx` (`CargoDocente` ASC);
ALTER TABLE `Integrantes` 
ADD CONSTRAINT `fk_Integrantes_1`
  FOREIGN KEY (`CargoDocente`)
  REFERENCES `CargosDocentes` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
