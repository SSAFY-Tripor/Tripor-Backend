use ssafytrip;

CREATE TABLE `ssafytrip`.`members` (
	`user_id` VARCHAR(20) NOT NULL,
	`user_pw` VARCHAR(20) NOT NULL,
	`user_name` VARCHAR(20) NOT NULL,
	`email_id` VARCHAR(20) NULL,
	`email_domain` VARCHAR(20) NULL,
	`sido` INT NULL,
	`gugun` INT NULL,
	`join_date` DATETIME NULL DEFAULT now(),
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `ssafytrip`.`board` (
	`board_no` INT NOT NULL AUTO_INCREMENT,
	`user_id` VARCHAR(16) NOT NULL,
	`subject` VARCHAR(100) NOT NULL,
	`content` VARCHAR(2000) NOT NULL,
	`hit` INT NULL DEFAULT '0',
	`register_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`board_no`),
	INDEX `userId_idx` (`user_id` ASC) VISIBLE,
	CONSTRAINT `user_id`
	FOREIGN KEY (`user_id`)
	REFERENCES `ssafytrip`.`members` (`user_id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
);
    
CREATE TABLE `ssafytrip`.`trip_plan` (
	`plan_id` INT AUTO_INCREMENT PRIMARY KEY,
	`plan_name` VARCHAR(255) NOT NULL,
	`trip_list` VARCHAR(1024) NOT NULL,
	`plan_user_id` VARCHAR(20) NOT NULL,
	`plan_register_date` DATETIME DEFAULT NOW() NOT NULL, -- 추가된 필드
	CONSTRAINT `fk_user_id`
	FOREIGN KEY (`plan_user_id`)
	REFERENCES `ssafytrip`.`members` (`user_id`)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
);