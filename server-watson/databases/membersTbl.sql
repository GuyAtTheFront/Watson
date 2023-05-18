CREATE TABLE members (
    id varchar(20) NOT NULL,
    username varchar(128) NOT NULL,
    image_url varchar(128) NOT NULL,

    primary key(id)
);

INSERT INTO members (id, username, image_url) VALUES
(6127352122, "KubeBot", "https://robohash.org/6127352122.png"),
(1248686954, "WatsonBot", "https://robohash.org/1248686954.png"),
(8654474256, "SherlockBot", "https://robohash.org/8654474256.png"),
(4603797720, "MycroftBot", "https://robohash.org/4603797720.png"),
(5017396989, "HudsonBot", "https://robohash.org/5017396989.png"),
(6492216932, "Moriarty", "https://robohash.org/6492216932.png"),
(966363364, 'didi', 'https://robohash.org/966363364.png'), 
(153628701, 'me', 'https://robohash.org/153628701.png');