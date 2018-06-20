-- Tipos Documento
insert into tipodocumento (id, descripcion) values (1, 'LC'), (2, 'LE'), (3, 'CI'), (4, 'CUIT');

-- Modulos nuevos:
insert into modulos (idmodulo, Descripcion) values
    (9, 'PERSONAS'),
    (10, 'PROYECTOS'),
    (11, 'PROGRAMAS');

-- Usuario administrador, con rol administrador:
insert into roles (id, nombre, descripcion) values (0, 'admin', 'Administrador');
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 0);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 1);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 2);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 3);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 4);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 5);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 6);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 7);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 8);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 9);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 10);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 0, 1, 1, 1, 1, 11);
