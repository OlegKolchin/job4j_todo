create table users(
    id serial primary key,
    name text,
    email text unique not null,
    password text not null,
)

create table items(
    id serial primary key,
    description text,
    created timestamp with time zone,
    done boolean,
    user_id int references users(id)
)