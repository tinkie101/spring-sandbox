CREATE SCHEMA adventure;

create table adventure
(
    id          serial not null
        constraint adventure_pkey
            primary key,
    description varchar(255),
    name        varchar(255)
);

alter table adventure
    owner to adventure_user;
INSERT INTO adventure.adventure (id, description, name) VALUES ('7dfd9998-c6b2-11eb-b8bc-0242ac130003', 'Test adventure 1', 'Adventure 1');
INSERT INTO adventure.adventure (id, description, name) VALUES ('7dfd9a7e-c6b2-11eb-b8bc-0242ac130003', 'Test adventure 2', 'Adventure 2');


create table adventure_user
(
    id           serial not null
        constraint adventure_user_pkey
            primary key,
    adventure_id uuid,
    user_id      uuid
);

alter table adventure_user
    owner to adventure_user;

INSERT INTO adventure.adventure_user (id, adventure_id, user_id) VALUES ('c5f8f4b2-c6d6-11eb-b8bc-0242ac130003', '7dfd9998-c6b2-11eb-b8bc-0242ac130003', '7dfd9786-c6b2-11eb-b8bc-0242ac130003');
INSERT INTO adventure.adventure_user (id, adventure_id, user_id) VALUES ('c5f8f7d2-c6d6-11eb-b8bc-0242ac130003', '7dfd9998-c6b2-11eb-b8bc-0242ac130003', 'ae4c2172-c6b3-11eb-b8bc-0242ac130003');
INSERT INTO adventure.adventure_user (id, adventure_id, user_id) VALUES ('e471f328-c6d9-11eb-b8bc-0242ac130003', '7dfd9a7e-c6b2-11eb-b8bc-0242ac130003', '7dfd9786-c6b2-11eb-b8bc-0242ac130003');
INSERT INTO adventure.adventure_user (id, adventure_id, user_id) VALUES ('e471f616-c6d9-11eb-b8bc-0242ac130003', '7dfd9a7e-c6b2-11eb-b8bc-0242ac130003', 'ae4c2172-c6b3-11eb-b8bc-0242ac130003');
INSERT INTO adventure.adventure_user (id, adventure_id, user_id) VALUES ('e471f710-c6d9-11eb-b8bc-0242ac130003', '7dfd9a7e-c6b2-11eb-b8bc-0242ac130003', '6d1e3ffa-c6b3-11eb-b8bc-0242ac130003');
INSERT INTO adventure.adventure_user (id, adventure_id, user_id) VALUES ('e471f83c-c6d9-11eb-b8bc-0242ac130003', '7dfd9a7e-c6b2-11eb-b8bc-0242ac130003', '70ff6838-c6b3-11eb-b8bc-0242ac130003');



