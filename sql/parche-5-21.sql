delete from Permisos;
delete from Usuarios;
delete from Roles;

ALTER TABLE `Areas` 
DROP FOREIGN KEY `Division`,
DROP FOREIGN KEY `fk_Areas_1`;
ALTER TABLE `Areas` 
ADD CONSTRAINT `Division`
  FOREIGN KEY (`Division`)
  REFERENCES `Divisiones` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Areas_1`
  FOREIGN KEY (`SubAreaDe`)
  REFERENCES `Areas` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `CargosDocentes` 
DROP FOREIGN KEY `areaDoc`,
DROP FOREIGN KEY `cargo`,
DROP FOREIGN KEY `legajo`;
ALTER TABLE `CargosDocentes` 
ADD CONSTRAINT `areaDoc`
  FOREIGN KEY (`Area`)
  REFERENCES `Areas` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `cargo`
  FOREIGN KEY (`Cargo`)
  REFERENCES `Cargos` (`Codigo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `legajo`
  FOREIGN KEY (`Legajo`)
  REFERENCES `Docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;





ALTER TABLE `Contactos` 
DROP FOREIGN KEY `fk_Contactos_2`;
ALTER TABLE `Contactos` 
ADD CONSTRAINT `fk_Contactos_2`
  FOREIGN KEY (`Tipo`)
  REFERENCES `TiposContactos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;




ALTER TABLE `Docentes` 
DROP FOREIGN KEY `fk_docentes_1`,
DROP FOREIGN KEY `fk_docentes_3`;
ALTER TABLE `Docentes` 
ADD CONSTRAINT `fk_docentes_1`
  FOREIGN KEY (`TipoDocumento` , `NroDocumento`)
  REFERENCES `Personas` (`TipoDocumento` , `NroDocumento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_docentes_3`
  FOREIGN KEY (`CategoriaInvestigacion`)
  REFERENCES `CategoriasInvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `Personas` 
DROP FOREIGN KEY `EstPersona`,
DROP FOREIGN KEY `tipoDoc`;
ALTER TABLE `Personas` 
ADD CONSTRAINT `EstPersona`
  FOREIGN KEY (`Estado`)
  REFERENCES `EstadosPersonas` (`idEstado`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `tipoDoc`
  FOREIGN KEY (`TipoDocumento`)
  REFERENCES `TiposDocumentos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;



ALTER TABLE `ProgramasInvestigacion` 
DROP FOREIGN KEY `fk_programainvestigacion_1`;
ALTER TABLE `ProgramasInvestigacion` 
ADD CONSTRAINT `fk_programainvestigacion_1`
  FOREIGN KEY (`Estado`)
  REFERENCES `EstadosProgramas` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `Prorrogas` 
DROP FOREIGN KEY `fk_Prorrogas_1`;
ALTER TABLE `Prorrogas` 
ADD CONSTRAINT `fk_Prorrogas_1`
  FOREIGN KEY (`Proyecto`)
  REFERENCES `Proyectos` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `Proyectos` 
DROP FOREIGN KEY `dir`,
DROP FOREIGN KEY `fk_proyecto_1`,
DROP FOREIGN KEY `programainves`;
ALTER TABLE `Proyectos` 
ADD CONSTRAINT `dir`
  FOREIGN KEY (`Director`)
  REFERENCES `Docentes` (`Legajo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_proyecto_1`
  FOREIGN KEY (`Estado`)
  REFERENCES `EstadosProyectos` (`idEstadoProyecto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `programainves`
  FOREIGN KEY (`Programa`)
  REFERENCES `ProgramasInvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `Eendisiones` 
RENAME TO  `Rendiciones` ;



ALTER TABLE `RolesXUsuario` 
DROP FOREIGN KEY `fk_RolesXUsuario_2`;
ALTER TABLE `RolesXUsuario` 
DROP INDEX `fk_RolesXUsuario_2_idx` ;

ALTER TABLE `Permisos` 
DROP FOREIGN KEY `fk_Permiso_1`;
ALTER TABLE `Permisos` 
DROP INDEX `fk_Permisos_2_idx` ;

ALTER TABLE `Roles` 
ADD COLUMN `id` INT NOT NULL FIRST,
ADD COLUMN `descripcion` VARCHAR(60) NULL AFTER `nombre`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

ALTER TABLE `Rolesxusuario` 
DROP FOREIGN KEY `fk_RolesXUsuario_1`;
ALTER TABLE `Rolesxusuario` 
CHANGE COLUMN `Rol` `Rol` INT NOT NULL ,
ADD INDEX `fk_rolesxusuario_2_idx` (`Rol` ASC);
ALTER TABLE `RolesXUsuario` 
ADD CONSTRAINT `fk_RolesXUsuario_1`
  FOREIGN KEY (`Usuario`)
  REFERENCES `Usuarios` (`Usuario`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `fk_rolesxusuario_2`
  FOREIGN KEY (`Rol`)
  REFERENCES `Roles` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `Permisos` 
DROP FOREIGN KEY `fk_Permiso_2`;
ALTER TABLE `Permisos` 
CHANGE COLUMN `Rol` `Rol` INT NOT NULL ,
ADD INDEX `fk_Permiso_1_idx` (`Rol` ASC);
ALTER TABLE `Permisos` 
ADD CONSTRAINT `fk_Permiso_2`
  FOREIGN KEY (`Modulo`)
  REFERENCES `modulos` (`idmodulo`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `fk_Permiso_1`
  FOREIGN KEY (`Rol`)
  REFERENCES `Roles` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;



ALTER TABLE `Titulos` 
DROP FOREIGN KEY `fk_titulos_1`;
ALTER TABLE `Titulos` 
ADD CONSTRAINT `fk_titulos_1`
  FOREIGN KEY (`TipoDocumento` , `NroDocumento`)
  REFERENCES `Personas` (`TipoDocumento` , `NroDocumento`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  

  
ALTER TABLE `Docentes` 
DROP FOREIGN KEY `fk_docentes_3`;
ALTER TABLE `Docentes` 
DROP INDEX `fk_docentes_3_idx` ;

ALTER TABLE `CategoriasInvestigacion` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ;


ALTER TABLE `Docentes` 
ADD INDEX `fk_docentes_3_idx` (`CategoriaInvestigacion` ASC);
ALTER TABLE `Docentes` 
ADD CONSTRAINT `fk_docentes_3`
  FOREIGN KEY (`CategoriaInvestigacion`)
  REFERENCES `CategoriasInvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
