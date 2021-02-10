create schema if not exists source;

drop table if exists source.clients cascade;
create table source.clients
(
    id         serial primary key,
    first_name text not null,
    last_name  text not null
);

insert into source.clients (first_name, last_name)
values ('Ashley', 'Williams'),
       ('Jessica', 'Jones'),
       ('Nicole', 'Johnson'),
       ('Matthew', 'Brown'),
       ('Joseph', 'Moore'),
       ('Joshua', 'Smith');

drop table if exists source.accounts cascade;
create table source.accounts
(
    id        serial primary key,
    client_id int            not null,
    number    varchar(20)    not null,
    balance   numeric(25, 5) not null,
    foreign key (client_id) references source.clients (id)
);

insert into source.accounts (client_id, number, balance)
values (1, '12345678901234567890', 100000.00),
       (2, '22345678901234567890', 200000.99),
       (3, '32345678901234567890', 300000.00),
       (4, '42345678901234567890', 400000.00),
       (5, '52345678901234567890', 500000.00),
       (6, '62345678901234567890', 600000.88),
       (6, '72345678901234567890', 100000.00);

drop table if exists source.orders cascade;
create table source.orders
(
    id        serial primary key,
    client_id int not null,
    number    int not null,
    foreign key (client_id) references source.clients (id)
);

insert into source.orders (client_id, number)
values (1, 1234),
       (1, 2234),
       (1, 3234),
       (2, 4234),
       (3, 5234),
       (4, 6234),
       (5, 7234),
       (5, 8234);

drop table if exists source.atms;
create table source.atms
(
    id      serial primary key,
    number  int,
    address text,
    cash numeric(25,2)
);

insert into source.atms (number, address, cash)
values (100000, '97217 Bogisich Meadows', 1000000),
       (100001, '536 Streich Squares', 2000),
       (100002, '27, Dead Sea', 0),
       (100003, '151, Enver Azemi', 50000),
       (100004, '151, Enver Azemi', 200000);

drop table if exists source.deals cascade;
create table source.deals
(
    id         serial primary key,
    account_id int            not null,
    order_id   int            not null,
    sum        numeric(25, 2) not null,
    foreign key (account_id) references source.accounts (id),
    foreign key (order_id) references source.orders (id)
);

insert into source.deals(account_id, order_id, sum)
values (1, 1, 100.50),
       (1, 2, 100.00),
       (1, 3, 200.00);


create schema if not exists target;

drop table if exists target.clients cascade;
create table target.clients
(
    id         int, -- primary key,
    first_name text            not null,
    last_name  text            not null
);

drop table if exists target.accounts cascade;
create table target.accounts
(
    id        int, -- primary key,
    client_id int            not null,
    number    varchar(20)    not null,
    balance   numeric(25, 5) not null
--     foreign key (client_id) references target.clients (id)
);

drop table if exists target.orders cascade;
create table target.orders
(
    id        int, -- primary key,
    client_id int not null,
    number    int not null
--     foreign key (client_id) references target.clients (id)
);

drop table if exists target.atms;
create table target.atms
(
    id      int,  -- primary key,
    number  int,
    address text,
    noCash bool
);

drop table if exists target.deals cascade;
create table target.deals
(
    id         int primary key,
    account_id int            not null,
    order_id   int            not null,
    sum        numeric(25, 2) not null
--     foreign key (account_id) references target.accounts (id),
--     foreign key (order_id) references target.orders (id)
);

drop table if exists target.deal_summary;
create table target.deal_summary(
    id serial primary key,
    sum numeric(25,2)
);