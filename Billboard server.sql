CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL
);

CREATE TABLE `billboards` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `background` varchar(255),
  `message` varchar(255),
  `message_color` varchar(255),
  `url` varchar(255),
  `data` varchar(255),
  `information` varchar(255),
  `information_color` varchar(255),
  `owner` int
);

CREATE TABLE `schedule` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `start_time` time,
  `end_time` time,
  `duration` int,
  `recurs` int,
  `weekday` ENUM ('sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday') not null,
  `billboard` int
);

CREATE TABLE `permissions` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `permission` ENUM ('create_billboards', 'edit_billboards', 'schedule_billboards', 'edit_users') NOT NULL,
  `user` int
);

ALTER TABLE `billboards` ADD FOREIGN KEY (`owner`) REFERENCES `users` (`id`);

ALTER TABLE `schedule` ADD FOREIGN KEY (`billboard`) REFERENCES `billboards` (`id`);

ALTER TABLE `permissions` ADD FOREIGN KEY (`user`) REFERENCES `users` (`id`);
