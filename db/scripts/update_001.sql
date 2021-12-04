create table items(
    id serial primary key,
    description text,
    created timestamp with time zone,
    done boolean
)