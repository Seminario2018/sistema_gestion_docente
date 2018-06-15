ALTER TABLE `cargosfaltantes` 
CHANGE COLUMN `FechaUltimoCosto` `FechaUltimoCosto` VARCHAR(45) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Legajo`, `Cargo`, `FechaUltimoCosto`);

ALTER TABLE `costos` 
CHANGE COLUMN `idcostos` `id` INT(11) NOT NULL ;

ALTER TABLE `plumasdocentes`.`cargosfaltantes` 
CHANGE COLUMN `UltimoCosto` `UltimoCosto` FLOAT NULL DEFAULT NULL ,
CHANGE COLUMN `FechaUltimoCosto` `FechaUltimoCosto` DATE NOT NULL ;
