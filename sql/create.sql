begin;
create table carrera(
       id integer primary key,
       nombre varchar(100)
);

create table usuario_sin_validar(
       id integer primary key,
       nombre varchar(100),
       email varchar(100) unique,
       password char(64)
);

create table usuario(
       id integer primary key,
       nombre varchar(100),
       email varchar(100) unique,
       password char(64),
       fecha_registro timestamp,
       fecha_nacimiento date,
       id_carrera int references carrera(id)
);

create table administrador(
       id integer primary key references usuario(id)
);

create table categoria(
       id integer primary key,
       nombre varchar(100)
);

create table pregunta(
       id integer primary key,
       titulo varchar(256) not null,
       contenido varchar(1024),
       fecha timestamp,
       id_categoria integer references categoria(id),
       id_usuario integer references usuario(id)
);

create table respuesta(
       id integer primary key,
       contenido varchar(1024) not null,
       fecha timestamp
);
commit;
