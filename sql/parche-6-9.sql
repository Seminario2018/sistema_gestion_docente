ALTER TABLE `Subsidios` 
CHANGE COLUMN `Disposicion` `Disposicion` VARCHAR(15) NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Proyecto`, `Year`);


ALTER TABLE `Rendisiones` 
ADD COLUMN `Proyecto` INT NOT NULL AFTER `id`,
ADD COLUMN `YearSubsidio` INT NOT NULL AFTER `Proyecto`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`, `Proyecto`, `YearSubsidio`),
ADD INDEX `fk_Rendisiones_1_idx` (`Proyecto` ASC, `YearSubsidio` ASC);

ALTER TABLE `Rendisiones` 
ADD CONSTRAINT `fk_Rendisiones_1`
  FOREIGN KEY (`Proyecto` , `YearSubsidio`)
  REFERENCES `plumasdocentes`.`Subsidios` (`Proyecto` , `Year`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;