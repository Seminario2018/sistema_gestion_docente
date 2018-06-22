CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewcargodocente` AS
    SELECT 
        `cd`.`Legajo` AS `Legajo`,
        `p`.`Nombre` AS `Nombre`,
        `p`.`Apellido` AS `Apellido`,
        `cd`.`Area` AS `Area`,
        `cd`.`Codigo` AS `Codigo`,
        `c`.`Descripcion` AS `descripcion`
    FROM
        ((`cargosdocentes` `cd`
        JOIN (`docentes` `d`
        JOIN `personas` `p` ON (((`d`.`TipoDocumento` = `p`.`TipoDocumento`)
            AND (`d`.`NroDocumento` = `p`.`NroDocumento`)))) ON ((`cd`.`Legajo` = `d`.`Legajo`)))
        JOIN `cargos` `c` ON ((`cd`.`Cargo` = `c`.`Codigo`)));
