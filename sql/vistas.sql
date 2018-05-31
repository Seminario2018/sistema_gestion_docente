CREATE VIEW `ViewCargoDocente` AS
    SELECT 
        `cd`.`Legajo` AS `Legajo`,
        `p`.`Nombre` AS `Nombre`,
        `p`.`Apellido` AS `Apellido`,
        `cd`.`Area` AS `Area`,
        `c`.`Descripcion` AS `descripcion`
    FROM
        ((`CargosDocentes` `cd`
        JOIN (`Docentes` `d`
        JOIN `Personas` `p` ON (((`d`.`TipoDocumento` = `p`.`TipoDocumento`)
            AND (`d`.`NroDocumento` = `p`.`NroDocumento`)))) ON ((`cd`.`Legajo` = `d`.`Legajo`)))
        JOIN `Cargos` `c` ON ((`cd`.`Cargo` = `c`.`Codigo`)))

CREATE VIEW `ViewPersona` AS
    SELECT 
        `p`.`NroDocumento` AS `NroDocumento`,
        `p`.`Nombre` AS `Nombre`,
        `p`.`Apellido` AS `Apellido`
    FROM
        (`Personas` `p`)


CREATE VIEW `ViewDocente` AS
    SELECT 
        `Docentes`.`Legajo` AS `Legajo`,
        `Personas`.`Nombre` AS `Nombre`,
        `Personas`.`Apellido` AS `Apellido`
    FROM
        (`Docentes`
        JOIN `Personas` ON (((`Docentes`.`TipoDocumento` = `Personas`.`TipoDocumento`)
            AND (`Docentes`.`NroDocumento` = `Personas`.`NroDocumento`))))


CREATE VIEW `ViewDocente` AS
    SELECT 
        `Docentes`.`Legajo` AS `Legajo`,
        `Personas`.`Nombre` AS `Nombre`,
        `Personas`.`Apellido` AS `Apellido`
    FROM
        (`Docentes`
        JOIN `Personas` ON (((`Docentes`.`TipoDocumento` = `Personas`.`TipoDocumento`)
            AND (`Docentes`.`NroDocumento` = `Personas`.`NroDocumento`))))


CREATE VIEW `ViewProyecto` AS
    SELECT 
        `Proyectos`.`id` AS `id`, `Proyectos`.`Nombre` AS `Nombre`
    FROM
        `Proyectos`

CREATE VIEW `ViewUsuario` AS
    SELECT 
        `Usuarios`.`Usuario` AS `Usuario`
    FROM
        `Usuarios`

