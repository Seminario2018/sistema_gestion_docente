ALTER TABLE `CargosDocentes` 
CHANGE COLUMN `TipoCargo` `TipoCargo` INT NOT NULL ,
ADD INDEX `fk_CargosDocentes_1_idx` (`TipoCargo` ASC);
ALTER TABLE `CargosDocentes` 
ADD CONSTRAINT `fk_CargosDocentes_1`
  FOREIGN KEY (`TipoCargo`)
  REFERENCES `TiposCargos` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  
ALTER TABLE `Docentes` 
DROP FOREIGN KEY `fk_docentes_3`;
ALTER TABLE `Docentes` 
DROP INDEX `fk_docentes_3_idx` ;

ALTER TABLE `CategoriasInvestigacion` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ,
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);

ALTER TABLE `Docentes` 
ADD CONSTRAINT `fk_docentes_3`
  FOREIGN KEY (`CategoriaInvestigacion`)
  REFERENCES `CategoriasInvestigacion` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  


ALTER TABLE `EstadosCargos` 
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);




ALTER TABLE `EstadosDocentes` 
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);




ALTER TABLE `ProgramasInvestigacion` 
DROP FOREIGN KEY `fk_programainvestigacion_1`;
ALTER TABLE `ProgramasInvestigacion` 
DROP INDEX `fk_programainvestigacion_1_idx` ;


ALTER TABLE `EstadosProgramas` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL ,
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);


ALTER TABLE `ProgramasInvestigacion` 
ADD INDEX `fk_ProgramasInvestigacion_3_idx` (`Estado` ASC);
ALTER TABLE `ProgramasInvestigacion` 
ADD CONSTRAINT `fk_ProgramasInvestigacion_3`
  FOREIGN KEY (`Estado`)
  REFERENCES `EstadosProgramas` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `Proyectos` 
DROP FOREIGN KEY `fk_proyecto_1`;
ALTER TABLE `Proyectos` 
DROP INDEX `fk_proyecto_1_idx` ;


ALTER TABLE `EstadosProyectos` 
CHANGE COLUMN `idEstadoProyecto` `idEstadoProyecto` INT(11) NOT NULL ,
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);



ALTER TABLE `Proyectos` 
ADD INDEX `fk_Proyectos_2_idx` (`Estado` ASC);
ALTER TABLE `Proyectos` 
ADD CONSTRAINT `fk_Proyectos_2`
  FOREIGN KEY (`Estado`)
  REFERENCES `EstadosProyectos` (`idEstadoProyecto`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `TiposCargos` 
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);


ALTER TABLE `TiposContactos` 
ADD UNIQUE INDEX `Descripcion_UNIQUE` (`Descripcion` ASC);



