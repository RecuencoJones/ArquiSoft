DROP SCHEMA IF EXISTS `testdb` ;
CREATE SCHEMA IF NOT EXISTS `testdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `testdb` ;

-- -----------------------------------------------------
-- Table `testdb`.`Publisher`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `testdb`.`Publisher` (
  `Id` INT NOT NULL ,
  `Type` BOOLEAN NOT NULL ,
  PRIMARY KEY (`Id`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `testdb`.`Person`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `testdb`.`Person` (
  `Id` INT NOT NULL ,
  `nombre` VARCHAR(20) NOT NULL ,
  `apellidos` VARCHAR(60) NULL ,
  `email` VARCHAR(60) NOT NULL ,
  `password` VARCHAR(20) NOT NULL ,
  `fechaNacimiento` BIGINT NOT NULL ,
  `ciudad` VARCHAR(45) NOT NULL ,
  `pais` VARCHAR(45) NOT NULL ,
  `telefono` INT NULL ,
  PRIMARY KEY (`Publicante_UUID`) ,
  INDEX `fk_Persona_Publicante1_idx` (`Publicante_UUID` ASC) ,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) ,
  CONSTRAINT `fk_Persona_Publicante1`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `mydb`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;