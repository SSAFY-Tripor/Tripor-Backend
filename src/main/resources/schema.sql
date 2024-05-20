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
-- Table `tripor`.`sido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`sido` ;

CREATE TABLE IF NOT EXISTS `tripor`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`gugun`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`gugun` ;

CREATE TABLE IF NOT EXISTS `tripor`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_info_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_info_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `tripor`.`sido` (`sido_code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


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
  PRIMARY KEY (`member_id`),
  INDEX `member_to_sido_sido_code_fk_idx` (`sido` ASC) VISIBLE,
  INDEX `member_to_gugun_gugun_code_fk_idx` (`gugun` ASC) VISIBLE,
  CONSTRAINT `member_to_sido_sido_code_fk`
    FOREIGN KEY (`sido`)
    REFERENCES `tripor`.`sido` (`sido_code`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `member_to_gugun_gugun_code_fk`
    FOREIGN KEY (`gugun`)
    REFERENCES `tripor`.`gugun` (`gugun_code`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `tripor`.`member`
ADD COLUMN `token` VARCHAR(1000) NULL AFTER `join_date`;

ALTER TABLE `tripor`.`member`
ADD COLUMN `profile` VARCHAR(100) NULL AFTER `token`;

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
    ON DELETE CASCADE
    ON UPDATE CASCADE)
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
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

ALTER TABLE tripor.trip_plan
ADD COLUMN memo VARCHAR(2000) NULL;


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
  `latitude` DECIMAL(20,17) NULL, -- 위도 (-90 ~ 90, 적도가 0도이며, 한국은 33 ~ 43도에 위치)
  `longitude` DECIMAL(20,17) NULL, -- 경도 (-180 ~ 180, 한국은 124 ~ 132도에 위치)
  `mlevel` VARCHAR(2) NULL,
  `cat1` VARCHAR(3) NULL,
  `cat2` VARCHAR(5) NULL,
  `cat3` VARCHAR(9) NULL,
  `description` VARCHAR(10000) NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_info_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_info_to_gugun_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_info_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `tripor`.`sido` (`sido_code`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `attraction_info_to_gugun_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `tripor`.`gugun` (`gugun_code`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE attraction_info
ADD CONSTRAINT longitude_range CHECK (longitude BETWEEN 124 AND 132),
ADD CONSTRAINT latitude_range CHECK (latitude BETWEEN 33 AND 43);


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
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `relaction_to_attraction_info_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `tripor`.`attraction_info` (`content_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
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
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `trip_review_to_attraction_info_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `tripor`.`attraction_info` (`content_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tripor`.`article_image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`article_image`;

CREATE TABLE IF NOT EXISTS `tripor`.`article_image` (
  `image_id` INT NOT NULL AUTO_INCREMENT,
  `save_folder` VARCHAR(45) NULL,
  `original_file` VARCHAR(50) NULL,
  `save_file` VARCHAR(50) NULL,
  PRIMARY KEY (`image_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tripor`.`article_image_relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tripor`.`article_image_relation`;

CREATE TABLE IF NOT EXISTS `tripor`.`article_image_relation` (
  `article_id` INT NOT NULL,
  `image_id` INT NOT NULL,
  PRIMARY KEY (`article_id`, `image_id`),
  INDEX `article_image_relation_article_idx` (`article_id` ASC) VISIBLE,
  INDEX `article_image_relation_image_idx` (`image_id` ASC) VISIBLE,
  CONSTRAINT `article_image_relation_article_fk`
    FOREIGN KEY (`article_id`)
    REFERENCES `tripor`.`article` (`article_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `article_image_relation_image_fk`
    FOREIGN KEY (`image_id`)
    REFERENCES `tripor`.`article_image` (`image_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
