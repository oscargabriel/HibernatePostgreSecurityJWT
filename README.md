programa que gestiona usuarios

con 
-autenticacion
-token y roles
-base de datos postgreSQL
-auditoria

usando Spring Boot 3.0.2

paquete CONTROLLER se encarga de recibir las solicitudes http y llama a service

paquete DTO son clases con estructura personalizada para ser usada en controller, repository y service

paquete ENTITIES estan las entidades que son reflejo de la base de datos

paquete EXCEPTION gestiona las diferentes excepciones que se generan en respuesta

  paquete CISTOMIZATIONS son las expeciones personalizadas que se crean en diferentes puntos que son capturadas por el controlador de expeciones

  paquete EXCEPTIONRED gestiona las exepciones que tengan asociadas una direccion de red

paquete REPOSITORY hace la conceccion con la base de datos y ejecuta CRUD

  paquete DAO son funciones para llamadas REQUEST personalizadas a la base de datos

  paquete JPA usa las funciones predefinidas por jpa para los llamdos CREATE, UPDATE, DELETE

paquete SECURITY se gestiona la seguridad del programa administradas por los paquetes

  paquete CONFIG se establecen las reglas de coneccion, acceso a pagina y caveceras

  paquete JWT se gestionan los token una vez que se inicia session

paquete SERVICE se encarga de gestionar las llamando la base de datos para consultas y authorizaciones










