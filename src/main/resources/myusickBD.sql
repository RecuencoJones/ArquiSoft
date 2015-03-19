SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Publicante`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Publicante` (
  `UUID` INT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(60) NOT NULL ,
  `tipoPublicante` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`UUID`) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Publicacion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Publicacion` (
  `idPublicacion` INT NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NOT NULL ,
  `contenido` VARCHAR(144) NOT NULL ,
  `Publicante_UUID` INT NOT NULL ,
  PRIMARY KEY (`idPublicacion`) ,
  INDEX `fk_Publicacion_Publicante_idx` (`Publicante_UUID` ASC) ,
  CONSTRAINT `fk_Publicacion_Publicante`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `mydb`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Persona`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Persona` (
  `Publicante_UUID` INT NOT NULL ,
  `nombre` VARCHAR(20) NOT NULL ,
  `apellidos` VARCHAR(60) NULL ,
  `password` VARCHAR(20) NOT NULL ,
  `fechaNacimiento` DATE NOT NULL ,
  `ciudad` VARCHAR(45) NOT NULL ,
  `pais` VARCHAR(45) NOT NULL ,
  `telefono` INT NULL ,
  PRIMARY KEY (`Publicante_UUID`) ,
  INDEX `fk_Persona_Publicante1_idx` (`Publicante_UUID` ASC) ,
  CONSTRAINT `fk_Persona_Publicante1`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `mydb`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Grupo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Grupo` (
  `Publicante_UUID` INT NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `anyoFundacion` DATE NOT NULL ,
  `descripcion` VARCHAR(144) NULL ,
  PRIMARY KEY (`Publicante_UUID`) ,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) ,
  INDEX `fk_Grupo_Publicante1_idx` (`Publicante_UUID` ASC) ,
  CONSTRAINT `fk_Grupo_Publicante1`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `mydb`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`es_integrante`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`es_integrante` (
  `UUID_G` INT NOT NULL ,
  `UUID_P` INT NOT NULL ,
  PRIMARY KEY (`UUID_G`, `UUID_P`) ,
  INDEX `fk_Grupo_has_Persona_Persona1_idx` (`UUID_P` ASC) ,
  INDEX `fk_Grupo_has_Persona_Grupo1_idx` (`UUID_G` ASC) ,
  CONSTRAINT `fk_Grupo_has_Persona_Grupo1`
    FOREIGN KEY (`UUID_G` )
    REFERENCES `mydb`.`Grupo` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Grupo_has_Persona_Persona1`
    FOREIGN KEY (`UUID_P` )
    REFERENCES `mydb`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Aptitud`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Aptitud` (
  `idAptitud` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idAptitud`) ,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tiene_aptitud`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`tiene_aptitud` (
  `UUID_P` INT NOT NULL ,
  `idAptitud` INT NOT NULL ,
  PRIMARY KEY (`UUID_P`, `idAptitud`) ,
  INDEX `fk_Persona_has_Aptitud_Aptitud1_idx` (`idAptitud` ASC) ,
  INDEX `fk_Persona_has_Aptitud_Persona1_idx` (`UUID_P` ASC) ,
  CONSTRAINT `fk_Persona_has_Aptitud_Persona1`
    FOREIGN KEY (`UUID_P` )
    REFERENCES `mydb`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Persona_has_Aptitud_Aptitud1`
    FOREIGN KEY (`idAptitud` )
    REFERENCES `mydb`.`Aptitud` (`idAptitud` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Tag` (
  `idTag` INT NOT NULL AUTO_INCREMENT ,
  `nombreTag` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idTag`) ,
  UNIQUE INDEX `nombreTag_UNIQUE` (`nombreTag` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tiene_tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`tiene_tag` (
  `idTag` INT NOT NULL ,
  `UUID_P` INT NOT NULL ,
  PRIMARY KEY (`idTag`, `UUID_P`) ,
  INDEX `fk_Tag_has_Persona_Persona1_idx` (`UUID_P` ASC) ,
  INDEX `fk_Tag_has_Persona_Tag1_idx` (`idTag` ASC) ,
  CONSTRAINT `fk_Tag_has_Persona_Tag1`
    FOREIGN KEY (`idTag` )
    REFERENCES `mydb`.`Tag` (`idTag` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tag_has_Persona_Persona1`
    FOREIGN KEY (`UUID_P` )
    REFERENCES `mydb`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
