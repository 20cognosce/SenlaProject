create table product (
    maker varchar(10),
    model varchar(50),
    type varchar(50),
    UNIQUE (model)
);

create table pc (
     code int,
     model varchar(50),
     speed smallint,
     ram smallint,
     hd real,
     cd varchar(10),
     price decimal (19, 4), -- money data type is deprecated and in my case it is not recognisable
     PRIMARY KEY (code),
     FOREIGN KEY (model) references product (model)
);

create table laptop (
    code int,
    model varchar(50),
    speed smallint,
    ram smallint,
    hd real,
    screen tinyint,
    price decimal (19, 4),
    PRIMARY KEY (code),
    FOREIGN KEY (model) references product (model)
);

create table printer (
    code int,
    model varchar(50),
    color varchar(1),
    type varchar(10),
    price decimal (19, 4),
    PRIMARY KEY (code),
    FOREIGN KEY (model) references product (model)
);