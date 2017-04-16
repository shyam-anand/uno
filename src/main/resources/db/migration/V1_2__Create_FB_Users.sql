CREATE TABLE fb_users (
  user_id VARCHAR(20)  NOT NULL PRIMARY KEY,
  name    VARCHAR(100) NOT NULL,
  INDEX `name` (name)
)