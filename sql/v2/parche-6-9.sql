ALTER TABLE `Subsidios` 
CHANGE COLUMN `Disposicion` `Disposicion` VARCHAR(15) NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Proyecto`, `Year`);


ALTER TABLE `Rendiciones` 
ADD COLUMN `Proyecto` INT NOT NULL AFTER `id`,
ADD COLUMN `YearSubsidio` INT NOT NULL AFTER `Proyecto`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`, `Proyecto`, `YearSubsidio`),
ADD INDEX `fk_Rendiciones_1_idx` (`Proyecto` ASC, `YearSubsidio` ASC);

ALTER TABLE `Rendiciones` 
ADD CONSTRAINT `fk_Rendiciones_1`
  FOREIGN KEY (`Proyecto` , `YearSubsidio`)
  REFERENCES `Subsidios` (`Proyecto` , `Year`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  
ALTER TABLE `Prorrogas` 
DROP COLUMN `Fecha_inicio`,
CHANGE COLUMN `Fecha_fin` `FechaFin` DATE NULL DEFAULT NULL ;
