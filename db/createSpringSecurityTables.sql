use computer-database-db;

CREATE TABLE if not exists user (
  id bigint not null auto_increment,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (id));

CREATE TABLE if not exists user_role (
  id bigint not null auto_increment,
  user_id bigint not null,
  role varchar(45) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_users_id FOREIGN KEY (user_id) REFERENCES user (id));


insert into user (id,username,password, enabled) values (  1,'admin','$2a$04$fc7hreDA7KNZf93RnJvRQ.IX7PAmDmaLcKWmH.6AUCPUfZ.hCgKoa',1);
insert into user (id,username,password, enabled) values (  2,'user','$2a$04$Fzn5220q30/kdVHrsmnrCuSlvGadOEIm38rP9h4w5Xb2SAj8YeKt6',1);

insert into user_role (id, user_id,role) values (1,1,'ROLE_ADMIN');
insert into user_role (id, user_id,role) values (2,2,'ROLE_USER');
