create sequence if not exists hibernate_sequence;

create sequence if not exists tenant_sequence;

create sequence if not exists tenant_id_seq;

create sequence if not exists user_id_seq;


create table if not exists tenant
(
    id  int NOT NULL DEFAULT nextval('tenant_id_seq')  
        constraint "tenantPK"
            primary key,
    version              bigint                not null,
    schema            varchar(255)          not null
        constraint tenant_tenant_id_key
            unique,
    state                varchar(255)          not null,
    date_created         timestamp with time zone
);

create unique index if not exists tenant_id_uniq_1399617387223
    on tenant (schema);


create table role
(
    id    int      not null
        constraint "rolePK"
            primary key,
    name varchar(20) null
);

insert into role (id, name)
values (1, 'ADMIN');
insert into role (id, name)
values (2, 'HELP_DESK');
insert into role (id, name)
values (3, 'PERSONAL_TRAINER');

create table "user"
(
    id  int NOT NULL DEFAULT nextval('user_id_seq')  
        constraint "userPK"
        primary key,
    email    varchar(50)  not null,
    password varchar(120) not null,
    username varchar(20)  not null,
    tenantID bigint  not null,
    constraint UKob8kqyqqgmefl0aco34akdtpe
        unique (email),
    constraint UKsb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

create table user_roles
(
    user_id int not null,
    role_id int    not null,
    primary key (user_id, role_id),
    constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id) references "user" (id),
    constraint FKrhfovtciq1l558cw6udg0h0d3
        foreign key (role_id) references role (id)
);
