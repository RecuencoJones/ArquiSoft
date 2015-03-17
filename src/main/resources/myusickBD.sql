SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `myusick` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `myusick` ;

-- -----------------------------------------------------
-- Table `myusick`.`Grupo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Grupo` (
  `idGrupo` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(200) NOT NULL ,
  `email` VARCHAR(100) NOT NULL ,
  `password` VARCHAR(100) NOT NULL ,
  `anyoFundacion` INT NULL ,
  `descripcion` VARCHAR(144) NULL ,
  PRIMARY KEY (`idGrupo`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`Miembro`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Miembro` (
  `idComponente` INT NOT NULL AUTO_INCREMENT ,
  `apNombre` VARCHAR(200) NOT NULL ,
  `edad` INT NULL ,
  `sexo` VARCHAR(1) NULL ,
  PRIMARY KEY (`idComponente`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`Publicacion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Publicacion` (
  `idPub` INT NOT NULL AUTO_INCREMENT ,
  `texto` VARCHAR(200) NOT NULL ,
  `autor` INT NOT NULL ,
  `enlace` VARCHAR(100) NULL ,
  PRIMARY KEY (`idPub`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`Tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Tag` (
  `idTag` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(60) NOT NULL ,
  PRIMARY KEY (`idTag`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`Aptitud`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Aptitud` (
  `idAptitud` INT NOT NULL AUTO_INCREMENT ,
  `Nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idAptitud`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`Pertenece`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Pertenece` (
  `Grupo_idGrupo` INT NOT NULL ,
  `Componente_idComponente` INT NOT NULL ,
  PRIMARY KEY (`Grupo_idGrupo`, `Componente_idComponente`) ,
  INDEX `fk_Grupo_has_Componente_Componente1_idx` (`Componente_idComponente` ASC) ,
  INDEX `fk_Grupo_has_Componente_Grupo1_idx` (`Grupo_idGrupo` ASC) ,
  CONSTRAINT `fk_Grupo_has_Componente_Grupo1`
    FOREIGN KEY (`Grupo_idGrupo` )
    REFERENCES `myusick`.`Grupo` (`idGrupo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Grupo_has_Componente_Componente1`
    FOREIGN KEY (`Componente_idComponente` )
    REFERENCES `myusick`.`Miembro` (`idComponente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`Tiene`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`Tiene` (
  `Componente_idComponente` INT NOT NULL ,
  `Aptitud_idAptitud` INT NOT NULL ,
  PRIMARY KEY (`Componente_idComponente`, `Aptitud_idAptitud`) ,
  INDEX `fk_Componente_has_Aptitud_Aptitud1_idx` (`Aptitud_idAptitud` ASC) ,
  INDEX `fk_Componente_has_Aptitud_Componente1_idx` (`Componente_idComponente` ASC) ,
  CONSTRAINT `fk_Componente_has_Aptitud_Componente1`
    FOREIGN KEY (`Componente_idComponente` )
    REFERENCES `myusick`.`Miembro` (`idComponente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Componente_has_Aptitud_Aptitud1`
    FOREIGN KEY (`Aptitud_idAptitud` )
    REFERENCES `myusick`.`Aptitud` (`idAptitud` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myusick`.`publica`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `myusick`.`publica` (
  `Tag_idTag` INT NOT NULL ,
  `Grupo_idGrupo` INT NOT NULL ,
  PRIMARY KEY (`Tag_idTag`, `Grupo_idGrupo`) ,
  INDEX `fk_Tag_has_Grupo_Grupo1_idx` (`Grupo_idGrupo` ASC) ,
  INDEX `fk_Tag_has_Grupo_Tag1_idx` (`Tag_idTag` ASC) ,
  CONSTRAINT `fk_Tag_has_Grupo_Tag1`
    FOREIGN KEY (`Tag_idTag` )
    REFERENCES `myusick`.`Tag` (`idTag` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tag_has_Grupo_Grupo1`
    FOREIGN KEY (`Grupo_idGrupo` )
    REFERENCES `myusick`.`Grupo` (`idGrupo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `myusick` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
