usando Spring Boot 3.0.2

paquete CONTROLLER se encarga de recibir las solicitudes http y llama a service

paquete DTO son clases con estructura personalizada para ser usada en controller, repository y service

paquete ENTITIES estan las entidades que son reflejo de la base de datos

paquete REPOSITORY hace la conceccion con la base de datos y ejecuta CRUD

  paquete DAO son funciones para llamadas REQUEST personalizadas a la base de datos

  paquete JPA usa las funciones predefinidas por jpa para los llamdos CREATE, UPDATE, DELETE

paquete SECURITY se gestiona la seguridad del programa administradas por los paquetes

  paquete COMPONENT se hacen los mensajes personalizados para las exepciones

  paquete CONFIG se establecen las reglas de coneccion, acceso a pagina y caveceras

  paquete JWT se gestionan los token una vez que se inicia session

paquete SERVICE se encarga de gestionar las llamando la base de datos para consultas y authorizaciones










