*** Importar la BD plumasdocentes ***

Para importar la BD "plumasdocentes vX.sql", ir a la opción "import from self-contained file" y seleccionar o crear un nuevo schema. Asegurarse de importar tanto datos como esctructura.

*** Aplicar parches ***

Aplicar los parches desde el más viejo al más nuevo, ejecutando los queries de los archivos "parche-mes-dia.sql" en forma secuencial.


*** Importar datos ***

Importar la última versión de los datos de prueba "datos vX.sql". Si el script fallara, chequee haber importado la última versión de la BD y haber aplicado correctamente todos los parches.