create table room
(
    id           smallserial PRIMARY KEY, -- 32767
    name         varchar(50),
    price        decimal(19, 4),
    capacity     smallint,
    stars_number smallint,
    room_status  varchar(20),
    details      varchar(250)
);

create table guest
(
    id             serial PRIMARY KEY,
    login          varchar(50) UNIQUE NOT NULL,
    password       varchar,
    role           varchar(20),
    name           varchar(100),
    price          decimal(19, 4),
    passport       varchar(20),
    check_in_date  date,
    check_out_date date,
    room_id        int references room (id) -- будет не null у тех, кто заселён в данный момент
);

create table maintenance
(
    id              serial PRIMARY KEY,
    name            varchar(100),
    price           decimal(19, 4),
    category        varchar(20)
);

create table "guest_2_maintenance"
(
    order_id        serial PRIMARY KEY,
    guest_id        int references guest (id),
    maintenance_id  int references maintenance (id),
    order_timestamp timestamp
);

create table token
(
    id             serial PRIMARY KEY,
    value          varchar,
    guest_id       int references guest (id)
);

/*DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;*/