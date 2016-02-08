CREATE TABLE `computer` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45),
  `company_id` INTEGER NOT NULL,
  `introduced` TIMESTAMP ,
  `discontinued` TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX(`manufacturer`),
  FOREIGN KEY (`manufacturer`) REFERENCES `company`(`id`)
)ENGINE=INNODB;