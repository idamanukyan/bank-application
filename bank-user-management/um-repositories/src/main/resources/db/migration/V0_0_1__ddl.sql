CREATE TABLE t_roles
(
    id UUID primary key,
    name        varchar(30)  not null,
    description varchar(100) not null

);


CREATE TABLE t_permissions
(
    id UUID primary key,
    name varchar not null
);


CREATE TABLE t_customer_users
(
    id UUID primary key,
    name     VARCHAR(25)  not null,
    surname  VARCHAR(25)  not null,
    email    VARCHAR(25)  not null,
    password varchar(120) not null,
    created  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

create table t_bank_users
(
    id uuid primary key,
    name     varchar(25)  not null,
    surname  varchar(25)  not null,
    email    varchar(25)  not null unique,
    password varchar(120) not null,
    created  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null
);



ALTER TABLE t_customer_users
    ADD CONSTRAINT uk_email_deleted UNIQUE (email, deleted);

ALTER TABLE t_bank_users
    ADD CONSTRAINT bank_uk_email_deleted UNIQUE (email, deleted);



CREATE TABLE t_roles_customer_users
(
    customer_user_id UUID REFERENCES t_customer_users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id UUID REFERENCES t_roles (id) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE t_roles_bank_users
(
    bank_user_id UUID REFERENCES t_bank_users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id UUID REFERENCES t_roles (id) ON UPDATE CASCADE ON DELETE CASCADE

);


CREATE TABLE t_roles_permissions
(
    role_id UUID REFERENCES t_roles (id) ON UPDATE CASCADE ON DELETE CASCADE,
    permission_id UUID REFERENCES t_permissions (id) ON UPDATE CASCADE ON DELETE CASCADE

);

