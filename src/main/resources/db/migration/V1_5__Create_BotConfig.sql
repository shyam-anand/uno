CREATE TABLE `bot_config` (
  `id`                     INT UNSIGNED         AUTO_INCREMENT PRIMARY KEY,
  `name`                   VARCHAR(40) NOT NULL,
  `client_access_token`    VARCHAR(40) NOT NULL,
  `developer_access_token` VARCHAR(40) NOT NULL,
  `created_time`           TIMESTAMP   NOT NULL DEFAULT current_timestamp
)