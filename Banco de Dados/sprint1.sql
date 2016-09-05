-- MySQL Script generated by MySQL Workbench
-- Dom 04 Set 2016 13:24:53 BRT
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema witc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema witc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `witc` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `witc` ;

-- -----------------------------------------------------
-- Table `witc`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`Usuario` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `nome` VARCHAR(50) NOT NULL COMMENT '',
  `sobrenome` VARCHAR(150) NOT NULL COMMENT '',
  `email` VARCHAR(150) NOT NULL COMMENT '',
  `dataAniversario` DATE NOT NULL COMMENT '',
  `genero` VARCHAR(20) NOT NULL COMMENT '',
  `foto` BLOB NULL COMMENT '',
  `senha` VARCHAR(64) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`TipoPerfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`TipoPerfil` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `tipoPerfil` VARCHAR(20) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`Perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`Perfil` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `idUsuario` INT UNSIGNED NOT NULL COMMENT '',
  `idTipoPerfil` INT UNSIGNED NOT NULL COMMENT '',
  `qualificacao` INT NULL COMMENT '',
  `pseudonimo` VARCHAR(100) NULL COMMENT '',
  INDEX `fk_Perfil_Usuario1_idx` (`idUsuario` ASC)  COMMENT '',
  INDEX `fk_Perfil_TipoPerfil1_idx` (`idTipoPerfil` ASC)  COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  CONSTRAINT `fk_Perfil_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `witc`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Perfil_TipoPerfil1`
    FOREIGN KEY (`idTipoPerfil`)
    REFERENCES `witc`.`TipoPerfil` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`TipoStatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`TipoStatus` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `tipoStatus` VARCHAR(20) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`TipoPerfil_tem_TipoStatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`TipoPerfil_tem_TipoStatus` (
  `TipoPerfil_id` INT UNSIGNED NOT NULL COMMENT '',
  `TipoStatus_id` INT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`TipoPerfil_id`, `TipoStatus_id`)  COMMENT '',
  INDEX `fk_TipoPerfil_has_TipoStatus_TipoStatus1_idx` (`TipoStatus_id` ASC)  COMMENT '',
  INDEX `fk_TipoPerfil_has_TipoStatus_TipoPerfil1_idx` (`TipoPerfil_id` ASC)  COMMENT '',
  CONSTRAINT `fk_TipoPerfil_has_TipoStatus_TipoPerfil1`
    FOREIGN KEY (`TipoPerfil_id`)
    REFERENCES `witc`.`TipoPerfil` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TipoPerfil_has_TipoStatus_TipoStatus1`
    FOREIGN KEY (`TipoStatus_id`)
    REFERENCES `witc`.`TipoStatus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`TipoGenero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`TipoGenero` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `tipoGenero` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`TipoTexto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`TipoTexto` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `tipoTexto` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`Livro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`Livro` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `idTipoGenero` INT UNSIGNED NULL COMMENT '',
  `idTipoTexto` INT UNSIGNED NULL COMMENT '',
  `titulo` VARCHAR(45) NOT NULL COMMENT '',
  `nroPaginas` INT NULL COMMENT '',
  `capa` BLOB NULL COMMENT '',
  `classificacao` VARCHAR(45) NOT NULL COMMENT '',
  `disponivelBiblioteca` TINYINT(1) NOT NULL COMMENT '',
  `reportadoConteudoImproprio` TINYINT(1) NOT NULL COMMENT '',
  `qualificacao` INT NOT NULL COMMENT '',
  `texto` LONGTEXT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_Livro_TipoGenero1_idx` (`idTipoGenero` ASC)  COMMENT '',
  INDEX `fk_Livro_TipoTexto1_idx` (`idTipoTexto` ASC)  COMMENT '',
  CONSTRAINT `fk_Livro_TipoGenero1`
    FOREIGN KEY (`idTipoGenero`)
    REFERENCES `witc`.`TipoGenero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Livro_TipoTexto1`
    FOREIGN KEY (`idTipoTexto`)
    REFERENCES `witc`.`TipoTexto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`HistoricoLivros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`HistoricoLivros` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `idPerfil` INT UNSIGNED NOT NULL COMMENT '',
  `idTipoStatus` INT UNSIGNED NOT NULL COMMENT '',
  `idLivro` INT UNSIGNED NOT NULL COMMENT '',
  `dataInicio` DATE NOT NULL COMMENT '',
  `dataConclusao` DATE NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_HistoricoLivros_Perfil1_idx` (`idPerfil` ASC)  COMMENT '',
  INDEX `fk_HistoricoLivros_TipoStatus1_idx` (`idTipoStatus` ASC)  COMMENT '',
  INDEX `fk_HistoricoLivros_Livro1_idx` (`idLivro` ASC)  COMMENT '',
  CONSTRAINT `fk_HistoricoLivros_Perfil1`
    FOREIGN KEY (`idPerfil`)
    REFERENCES `witc`.`Perfil` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HistoricoLivros_TipoStatus1`
    FOREIGN KEY (`idTipoStatus`)
    REFERENCES `witc`.`TipoStatus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HistoricoLivros_Livro1`
    FOREIGN KEY (`idLivro`)
    REFERENCES `witc`.`Livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `witc`.`Usuario_tem_Amigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `witc`.`Usuario_tem_Amigo` (
  `idUsuario` INT UNSIGNED NOT NULL COMMENT '',
  `idAmigo` INT UNSIGNED NOT NULL COMMENT '',
  `dataSolicitacao` DATE NULL COMMENT '',
  `dataAceitacao` DATE NULL COMMENT '',
  `amigoStatus` TINYINT(1) NULL COMMENT '',
  PRIMARY KEY (`idUsuario`, `idAmigo`)  COMMENT '',
  INDEX `fk_Usuario_has_Usuario_Usuario2_idx` (`idAmigo` ASC)  COMMENT '',
  INDEX `fk_Usuario_has_Usuario_Usuario1_idx` (`idUsuario` ASC)  COMMENT '',
  INDEX `idx_Usuario_has_Amigo_idx` (`amigoStatus` ASC, `idUsuario` ASC, `idAmigo` ASC)  COMMENT '',
  CONSTRAINT `fk_Usuario_has_Usuario_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `witc`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Usuario_Usuario2`
    FOREIGN KEY (`idAmigo`)
    REFERENCES `witc`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
