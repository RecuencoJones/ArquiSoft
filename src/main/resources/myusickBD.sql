SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `myusickDB` ;
CREATE SCHEMA IF NOT EXISTS `myusickDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `myusickDB` ;

-- -----------------------------------------------------
-- Table `myusickDB`.`Publicante`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Publicante` (
  `UUID` INT NOT NULL AUTO_INCREMENT ,
  `tipoPublicante` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`UUID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`Publicacion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Publicacion` (
  `idPublicacion` INT NOT NULL AUTO_INCREMENT ,
  `fecha` BIGINT NOT NULL ,
  `contenido` VARCHAR(144) NOT NULL ,
  `Publicante_UUID` INT NOT NULL ,
  PRIMARY KEY (`idPublicacion`) ,
  INDEX `fk_Publicacion_Publicante_idx` (`Publicante_UUID` ASC) ,
  CONSTRAINT `fk_Publicacion_Publicante`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `myusickDB`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`Persona`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Persona` (
  `Publicante_UUID` INT NOT NULL ,
  `nombre` VARCHAR(20) NOT NULL ,
  `apellidos` VARCHAR(60) NULL ,
  `avatar` VARCHAR(500) NULL ,
  `email` VARCHAR(60) NOT NULL ,
  `password` VARCHAR(20) NOT NULL ,
  `fechaNacimiento` BIGINT NOT NULL ,
  `ciudad` VARCHAR(45) NOT NULL ,
  `pais` VARCHAR(45) NOT NULL ,
  `telefono` VARCHAR(10) NULL ,
  `descripcion` VARCHAR(144) NULL ,
  PRIMARY KEY (`Publicante_UUID`, `email`) ,
  INDEX `fk_Persona_Publicante1_idx` (`Publicante_UUID` ASC) ,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) ,
  CONSTRAINT `fk_Persona_Publicante1`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `myusickDB`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`Grupo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Grupo` (
  `Publicante_UUID` INT NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `anyoFundacion` INT NOT NULL ,
  `descripcion` VARCHAR(144) NULL ,
  `avatar` VARCHAR(500) NULL ,
  PRIMARY KEY (`Publicante_UUID`) ,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) ,
  INDEX `fk_Grupo_Publicante1_idx` (`Publicante_UUID` ASC) ,
  CONSTRAINT `fk_Grupo_Publicante1`
    FOREIGN KEY (`Publicante_UUID` )
    REFERENCES `myusickDB`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`es_integrante`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`es_integrante` (
  `UUID_G` INT NOT NULL ,
  `UUID_P` INT NOT NULL ,
  PRIMARY KEY (`UUID_G`, `UUID_P`) ,
  INDEX `fk_Grupo_has_Persona_Persona1_idx` (`UUID_P` ASC) ,
  INDEX `fk_Grupo_has_Persona_Grupo1_idx` (`UUID_G` ASC) ,
  CONSTRAINT `fk_Grupo_has_Persona_Grupo1`
    FOREIGN KEY (`UUID_G` )
    REFERENCES `myusickDB`.`Grupo` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Grupo_has_Persona_Persona1`
    FOREIGN KEY (`UUID_P` )
    REFERENCES `myusickDB`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`Aptitud`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Aptitud` (
  `idAptitud` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idAptitud`) ,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`tiene_aptitud`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`tiene_aptitud` (
  `UUID_P` INT NOT NULL ,
  `idAptitud` INT NOT NULL ,
  PRIMARY KEY (`UUID_P`, `idAptitud`) ,
  INDEX `fk_Persona_has_Aptitud_Aptitud1_idx` (`idAptitud` ASC) ,
  INDEX `fk_Persona_has_Aptitud_Persona1_idx` (`UUID_P` ASC) ,
  CONSTRAINT `fk_Persona_has_Aptitud_Persona1`
    FOREIGN KEY (`UUID_P` )
    REFERENCES `myusickDB`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Persona_has_Aptitud_Aptitud1`
    FOREIGN KEY (`idAptitud` )
    REFERENCES `myusickDB`.`Aptitud` (`idAptitud` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`Tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Tag` (
  `idTag` INT NOT NULL AUTO_INCREMENT ,
  `nombreTag` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idTag`) ,
  UNIQUE INDEX `nombreTag_UNIQUE` (`nombreTag` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`persona_tiene_tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`persona_tiene_tag` (
  `idTag` INT NOT NULL ,
  `UUID_P` INT NOT NULL ,
  PRIMARY KEY (`idTag`, `UUID_P`) ,
  INDEX `fk_Tag_has_Persona_Persona1_idx` (`UUID_P` ASC) ,
  INDEX `fk_Tag_has_Persona_Tag1_idx` (`idTag` ASC) ,
  CONSTRAINT `fk_Tag_has_Persona_Tag1`
    FOREIGN KEY (`idTag` )
    REFERENCES `myusickDB`.`Tag` (`idTag` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tag_has_Persona_Persona1`
    FOREIGN KEY (`UUID_P` )
    REFERENCES `myusickDB`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`grupo_tiene_tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`grupo_tiene_tag` (
  `idTag` INT NOT NULL ,
  `UUID_G` INT NOT NULL ,
  PRIMARY KEY (`idTag`, `UUID_G`) ,
  INDEX `fk_Tag_has_Grupo_Grupo1_idx` (`UUID_G` ASC) ,
  INDEX `fk_Tag_has_Grupo_Tag1_idx` (`idTag` ASC) ,
  CONSTRAINT `fk_Tag_has_Grupo_Tag1`
    FOREIGN KEY (`idTag` )
    REFERENCES `myusickDB`.`Tag` (`idTag` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tag_has_Grupo_Grupo1`
    FOREIGN KEY (`UUID_G` )
    REFERENCES `myusickDB`.`Grupo` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusickDB`.`Seguir`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Seguir` (
  `seguidor` INT NOT NULL ,
  `seguido` INT NOT NULL ,
  PRIMARY KEY (`seguidor`, `seguido`) ,
  INDEX `fk_Publicante_has_Publicante_Publicante2_idx` (`seguido` ASC) ,
  INDEX `fk_Publicante_has_Publicante_Publicante1_idx` (`seguidor` ASC) ,
  CONSTRAINT `fk_Publicante_has_Publicante_Publicante1`
    FOREIGN KEY (`seguidor` )
    REFERENCES `myusickDB`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Publicante_has_Publicante_Publicante2`
    FOREIGN KEY (`seguido` )
    REFERENCES `myusickDB`.`Publicante` (`UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myusickDB`.`Pendiente_aceptacion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusickDB`.`Pendiente_aceptacion` (
  `Grupo` INT NOT NULL ,
  `Persona` INT NOT NULL ,
  PRIMARY KEY (`Grupo`, `Persona`) ,
  INDEX `fk_Grupo_has_Persona_Persona2_idx` (`Persona` ASC) ,
  INDEX `fk_Grupo_has_Persona_Grupo2_idx` (`Grupo` ASC) ,
  CONSTRAINT `fk_Grupo_has_Persona_Grupo2`
  FOREIGN KEY (`Grupo` )
  REFERENCES `myusickDB`.`Grupo` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Grupo_has_Persona_Persona2`
  FOREIGN KEY (`Persona` )
  REFERENCES `myusickDB`.`Persona` (`Publicante_UUID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

USE `myusickDB` ;


-- -----------------------------------------------------
-- BEST TRIGGER
-- -----------------------------------------------------
DELIMITER $$

CREATE TRIGGER insert_user BEFORE insert ON `Persona`
FOR EACH ROW BEGIN
IF (NEW.avatar IS NULL OR NEW.avatar = '') THEN
SET NEW.avatar ='img/placeholder.jpg';
END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER insert_group BEFORE insert ON `Grupo`
FOR EACH ROW BEGIN
IF (NEW.avatar IS NULL OR NEW.avatar = '') THEN
SET NEW.avatar ='img/placeholder.jpg';
END IF;
END$$

DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
