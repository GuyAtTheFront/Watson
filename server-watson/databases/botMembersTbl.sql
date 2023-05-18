CREATE TABLE bot_member (
    bot_id varchar(20),
    member_id varchar(20),

    primary key(bot_id, member_id),
    constraint fk_bot_members_to_bots foreign key(bot_id) references bots(id),
    constraint fk_bot_members_to_members foreign key(member_id) references members(id)
);

INSERT INTO bot_member (bot_id, member_id) VALUES 
(6127352122, 153628701), 
(6127352122, 966363364);