CREATE TABLE fb_users_pages (
  user_id VARCHAR(20) NOT NULL,
  page_id VARCHAR(100) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES fb_users (user_id),
  FOREIGN KEY (page_id) REFERENCES fb_pages (page_id)
)