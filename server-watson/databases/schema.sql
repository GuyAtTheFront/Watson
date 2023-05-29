DROP DATABASE IF EXISTS watson_db;
CREATE DATABASE watson_db;
USE watson_db;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(64) NOT NULL,
  `enabled` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE bots(
    id varchar(20) NOT NULL,
    username varchar(128) NOT NULL,
    image_url varchar(128) NOT NULL,
    token varchar(128) NOT NULL,

    primary key(id)
);

CREATE TABLE members (
    id varchar(20) NOT NULL,
    username varchar(128) NOT NULL,
    image_url varchar(128) NOT NULL,

    primary key(id)
);

CREATE TABLE bot_member (
    bot_id varchar(20),
    member_id varchar(20),

    primary key(bot_id, member_id),
    constraint fk_bot_members_to_bots foreign key(bot_id) references bots(id),
    constraint fk_bot_members_to_members foreign key(member_id) references members(id)
);

CREATE TABLE users_bots (
    user_id int,
    bot_id varchar(20),

    primary key(user_id, bot_id),
    constraint fk_users_bots_to_users foreign key(user_id) references users(id),
    constraint fk_users_bots_to_bots foreign key(bot_id) references bots(id)
);
