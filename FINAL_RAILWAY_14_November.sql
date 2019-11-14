-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Agent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Agent` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Agent` (
  `agent_id` INT(11) NOT NULL,
  `Employee_employee_id` INT(11) NOT NULL,
  PRIMARY KEY (`agent_id`),
  -- INDEX `fk_Agent_Employee1_idx` (`Employee_employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_Agent_Employee1`
    FOREIGN KEY (`Employee_employee_id`)
    REFERENCES `mydb`.`employee` (`employee_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Arrival`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Arrival` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Arrival` (
  `station_id` INT(11) NOT NULL,
  `route_id` INT(11) NOT NULL,
  `route_start_date` DATE NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`station_id`, `route_id`, `route_start_date`),
  -- INDEX `fk_Station_has_Route_Route3_idx` (`route_start_date` ASC, `route_id` ASC) VISIBLE,
  -- INDEX `fk_Station_has_Route_Station3_idx` (`station_id` ASC) VISIBLE,
  CONSTRAINT `fk_Station_has_Route_Route3`
    FOREIGN KEY (`route_start_date` , `route_id`)
    REFERENCES `mydb`.`route_instance` (`start_date` , `route_id`),
  CONSTRAINT `fk_Station_has_Route_Station3`
    FOREIGN KEY (`station_id`)
    REFERENCES `mydb`.`station` (`station_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Arrives`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Arrives` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Arrives` (
  `Station_station_id` INT(11) NOT NULL,
  `Route_route_id` INT(11) NOT NULL,
  `date` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`Station_station_id`, `Route_route_id`),
  -- INDEX `fk_Station_has_Route_Route1_idx` (`Route_route_id` ASC) VISIBLE,
  -- INDEX `fk_Station_has_Route_Station1_idx` (`Station_station_id` ASC) VISIBLE,
  CONSTRAINT `fk_Station_has_Route_Route1`
    FOREIGN KEY (`Route_route_id`)
    REFERENCES `mydb`.`route` (`route_id`),
  CONSTRAINT `fk_Station_has_Route_Station1`
    FOREIGN KEY (`Station_station_id`)
    REFERENCES `mydb`.`station` (`station_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Carriage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Carriage` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Carriage` (
  `train_id` INT(11) NOT NULL,
  `route_id` INT(11) NOT NULL,
  `carriage_num` INT(11) NOT NULL,
  PRIMARY KEY (`train_id`, `route_id`, `carriage_num`),
  CONSTRAINT `fk_Carriage_Train1`
    FOREIGN KEY (`train_id` , `route_id`)
    REFERENCES `mydb`.`train` (`train_id` , `route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Departs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Departs` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Departs` (
  `Station_station_id` INT(11) NOT NULL,
  `Route_route_id` INT(11) NOT NULL,
  `date` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`Station_station_id`, `Route_route_id`),
  -- INDEX `fk_Station_has_Route_Route2_idx` (`Route_route_id` ASC) VISIBLE,
  -- INDEX `fk_Station_has_Route_Station2_idx` (`Station_station_id` ASC) VISIBLE,
  CONSTRAINT `fk_Station_has_Route_Route2`
    FOREIGN KEY (`Route_route_id`)
    REFERENCES `mydb`.`route` (`route_id`),
  CONSTRAINT `fk_Station_has_Route_Station2`
    FOREIGN KEY (`Station_station_id`)
    REFERENCES `mydb`.`station` (`station_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Departure`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Departure` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Departure` (
  `station_id` INT(11) NOT NULL,
  `route_id` INT(11) NOT NULL,
  `route_start_date` DATE NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`station_id`, `route_id`, `route_start_date`),
  -- INDEX `fk_Station_has_Route_Route4_idx` (`route_start_date` ASC, `route_id` ASC) VISIBLE,
  -- INDEX `fk_Station_has_Route_Station4_idx` (`station_id` ASC) VISIBLE,
  CONSTRAINT `fk_Station_has_Route_Route4`
    FOREIGN KEY (`route_start_date` , `route_id`)
    REFERENCES `mydb`.`route_instance` (`start_date` , `route_id`),
  CONSTRAINT `fk_Station_has_Route_Station4`
    FOREIGN KEY (`station_id`)
    REFERENCES `mydb`.`station` (`station_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Employee` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Employee` (
  `employee_id` INT(11) NOT NULL,
  `salary` VARCHAR(45) NULL DEFAULT NULL,
  `Station_station_id` INT(11) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  -- UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  -- INDEX `fk_Employee_Station1_idx` (`Station_station_id` ASC) VISIBLE,
  CONSTRAINT `fk_Employee_Station1`
    FOREIGN KEY (`Station_station_id`)
    REFERENCES `mydb`.`station` (`station_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Locality`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Locality` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Locality` (
  `locality_id` INT(11) NOT NULL,
  `locality_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`locality_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Manager`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Manager` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Manager` (
  `manager_id` INT(11) NOT NULL,
  `Managercol` VARCHAR(45) NULL DEFAULT NULL,
  `Employee_employee_id` INT(11) NOT NULL,
  PRIMARY KEY (`manager_id`, `Employee_employee_id`),
  -- INDEX `fk_Manager_Employee1_idx` (`Employee_employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_Manager_Employee1`
    FOREIGN KEY (`Employee_employee_id`)
    REFERENCES `mydb`.`employee` (`employee_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Passenger`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Passenger` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Passenger` (
  `passenger_id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(64) NULL DEFAULT NULL,
  PRIMARY KEY (`passenger_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Route` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Route` (
  `route_id` INT(11) NOT NULL AUTO_INCREMENT,
  `route_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Route_Instance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Route_Instance` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Route_Instance` (
  `start_date` DATE NOT NULL,
  `route_id` INT(11) NOT NULL,
  PRIMARY KEY (`start_date`, `route_id`),
  -- INDEX `fk_Route_Instance_Route1_idx` (`route_id` ASC) VISIBLE,
  CONSTRAINT `fk_Route_Instance_Route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `mydb`.`route` (`route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Seat` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Seat` (
  `train_id` INT(11) NOT NULL,
  `route_id` INT(11) NOT NULL,
  `carriage_num` INT(11) NOT NULL,
  `seat_num` INT(11) NOT NULL,
  PRIMARY KEY (`train_id`, `route_id`, `carriage_num`, `seat_num`),
  CONSTRAINT `fk_Seat_Carriage1`
    FOREIGN KEY (`train_id` , `route_id` , `carriage_num`)
    REFERENCES `mydb`.`carriage` (`train_id` , `route_id` , `carriage_num`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Station`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Station` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Station` (
  `station_id` INT(11) NOT NULL,
  `station_name` VARCHAR(45) NULL DEFAULT NULL,
  `Locality_locality_id` INT(11) NOT NULL,
  PRIMARY KEY (`station_id`),
  -- INDEX `fk_Station_Locality1_idx` (`Locality_locality_id` ASC) VISIBLE,
  CONSTRAINT `fk_Station_Locality1`
    FOREIGN KEY (`Locality_locality_id`)
    REFERENCES `mydb`.`locality` (`locality_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Ticket` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Ticket` (
  `train_id` INT(11) NOT NULL,
  `route_id` INT(11) NOT NULL,
  `route_start_date` DATE NOT NULL,
  `carriage_num` INT(11) NOT NULL,
  `seat_num` INT(11) NOT NULL,
  `station_from` INT(11) NOT NULL,
  `station_to` INT(11) NOT NULL,
  `ticket_id` INT(11) NOT NULL AUTO_INCREMENT,
  `Passenger_passenger_id` INT(11) NOT NULL,
  PRIMARY KEY (`carriage_num`, `seat_num`, `train_id`, `route_start_date`, `route_id`, `Passenger_passenger_id`),
  key(`ticket_id`),
  -- UNIQUE INDEX `ticket_id_UNIQUE` (`ticket_id` ASC) VISIBLE,
  -- INDEX `fk_Ticket_Station1_idx` (`station_from` ASC) VISIBLE,
  -- INDEX `fk_Ticket_RouteInst_idx` (`route_start_date` ASC, `route_id` ASC) VISIBLE,
  -- INDEX `fk_Ticket_Station2_idx` (`station_to` ASC) VISIBLE,
  -- INDEX `fk_Ticket_Passenger1_idx` (`Passenger_passenger_id` ASC) VISIBLE,
  -- INDEX `fk_Ticket_Seat1` (`train_id` ASC, `route_id` ASC, `carriage_num` ASC, `seat_num` ASC) VISIBLE,
  CONSTRAINT `fk_Ticket_Passenger1`
    FOREIGN KEY (`Passenger_passenger_id`)
    REFERENCES `mydb`.`passenger` (`passenger_id`),
  CONSTRAINT `fk_Ticket_RouteInst`
    FOREIGN KEY (`route_start_date` , `route_id`)
    REFERENCES `mydb`.`route_instance` (`start_date` , `route_id`),
  CONSTRAINT `fk_Ticket_Seat1`
    FOREIGN KEY (`train_id` , `route_id` , `carriage_num` , `seat_num`)
    REFERENCES `mydb`.`seat` (`train_id` , `route_id` , `carriage_num` , `seat_num`),
  CONSTRAINT `fk_Ticket_Station1`
    FOREIGN KEY (`station_from`)
    REFERENCES `mydb`.`station` (`station_id`),
  CONSTRAINT `fk_Ticket_Station2`
    FOREIGN KEY (`station_to`)
    REFERENCES `mydb`.`station` (`station_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Train`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Train` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Train` (
  `train_id` INT(11) NOT NULL AUTO_INCREMENT,
  `route_id` INT(11) NOT NULL,
  PRIMARY KEY (`train_id`, `route_id`),
  -- INDEX `fk_Train_Route2_idx` (`route_id` ASC) VISIBLE,
  CONSTRAINT `fk_Train_Route2`
    FOREIGN KEY (`route_id`)
    REFERENCES `mydb`.`route` (`route_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Schedule` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Schedule` (
  `e_id` INT NOT NULL,
  `week_day` INT NOT NULL,
  `start_hour` INT NULL,
  `hours_num` INT NULL,
  PRIMARY KEY (`e_id`, `week_day`),
  CONSTRAINT `emp`
    FOREIGN KEY (`e_id`)
    REFERENCES `mydb`.`Employee` (`employee_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
