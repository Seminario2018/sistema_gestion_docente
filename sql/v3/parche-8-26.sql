ALTER TABLE `costos` 
CHANGE COLUMN `Costo` `Costo` FLOAT(11,2) NOT NULL ;

ALTER TABLE `cargosdocentes` 
CHANGE COLUMN `UltimoCosto` `UltimoCosto` FLOAT(11,2) NULL DEFAULT NULL ;

ALTER TABLE `cargosfaltantes` 
CHANGE COLUMN `UltimoCosto` `UltimoCosto` FLOAT(11,2) NULL DEFAULT NULL ;

ALTER TABLE `rendiciones` 
CHANGE COLUMN `Monto` `Monto` FLOAT(11,2) NOT NULL ;

ALTER TABLE `subsidios` 
CHANGE COLUMN `MontoTotal` `MontoTotal` FLOAT(11,2) NOT NULL ;
