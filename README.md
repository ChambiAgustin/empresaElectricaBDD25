# Proyecto Final de Bases de Datos - Empresa Eléctrica

Este proyecto implementa un sistema de gestión de reclamos para una empresa eléctrica, utilizando Java con JDBC para la lógica de la aplicación y MySQL como motor de base de datos.

El proyecto cumple con los requisitos del trabajo práctico integrador, incluyendo:
* **Diseño y DDL (Punto 3):** Creación de la estructura de la base de datos, tablas, relaciones, claves foráneas y triggers (`sql/proyecto-ddl - punto 3.sql`).
* **Carga de Datos (Punto 4):** Script de inserción de datos de prueba (`sql/proyecto-dml - punto 4.sql`).
* **Aplicación Java (Punto 5):** Programa en Java que permite insertar usuarios, eliminar reclamos (con auditoría por trigger) y listar reclamos de un usuario con sus rellamados.
* **Consultas SQL (Punto 6):** Script con consultas complejas (`sql/proyecto-consultas-sql - punto 6.sql`).

## 👨‍💻 Integrantes

* Riberi Walter Ivan
* Ferreres Dylan Agustin
* Chambi Agustin Ezequiel

---

## ⚙️ 1. Prerrequisitos

Para ejecutar este proyecto, necesitarás:

1.  **Java JDK** (versión 8 o superior).
2.  [cite_start]**MySQL Server** (versión 8.0 o superior)[cite: 2332].
3.  Un cliente de MySQL (como MySQL Workbench o DBeaver) para ejecutar los scripts.
4.  El **Driver de MySQL (Connector/J)**. Este proyecto **requiere** que el archivo `.jar` del conector esté presente.
    * Puedes descargarlo desde: [MySQL Connector/J Downloads](https://dev.mysql.com/downloads/connector/j/)
    * Descarga la versión "Platform Independent" (zip) y extrae el archivo `mysql-connector-j-X.X.XX.jar`.

---

## 🔑 2. Configuración de la Conexión (¡Importante!)

Para que la aplicación Java se conecte a tu base de datos local, debes configurar tus credenciales.

**Archivo a modificar:** `config.properties` (ubicado en la raíz del proyecto).

Este archivo contiene los datos de conexión que `DbManager.java` utiliza para conectarse a MySQL.

```properties
# Configuración de la Base de Datos MySQL
host=localhost
port=3306

# Asegúrate de que este sea el nombre de tu BD
database=empresa_electrica

# Cambia 'root' por tu usuario de MySQL
user=root

# CAMBIA 'tu_contraseña' POR TU CONTRASEÑA DE MYSQL
password=tu_contraseña