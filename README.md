# Proyecto Base de Datos - Empresa Eléctrica

## Ejecución
1. Crear la base de datos ejecutando los scripts en `sql/`.
2. Modificar `config.properties` con tus datos de conexión.
3. Compilar el proyecto:
   javac -d bin src/**/*.java
4. Ejecutar:
   java -cp "bin;mysql-connector-j-8.0.33.jar" App

## Descripción
- `DbManager`: maneja la conexión JDBC a MySQL.
- `UsuarioDao` y `ReclamoDao`: realizan operaciones CRUD con `PreparedStatement`.
- `App`: prueba las operaciones principales.
- Los triggers en MySQL auditan las eliminaciones automáticamente.
