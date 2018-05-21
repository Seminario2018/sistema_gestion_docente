delete from permisos;
delete from usuarios;
delete from roles;

ALTER TABLE `areas` 
DROP FOREIGN KEY `Division`,
DROP FOREIGN KEY `fk_Areas_1`;
ALTER TABLE `areas` 
ADD CONSTRAINT `Division`
  FOREIGN KEY (`Division`)
  REFERENCES `divisiones` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Areas_1`
  FOREIGN KEY (`SubAreaDe`)
  REFERENCES `areas` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `cargosdocentes` 
DROP FOREIGN KEY `areaDoc`,
DROP FOREIGN KEY `cargo`,
DROP FOREIGN KEY `legajo`;
ALTER TABLE `cargosdocentes` 
ADD CONSTRAINT `areaDoc`
  FOREIGN KEY (`Area`)
  REFERENCES `areas` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `cargo`
  FOREIGN KEY (`Cargo`)
  REFERENCES `cargos` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `legajo`
  FOREIGN KEY (`Legajo`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;





ALTER TABLE `contactos` 
DROP FOREIGN KEY `fk_Contactos_2`;
ALTER TABLE `contactos` 
ADD CONSTRAINT `fk_Contactos_2`
  FOREIGN KEY (`Tipo`)
  REFERENCES `tiposcontactos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;




ALTER TABLE `docentes` 
DROP FOREIGN KEY `fk_docentes_1`,
DROP FOREIGN KEY `fk_docentes_3`;
ALTER TABLE `docentes` 
ADD CONSTRAINT `fk_docentes_1`
  FOREIGN KEY (`TipoDocumento` , `NroDocumento`)
  REFERENCES `personas` (`TipoDocumento` , `NroDocumento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_docentes_3`
  FOREIGN KEY (`CategoriaInvestigacion`)
  REFERENCES `categoriasinvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `personas` 
DROP FOREIGN KEY `EstPersona`,
DROP FOREIGN KEY `tipoDoc`;
ALTER TABLE `personas` 
ADD CONSTRAINT `EstPersona`
  FOREIGN KEY (`Estado`)
  REFERENCES `estadospersonas` (`idEstado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `tipoDoc`
  FOREIGN KEY (`TipoDocumento`)
  REFERENCES `tiposdocumentos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `programasinvestigacion` 
DROP FOREIGN KEY `fk_programainvestigacion_1`;
ALTER TABLE `programasinvestigacion` 
ADD CONSTRAINT `fk_programainvestigacion_1`
  FOREIGN KEY (`Estado`)
  REFERENCES `estadosprogramas` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `prorrogas` 
DROP FOREIGN KEY `fk_Prorrogas_1`;
ALTER TABLE `prorrogas` 
ADD CONSTRAINT `fk_Prorrogas_1`
  FOREIGN KEY (`Proyecto`)
  REFERENCES `proyectos` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `proyectos` 
DROP FOREIGN KEY `dir`,
DROP FOREIGN KEY `fk_proyecto_1`,
DROP FOREIGN KEY `programainves`;
ALTER TABLE `proyectos` 
ADD CONSTRAINT `dir`
  FOREIGN KEY (`Director`)
  REFERENCES `docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_proyecto_1`
  FOREIGN KEY (`Estado`)
  REFERENCES `estadosproyectos` (`idEstadoProyecto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `programainves`
  FOREIGN KEY (`Programa`)
  REFERENCES `programasinvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `rendisiones` 
RENAME TO  `rendiciones` ;



ALTER TABLE `rolesxusuario` 
DROP FOREIGN KEY `fk_RolesXUsuario_2`;
ALTER TABLE `rolesxusuario` 
DROP INDEX `fk_RolesXUsuario_2_idx` ;

ALTER TABLE `permisos` 
DROP FOREIGN KEY `fk_Permiso_1`;
ALTER TABLE `permisos` 
DROP INDEX `fk_permisos_2_idx` ;

ALTER TABLE `roles` 
ADD COLUMN `id` INT NOT NULL FIRST,
ADD COLUMN `descripcion` VARCHAR(60) NULL AFTER `nombre`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

ALTER TABLE `rolesxusuario` 
DROP FOREIGN KEY `fk_RolesXUsuario_1`;
ALTER TABLE `rolesxusuario` 
CHANGE COLUMN `Rol` `Rol` INT NOT NULL ,
ADD INDEX `fk_rolesxusuario_2_idx` (`Rol` ASC);
ALTER TABLE `rolesxusuario` 
ADD CONSTRAINT `fk_RolesXUsuario_1`
  FOREIGN KEY (`Usuario`)
  REFERENCES `usuarios` (`Usuario`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `fk_rolesxusuario_2`
  FOREIGN KEY (`Rol`)
  REFERENCES `roles` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `permisos` 
DROP FOREIGN KEY `fk_Permiso_2`;
ALTER TABLE `permisos` 
CHANGE COLUMN `Rol` `Rol` INT NOT NULL ,
ADD INDEX `fk_Permiso_1_idx` (`Rol` ASC);
ALTER TABLE `permisos` 
ADD CONSTRAINT `fk_Permiso_2`
  FOREIGN KEY (`Modulo`)
  REFERENCES `modulos` (`idmodulo`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `fk_Permiso_1`
  FOREIGN KEY (`Rol`)
  REFERENCES `roles` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;



ALTER TABLE `titulos` 
DROP FOREIGN KEY `fk_titulos_1`;
ALTER TABLE `titulos` 
ADD CONSTRAINT `fk_titulos_1`
  FOREIGN KEY (`TipoDocumento` , `NroDocumento`)
  REFERENCES `personas` (`TipoDocumento` , `NroDocumento`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  

  
ALTER TABLE `docentes` 
DROP FOREIGN KEY `fk_docentes_3`;
ALTER TABLE `docentes` 
DROP INDEX `fk_docentes_3_idx` ;

ALTER TABLE `categoriasinvestigacion` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ;


ALTER TABLE `docentes` 
ADD INDEX `fk_docentes_3_idx` (`CategoriaInvestigacion` ASC);
ALTER TABLE `docentes` 
ADD CONSTRAINT `fk_docentes_3`
  FOREIGN KEY (`CategoriaInvestigacion`)
  REFERENCES `categoriasinvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
