CREATE 
	OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewcargodocente` AS
    SELECT 
        `d`.`Legajo` AS `Legajo`,
        `p`.`Nombre` AS `Nombre`,
        `p`.`Apellido` AS `Apellido`,
        `cd`.`Area` AS `Area`,
        `cd`.`Codigo` AS `Codigo`,
        `c`.`Descripcion` AS `descripcion`
    FROM
        (((`personas` `p`
        JOIN `docentes` `d` ON (((`d`.`TipoDocumento` = `p`.`TipoDocumento`)
            AND (`d`.`NroDocumento` = `p`.`NroDocumento`))))
        LEFT JOIN `cargosdocentes` `cd` ON ((`cd`.`Legajo` = `d`.`Legajo`)))
        LEFT JOIN `cargos` `c` ON ((`cd`.`Cargo` = `c`.`Codigo`)))
    ORDER BY ISNULL(`d`.`Legajo`) , `d`.`Legajo`;



CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewdocente` AS
    SELECT 
        `docentes`.`Legajo` AS `Legajo`,
        `personas`.`NroDocumento` AS `NroDocumento`,
        `personas`.`Nombre` AS `Nombre`,
        `personas`.`Apellido` AS `Apellido`
    FROM
        (`personas`
        LEFT JOIN `docentes` ON (((`docentes`.`TipoDocumento` = `personas`.`TipoDocumento`)
            AND (`docentes`.`NroDocumento` = `personas`.`NroDocumento`))))
    WHERE
        (`personas`.`NroDocumento` <> '0')
	ORDER BY ISNULL(`docentes`.`Legajo`), `docentes`.`Legajo`;

                
                
CREATE 
    OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewusuario` AS
    SELECT 
        `usuarios`.`Usuario` AS `Usuario`,
        `personas`.`NroDocumento` AS `NroDocumento`,
        `personas`.`Nombre` AS `Nombre`,
        `personas`.`Apellido` AS `Apellido`
    FROM
        (`personas`
        LEFT JOIN `usuarios` ON (((`usuarios`.`TipoDocumentoPersona` = `personas`.`TipoDocumento`)
            AND (`personas`.`NroDocumento` = `usuarios`.`NroDocumentoPerson`))))
    WHERE
        (`personas`.`NroDocumento` <> '0')
    ORDER BY ISNULL(`usuarios`.`Usuario`) , `usuarios`.`Usuario`;
        


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