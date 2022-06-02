insert ignore into product (maker, model, type)
    VALUES ('Asus', '1', 'PC'),
           ('HP', '2', 'Printer'),
           ('Apple', '3', 'Laptop'),
           ('Acer', '5', 'PC'),
           ('HP', '6', 'PC'),
           ('B', '7', 'PC'),
           ('B', '8', 'Laptop'),
           ('HP', '9', 'Printer'),
           ('HP', '10', 'Printer'),
           ('MSI', '11', 'Laptop'),
           ('Asus', '12', 'PC'),
           ('Asus', '13', 'PC'),
           ('HP', '14', 'PC'),
           ('IRIS', '15', 'Laptop'),
           ('Sony', '16', 'Printer'),
           ('MSI', '17', 'Laptop'),
           ('HP', '4', 'PC');

insert ignore into pc (code, model, speed, ram, hd, cd, price)
    VALUES (1, 1, 2200, 8192, 512, '4x', 499),
           (2, 5, 1400, 4096, 512, '12x', 330),
           (3, 6, 1700, 8192, 1024, '24x', 450),
           (4, 7, 2200, 16384, 1024, '12x', 900),
           (5, 12, 3700, 16384, 1024, '6x', 649),
           (6, 13, 1400, 4096, 1024, '6x', 399),
           (7, 14, 1400, 4096, 1024, '12x', 349),
           (8, 4, 1600, 4096, 512, '12x', 299);

insert ignore into laptop (code, model, speed, ram, hd, screen, price)
    VALUES (1, 3, 1900, 8192, 256, 15, 2499),
           (2, 8, 1900, 8192, 256, 15, 399),
           (3, 11, 3300, 16384, 512, 17, 1099),
           (4, 15, 1100, 4096, 128, 13, 99),
           (5, 17, 1300, 4096, 512, 15, 299);

insert ignore into printer (code, model, color, type, price)
    values (1, 2, 'y', 'Laser', 299),
           (2, 9, 'n', 'Matrix', 69),
           (3, 10, 'n', 'Jet', 2499),
           (4, 16, 'y', 'Laser', 349)