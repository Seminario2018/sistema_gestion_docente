CREATE  OR REPLACE VIEW `ViewCargo` AS
    SELECT 
        `Cargos`.`Descripcion` AS `Descripcion`,
        `Cargos`.`Codigo` AS `Codigo`
    FROM
        `Cargos`;
