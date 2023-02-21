usando Spring Boot 3.0.2

paquete controller se encarga de recibir las solicitudes http y llama a service

paquete service se encarga de procesar las solicitudes llamando a repository y jwt de security

paquete repository hace la conceccion con la base de datos y ejecuta CRUD

paquete security se gestiona la seguridad

paquete config se establecen las reglas de coneccion, acceso a pagina y caveceras

paquete component se hacen los mensajes personalizados para las exepciones

paquete jwt se gestionan los token una vez que se inicia session

paquete entities estan las entidades que son reflejo de la base de datos

paquete dto son clases con estructura personalizada para usar en por controller, repository y service


