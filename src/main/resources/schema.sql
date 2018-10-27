--
-- Отключение внешних ключей
--
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

--
-- Установить режим SQL (SQL mode)
--
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';

--
-- Установка базы данных по умолчанию
--
USE social;

--
-- Удалить таблицы
--
DROP TABLE IF EXISTS user_groups;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS settings;
DROP TABLE IF EXISTS presents;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS group_messages;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS attachments;
DROP TABLE IF EXISTS wall_messages;
--
-- Установка базы данных по умолчанию
--
USE social;

--
-- Создать таблицу "attachments"
--
CREATE TABLE attachments (
  attachment_id int(11) NOT NULL AUTO_INCREMENT,
  message_id int(11) NOT NULL,
  filename varchar(255) NOT NULL,
  PRIMARY KEY (attachment_id),
  UNIQUE INDEX id (attachment_id),
  CONSTRAINT FK_attachments_messages_message_id FOREIGN KEY (message_id)
  REFERENCES messages (message_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "friends"
--
CREATE TABLE friends (
  first_person_id int(11) NOT NULL,
  second_person_id int(11) NOT NULL,
  INDEX FK86yis5vrp3irwhum3bpvgyxb1 (second_person_id),
  INDEX FKn7m4niv5f71o1giq62gir7c4g (first_person_id),
  CONSTRAINT FK_friends_users_user_id FOREIGN KEY (first_person_id)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  AVG_ROW_LENGTH = 9
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "group_messages"
--
CREATE TABLE group_messages (
  group_message_id int(11) NOT NULL AUTO_INCREMENT,
  group_id int(11) NOT NULL,
  text varchar(255) NOT NULL,
  date datetime NOT NULL,
  sender int(11) NOT NULL,
  PRIMARY KEY (group_message_id),
  UNIQUE INDEX group_message_id (group_message_id),
  CONSTRAINT FK_group_messages_groups_group_id FOREIGN KEY (group_id)
  REFERENCES groups (group_id) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT FK_group_messages_users_user_id FOREIGN KEY (sender)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "groups"
--
CREATE TABLE groups (
  group_id int(11) NOT NULL AUTO_INCREMENT,
  creator_id int(11) NOT NULL,
  name varchar(50) NOT NULL,
  group_photo varchar(50) DEFAULT NULL,
  PRIMARY KEY (group_id),
  CONSTRAINT FK_groups_users_user_id FOREIGN KEY (creator_id)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "messages"
--
CREATE TABLE messages (
  message_id int(11) NOT NULL AUTO_INCREMENT,
  date datetime NOT NULL,
  text varchar(255) NOT NULL,
  source int(11) NOT NULL,
  recipient int(11) NOT NULL,
  PRIMARY KEY (message_id),
  INDEX FKnn3n88n5auohw8lbajxiywx1j (source),
  CONSTRAINT FK_messages_users_user_id FOREIGN KEY (source)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  AUTO_INCREMENT = 2
  AVG_ROW_LENGTH = 32
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "presents"
--
CREATE TABLE presents (
  present_id int(11) NOT NULL AUTO_INCREMENT,
  date datetime NOT NULL,
  name varchar(100) NOT NULL,
  source int(11) NOT NULL,
  recipient int(11) NOT NULL,
  signature varchar(255) NOT NULL,
  PRIMARY KEY (present_id),
  INDEX FK4nr9f50ga0q1b4cuqw5t7s3hy (source),
  CONSTRAINT FK_presents_users_user_id FOREIGN KEY (source)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  AUTO_INCREMENT = 3
  AVG_ROW_LENGTH = 28
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "settings"
--
CREATE TABLE settings (
  user_id int(11) NOT NULL,
  birthday_visibility tinyint(1) NOT NULL DEFAULT 1,
  age_visibility tinyint(1) NOT NULL DEFAULT 1,
  profile_visibility tinyint(1) NOT NULL DEFAULT 0,
  location_visibility tinyint(1) NOT NULL DEFAULT 1,
  gender_visibility tinyint(1) NOT NULL DEFAULT 1,
  gifts_visibility tinyint(1) NOT NULL DEFAULT 1,
  friends_visibility tinyint(1) NOT NULL DEFAULT 1,
  group_visibility tinyint(1) NOT NULL DEFAULT 1,
  non_friends_block tinyint(1) NOT NULL DEFAULT 0,
  UNIQUE INDEX user_id (user_id),
  CONSTRAINT FK_settings_users_user_id FOREIGN KEY (user_id)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "users"
--
CREATE TABLE users (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  birth_date datetime DEFAULT NULL,
  gender tinyint(4) DEFAULT NULL,
  name varchar(50) NOT NULL,
  password varchar(255) NOT NULL,
  patronymic varchar(50) DEFAULT NULL,
  surname varchar(50) NOT NULL,
  username varchar(50) NOT NULL,
  location varchar(255) DEFAULT NULL,
  deleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (user_id)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 3
  AVG_ROW_LENGTH = 96
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "user_groups"
--
CREATE TABLE user_groups (
  user_id int(11) NOT NULL,
  group_id int(11) NOT NULL,
  CONSTRAINT FK_user_groups_groups_group_id FOREIGN KEY (group_id)
  REFERENCES groups (group_id) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT FK_user_groups_user_user_id FOREIGN KEY (user_id)
  REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

--
-- Создать таблицу "wall_messages"
--
CREATE TABLE wall_messages (
  message_id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  text varchar(255) NOT NULL,
  date datetime NOT NULL,
  PRIMARY KEY (message_id),
  CONSTRAINT FK_wall_messages_users_user_id FOREIGN KEY (user_id)
  REFERENCES social2.users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)
  ENGINE = INNODB
  CHARACTER SET utf8
  COLLATE utf8_general_ci;
--
-- Восстановить предыдущий режим SQL (SQL mode)
--
SET SQL_MODE=@OLD_SQL_MODE;

--
-- Включение внешних ключей
--
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
