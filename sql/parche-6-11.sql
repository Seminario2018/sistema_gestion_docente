CREATE VIEW `ViewProyectosDocentes` AS
    SELECT 
        `p`.`Nombre` AS `Nombre Proyecto`,
        `a`.`Descripcion` AS `Area`,
        `i`.`Cargo` AS `Cargo`,
        `cd`.`Codigo` AS `CargoDocente`,
        `i`.`HorasSemanales` AS `HorasSemanales`
    FROM
        (`Proyectos` `p`
        JOIN (`Integrantes` `i`
        JOIN ((`CargosDocentes` `cd`
        JOIN `Areas` `a` ON ((`cd`.`Area` = `a`.`Codigo`)))
        JOIN `Docentes` `D` ON ((`cd`.`Legajo` = `D`.`Legajo`))) 
        ON ((`i`.`CargoDocente` = `cd`.`Codigo`))) ON ((`i`.`Proyecto` = `p`.`id`)))