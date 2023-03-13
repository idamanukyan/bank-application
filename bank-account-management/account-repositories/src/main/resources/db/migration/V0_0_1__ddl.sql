create table customers
(
    id uuid primary key,
    logged_user_id uuid,
    passport_number varchar(30) not null,
    legal_account_id uuid,
    physical_account_id uuid,
    created         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null
);

create table legal_entity_accounts
(
    id uuid primary key,
    account_number varchar(20),
    balance        decimal,
    status         varchar(20),
    currency       varchar(20),
    created        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null,
    company_name   varchar(30),
    customer_id uuid
);

create table physical_entity_accounts
(
    id uuid primary key,
    account_number varchar(20),
    balance        decimal,
    status         varchar(20),
    currency       varchar(20),
    created        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null,
    customer_id uuid
);

ALTER TABLE legal_entity_accounts
    ADD CONSTRAINT legal_entity_customer_fk FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE physical_entity_accounts
    ADD CONSTRAINT physical_entity_customer_fk FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE customers
    ADD CONSTRAINT legal_entity_customer_fk FOREIGN KEY (legal_account_id) REFERENCES legal_entity_accounts (id);

ALTER TABLE customers
    ADD CONSTRAINT physical_entity_customer_fk FOREIGN KEY (physical_account_id) REFERENCES physical_entity_accounts (id);

create table physical_entity_card
(
    id uuid primary key,
    card_number varchar(50),
    card_level varchar(20),
    card_type  varchar(20),
    amount     decimal,
    currency       varchar(20),
    status     varchar(20),
    created    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null,
    physical_entity_account_id uuid
);

ALTER TABLE physical_entity_card
    ADD CONSTRAINT physical_entity_card_fk FOREIGN KEY (physical_entity_account_id) REFERENCES physical_entity_accounts (id);

create table legal_entity_card
(
    id uuid primary key,
    card_number varchar(50),
    card_level varchar(20),
    card_type  varchar(20),
    amount     decimal,
    currency       varchar(20),
    status     varchar(20),
    created    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    updated    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
    deleted    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null,
    legal_entity_account_id uuid
);

ALTER TABLE legal_entity_card
    ADD CONSTRAINT legal_entity_card_fk FOREIGN KEY (legal_entity_account_id) REFERENCES legal_entity_accounts (id);


