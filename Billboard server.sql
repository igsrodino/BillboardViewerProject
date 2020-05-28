SET FOREIGN_KEY_CHECKS = 0;
drop table if exists billboards;
drop table if exists users;
drop table if exists schedule;
drop table if exists permissions;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL
);

CREATE TABLE `billboards` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `background` varchar(255),
  `message` varchar(1000),
  `message_color` varchar(255),
  `url` varchar(255),
  `data` mediumtext,
  `information` varchar(4000),
  `information_color` varchar(255),
  `owner` int
);

CREATE TABLE `schedule` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `duration` int NOT NULL,
  `weekday` int NOT NULL,
  `recurs` int,
  `billboard` int NOT NULL
);

CREATE TABLE `permissions` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `create_billboards` boolean DEFAULT false,
  `edit_billboards` boolean DEFAULT false,
  `schedule_billboards` boolean DEFAULT false,
  `edit_users` boolean DEFAULT false,
  `user` int
);

ALTER TABLE `billboards` ADD FOREIGN KEY (`owner`) REFERENCES `users` (`id`);

ALTER TABLE `schedule` ADD FOREIGN KEY (`billboard`) REFERENCES `billboards` (`id`);

ALTER TABLE `permissions` ADD FOREIGN KEY (`user`) REFERENCES `users` (`id`);
