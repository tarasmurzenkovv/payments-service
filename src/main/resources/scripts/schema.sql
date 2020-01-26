create table if not exists customer
(
    id   integer auto_increment primary key,
    name char(200)
);

create table if not exists account
(
    id       integer auto_increment primary key,
    customer integer,
    amount   decimal,
    foreign key (customer) references customer (id)
);

