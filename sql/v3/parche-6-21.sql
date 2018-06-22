ALTER TABLE `columnas` 
ADD COLUMN `Tipo` VARCHAR(45) NULL AFTER `Posicion`;

ALTER TABLE `columnas` 
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `Atributo` `Atributo` VARCHAR(100) NOT NULL ;

ALTER TABLE `tiposinformes` 
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(200) NOT NULL ,
CHANGE COLUMN `Descripcion` `Descripcion` VARCHAR(1000) NULL DEFAULT NULL ,
CHANGE COLUMN `FromString` `FromString` VARCHAR(1000) NULL DEFAULT NULL ,
CHANGE COLUMN `GroupByString` `GroupByString` VARCHAR(1000) NULL DEFAULT NULL ;

ALTER TABLE `columnas` 
DROP FOREIGN KEY `fk_Columna_1`;

ALTER TABLE `tiposinformes` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ;

ALTER TABLE `columnas` 
ADD CONSTRAINT `fk_columnas_1`
  FOREIGN KEY (`TipoInforme`)
  REFERENCES `tiposinformes` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `columnas` 
CHANGE COLUMN `Visible` `Visible` TINYINT NULL DEFAULT NULL ;

ALTER TABLE `tiposinformes` 
CHANGE COLUMN `Editable` `Editable` TINYINT NOT NULL DEFAULT 0 ;

CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewusuario` AS
    SELECT 
        `usuarios`.`Usuario` AS `Usuario`
    FROM
        `usuarios`
	WHERE
		`usuarios`.`Usuario` != 'su';

CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewpersona` AS
    SELECT 
        `p`.`NroDocumento` AS `NroDocumento`,
        `p`.`Nombre` AS `Nombre`,
        `p`.`Apellido` AS `Apellido`
    FROM
        `personas` `p`
	WHERE
		`p`.`NroDocumento` != 0;

CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewrol` AS
    SELECT 
        `roles`.`id` AS `id`, `roles`.`nombre` AS `nombre`
    FROM
        `roles`
	WHERE
		`roles`.`id` != 0;
