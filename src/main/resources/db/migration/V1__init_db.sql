create table if not exists users
(
    id bigint primary key auto_increment,
    username varchar(256),
    email varchar(50)
);

create table if not exists files
(
    id bigint primary key auto_increment,
    file_path varchar(256),
    event_id bigint references events(id)
);

create table if not exists events
(
    id bigint primary key auto_increment,
    create_data timestamp,
    user_id bigint references users (id),
    file_id bigint references files (id)
);