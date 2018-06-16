ALTER TABLE `cargosfaltantes` 
CHANGE COLUMN `FechaUltimoCosto` `FechaUltimoCosto` VARCHAR(45) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Legajo`, `Cargo`, `FechaUltimoCosto`);

ALTER TABLE `costos` 
CHANGE COLUMN `idcostos` `id` INT(11) NOT NULL ;

ALTER TABLE `plumasdocentes`.`cargosfaltantes` 
CHANGE COLUMN `UltimoCosto` `UltimoCosto` FLOAT NULL DEFAULT NULL ,
CHANGE COLUMN `FechaUltimoCosto` `FechaUltimoCosto` DATE NOT NULL ;


CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewproyectosdocentes` AS
    SELECT 
        `p`.`Nombre` AS `Nombre Proyecto`,
        `a`.`Descripcion` AS `Area`,
        `i`.`Cargo` AS `Cargo`,
        `cd`.`Codigo` AS `CargoDocente`,
        `i`.`HorasSemanales` AS `HorasSemanales`,
        `d`.`Legajo` AS `Legajo`
    FROM
        (`proyectos` `p`
        JOIN (`integrantes` `i`
        JOIN ((`cargosdocentes` `cd`
        JOIN `areas` `a` ON ((`cd`.`Area` = `a`.`Codigo`)))
        JOIN `docentes` `d` ON ((`cd`.`Legajo` = `d`.`Legajo`))) ON ((`i`.`CargoDocente` = `cd`.`Codigo`))) ON ((`i`.`Proyecto` = `p`.`id`)));
