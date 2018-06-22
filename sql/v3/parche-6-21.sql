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

ALTER TABLE `plumasdocentes`.`tiposinformes` 
CHANGE COLUMN `Editable` `Editable` TINYINT NOT NULL DEFAULT 1 ;
