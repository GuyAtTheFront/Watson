DROP DATABASE IF EXISTS watson_db;
CREATE DATABASE watson_db;
USE watson_db;

CREATE TABLE bots(
    id varchar(20) NOT NULL,
    username varchar(128) NOT NULL,
    image_url varchar(128) NOT NULL,
    token varchar(128) NOT NULL,

    primary key(id)
);


INSERT INTO bots (id, username, image_url, token) VALUES
(6127352122, "KubeBot", "https://robohash.org/6127352122.png", "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg"),
(1248686954, "WatsonBot", "https://robohash.org/1248686954.png", "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg"),
(8654474256, "SherlockBot", "https://robohash.org/8654474256.png", "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg"),
(4603797720, "MycroftBot", "https://robohash.org/4603797720.png", "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg"),
(5017396989, "HudsonBot", "https://robohash.org/5017396989.png", "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg"),
(6492216932, "Moriarty", "https://robohash.org/6492216932.png", "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg");
    
-- CREATE USER IF NOT EXISTS "watson";
-- grant all privileges on watson_db.* to 'watson'@'%';
-- flush privileges;