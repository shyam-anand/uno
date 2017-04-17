CREATE TABLE `category_agent` (
  `category` INT UNSIGNED NOT NULL,
  `agent`    INT UNSIGNED NOT NULL,
  INDEX `cat` (`category`),
  INDEX `agent` (`agent`)
)