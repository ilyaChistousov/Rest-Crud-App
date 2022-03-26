create table if not exists files
(
    id bigserial primary key,
    file_path varchar(256)
);