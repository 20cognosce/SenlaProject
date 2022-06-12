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
    name           varchar(100),
    price          decimal(19, 4),
    passport       varchar(20),
    check_in_date  date,
    check_out_date date,
    room_id        int references room (id) -- будет не null у тех, кто заселён в данный момент
);

CREATE TABLE room_guest
(
    room_id      int REFERENCES room (id),
    guest_id     int REFERENCES guest (id),
    booking_time timestamp not null,
    CONSTRAINT room_guest_pkey PRIMARY KEY (room_id, guest_id, booking_time)
);

-- Шаблоны услуг maintenance не будут иметь время заказа
create table maintenance_template
(
    id       smallserial PRIMARY KEY,
    name     varchar(100),
    price    decimal(19, 4),
    category varchar(20)
);

-- При заказе будет выбираться maintenance_template по имени/id
-- и в maintenance_instance будет вставляться строка с его копией
-- но с уже заполненным временем и новым id, плюс id гостя
create table maintenance_instance
(
    id              serial PRIMARY KEY,
    name            varchar(100),
    price           decimal(19, 4),
    category        varchar(20),
    order_timestamp timestamp,
    guest_id        int references guest (id)
);