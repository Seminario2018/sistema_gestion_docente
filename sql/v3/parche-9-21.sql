CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewdocente` AS
    SELECT 
        `docentes`.`Legajo` AS `Legajo`,
        `personas`.`Nombre` AS `Nombre`,
        `personas`.`Apellido` AS `Apellido`
    FROM
        (`docentes`
        left JOIN `personas` ON (((`docentes`.`TipoDocumento` = `personas`.`TipoDocumento`)
            AND (`docentes`.`NroDocumento` = `personas`.`NroDocumento`))));


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
        left JOIN (`integrantes` `i`
        JOIN ((`cargosdocentes` `cd`
        JOIN `areas` `a` ON ((`cd`.`Area` = `a`.`Codigo`)))
        JOIN `docentes` `d` ON ((`cd`.`Legajo` = `d`.`Legajo`))) ON ((`i`.`CargoDocente` = `cd`.`Codigo`))) ON ((`i`.`Proyecto` = `p`.`id`)));
