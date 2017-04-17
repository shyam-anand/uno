CREATE TABLE `business` (
  `id`           INT UNSIGNED          AUTO_INCREMENT PRIMARY KEY,
  `name`         VARCHAR(40)  NOT NULL,
  `fb_user_id`   VARCHAR(40)  NOT NULL,
  `fb_page_id`   VARCHAR(40)  NOT NULL,
  `category`     INT UNSIGNED NOT NULL,
  `description`  TEXT,
  `address`      TINYTEXT,
  `created_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp
)