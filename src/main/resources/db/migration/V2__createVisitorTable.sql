create table visitors (
    id int not null auto_increment,
    real_name varchar(32) not null,
    dob date,
    gender varchar(8),
    age int,
    nationality varchar(8),
    mobile varchar(32),
    address varchar(128),
    consultant_id int,

    created_datetime timestamp not null default CURRENT_TIMESTAMP,
    updated_datetime timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted boolean not null default false,

    PRIMARY KEY (id)
);