-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tripor
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tripor
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tripor` DEFAULT CHARACTER SET utf8mb4 ;
USE `tripor` ;

-- -----------------------------------------------------
-- Table `tripor`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`member` ;

CREATE TABLE IF NOT EXISTS `tripor`.`member` (
  `member_id` VARCHAR(16) NOT NULL,
  `member_pw` VARCHAR(20) NOT NULL,
  `member_name` VARCHAR(20) NOT NULL,
  `email_id` VARCHAR(16) NULL,
  `email_domain` VARCHAR(16) NULL,
  `sido` INT NULL,
  `gugun` INT NULL,
  `join_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`article` ;

CREATE TABLE IF NOT EXISTS `tripor`.`article` (
  `article_id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(16) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_id`),
  INDEX `article_to_member_member_id_fk_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `article_to_member_member_id_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `tripor`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`trip_plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`trip_plan` ;

CREATE TABLE IF NOT EXISTS `tripor`.`trip_plan` (
  `plan_id` INT NOT NULL AUTO_INCREMENT,
  `plan_name` VARCHAR(20) NOT NULL,
  `member_id` VARCHAR(16) NOT NULL,
  `plan_register_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`plan_id`),
  INDEX `trip_plan_to_member_member_id_fk_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `trip_plan_to_member_member_id_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `tripor`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`attraction_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`attraction_info` ;

CREATE TABLE IF NOT EXISTS `tripor`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL,
  `title` VARCHAR(100) NULL,
  `addr` VARCHAR(100) NULL,
  `tel` VARCHAR(50) NULL,
  `first_image` VARCHAR(200) NULL,
  `sido_code` INT NULL,
  `gugun_code` INT NULL,
  `latitude` DECIMAL(20,17) NULL,
  `longitude` DECIMAL(20,17) NULL,
  `mlevel` VARCHAR(2) NULL,
  `cat1` VARCHAR(3) NULL,
  `cat2` VARCHAR(5) NULL,
  `cat3` VARCHAR(9) NULL,
  `description` VARCHAR(10000) NULL,
  PRIMARY KEY (`content_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`plan_content_relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`plan_content_relation` ;

CREATE TABLE IF NOT EXISTS `tripor`.`plan_content_relation` (
  `plan_relation_id` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `content_id` INT NOT NULL,
  PRIMARY KEY (`plan_relation_id`),
  INDEX `relation_to_trip_plan_plan_id_fk_idx` (`plan_id` ASC) VISIBLE,
  INDEX `relaction_to_attraction_info_content_id_fk_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `relation_to_trip_plan_plan_id_fk`
    FOREIGN KEY (`plan_id`)
    REFERENCES `tripor`.`trip_plan` (`plan_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `relaction_to_attraction_info_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `tripor`.`attraction_info` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`trip_review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`trip_review` ;

CREATE TABLE IF NOT EXISTS `tripor`.`trip_review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NOT NULL,
  `member_id` VARCHAR(16) NOT NULL,
  `review_content` VARCHAR(1000) NOT NULL,
  `review_register_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  INDEX `trip_review_to_member_member_id_fk_idx` (`member_id` ASC) VISIBLE,
  INDEX `trip_review_to_attraction_info_content_id_fk_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `trip_review_to_member_member_id_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `tripor`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `trip_review_to_attraction_info_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `tripor`.`attraction_info` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`article_image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`article_image` ;

CREATE TABLE IF NOT EXISTS `tripor`.`article_image` (
  `image_id` INT NOT NULL AUTO_INCREMENT,
  `article_id` INT NOT NULL,
  `image_name` VARCHAR(20) NOT NULL,
  `image_path` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`image_id`),
  INDEX `article_image_to_article_artice_id_fk_idx` (`article_id` ASC) VISIBLE,
  CONSTRAINT `article_image_to_article_artice_id_fk`
    FOREIGN KEY (`article_id`)
    REFERENCES `tripor`.`article` (`article_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
