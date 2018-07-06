create table consultants (
    id int not null auto_increment,
    mobile varchar(16) not null,
    password varchar(128) not null,
    name varchar(32),
    email varchar(32),
    created_datetime timestamp not null default CURRENT_TIMESTAMP,
    updated_datetime timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted boolean not null default false,

    PRIMARY KEY (id)
);