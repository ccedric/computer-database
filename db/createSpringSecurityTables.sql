CREATE  TABLE user (
  id bigint not null auto_increment,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (id));

CREATE TABLE user_role (
  id bigint not null auto_increment,
  user_id bigint not null,
  role varchar(45) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_users_id FOREIGN KEY (user_id) REFERENCES users (id));