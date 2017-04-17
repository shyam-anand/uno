CREATE TABLE `catalog` (
  `id`           INT UNSIGNED            AUTO_INCREMENT PRIMARY KEY,
  `business_id`  INT UNSIGNED   NOT NULL,
  `item`         VARCHAR(40)    NOT NULL,
  `price`        DECIMAL(13, 4) NOT NULL,
  `category`     INT UNSIGNED   NOT NULL,
  `synonyms`     TINYTEXT,
  `created_time` TIMESTAMP      NOT NULL DEFAULT current_timestamp,
  INDEX `item` (`item`),
  INDEX `price` (`price`),
  INDEX `category` (`category`),
  FOREIGN KEY `business_id` (`business_id`) REFERENCES `business` (`id`)
    ON DELETE CASCADE
)