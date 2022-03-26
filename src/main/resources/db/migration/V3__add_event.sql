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