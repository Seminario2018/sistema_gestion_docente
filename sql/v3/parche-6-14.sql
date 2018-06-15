CREATE  OR REPLACE VIEW `ViewProgramas` AS
select id, Nombre from programasinvestigacion;

CREATE TABLE `costos` (
  `idcostos` INT NOT NULL,
  `CodigoCargo` INT NOT NULL,
  `Costo` FLOAT NULL,
  `Fecha` DATE NULL,
  PRIMARY KEY (`idcostos`, `CodigoCargo`),
  INDEX `fk_Costos_1_idx` (`CodigoCargo` ASC),
  CONSTRAINT `fk_Costos_1`
    FOREIGN KEY (`CodigoCargo`)
    REFERENCES `plumasdocentes`.`cargosdocentes` (`Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


ALTER TABLE `costos` 
CHANGE COLUMN `Costo` `Costo` FLOAT NOT NULL ;

ALTER TABLE `plumasdocentes`.`costos` 
DROP FOREIGN KEY `fk_Costos_1`;
ALTER TABLE `plumasdocentes`.`costos` 
ADD CONSTRAINT `fk_Costos_1`
  FOREIGN KEY (`CodigoCargo`)
  REFERENCES `plumasdocentes`.`cargosdocentes` (`Codigo`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

