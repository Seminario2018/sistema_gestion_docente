CREATE TABLE `CargosFaltantes` (
  `Legajo` INT NOT NULL,
  `Apellido` VARCHAR(50) NULL,
  `Nombre` VARCHAR(45) NULL,
  `Cargo` INT NOT NULL,
  `UltimoCosto` VARCHAR(45) NULL,
  `FechaUltimoCosto` VARCHAR(45) NULL,
  `Tipo` INT NOT NULL,
  PRIMARY KEY (`Legajo`, `Cargo`));