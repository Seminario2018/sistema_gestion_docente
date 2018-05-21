*** Importar la BD plumasdocentes ***

Para importar en la BD "plumasdocentes vX.sql" es necesario realizar drop de las tablas que se tengan en el schema plumasdocentes.

Se recomienda realizar drop del schema y crear uno nuevo con el mismo nombre.

*** Aplicar parches ***

Aplicar los parches desde el más viejo al más nuevo, ejecutando los queries de los archivos "parche-mes-dia.sql" en forma secuencial.


*** Importar datos ***

Importar la última versión de los datos de prueba "datos vX.sql". Si el script fallara, chequee haber importado la última versión de la BD y haber aplicado correctamente todos los parches.