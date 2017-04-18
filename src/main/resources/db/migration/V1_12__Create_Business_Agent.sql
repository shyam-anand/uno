CREATE TABLE `business_agent` (
  `business` INT UNSIGNED NOT NULL,
  `agent`    INT UNSIGNED NOT NULL,
  INDEX `business` (`business`),
  INDEX `agent` (`agent`)
)