ALTER TABLE `cargosfaltantes` 
CHANGE COLUMN `FechaUltimoCosto` `FechaUltimoCosto` VARCHAR(45) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Legajo`, `Cargo`, `FechaUltimoCosto`);
