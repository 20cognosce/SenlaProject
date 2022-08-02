insert into room (name, price, capacity, stars_number, room_status, details)
VALUES ('1', 2000, 2, 3, 'FREE', ''),
       ('2', 5000, 5, 4, 'FREE', ''),
       ('3', 3500, 3, 3, 'UNDER_REPAIR', ''),
       ('4', 7500, 6, 4, 'CLEANING', ''),
       ('5', 9000, 4, 5, 'FREE', '');

/*insert into guest (login, password, role, name, price, passport, room_id, check_in_date, check_out_date)
VALUES ('Ivanov Ivan Ivanovich', 5000, '1111222222', 2, '2022-03-02', '2022-03-05'),
       ('Ivanova Maria Ivanovna', 800, '3333444444', 2, '2022-03-02', '2022-03-05'),
       ('Petrov Petr Petrovich', 2000, '5555666666', 1, '2022-03-01', '2022-03-14'),
       ('Abramov Nikita Alexandrovich', 0, '7777888888', null, '2022-03-01', '2022-03-14'),
       ('Yakovleva Margarita Vladimirovna', 0, '9999000000', null, '2022-03-01', '2022-03-14');*/

insert into maintenance (name, price, category)
values ('Завтрак в номер', 500, 'LOCAL_FOOD'),
       ('Обед в номер', 600, 'LOCAL_FOOD'),
       ('Ужин в номер', 800, 'LOCAL_FOOD'),
       ('Принести доставку в номер', 200, 'DELIVERY_FOOD'),
       ('Дополнительный набор для душа', 200, 'ACCESSORIES');

/*insert into guest_2_maintenance (guest_id, maintenance_id, order_timestamp)
values (2, 3, '2022-03-02 14:12:25');*/