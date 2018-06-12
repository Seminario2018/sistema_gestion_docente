ALTER TABLE `Integrantes` 
DROP FOREIGN KEY `fk_cargosdocentes`;
ALTER TABLE `Integrantes` 
DROP COLUMN `CodigoCargo`,
DROP INDEX `fk_cargosdocentes_idx` ;

ALTER TABLE `Areas` 
CHANGE COLUMN `Desde` `Desde` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `Hasta` `Hasta` DATE NULL DEFAULT NULL ;

