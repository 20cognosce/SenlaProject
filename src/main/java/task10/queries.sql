-- 1. Найти номер модели, скорость и размер жесткого диска для всех ПК стоимостью менее 500 долларов.
select model, speed, hd from pc
        where price < 500;

-- 2 Найти производителей принтеров. Вывести поля: maker.
SELECT maker
    FROM product where type = 'Printer';

-- 3 Найти номер модели, объем памяти и размеры экранов ноутбуков, цена которых превышает 1000 долларов.
select model, ram, screen from laptop
    where price > 1000;

-- 4 Найти все записи таблицы Printer для цветных принтеров.
select * from printer
    where color = 'y';

-- 5 Найти номер модели, скорость и размер жесткого диска для ПК,
-- имеющих скорость cd 12x или 24x и цену менее 600 долларов.
select model, speed, hd from pc
    where cd in ('12x', '24x') and price < 600;

-- 6 Указать производителя и скорость для тех ноутбуков, которые имеют жесткий диск объемом не менее 100 Гбайт.
select maker, speed
    from laptop join product p on p.model = laptop.model
        where hd >= 100;

-- 7 Найти номера моделей и цены всех продуктов (любого типа), выпущенных производителем B (латинская буква).
SELECT model, price FROM
    (
    select laptop.model, price, maker FROM laptop join product p on p.model = laptop.model
        UNION
    select pc.model, price, maker FROM pc join product p on p.model = pc.model
        UNION
    select printer.model, price, maker FROM printer join product p on p.model = printer.model
    ) as alias where maker = 'B';

-- 8 Найти производителя, выпускающего ПК, но не ноутбуки.
create function count_devices(maker_param char(10), type_param char(10))
    returns int
    READS SQL DATA
    DETERMINISTIC
begin
    declare i int;
    set i = (select count(*) from product where maker = maker_param and type = type_param);
    return i;
end;

select distinct maker from product where
    count_devices(maker, 'Laptop') = 0 and count_devices(maker, 'PC') > 0;

-- 9 Найти производителей ПК с процессором не менее 450 Мгц. Вывести поля: maker.
select maker from pc join product p on p.model = pc.model
    where speed >= 450;

-- 10 Найти принтеры, имеющие самую высокую цену. Вывести поля: model, price.
select model, price from printer
    order by price desc
    limit 2;

-- 11 Найти среднюю скорость ПК.
select avg(speed) from pc;

-- 12 Найти среднюю скорость ноутбуков, цена которых превышает 1000 долларов.
select avg(speed) from laptop
    where price > 1000;

-- 13 Найти среднюю скорость ПК, выпущенных производителем A.
select avg(speed) from pc join product p on pc.model = p.model
    where p.maker = 'Asus';

-- 14 Для каждого значения скорости процессора найти среднюю стоимость ПК с такой же скоростью.
-- Вывести поля: скорость, средняя цена.
create function calculate_avg_price(speed_param int)
    returns int
    READS SQL DATA
    DETERMINISTIC
begin
    declare avg_price int;
    set avg_price = (select avg(price) from pc where speed = speed_param);
    return avg_price;
end;

select distinct speed, calculate_avg_price(speed) from pc;
-- задание 22 аналогично, там я уже постиг group by

-- 15 Найти размеры жестких дисков, совпадающих у двух и более PC. Вывести поля: hd.
select hd from
    (
    SELECT hd, count(hd) as occurance
        FROM pc
        GROUP BY hd
    ) as hd_to_occurance where occurance >= 2;

-- 16 Найти пары моделей PC, имеющих одинаковые скорость процессора и RAM.
-- В результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i),
-- Порядок вывода полей: модель с большим номером, модель с меньшим номером, скорость, RAM.

select model, speed, ram
from pc
where (select speed, ram)
       in (select speed, ram
           from pc
           group by speed, ram
           having count(*) >= 2);

/* дальше нужно создать новую таблицу с колонками model1, model2, speed, ram
   где model1, model2 являются всеми комбинаторными сочетаниями С из n по 2 значений колонки model

   не смог это сделать и не понимаю зачем...
*/

-- 17 Найти модели ноутбуков, скорость которых меньше скорости любого из ПК. Вывести поля: type, model, speed.
set @lowest_pc_speed := (select speed from pc order by speed limit 1);
-- select @lowest_pc_speed;
select type, laptop.model, speed
    from laptop join product p on laptop.model = p.model
    where speed < @lowest_pc_speed;


-- 18 Найти производителей самых дешевых цветных принтеров. Вывести поля: maker, price.
select maker, price
from printer join product p on printer.model = p.model
    where color = 'y'
    order by price limit 1;

-- 19 Для каждого производителя найти средний размер экрана выпускаемых им ноутбуков.
-- Вывести поля: maker, средний размер экрана.
SELECT maker, sum(screen)/count(maker)
FROM laptop join product p on laptop.model = p.model
GROUP by maker;

-- 20 Найти производителей, выпускающих по меньшей мере три различных модели ПК. Вывести поля: maker, число моделей.
SELECT maker, count(pc.model)
FROM pc join product p on pc.model = p.model
GROUP by maker
having count(pc.model) >= 3;

-- 21 Найти максимальную цену ПК, выпускаемых каждым производителем. Вывести поля: maker, максимальная цена.
SELECT maker, max(price)
FROM pc join product p on pc.model = p.model
GROUP by maker;

-- 22 Для каждого значения скорости процессора ПК, превышающего 600 МГц, найти среднюю цену ПК с такой же скоростью.
-- Вывести поля: speed, средняя цена.
select distinct speed, calculate_avg_price(speed) from pc
    where speed > 600;

select distinct speed, avg(price) from pc
    where speed > 600
    group by speed;

-- 23 Найти производителей, которые производили бы как ПК, так и ноутбуки со скоростью не менее 750 МГц.
-- Вывести поля: maker
select distinct maker
from pc join product p on p.model = pc.model
where maker in
    (
    select maker
    from laptop join product p on p.model = laptop.model
    where laptop.speed > 750
    );


-- 24 Перечислить номера моделей любых типов, имеющих самую высокую цену по всей имеющейся в базе данных продукции.
select model from
(
select maker, product.model, price from product join laptop l on product.model = l.model
union
select maker, product.model, price from product join pc p on product.model = p.model
union
select maker, product.model, price from product join printer p2 on product.model = p2.model
order by price desc
limit 5
) as global_table;

-- 25 Найти производителей принтеров, которые производят ПК с наименьшим объемом RAM
-- и с самым быстрым процессором среди всех ПК, имеющих наименьший объем RAM. Вывести поля: maker
set @lowest_ram := (select ram from pc order by ram limit 1);
set @highest_speed_of_lowest_ram := (select max(speed) from pc where (ram = @lowest_ram));
select distinct maker
from printer join product p on p.model = printer.model
where maker in
      (
          select maker from
          (
          select maker, pc.model, speed
           from pc
                    join product p on p.model = pc.model
           where (ram = @lowest_ram and speed = @highest_speed_of_lowest_ram)
           ) as final_table
      );

