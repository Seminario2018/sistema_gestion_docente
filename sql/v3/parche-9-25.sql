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
        left JOIN (`docentes` `d`
        left JOIN `personas` `p` ON (((`d`.`TipoDocumento` = `p`.`TipoDocumento`)
            AND (`d`.`NroDocumento` = `p`.`NroDocumento`)))) ON ((`cd`.`Legajo` = `d`.`Legajo`)))
        left JOIN `cargos` `c` ON ((`cd`.`Cargo` = `c`.`Codigo`)));


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
        LEFT JOIN (`integrantes` `i`
        left JOIN ((`cargosdocentes` `cd`
        JOIN `areas` `a` ON ((`cd`.`Area` = `a`.`Codigo`)))
        JOIN `docentes` `d` ON ((`cd`.`Legajo` = `d`.`Legajo`))) ON ((`i`.`CargoDocente` = `cd`.`Codigo`))) ON ((`i`.`Proyecto` = `p`.`id`)));
