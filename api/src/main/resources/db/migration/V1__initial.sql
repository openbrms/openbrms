create table rule
(
    id          varchar(255) not null
        primary key,
    required    boolean      not null,
    rule_then   text,
    variables   jsonb,
    rule_when   text,
    workflow_id varchar(255)
);

create table workflow
(
    id   varchar(255) not null
        primary key,
    name varchar(255)
);

