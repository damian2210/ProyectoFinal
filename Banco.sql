drop DATABASE IF  EXISTS banco;
create DATABASE banco;
use banco;

create table producto_financiero(
Cod_producto varchar(3) primary key,
puntuacion varchar(3),
tipo varchar(12),
interes int,
fecha_devolucion date
);

create table cliente(
id_cliente varchar(4) primary key,
dni varchar(9),
telefono int,
nombre varchar(15),
num_cuenta long
);

create table empleado(
cod_empleado varchar(3) primary key,
dni varchar(9),
direccion varchar(40),
nombre varchar(15),
telefono int,
rol varchar(20),
usuario varchar(15),
contraseña varchar(6)
);

create table vender(
Cod_producto varchar(3),
id_cliente varchar(4),
fecha_venta date,
cod_empleado varchar(3),
primary key(cod_producto,id_cliente),
foreign key(cod_producto) references producto_financiero(cod_producto),
foreign key(id_cliente) references cliente(id_cliente),
foreign key(cod_empleado) references empleado(cod_empleado)
);

create table sucursal(
cod_sucursal varchar(3) primary key,
direccion varchar(40),
telefono int
);

create table prestar(
cod_sucursal varchar(3),
cod_sucursal_prestadora varchar(3),
cantidad int,
primary key(cod_sucursal,cod_sucursal_prestadora),
foreign key(cod_sucursal) references sucursal(cod_sucursal),
foreign key(cod_sucursal_prestadora) references sucursal(cod_sucursal)
);

create table contratar(
cod_empleado varchar(3),
fecha_inicio date,
fecha_fin date,
cantidad int,
cod_sucursal varchar(3),
primary key(cod_empleado,fecha_inicio)
);

create table dirigir(
cod_sucursal varchar(3)  ,
fecha_inicio date,
fecha_fin date,
cod_empleado_director varchar(3),
primary key(cod_sucursal,fecha_inicio)
);

insert into producto_financiero(cod_producto,puntuacion,tipo,interes,fecha_devolucion)
values('p00','AAA',"inversión",10,null),
('p01','AAa',"financiación",12,'2023-10-10'),
('p03','AAA',"financiación",15,'2023-09-10'),
('p04','Aaa',"inversión",18,null),
('p02','Aaa',"ahorro",15,null);

insert into cliente(id_cliente,dni,telefono,nombre,num_cuenta)
values('c123','48923423N',722319308,'Mariano',3216543211),
('c124','73413442F',611420419,'Pepe',2314325611),
('c125','74313343H',622420429,'María',1231234561); 

insert into empleado(cod_empleado,dni,direccion,nombre,telefono,rol,usuario,contraseña)
values('e23','65313343E','Calle María 52 3ºC','Antonio','632532734','Director','Antonio','ant'),
('e24','83728383G','Calle Santalucía 52 2ºC','Alejandro','745634372','Director','Alejandro','ale'),
('e25','92546343I','Calle Caballero 32 1ºB','Pablo','619293634','Director','Pablo','pab'),
('e26','78143833J','Calle San José 50 2ºA','Pedro','690120489','Inversionista','Pedro','ped'),
('e27','76543342F','Calle María 50 3ºC','Jorge','754532734','Inversionista','Jorge','jor'),
('e28','91565443U','Calle San Manuel 46 1ºD','Manuel','692845921','Gestor de préstamos','Manuel','man');

insert into vender(Cod_producto,id_cliente,fecha_venta,cod_empleado)
values('p00','c123','2023-04-09','e26'),
('p01','c123','2023-05-09','e27'),
('p04','c124','2022-12-09','e26'),
('p02','c125','2023-04-03','e27');


insert into sucursal(cod_sucursal,direccion,telefono)
values('s22','Calle María Jose 10','612892959'),
('s24','Calle María 10',692054920),
('s23','Calle José María 0',875757566);


insert into prestar(cod_sucursal,cod_sucursal_prestadora,cantidad)
values('s24','s22',10000);


insert into contratar(cod_empleado,fecha_inicio,fecha_fin,cod_sucursal)
values('e23','2003-10-10',null,'s23'),
('e24','2005-10-10',null,'s22'),
('e25','2004-10-10',null,'s24'),
('e26','2003-09-10',null,'s22'),
('e27','2008-08-10',null,'s23'),
('e28','2007-10-10',null,'s24');

/*e26 en s22 con e24 director
27 23 23
28 24 25*/
insert into dirigir(cod_sucursal,fecha_inicio,fecha_fin,cod_empleado_director)
values('s23','2003-10-10',null,'e23'),
('s22','2002-10-10',null,'e24'),
('s24','2001-10-10',null,'e25');










