ALTER TABLE `programasinvestigacion` 
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(400) NOT NULL ;

ALTER TABLE `proyectos` 
CHANGE COLUMN `Nombre` `Nombre` VARCHAR(500) NOT NULL ,
CHANGE COLUMN `Resumen` `Resumen` VARCHAR(10000) NULL DEFAULT NULL ,
CHANGE COLUMN `Descripcion` `Descripcion` VARCHAR(1000) NULL DEFAULT NULL ;

ALTER TABLE `contactos` 
DROP FOREIGN KEY `fk_Contactos_1`;
ALTER TABLE `contactos` 
ADD CONSTRAINT `fk_Contactos_1`
  FOREIGN KEY (`TipoDocumento` , `NroDocumento`)
  REFERENCES `personas` (`TipoDocumento` , `NroDocumento`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `domicilios` 
DROP FOREIGN KEY `fk_domicilios_1`;
ALTER TABLE `domicilios` 
ADD CONSTRAINT `fk_domicilios_1`
  FOREIGN KEY (`TipoDocumento` , `NroDocumento`)
  REFERENCES `personas` (`TipoDocumento` , `NroDocumento`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

