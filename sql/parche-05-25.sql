ALTER TABLE `plumasdocentes`.`Integrantes` 
DROP FOREIGN KEY `fk_cargosdocentes`;
ALTER TABLE `plumasdocentes`.`Integrantes` 
DROP COLUMN `CodigoCargo`,
DROP INDEX `fk_cargosdocentes_idx` ;
