*** Importar la BD plumasdocentes ***

Para importar la BD "plumasdocentes vX.sql", ir a la opci�n "import from self-contained file" y seleccionar o crear un nuevo schema. Asegurarse de importar tanto datos como esctructura.

*** Aplicar parches ***

Aplicar los parches desde el m�s viejo al m�s nuevo, ejecutando los queries de los archivos "parche-mes-dia.sql" en forma secuencial.


*** Importar datos ***

Importar la �ltima versi�n de los datos de prueba "datos vX.sql". Si el script fallara, chequee haber importado la �ltima versi�n de la BD y haber aplicado correctamente todos los parches.