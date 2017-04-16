CREATE TABLE fb_pages (
  page_id      VARCHAR(20)  NOT NULL PRIMARY KEY,
  name         VARCHAR(100) NOT NULL,
  access_token VARCHAR(250) NOT NULL,
  INDEX `name` (name)
)