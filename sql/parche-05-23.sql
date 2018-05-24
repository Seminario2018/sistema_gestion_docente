ALTER TABLE `CargosDocentes` 
CHANGE COLUMN `TipoCargo` `TipoCargo` INT NOT NULL ,
ADD INDEX `fk_CargosDocentes_1_idx` (`TipoCargo` ASC);
ALTER TABLE `CargosDocentes` 
ADD CONSTRAINT `fk_CargosDocentes_1`
  FOREIGN KEY (`TipoCargo`)
  REFERENCES `TiposCargos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
