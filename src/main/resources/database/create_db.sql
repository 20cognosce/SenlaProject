-- Мне очень не нравится, что комната хранит id гостей в списке, из-за этого я не могу установить для них foreign_key
-- Аналогично для гостя и услуг
-- в комментариях возможные конструкции для разрешения этого, но они заработают только при добавлении определенного патча
-- https://stackoverflow.com/questions/41054507/postgresql-array-of-elements-that-each-are-a-foreign-key
create table room (
    id smallserial PRIMARY KEY, -- 32767
    name varchar(50),
    price decimal (19, 4),
    capacity smallint,
    stars_number smallint,
    current_guest_id_list int[],
    previous_guest_id_list int[],
    room_status varchar(20),
    details varchar(250)
    -- FOREIGN KEY (each element of currentGuestIdList) references guest (id),
    -- FOREIGN KEY (each element of orderedMaintenances) references maintenance_instance (id)
);

create table guest (
    id serial PRIMARY KEY,
    name varchar(100),
    price decimal (19, 4),
    passport varchar(20), -- если вдруг иностранцы
    room_id int,
    check_in_date date,
    check_out_date date,
    ordered_maintenances_id_list int[],
    FOREIGN KEY (room_id) references room (id)
    -- FOREIGN KEY (each element of orderedMaintenancesId) references maintenance_instance (id)
);

-- Шаблоны услуг maintenance будут иметь время заказа null
create table maintenance_template (
    id smallserial PRIMARY KEY,
    name varchar(100),
    price decimal (19, 4),
    category varchar(20),
    order_timestamp timestamp default null
);

-- При заказе будет выбираться maintenance_template по имени/id
-- и в maintenance_instance будет вставляться строка с его копией но с уже заполненным временем и новым id
-- guest будет иметь ссылку именно на maintenance_instance
create table maintenance_instance (
    id serial PRIMARY KEY,
    name varchar(100),
    price decimal (19, 4),
    category varchar(20),
    order_timestamp timestamp
);
