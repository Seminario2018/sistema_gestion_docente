ALTER TABLE `programasinvestigacion` 
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(400) NOT NULL ;

ALTER TABLE `proyectos` 
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(500) NOT NULL ,
CHANGE COLUMN `Resumen` `Resumen` VARCHAR(10000) NULL DEFAULT NULL ,
CHANGE COLUMN `Descripcion` `Descripcion` VARCHAR(1000) NULL DEFAULT NULL ;
