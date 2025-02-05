# AmazonBeta3

AmazonBeta3 es una aplicación de escritorio desarrollada en JavaFX que permite la gestión de productos, usuarios y categorías mediante una interfaz gráfica amigable.

## Requisitos

- Java 11 o superior
- Apache Maven
- MySQL u otro sistema de base de datos compatible

## Instalación

1. Clona el repositorio o descarga el proyecto.
2. Asegúrate de tener Maven instalado y configurado.
3. Configura la base de datos en `application.properties` o en la clase de acceso a datos correspondiente.
4. Ejecuta el siguiente comando para compilar el proyecto:
   ```sh
   mvn clean install
   ```
5. Para ejecutar la aplicación, usa:
   ```sh
   mvn javafx:run
   ```

## Funcionalidades

- Gestión de productos (añadir, editar, eliminar)
- Gestión de usuarios
- Gestión de categorías
- Carrito de compras

## Conexión con la Base de Datos

La aplicación se conecta a una base de datos MySQL. Asegúrate de configurar correctamente las credenciales en `accesoDatos`.

## Diagramas UML

(Agregar aquí el diagrama UML una vez generado)

## Autores

Desarrollado por [Tu Nombre].

