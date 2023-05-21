CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(64) NOT NULL,
  `enabled` tinyint DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);

INSERT INTO `users` (`username`,`password`, `role`,`enabled`)
VALUES ('user',
'$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu',
'ROLE_USER',
 1);
 -- codejava
 
INSERT INTO `users` (`username`,`password`, `role`,`enabled`)
VALUES ('admin',
'$2a$10$zxvEq8XzYEYtNjbkRsJEbukHeRx3XS6MDXHMu8cNuNsRfZJWwswDy',
'ROLE_ADMIN',
 1);
 -- nimda