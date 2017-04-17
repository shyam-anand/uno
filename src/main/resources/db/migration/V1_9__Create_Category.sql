CREATE TABLE `category` (
  `id`   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50)  NOT NULL,
  UNIQUE INDEX `name` (`name`)
);

INSERT INTO `category` (`name`) VALUES ('Retail Store'), ('Hotel'), ('Restaurant'), ('Spa/Salon');