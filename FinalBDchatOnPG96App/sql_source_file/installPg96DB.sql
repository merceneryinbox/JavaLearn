/*  --------------------- Create database structurer (Data Definition Language) = DDL ---------------------------- */
create schema Chat;

--drop table roles;
create table roles
(
    id serial primary key,
    namess varchar(20),
    can_read boolean ,
    can_write boolean ,
    can_update boolean ,
    can_revoke boolean ,
    misc varchar(150)
);

--drop table users;
create table users(
    u_id int,
    login varchar(25) not null,
    password varchar(50) not null,
    foreign key(u_id) references roles(id),
    primary key(login)
);


/* ----------------- Inserting initial data (Data Manipulation Language) = DML --------------------------------*/
--truncate table roles;

    insert into roles
    (namess, can_read, can_write, can_update, can_revoke, misc)
values
    ('anonymous',    true, false, true, false, 'some data about anonymous');
    insert into roles
    (namess, can_read, can_write, can_update, can_revoke, misc)
values
    ('guest',    true, false, true, false, 'some data about guest');
    insert into roles
    (namess, can_read, can_write, can_update, can_revoke, misc)
values
    ('admin',    true, true, true, true, 'some data about admin');
    insert into roles
    (namess, can_read, can_write, can_update, can_revoke, misc)
values
    ('updaters',    true, false, true, false, 'some data about updaters');
    insert into roles
    (namess, can_read, can_write, can_update, can_revoke, misc)
values
    ('mercenery',    true, false, true, false, 'some data about mercenery');
    insert into roles
    (namess, can_read, can_write, can_update, can_revoke, misc)
values
    ('modest',      true, true, false, false, 'some data about moderators');

update roles set namess = 'moderators' where namess = 'modest';
select * from roles ;

        insert into users(login, password, u_id)
values  ('admin', 'Pa$$w0rD_admin', (select id from roles where namess = 'admin'));
        insert into users(login, password, u_id)
values
        ('anonymous', 'Pa$$w0rD_anonymous', (select id from roles where namess = 'anonymous'));
        insert into users(login, password, u_id)
values
        ('guest', 'Pa$$w0rD_guest', (select id from roles where namess = 'guest'));
        insert into users(login, password, u_id)
values
        ('updaters', 'Pa$$w0rD_updaters', (select id from roles where namess = 'updaters'));
        insert into users(login, password, u_id)
values
        ('moderator', 'Pa$$w0rD_moderator', (select id from roles where namess = 'moderators'));
        insert into users(login, password, u_id)
values
        ('mercenery', 'Pa$$w0rD_mercenery', (select id from roles where namess = 'mercenery'));

        select * from users;

/* ----------------------------- Selecting data (Data Querying Language) = DQL ---------------------------- */

select
    r.id,
    r.namess,
    r.can_write as "w",
    r.can_read as "r",
    r.can_update as "u",
    r.can_revoke as "d"
from roles as r
order by r.namess desc;

--drop view users_roles_view;
create view users_roles_view
as select
    u.u_id,
    u.login,
    u.password as "pass",
    r.misc as "misc",
    r.namess as "role",
    r.id as role_id
from users as u
left join roles as r on u.u_id = r.id
order by u.login asc;

select * from users_roles_view;
select * from users;
select * from roles;

select * from users left outer join roles on users.u_id = roles.id;
select * from users right outer join roles on users.u_id = roles.id;
select * from users natural join roles where users.u_id = roles.id;
select * from roles  natural join users where users.u_id = roles.id;
select * from users full outer join roles on users.u_id = roles.id;