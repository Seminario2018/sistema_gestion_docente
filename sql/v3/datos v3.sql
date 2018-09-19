delete from columnas;
delete from tiposinformes;
delete from Modulos;
delete from Permisos;
delete from rolesxusuario;
delete from Roles;
delete from Usuarios;
delete from proyectos;
delete from programasinvestigacion;
delete from CargosDocentes;
delete from Areas where Areas.SubAreaDe is not null;
delete from Areas;
delete from Divisiones;
delete from Docentes;
delete from Personas;
delete from Cargos;
delete from CategoriasInvestigacion;
delete from estadosproyectos;
delete from estadosprogramas;
delete from EstadosCargos;
delete from EstadosDocentes;
delete from EstadosPersonas;
delete from TiposDocumentos;
delete from TiposCargos;
delete from TiposContactos;
delete from cargosfaltantes;
delete from integrantes;
delete from proyectos;
delete from programasinvestigacion;


insert into tiposinformes (id, Nombre, Descripcion, Editable, FromString, GroupByString) values
(0, 'Impacto Docente', 'Lista el costo de los docentes activos y su carga horaria total', 0,
 'DOCENTES INNER JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO LEFT JOIN CARGOSDOCENTES ON DOCENTES.LEGAJO = CARGOSDOCENTES.LEGAJO INNER JOIN CARGOS ON CARGOSDOCENTES.CARGO = CARGOS.CODIGO LEFT JOIN INTEGRANTES ON CARGOSDOCENTES.CODIGO = INTEGRANTES.CARGODOCENTE INNER JOIN ESTADOSDOCENTES ON DOCENTES.ESTADO = ESTADOSDOCENTES.ID INNER JOIN ESTADOSCARGOS ON CARGOSDOCENTES.ESTADOCARGO = ESTADOSCARGOS.IDESTADOCARGO',
 'DOCENTES.LEGAJO, ESTADOSDOCENTES.DESCRIPCION, PERSONAS.APELLIDO, PERSONAS.NOMBRE, ESTADOSCARGOS.DESCRIPCION');

insert into columnas (TipoInforme, Visible, Nombre, Atributo, Filtros, Calculo, Ordenar, Posicion, Tipo) values
(0, 1, 'Legajo', 'DOCENTES.LEGAJO', '', '', 0, 0, 'INTEGER'),
(0, 0, 'Estado Docente', 'ESTADOSDOCENTES.DESCRIPCION', '= \'Activo\'', '', 0, 1, 'STRING'),
(0, 1, 'Apellido', 'PERSONAS.APELLIDO', '', '', 0, 2, 'STRING'),
(0, 1, 'Nombre', 'PERSONAS.NOMBRE', '', '', 0, 3, 'STRING'),
(0, 1, 'Cantidad Cargos', 'CARGOSDOCENTES.CODIGO', '', 'COUNT', 0, 4, 'INTEGER'),
(0, 0, 'Estado Cargo', 'ESTADOSCARGOS.DESCRIPCION', '= \'Activo\'', '', 0, 5, 'STRING'),
(0, 1, 'Costo de planta', 'CARGOSDOCENTES.ULTIMOCOSTO', '', 'SUM', 0, 6, 'FLOAT'),
(0, 1, 'Horas de docencia', 'CARGOS.CARGAHORARIA', '', 'SUM', 0, 7, 'FLOAT'),
(0, 1, 'Proyectos en los que participa', 'INTEGRANTES.ID', '', 'COUNT', 0, 8, 'INTEGER'),
(0, 1, 'Horas de investigación', 'INTEGRANTES.HORASSEMANALES', '', 'SUM', 0, 9, 'FLOAT');


insert into tiposinformes (id, Nombre, Descripcion, Editable, FromString, GroupByString) values
(1, 'Resumen de Proyectos de Investigación', 'Lista un resumen de cada Proyecto de Investigación', 0,
'PROYECTOS LEFT JOIN INTEGRANTES ON PROYECTOS.ID = INTEGRANTES.PROYECTO LEFT JOIN CARGOSDOCENTES ON INTEGRANTES.CARGODOCENTE = CARGOSDOCENTES.CODIGO LEFT JOIN AREAS ON CARGOSDOCENTES.AREA = AREAS.CODIGO LEFT JOIN DIVISIONES ON AREAS.DIVISION = DIVISIONES.CODIGO LEFT JOIN DOCENTES ON PROYECTOS.DIRECTOR = DOCENTES.LEGAJO LEFT JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO LEFT JOIN CONTACTOS ON PERSONAS.NRODOCUMENTO = CONTACTOS.NRODOCUMENTO LEFT JOIN PRORROGAS ON PROYECTOS.ID = PRORROGAS.PROYECTO',
'PROYECTOS.NOMBRE, PROYECTOS.FECHAINICIO, PROYECTOS.FECHA_FIN, PRORROGAS.FECHAFIN, DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE');

insert into columnas (TipoInforme, Visible, Nombre, Atributo, Filtros, Calculo, Ordenar, Posicion, Tipo) values
(1, 1, 'Nombre Proyecto', 'PROYECTOS.NOMBRE', '', '', 0, 0, 'STRING'),
(1, 1, 'Código División', 'DIVISIONES.CODIGO', '', 'GROUP_CONCAT', 0, 1, 'STRING'),
(1, 1, 'División', 'DIVISIONES.DESCRIPCION', '', 'GROUP_CONCAT', 0, 2, 'STRING'),
(1, 1, 'Código Área', 'AREAS.CODIGO', '', 'GROUP_CONCAT', 0, 3, 'STRING'),
(1, 1, 'Área', 'AREAS.DESCRIPCION', '', 'GROUP_CONCAT', 0, 4, 'STRING'),
(1, 1, 'Fecha inicio', 'PROYECTOS.FECHAINICIO', '', '', 0, 5, 'DATE'),
(1, 1, 'Fecha fin', 'PROYECTOS.FECHA_FIN', '', '', 0, 6, 'DATE'),
(1, 1, 'Prórroga', 'PRORROGAS.FECHAFIN', '', 'MAX', 0, 7, 'DATE'),
(1, 1, 'Legajo Director', 'DOCENTES.LEGAJO', '', '', 0, 8, 'INTEGER'),
(1, 1, 'Apellido Director', 'PERSONAS.APELLIDO', '', '', 0, 9, 'STRING'),
(1, 1, 'Nombre Director', 'PERSONAS.NOMBRE', '', '', 0, 10, 'STRING'),
(1, 1, 'Mail Director', 'CONTACTOS.VALOR', '', 'GROUP_CONCAT', 0, 11, 'STRING');


/*
insert into tiposinformes (id, Nombre, Descripcion, Editable, FromString, GroupByString) values
(1, 'Resumen de Proyectos de Investigación', 'Lista un resumen de cada Proyecto de Investigación', 0,
'PROYECTOS JOIN INTEGRANTES ON PROYECTOS.ID = INTEGRANTES.PROYECTO JOIN CARGOSDOCENTES ON INTEGRANTES.CARGODOCENTE = CARGOSDOCENTES.CODIGO JOIN AREAS ON CARGOSDOCENTES.AREA = AREAS.CODIGO JOIN DIVISIONES ON AREAS.DIVISION = DIVISIONES.CODIGO JOIN DOCENTES ON PROYECTOS.DIRECTOR = DOCENTES.LEGAJO JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO JOIN CONTACTOS ON PERSONAS.NRODOCUMENTO = CONTACTOS.NRODOCUMENTO JOIN INCENTIVOS ON DOCENTES.LEGAJO = INCENTIVOS.LEGAJO JOIN PRORROGAS ON PROYECTOS.ID = PRORROGAS.PROYECTO JOIN SUBSIDIOS ON PROYECTOS.ID = SUBSIDIOS.PROYECTO',
'PROYECTOS.NOMBRE, DIVISIONES.CODIGO, DIVISIONES.DESCRIPCION, AREAS.CODIGO, AREAS.DESCRIPCION, DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE, CONTACTOS.VALOR, PROYECTOS.FECHAINICIO, PROYECTOS.FECHA_FIN, PRORROGAS.FECHAFIN');

insert into columnas (TipoInforme, Visible, Nombre, Atributo, Filtros, Calculo, Ordenar, Posicion, Tipo) values
(1, 1, 'Nombre Proyecto', 'PROYECTOS.NOMBRE', '', '', 0, 0, 'STRING'),
(1, 1, 'Código División', 'DIVISIONES.CODIGO', '', '', 0, 1, 'STRING'),
(1, 1, 'División', 'DIVISIONES.DESCRIPCION', '', '', 0, 2, 'STRING'),
(1, 1, 'Código Área', 'AREAS.CODIGO', '', '', 0, 3, 'STRING'),
(1, 1, 'Área', 'AREAS.DESCRIPCION', '', '', 0, 4, 'STRING'),
(1, 1, 'Legajo Director', 'DOCENTES.LEGAJO', '', '', 0, 5, 'INTEGER'),
(1, 1, 'Apellido Director', 'PERSONAS.APELLIDO', '', '', 0, 6, 'STRING'),
(1, 1, 'Nombre Director', 'PERSONAS.NOMBRE', '', '', 0, 7, 'STRING'),
(1, 1, 'Mail Director', 'CONTACTOS.VALOR', '', '', 0, 8, 'STRING'),
(1, 1, 'Fecha inicio', 'PROYECTOS.FECHAINICIO', '', '', 0, 9, 'DATE'),
(1, 1, 'Fecha fin', 'PROYECTOS.FECHA_FIN', '', '', 0, 10, 'DATE'),
(1, 1, 'Última Prórroga', 'PRORROGAS.FECHAFIN', '', 'MAX', 0, 11, 'DATE'),
(1, 1, 'Total subsidiado', 'SUBSIDIOS.MONTOTOTAL', '', 'SUM', 0, 12, 'FLOAT'),
(1, 1, 'Total horas semanales', 'INTEGRANTES.HORASSEMANALES', '', 'SUM', 0, 13, 'FLOAT'),
(1, 1, 'Integrantes', 'INTEGRANTES.ID', '', 'COUNT', 0, 14, 'INTEGER'),
(1, 1, 'Docentes Incentivados', 'INCENTIVOS.LEGAJO', '', 'COUNT', 0, 15, 'INTEGER');
*/



insert into modulos (idmodulo, Descripcion) values
	(0, 'General'),
	(1, 'Estación'),
	(2, 'Roles'),
	(3, 'Usuarios'),
	(4, 'Cargos'),
	(5, 'Docentes'),
	(6, 'Investigación'),
	(7, 'Informes'),
	(8, 'Personas'),
	(9, 'Proyectos'),
	(10, 'Programas');
    
    
-- Rol Administrador:
insert into roles (id, nombre, descripcion) values (1, 'admin', 'Administrador');
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 0);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 1);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 2);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 3);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 4);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 5);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 6);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 7);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 8);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 9);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 1, 1, 1, 1, 1, 10);

-- Rol Usuario común:
insert into roles (id, nombre, descripcion) values (2, 'usuario', 'Usuario común');
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 0);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 1);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 0, 0, 0, 0, 2);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 0, 0, 0, 0, 3);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 4);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 5);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 6);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 7);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 8);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 9);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 2, 1, 0, 1, 1, 10);

-- Rol Invitado:
insert into roles (id, nombre, descripcion) values (3, 'invitado', 'Invitado');
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 0, 0);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 0, 1);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 0, 2);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 0, 3);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 4);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 5);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 6);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 7);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 8);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 9);
insert into permisos (id, Rol, Crear, Eliminar, Modificar, Listar, Modulo) values (0, 3, 0, 0, 0, 1, 10);


insert into TiposCargos values
(0, 'Ordinario'),
(1, 'Interino');


insert into TiposDocumentos values
(0, 'DNI'),
(1, 'LC'),
(2, 'LE'),
(3, 'CI'),
(4, 'CUIT');


insert into TiposContactos values
(0, 'Mail Laboral'),
(1, 'Mail Personal'),
(2, 'Teléfono Laboral'),
(3, 'Teléfono Particular'),
(4, 'Teléfono Celular');


insert into EstadosPersonas values 
(0, 'Activa'),
(1, 'Inactiva');


insert into EstadosDocentes values 
(0, 'Activo'),
(1, 'Inactivo'),
(2, 'Jubilado'),
(3, 'Retiro'),
(4, 'Renuncia'),
(5, 'Fallecido'),
(6, 'Licencia');
    

insert into EstadosCargos values 
(0, 'Activo'),
(1, 'Inactivo');


insert into estadosprogramas values
(0, 'Activo'),
(1, 'Inactivo');


insert into estadosproyectos values
(0, 'Activo'),
(1, 'Inactivo');


insert into CategoriasInvestigacion values
(0, 'No categorizado'),
(1, 'I'),
(2, 'II'),
(3, 'III'),
(4, 'IV'),
(5, 'V');
    

insert into Cargos values
(213,'Profesor Titular Exclusiva',40.0),
(214,'Profesor Titular Semiexclusiva',20.0),
(215,'Profesor Titular Simple',10.0),
(216,'Profesor Asociado Exclusiva',40.0),
(217,'Profesor Asociado Semiexclusiva',20.0),
(218,'Profesor Asociado Simple',10.0),
(219,'Profesor Adjunto Exclusiva',36.0),
(220,'Profesor Adjunto Semiexclusiva',18.0),
(221,'Profesor Adjunto Simple',9.0),
(222,'Jefe de Trabajos Prácticos Exclusiva',36.0),
(223,'Jefe de Trabajos Prácticos Semiexclusiva',18.0),
(224,'Jefe de Trabajos Prácticos Simple',9.0),
(225,'Ayudante de Primera Exclusiva',18.0),
(226,'Ayudante de Primera Semiexclusiva',14.0),
(227,'Ayudante de Primera Simple',9.0),
(228,'Ayudante de Segunda',6.0),
(638,'Bedel',4.0),
(1213,'Profesor Titular Ad-Honorem',9.0),
(1218,'Profesor Asociado Ad-Honorem',9.0),
(1221,'Profesor Adjunto Ad-Honorem',9.0),
(1224,'Jefe de Trabajos Prácticos Ad-Honorem',9.0),
(1227,'Ayudante de Primera Ad-Honorem',9.0),
(1228,'Ayudante de Segunda Ad-Honorem',9.0);


# Jefes de División
insert into Personas values 
(0, 9675920, 'Gonzalez', 'Julián', '1945-03-29', 0),
(0, 17200893, 'Cisneros', 'Guillermo', '1967-10-02', 0),
(0, 11242536, 'Urritiaga', 'Daniel Alfonso', '1951-05-12', 0),
(0, 12675920, 'Alcaraz', 'Marcelo Fernando', '1955-11-08', 0),
(0, 16405290, 'Fernandez Perez', 'Julieta', '1964-02-28', 0),
(0, 15603702, 'Aramburu', 'Graciela', '1959-07-10', 0),

# Responsables de Área
(0, 19732040, 'Martínez Mate', 'Laura', '1975-07-04', 0),
(0, 10928479, 'Peralta', 'Ramón', '1949-09-04', 0),
(0, 17875356, 'Mate', 'Cristina', '1971-10-09', 0),
(0, 17645568, 'Manzano', 'María', '1969-02-04', 0),
(0, 12976600, 'Benítez López', 'Pilar Laura', '1955-06-12', 0),
(0, 12506506, 'Del Moral', 'Francisco Guillermo', '1954-12-29', 0),
(0, 19113505, 'Gutiérrez Pérez', 'Óscar', '1975-05-26', 0),
(0, 9849483, 'Lopes Martínez', 'Ana', '1947-09-09', 0),
(0, 9945503, 'Lafuente', 'Dolores', '1945-10-17', 0),
(0, 17112281, 'Montserrat', 'Martín Javier', '1971-05-13', 0),
(0, 13582584, 'Miranda', 'Martín', '1957-09-29', 0),
(0, 18178625, 'Herrero Rubio', 'Nuria', '1974-05-01', 0),
(0, 10071374, 'Lozano Peralta', 'Guillermo Javier', '1950-12-31', 0),
(0, 17457547, 'Peralta Fernández', 'Ezequiel José', '1971-08-13', 0),
(0, 12579600, 'Benítez García', 'Francisco Jorge', '1956-09-04', 0),
(0, 12147287, 'Cabeza Algaba', 'Vicente', '1956-09-09', 0),
(0, 17504187, 'López', 'Adán Antonio', '1971-05-02', 0),
(0, 10284846, 'Castaño', 'Juan', '1949-07-10', 0),
(0, 16647413, 'Sainz Cabeza', 'Dolores Ana', '1966-01-21', 0),
(0, 9834195, 'González', 'Adán', '1947-02-21', 0),
(0, 18373319, 'López Lafuente', 'Martín', '1973-10-27', 0),
(0, 17032691, 'Pérez', 'Óscar', '1969-09-21', 0),
(0, 12329525, 'González Muñoz', 'Jorge Carlos', '1956-08-14', 0),
(0, 18663341, 'Rubio', 'Leonardo', '1974-05-22', 0),
(0, 13522037, 'González Gutiérrez', 'Vicente', '1957-07-30', 0),
(0, 16677483, 'Peralta Castaño', 'Juan Nicolás', '1968-11-03', 0),
(0, 10117993, 'Cabo Benítez', 'Jorge', '1948-08-06', 0),
(0, 11915775, 'Lozano', 'Carmen Rosario', '1951-08-01', 0),
(0, 13959331, 'Sainz Álvarez', 'Leonardo', '1958-02-25', 0),
(0, 16314292, 'Iborra Mate', 'Rosario', '1966-12-31', 0),
(0, 15491157, 'García', 'Julián', '1965-05-11', 0),
(0, 16015748, 'Lozano Benítez', 'Carmen María', '1966-02-11', 0),
(0, 18661346, 'Pérez', 'Rosa', '1972-06-30', 0),
(0, 19751064, 'Peralta García', 'Óscar', '1975-06-30', 0),
(0, 9550991, 'Vázquez Cabeza', 'Carmen Rosario', '1945-01-25', 0),
(0, 17924559, 'Peralta Sánchez', 'Nicolás', '1970-04-09', 0),
(0, 10662538, 'Peralta San Juan', 'Leonardo', '1948-01-11', 0),
(0, 14871880, 'Benítez', 'Elías Adán', '1961-12-06', 0),

# Equipo
(0, 39586150, 'Juran', 'Martín Tomás', '1996-06-29', 0),
(0, 40455634, 'Marazzo', 'Leonardo José', '1997-06-22', 0);



insert into Contactos values
# Jefes de División
(0, 0, 9675920, 0, 'semint2018@gmail.com'),
(0, 0, 17200893, 0, 'tomasjuran96@gmail.com'),
(0, 0, 11242536, 0, 'semint2018@gmail.com'),
(0, 0, 12675920, 0, 'semint2018@gmail.com'),
(0, 0, 16405290, 0, 'semint2018@gmail.com'),
(0, 0, 15603702, 0, 'semint2018@gmail.com');


# Jefes de División
insert into Docentes values 
(469, 0, 9675920, 'Jefe de la división Biología', 1, 0),
(94, 0, 17200893, 'Jefe de la división Computación', 1, 0),
(107, 0, 11242536, 'Jefe de la división Estadísticas y Sistemas', 1, 0),
(480, 0, 12675920, 'Jefe de la división Física', 1, 0),
(57, 0, 16405290, 'Jefa de la división Matemática', 1, 0),
(104, 0, 15603702, 'Jefa de la división Química', 1, 0),

# Responsables de Área
(3097, 0, 19732040, 'Observaciones de Martínez Mate', 1, 0),
(427, 0, 10928479, 'Observaciones de Peralta', 1, 0),
(2675, 0, 17875356, 'Observaciones de Mate', 1, 0),
(2459, 0, 17645568, 'Observaciones de Manzano', 1, 0),
(1027, 0, 12976600, 'Observaciones de Benítez López', 1, 0),
(948, 0, 12506506, 'Observaciones de Del Moral', 1, 0),
(3043, 0, 19113505, 'Observaciones de Gutiérrez Pérez', 1, 0),
(257, 0, 9849483, 'Observaciones de Lopes Martínez', 1, 0),
(268, 0, 9945503, 'Observaciones de Lafuente', 1, 0),
(2609, 0, 17112281, 'Observaciones de Montserrat', 1, 0),
(1229, 0, 13582584, 'Observaciones de Miranda', 1, 0),
(2924, 0, 18178625, 'Observaciones de Herrero Rubio', 1, 0),
(521, 0, 10071374, 'Observaciones de Lozano Peralta', 1, 0),
(2653, 0, 17457547, 'Observaciones de Peralta Fernández', 1, 0),
(1193, 0, 12579600, 'Observaciones de Benítez García', 1, 0),
(1153, 0, 12147287, 'Observaciones de Cabeza Algaba', 1, 0),
(2647, 0, 17504187, 'Observaciones de López', 1, 0),
(467, 0, 10284846, 'Observaciones de Castaño', 1, 0),
(2125, 0, 16647413, 'Observaciones de Sainz Cabeza', 1, 0),
(218, 0, 9834195, 'Observaciones de González', 1, 0),
(2859, 0, 18373319, 'Observaciones de López Lafuente', 1, 0),
(2407, 0, 17032691, 'Observaciones de Pérez', 1, 0),
(1108, 0, 12329525, 'Observaciones de González Muñoz', 1, 0),
(2974, 0, 18663341, 'Observaciones de Rubio', 1, 0),
(1221, 0, 13522037, 'Observaciones de González Gutiérrez', 1, 0),
(2330, 0, 16677483, 'Observaciones de Peralta Castaño', 1, 0),
(318, 0, 10117993, 'Observaciones de Cabo Benítez', 1, 0),
(671, 0, 11915775, 'Observaciones de Lozano', 1, 0),
(1358, 0, 13959331, 'Observaciones de Sainz Álvarez', 1, 0),
(2194, 0, 16314292, 'Observaciones de Iborra Mate', 1, 0),
(2067, 0, 15491157, 'Observaciones de García', 1, 0),
(2176, 0, 16015748, 'Observaciones de Lozano Benítez', 1, 0),
(2786, 0, 18661346, 'Observaciones de Pérez', 1, 0),
(3049, 0, 19751064, 'Observaciones de Peralta García', 1, 0),
(81, 0, 9550991, 'Observaciones de Vázquez Cabeza', 1, 0),
(2592, 0, 17924559, 'Observaciones de Peralta Sánchez', 1, 0),
(325, 0, 10662538, 'Observaciones de Peralta San Juan', 1, 0),
(1640, 0, 14871880, 'Observaciones de Benítez', 1, 0),

# Equipo
(143191, 0, 39586150, 'Observaciones de Juran', 1, 0),
(143112, 0, 40455634, 'Observaciones de Marazzo', 1, 0);


insert into Divisiones values
('BI','Biología',469,'DD 04/2006','2006-02-14','2009-12-20'),
('CO','Computación',94,'DD 13/2012','2006-02-14','2009-12-20'),
('ES','Estadísticas y Sistemas',107,'DD 02/2006','2006-02-14','2009-12-20'),
('FI','Física',480,'DD 03/2006','2006-02-14','2009-12-20'),
('MA','Matemática',57,'DD 05/2006','2006-02-14','2009-12-20'),
('QU','Química',104,'DD 06/2006','2006-03-01','2009-12-20');


insert into Areas values
('BI.01.00','Ecología','BI',3097,'506/12','2001-12-31','2013-12-31',null),
('BI.01.01','Ecología Básica','BI',427,'','2001-12-31','2001-12-31','BI.01.00'),
('BI.01.02','Ecología Avanzada','BI',2675,'','2001-12-31','2001-12-31','BI.01.00'),
('BI.02.00','Biología General','BI',2459,'506/12','2001-12-31','2013-12-31',null),
('BI.03.00','Biología  Molecular y Microbiología','BI',1027,'506/12','2001-12-31','2013-12-31',null),
('BI.03.01','Biología  Celular y Molecular','BI',948,'506/12','2001-12-31','2013-12-31','BI.03.00'),
('BI.03.02','Microbiología','BI',3043,'','2001-12-31','2001-12-31','BI.03.00'),
('BI.04.00','Genética y Evolución','BI',257,'506/12','2001-12-31','2013-12-31',null),
('BI.05.00','Biología Vegetal','BI',268,'','2001-12-31','2013-12-31',null),
('BI.06.00','Biología Animal','BI',2609,'506/12','2001-12-31','2013-12-31',null),
('BI.06.01','Biología Animal Estructural','BI',1229,'','2001-12-31','2001-12-31','BI.06.00'),
('BI.06.02','anulada','BI',2924,'','2001-12-31','2001-12-31','BI.06.00'),
('BI.06.03','Fisiología','BI',521,'','2001-12-31','2001-12-31','BI.06.00'),
('CO.01.00','Algoritmos y Lenguajes','CO',2653,'506/12','2001-12-31','2013-12-31',null),
('CO.02.00','Arquitectura,Sistemas Operativos y Redes','CO',1193,'506/12','2001-12-31','2013-12-31',null),
('CO.03.00','Ing.de Soft, Base de Dat. y Sist de Inf.','CO',1153,'506/12','2012-05-10','2013-12-31',null),
('CO.03.01','Sistema de Información ','CO',2647,'','2001-12-31','2001-12-31','CO.03.00'),
('CO.03.02','Base de Datos','CO',467,'','2012-05-10','2001-12-31','CO.03.00'),
('CO.03.03','Proyectos de Investigación','CO',2125,'','2012-05-10','2001-12-31','CO.03.00'),
('CO.04.00','Teoría de la Computación','CO',218,'506/12','2001-12-31','2013-12-31',null),
('CO.05.00','Computación Aplicada','CO',2859,'506/12','2001-12-31','2013-12-31',null),
('CO.06.00','Informática','CO',2407,'506/12','2001-12-31','2013-12-31',null),
('ES.01.00','Estadística Socioeconómica','ES',1108,'506/12','2001-12-31','2013-12-31',null),
('ES.02.00','Bioestadística','ES',2974,'506/12','2001-12-31','2013-12-31',null),
('ES.03.00','Muestreo y Control de Procesos','ES',1221,'506/12','2001-12-31','2013-12-31',null),
('ES.04.00','Modelos Avanzados Metod Cuali-Cuanti del','ES',2330,'506/12','2001-12-31','2013-12-31',null),
('ES.05.00','Informática Básica','ES',318,'','2001-12-31','2001-12-31',null),
('ES.06.00','Teoría de la Computación','ES',671,'CDDCB 111/2009','2009-04-01','2009-12-31',null),
('ES.07.00','Biometría','ES',1358,'506/12','2001-12-31','2013-12-31',null),
('FI.01.00','Física Básica','FI',2194,'506/12','2001-12-31','2013-12-31',null),
('FI.02.00','Física Aplicada','FI',2067,'506/12','2001-12-31','2013-12-31',null),
('MA.01.00','Matemática en las Ciencias Básicas','MA',2176,'CDDCB 041/2009','2009-02-26','2014-11-06',null),
('MA.02.00','Matemática en las Ciencias Económicas ','MA',2786,'506/12','2001-12-31','2013-12-31',null),
('MA.03.00','Mat. en la Agronomía y las Cs Soc. y Hum','MA',3049,'506/12','2001-12-31','2012-12-31',null),
('QU.01.00','Química General e Inorgánica','QU',81,'506/12','2001-12-31','2013-12-31',null),
('QU.02.00','Química Orgánica','QU',2592,'506/12','2001-12-31','2013-12-31',null),
('QU.03.00','Química Biológica','QU',325,'506/12','2001-12-31','2013-12-31',null),
('QU.04.00','Química Analítica','QU',1640,'506/12','2001-12-31','2013-12-31',null);


insert into CargosDocentes values
(100, 143191, 'CO.01.00', 228, 0, '507/18', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(1301, 143112, 'FI.02.00', 228, 0, '507/19', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(1400, 1640, 'QU.04.00', 214, 0, '507/20', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(1500, 2176, 'MA.01.00', 224, 0, '507/21', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(2000, 1221, 'ES.03.00', 224, 0, '507/22', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(102, 1640, 'QU.04.00', 214, 0, '507/23', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(500, 2176, 'MA.01.00', 222, 0, '507/24', null, null, null, null, null, 4000.0, '2018-06-1', 0);
insert into CargosDocentes values
(121, 1221, 'ES.03.00', 213, 0, '507/25', null, null, null, null, null, 4000.0, '2018-06-1', 0);

/*
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('10', 'Cabo Benítez', 'Jorge', '1', '14095.33', '2018-06-15', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('10', 'Cabo Benítez', 'Jorge', '2', '77791.13', '2018-06-16', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('57', 'Fernandez Perez', 'Julieta', '3', '28191.09', '2018-06-17', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('57', 'Fernandez Perez', 'Julieta', '4', '15248.58', '2018-06-18', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('81', 'Vázquez Cabeza', 'Carmen Rosario', '5', '28191.09', '2018-06-19', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('81', 'Vázquez Cabeza', 'Carmen Rosario', '6', '24309.69', '2018-06-20', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('94', 'Cisneros', 'Guillermo', '7', '52597.41', '2018-06-21', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('94', 'Cisneros', 'Guillermo', '8', '15248.58', '2018-06-22', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('104', 'Aramburu', 'Graciela', '9', '15248.58', '2018-06-23', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('104', 'Aramburu', 'Graciela', '10', '26298.66', '2018-06-24', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('107', 'Urritiaga', 'Daniel Alfonso', '11', '32072.46', '2018-06-25', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('107', 'Urritiaga', 'Daniel Alfonso', '12', '48619.45', '2018-06-26', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('218', 'González', 'Adán', '13', '14095.33', '2018-06-27', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('218', 'González', 'Adán', '14', '56382.22', '2018-06-28', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('257', 'Lopes Martínez', 'Ana', '15', '14095.33', '2018-06-29', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('257', 'Lopes Martínez', 'Ana', '16', '14095.33', '2018-06-30', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('268', 'Lafuente', 'Dolores', '17', '56382.22', '2018-07-01', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('268', 'Lafuente', 'Dolores', '18', '56382.22', '2018-07-02', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('318', 'Cabo Benítez', 'Jorge', '19', '18784.76', '2018-07-03', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('318', 'Cabo Benítez', 'Jorge', '20', '28191.09', '2018-07-04', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('325', 'Peralta San Juan', 'Leonardo', '21', '10214.01', '2018-07-05', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('325', 'Peralta San Juan', 'Leonardo', '22', '56382.22', '2018-07-06', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('427', 'Peralta', 'Ramón', '23', '14095.33', '2018-07-07', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('427', 'Peralta', 'Ramón', '24', '49724.44', '2018-07-08', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('467', 'Castaño', 'Juan', '25', '12430.91', '2018-07-09', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('467', 'Castaño', 'Juan', '26', '24309.69', '2018-07-10', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('469', 'Gonzalez', 'Julián', '27', '28191.09', '2018-07-11', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('469', 'Gonzalez', 'Julián', '28', '24309.69', '2018-07-12', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('480', 'Alcaraz', 'Marcelo Fernando', '29', '12154.67', '2018-07-13', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('480', 'Alcaraz', 'Marcelo Fernando', '30', '24309.69', '2018-07-14', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('521', 'Lozano Peralta', 'Guillermo Javier', '31', '24309.69', '2018-07-15', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('521', 'Lozano Peralta', 'Guillermo Javier', '32', '60995.31', '2018-07-16', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('671', 'Lozano', 'Carmen Rosario', '33', '7037', '2018-07-17', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('671', 'Lozano', 'Carmen Rosario', '34', '14095.33', '2018-07-18', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('948', 'Del Moral', 'Francisco Guillermo', '35', '14095.33', '2018-07-19', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('948', 'Del Moral', 'Francisco Guillermo', '36', '12154.67', '2018-07-20', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1027', 'Benítez López', 'Pilar Laura', '37', '48619.45', '2018-07-21', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1027', 'Benítez López', 'Pilar Laura', '38', '7428.37', '2018-07-22', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1108', 'González Muñoz', 'Jorge Carlos', '39', '56382.22', '2018-07-23', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1108', 'González Muñoz', 'Jorge Carlos', '40', '20428.32', '2018-07-24', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1153', 'Cabeza Algaba', 'Vicente', '41', '28191.09', '2018-07-25', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1153', 'Cabeza Algaba', 'Vicente', '42', '24309.69', '2018-07-26', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1193', 'Benítez García', 'Francisco Jorge', '43', '28191.09', '2018-07-27', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1193', 'Benítez García', 'Francisco Jorge', '44', '48619.45', '2018-07-28', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1221', 'González Gutiérrez', 'Vicente', '45', '26298.66', '2018-07-29', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1221', 'González Gutiérrez', 'Vicente', '46', '14095.33', '2018-07-30', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1229', 'Miranda', 'Martín', '47', '20428.32', '2018-07-31', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1229', 'Miranda', 'Martín', '48', '20428.32', '2018-08-01', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1358', 'Sainz Álvarez', 'Leonardo', '49', '20428.32', '2018-08-02', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1358', 'Sainz Álvarez', 'Leonardo', '50', '24309.69', '2018-08-03', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1640', 'Benítez', 'Elías Adán', '51', '48619.45', '2018-08-04', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('1640', 'Benítez', 'Elías Adán', '52', '12154.67', '2018-08-05', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2067', 'García', 'Julián', '53', '28191.09', '2018-08-06', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2067', 'García', 'Julián', '54', '10214.01', '2018-08-07', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2125', 'Sainz Cabeza', 'Dolores Ana', '55', '14095.33', '2018-08-08', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2125', 'Sainz Cabeza', 'Dolores Ana', '56', '28191.09', '2018-08-09', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2176', 'Lozano Benítez', 'Carmen María', '57', '14095.33', '2018-08-10', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2176', 'Lozano Benítez', 'Carmen María', '58', '56382.22', '2018-08-11', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2194', 'Iborra Mate', 'Rosario', '59', '14095.33', '2018-08-12', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2194', 'Iborra Mate', 'Rosario', '60', '14095.33', '2018-08-13', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2330', 'Peralta Castaño', 'Juan Nicolás', '61', '115221', '2018-08-14', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2330', 'Peralta Castaño', 'Juan Nicolás', '62', '48619.45', '2018-08-15', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2407', 'Pérez', 'Óscar', '63', '56382.22', '2018-08-16', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2407', 'Pérez', 'Óscar', '64', '21585', '2018-08-17', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2459', 'Manzano', 'María', '65', '12596.66', '2018-08-18', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2459', 'Manzano', 'María', '66', '12596.66', '2018-08-19', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2592', 'Peralta Sánchez', 'Nicolás', '67', '29216.22', '2018-08-20', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2592', 'Peralta Sánchez', 'Nicolás', '68', '50387.44', '2018-08-21', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2609', 'Montserrat', 'Martín Javier', '69', '14607.89', '2018-08-22', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2609', 'Montserrat', 'Martín Javier', '70', '56382.22', '2018-08-23', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2647', 'López', 'Adán Antonio', '71', '14095.33', '2018-08-24', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2647', 'López', 'Adán Antonio', '72', '28191.09', '2018-08-25', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2653', 'Peralta Fernández', 'Ezequiel José', '73', '24309.69', '2018-08-26', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2653', 'Peralta Fernández', 'Ezequiel José', '74', '14095.33', '2018-08-27', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2675', 'Mate', 'Cristina', '75', '26909.67', '2018-08-28', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2675', 'Mate', 'Cristina', '76', '14095.33', '2018-08-29', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2786', 'Pérez', 'Rosa', '77', '55869.65', '2018-08-30', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2786', 'Pérez', 'Rosa', '78', '13967.2', '2018-08-31', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2859', 'López Lafuente', 'Martín', '79', '9749.73', '2018-09-01', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2859', 'López Lafuente', 'Martín', '80', '58432.48', '2018-09-02', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2924', 'Herrero Rubio', 'Nuria', '81', '24309.69', '2018-09-03', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2924', 'Herrero Rubio', 'Nuria', '82', '12154.67', '2018-09-04', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2974', 'Rubio', 'Leonardo', '83', '24309.69', '2018-09-05', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('2974', 'Rubio', 'Leonardo', '84', '7428.37', '2018-09-06', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('3043', 'Gutiérrez Pérez', 'Óscar', '85', '13454.64', '2018-09-07', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('3043', 'Gutiérrez Pérez', 'Óscar', '86', '43171', '2018-09-08', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('3049', 'Peralta García', 'Óscar', '87', '49334.71', '2018-09-09', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('3049', 'Peralta García', 'Óscar', '88', '56382.22', '2018-09-10', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('3097', 'Martínez Mate', 'Laura', '89', '18571.2', '2018-09-11', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('3097', 'Martínez Mate', 'Laura', '90', '24309.69', '2018-09-12', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('143191', 'Juran', 'Martín Tomás', '91', '9749.73', '2018-09-13', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('143191', 'Juran', 'Martín Tomás', '92', '51256.56', '2018-09-14', '0');
INSERT INTO `cargosfaltantes` (`Legajo`, `Apellido`, `Nombre`, `Cargo`, `UltimoCosto`, `FechaUltimoCosto`, `Tipo`) VALUES ('143112', 'Marazzo', 'Leonardo', '93', '12813.94', '2018-09-15', '0');
*/
/*
insert into programasinvestigacion (`id`, `Nombre`, `Director`, `Estado`) 
	values (1, 'Estudios de los alimentos', 143112, 0);
insert into programasinvestigacion (`id`, `Nombre`, `Director`, `Estado`) 
	values (2, 'Las Tecnologías de la Información y la Comunicación', 57, 0);
insert into programasinvestigacion (`id`, `Nombre`, `Director`, `Estado`) 
	values (3, 'Estudios de física', 325, 0);


insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (1, 'Estudio de las calorías en grasas', '2018-06-01', 143112, 1, 0);
    
insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (2, 'Estudio del consumo de carne porcina', '2018-06-01', 81, 1, 0);

insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (3, 'Estudio sobre las aguas', '2018-06-01', 104, 1, 0);


insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (4, 'Las TIC y la educación', '2018-06-01', 218, 2, 0);

insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (5, 'El uso de las TIC en carreras no afines', '2018-06-01', 257, 2, 0);

insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (6, 'WEB 2.0', '2018-06-01', 318, 2, 0);
    



insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (7, 'Los agujeros negros', '2018-06-01', 1153, 3, 0);

insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (8, 'La aerodinámica de los automóviles', '2018-06-01', 1193, 3, 0);

insert into proyectos (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`)
	values (9, 'MRU', '2018-06-01', 1221, 3, 1);
*/



# SuperUsuario oculto (Contraseña superusuario)
insert into roles (id, nombre, descripcion) values (0, 'su', 'Oculto');
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

insert into personas values
(0, 0, 's', 'u', '2018-03-10', 1);

# SuperUsuario - Usuario: su | Password: semint2018
insert into usuarios values
('su', 'c2VtaW50MjAxOA==', 'Ve3nYAXwigF2BfAU0H+1+Q==', 0, 0, 'Oculto');

insert into rolesxusuario values
('su', 0);


INSERT INTO `programasinvestigacion` (`id`, `Nombre`, `Director`, `Codirector`, `Disposicion`, `Desde`, `Hasta`, `Estado`) VALUES (1, 'Ciencias Biologicas', 143112, null, null, null, null, '0');
INSERT INTO `programasinvestigacion` (`id`, `Nombre`, `Director`, `Codirector`, `Disposicion`, `Desde`, `Hasta`, `Estado`) VALUES (2, 'Las Tecnologías de la Información y la Comunicación', 57, null, null, null, null, '0');
INSERT INTO `programasinvestigacion` (`id`, `Nombre`, `Director`, `Codirector`, `Disposicion`, `Desde`, `Hasta`, `Estado`) VALUES (3, 'Estudios de física', 325, null, null, null, null, 0);
INSERT INTO `programasinvestigacion` (`id`, `Nombre`, `Director`, `Codirector`, `Disposicion`, `Desde`, `Hasta`, `Estado`) VALUES (4, 'Quimica', 671, null, null, null, null, 0);
INSERT INTO `programasinvestigacion` (`id`, `Nombre`, `Director`, `Codirector`, `Disposicion`, `Desde`, `Hasta`, `Estado`) VALUES (5, 'Analisis matematicos', 521, null, null, null, null, 0);



INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Codirector`, `FechaInicio`, `Programa`, `Estado`) VALUES (1, 'Cultivo in vitro de especies leñosas nativas', '2008-06-22', '94', null, '2008-09-15', '1', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Codirector`, `FechaInicio`, `Programa`, `Estado`) VALUES (2, 'Morfogénesis in vitro de Handroanthus impetiginosus', '2009-07-15', '107', null, '2009-10-30', '1', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Codirector`, `FechaInicio`, `Programa`, `Estado`) VALUES (3, 'El aprendizaje basado en problemas como técnicas didáctica.', '2015-02-14', '469', null, '2016-05-01', '1', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (4, 'P.A.M.P.A.', '2010-05-30', '107', '2010-06-14', '2', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (5, 'Decisiones inteligentes en sistemas complejos', '2011-02-01', '257', '2011-04-13', '2', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (6, 'Estudio multitemporal y monitoreo de los glaciares de Patagonia Sur y del Mar Weddell Sur ', '2011-03-01', '3049', '2012-02-01', '2', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (7, 'Medición de la Radiación Fotosintéticamente activa (PAR) en los planos con diferentes orientaciones, mediante el desarrollo y ensayo de un dispositivo ad-hoc', '2010-07-26', '2609', '2010-08-26', '3', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (8, 'Red solarimétrica Regional', '2017-02-01', '2647', '2017-06-15', '3', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `Programa`, `Estado`) VALUES (9, 'Medición y modelo la componente directa de la radiación solar: aplicaciones', '2018-03-06', '3043', '3', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (10, 'Síntesis y estudio de la estereoquímica de Tetralinas e Indanos sustituidos', '2006-06-24', '2609', '2006-10-24', '4', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (11, 'Formulación de complejos entre ciclodextrinas y compuestos destinados a mejorar la calidad de los alimentos y su aplicación\n', '2006-07-28', '2609', '2006-11-01', '4', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (12, 'Propiedades de oxo – hidróxidos comunes en suelos. Su actividad como inmovilizantes de iones pesados y posibles uso en remediación', '2016-04-22', '104', '2017-11-22', '4', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (13, 'Los estilos de Aprendizaje y la Matemática', '2009-11-22', '218', '2011-02-22', '5', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (14, 'Los errores mas frecuentes en el aprendizaje de Matemática I', '2007-08-19', '257', '2009-04-19', '5', 0);
INSERT INTO `proyectos` (`id`, `Nombre`, `FechaPresentacion`, `Director`, `FechaInicio`, `Programa`, `Estado`) VALUES (15, 'Un estudio sobre la comprensión en Matemática Superior. Consecuencias para la enseñanza', '2005-12-12', '427', '2007-01-19', '5', 0);



INSERT INTO `integrantes` (`id`, `CargoDocente`, `Apellido`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (1, 100, '', 'UNLu', 15, 1);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (2, 102, 'UNLu', 48, 2);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (3, 121, 'UNLu', 20, 3);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (4, 500, 'UNLu', 11, 4);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (5, 1301, 'UNLu', 10, 5);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (6, 1400, 'UNLu', 5, 6);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (7, 1500, 'UNLu', 13, 7);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (8, 2000, 'UNLu', 22, 8);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (9, 2000, 'UNLu', 24, 9);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (10, 1500, 'UNLu', 5, 10);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (11, 1301, 'UNLu', 18, 11);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (12, 500, 'UNLu', 48, 12);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (13, 100, 'UNLu', 11, 13);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (14, 1400, 'UNLu', 11, 14);
INSERT INTO `integrantes` (`id`, `CargoDocente`, `Institucion`, `HorasSemanales`, `Proyecto`) VALUES (15, 121, 'UNLu', 20, 15);



INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('64', '2407', 'ES.04.00', '1213', '0', '507/29', null, null, null, null, null, '21585', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('65', '2459', 'ES.05.00', '1218', '0', '507/26', null, null, null, null, null, '12596.66', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('66', '2459', 'ES.06.00', '1221', '0', '507/31', null, null, null, null, null, '12596.66', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('67', '2592', 'ES.07.00', '1224', '0', '507/33', null, null, null, null, null, '29216.22', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('68', '2592', 'FI.01.00', '1227', '0', '507/32', null, null, null, null, null, '50387.44', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('69', '2609', 'FI.02.00', '1228', '0', '507/27', null, null, null, null, null, '14607.89', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('70', '2609', 'MA.01.00', '213', '0', '507/28', null, null, null, null, null, '56382.22', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('71', '2647', 'MA.02.00', '214', '0', '507/29', null, null, null, null, null, '14095.33', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('72', '2647', 'MA.03.00', '215', '0', '507/30', null, null, null, null, null, '28191.09', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('73', '2653', 'QU.01.00', '216', '0', '507/27', null, null, null, null, null, '24309.69', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('74', '2653', 'QU.02.00', '217', '0', '507/32', null, null, null, null, null, '14095.33', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('75', '2675', 'QU.03.00', '218', '0', '507/34', null, null, null, null, null, '26909.67', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('76', '2675', 'QU.04.00', '219', '0', '507/33', null, null, null, null, null, '14095.33', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('77', '2786', 'BI.01.00', '220', '0', '507/28', null, null, null, null, null, '55869.65', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('78', '2786', 'BI.01.01', '221', '0', '507/29', null, null, null, null, null, '13967.2', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('79', '2859', 'BI.01.02', '222', '0', '507/30', null, null, null, null, null, '9749.73', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('80', '2859', 'BI.02.00', '223', '0', '507/31', null, null, null, null, null, '58432.48', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('81', '2924', 'BI.03.00', '224', '0', '507/28', null, null, null, null, null, '24309.69', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('82', '2924', 'BI.03.01', '225', '0', '507/33', null, null, null, null, null, '12154.67', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('83', '2974', 'BI.03.02', '226', '0', '507/35', null, null, null, null, null, '24309.69', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('84', '2974', 'BI.04.00', '227', '0', '507/34', null, null, null, null, null, '7428.37', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('85', '3043', 'BI.05.00', '228', '0', '507/29', null, null, null, null, null, '13454.64', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('86', '3043', 'BI.06.00', '638', '0', '507/30', null, null, null, null, null, '43171', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('87', '3049', 'BI.06.01', '1213', '0', '507/31', null, null, null, null, null, '49334.71', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('88', '3049', 'BI.06.02', '1218', '0', '507/32', null, null, null, null, null, '56382.22', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('89', '3097', 'BI.06.03', '1221', '0', '507/29', null, null, null, null, null, '18571.2', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('90', '3097', 'CO.01.00', '1224', '0', '507/34', null, null, null, null, null, '24309.69', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('91', '143191', 'CO.02.00', '1227', '0', '507/36', null, null, null, null, null, '9749.73', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('92', '143191', 'CO.03.00', '1228', '0', '507/35', null, null, null, null, null, '51256.56', '2018-06-01', '0');
INSERT INTO `cargosdocentes` (`Codigo`, `Legajo`, `Area`, `Cargo`, `TipoCargo`, `Disposicion`, `DispDesde`, `DispHasta`, `Resolucion`, `ResDesde`, `ResHasta`, `UltimoCosto`, `FechaUltimoCosto`, `EstadoCargo`) VALUES ('93', '143112', 'CO.03.01', '1228', '0', '507/30', null, null, null, null, null, '12813.94', '2018-06-01', '0');



INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '3', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '4', '15248.58', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '5', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '6', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '7', '52597.41', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '8', '15248.58', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '9', '15248.58', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '10', '26298.66', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '11', '32072.46', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '12', '48619.45', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '13', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '14', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '15', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '16', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '17', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '18', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '19', '18784.76', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '20', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '21', '10214.01', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '22', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '23', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '24', '49724.44', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '25', '12430.91', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '26', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '27', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '28', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '29', '12154.67', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '30', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '31', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '32', '60995.31', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '33', '7037', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '34', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '35', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '36', '12154.67', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '37', '48619.45', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '38', '7428.37', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '39', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '40', '20428.32', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '41', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '42', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '43', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '44', '48619.45', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '45', '26298.66', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '46', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '47', '20428.32', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '48', '20428.32', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '49', '20428.32', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '50', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '51', '48619.45', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '52', '12154.67', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '53', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '54', '10214.01', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '55', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '56', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '57', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '58', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '59', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '60', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '61', '115221', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '62', '48619.45', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '63', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '64', '21585', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '65', '12596.66', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '66', '12596.66', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '67', '29216.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '68', '50387.44', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '69', '14607.89', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '70', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '71', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '72', '28191.09', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '73', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '74', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '75', '26909.67', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '76', '14095.33', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '77', '55869.65', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '78', '13967.2', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '79', '9749.73', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '80', '58432.48', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '81', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '82', '12154.67', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '83', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '84', '7428.37', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '85', '13454.64', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '86', '43171', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '87', '49334.71', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '88', '56382.22', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '89', '18571.2', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '90', '24309.69', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '91', '9749.73', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '92', '51256.56', '2018-06-01');
INSERT INTO `costos` (`id`, `CodigoCargo`, `Costo`, `Fecha`) VALUES ('1', '93', '12813.94', '2018-06-01');
