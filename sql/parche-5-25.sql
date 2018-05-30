ALTER TABLE `plumasdocentes`.`Integrantes` 
DROP FOREIGN KEY `fk_cargosdocentes`;
ALTER TABLE `plumasdocentes`.`Integrantes` 
DROP COLUMN `CodigoCargo`,
DROP INDEX `fk_cargosdocentes_idx` ;

ALTER TABLE `plumasdocentes`.`Areas` 
CHANGE COLUMN `Desde` `Desde` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `Hasta` `Hasta` DATE NULL DEFAULT NULL ;

