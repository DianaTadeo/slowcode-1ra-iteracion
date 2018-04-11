begin;
create table carrera(
       id serial primary key,
       nombre varchar(100) unique not null
);

create table usuario_sin_validar(
       id serial primary key,
       nombre varchar(100) not null,
       email varchar(100) not null unique,
       password char(64) not null
);

create table usuario(
       id serial primary key,
       nombre varchar(100) not null,
       email varchar(100) unique not null,
       password char(64) not null,
       fecha_registro timestamp not null,
       id_carrera int references carrera(id),
       es_admin boolean not null
);

create table categoria(
       id serial primary key,
       nombre varchar(100) unique not null
);

create table pregunta(
       id serial primary key,
       titulo text not null,
       contenido text,
       fecha timestamp not null,
       id_categoria integer references categoria(id),
       id_usuario integer references usuario(id) not null
);

create table respuesta(
       id serial primary key,
       contenido text not null,
       fecha timestamp not null,
       id_usuario integer references usuario(id) not null,
       id_pregunta integer references pregunta(id) not null
);
commit;
