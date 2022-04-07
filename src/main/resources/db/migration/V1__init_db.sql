create table if not exists users
(
    id bigserial primary key,
    username varchar(256),
    email varchar(50)
);

create table if not exists files
(
    id bigserial primary key,
    file_path varchar(256)
);

create table if not exists events
(
    id bigserial primary key,
    create_data timestamp,
    user_id bigint references users (id),
    file_id bigint references files (id)
);

alter table files
    add column event_id bigint
        references events (id)
