ALTER TABLE `plumasdocentes`.`Integrantes` 
DROP FOREIGN KEY `fk_docentes`;
ALTER TABLE `plumasdocentes`.`Integrantes` 
CHANGE COLUMN `Legajo` `CargoDocente` INT(11) NULL DEFAULT NULL ,
DROP INDEX `fk_docentes_idx` ;

ALTER TABLE `plumasdocentes`.`Integrantes` 
ADD INDEX `fk_Integrantes_1_idx` (`CargoDocente` ASC);
ALTER TABLE `plumasdocentes`.`Integrantes` 
ADD CONSTRAINT `fk_Integrantes_1`
  FOREIGN KEY (`CargoDocente`)
  REFERENCES `plumasdocentes`.`CargosDocentes` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
