# ☕♨ turnos-rotativos 📅
 **TP 2**: Sistema de gestión de turnos rotativos que permite la administración de empleados, la obtención y gestión de conceptos laborales, y la planificación de jornadas laborales. Incluye funcionalidades para el ABM de empleados, consulta y asignación de jornadas, y creación de nuevas jornadas laborales según las necesidades del negocio.

## ℹ️ Consideraciones o criterios utilizados

🎯 Hice todo lo posible por usar buenas prácticas y lo aprendido en clases, separar en capas siempre que lo considere oportuno.

🗂️ EL proyecto utilice **git** y todo lo solicitado para el **TP2** las **HU** están en el branch: **features-empleados-turnos** 

📚 Intenté utilizar la librería **MapStruck**, pero termine descartando y arme una estructura de clases similar que me resulto bastante práctica usarlas para realizar el mapeo de objetos entity/dto.

🧩 Algo que en un principio me pareció lo correcto, pero ahora cambie un poco de opinión jeje es como trate las validaciones. Paso a explicarme mejor, tome como criterio todo lo que pueda validar por base de datos hacerlo así, por ej.: crear un empleado con un nro de documento que ya existe, que la validación la realice la base de datos al intentar crear y se lanza la excepción de violación de integridad por la unique key de nroDocumento. Opte por está opción porque desde un pto de vista es optimo debido a que realiza una sola conexión a la base de datos para realizar el insert, en cambio si hacía una validación previa al insert iba a necesitar dos accesos a la BD para realizar el insert de empleado, pero luego termine pagando en la complejidad por lo menos para mi de tratar esa excepción que se lanza que es lo que no me gusto como lo trate personalmente, debido a que lo considero un tanto compleja y lo podía haber tratado en la capa de servicio haciendo 2 accesos y dejando mucha más simple el tratamiento general de la funcionalidad de agregar empleado.

💡 De igual manera estoy seguro que se podría trabajar tranquilamente las excepciones de la BD con un poco más de conocimiento en la arquitectura que nos provee Spring customizar de forma especifica las excepciones por entidad y evitar tratarlas en la clase CustomExceptionHandler de forma muy genérica que para el TP resuelve lo que piden, pero si sigue creciendo tanto en entidades como reglas de negocio, no es rentable está forma de tratar las validaciones que implemente.

</> Otra cosita por comentar es que solamente utilice una clase **Validator** para **Jornada** debido a la cantidad de reglas de negocio que tenía jeje, pero luego me dí cuenta que hubiese estado bueno tratar de forma homogenea todo el proyecto las validaciones de empleado y concepto laboral también en la capa validator. Así con un par de cosas considero que hubiese prestado más atención en hacer homogeneo la forma de tratar las funcionalidades también considero que hay que hacer un balance cuando por ej.: utilizar la capa de validaciones y cuando no, porque si es muy simple y tiene unas pocas reglas termino complicarme más la existencia implementar esa capa que hacerla de forma simple las validaciones.

📋 Tema de **Logs** utilice en la clase **CustomExceptionHandler** que fue lo que más me costo tratar para dar forma las respuestas de las peticiones a la API.

🎉 **Conclusión final** personalmente aprendí muchísimo!!!. Muchas gracias por su tiempo, dedición, amabilidad son unos cracks!!!. Muy contento de poder tenerlos como profes y escuchar sus experiencias y opiniones son muy valiosas 🤓💪.

## 🚀 Importante: ¿cómo hacer las pruebas en Postman?

🏋🏽Sinceramente la complique jeje, pero al fin y al cabo me resulto útil no sé si práctico, pero útil seguro.

Paso a explicar como realizar las pruebas 🧪:
- En la raíz del proyecto deje un archivo con nombre **TurnosRotativos.postman_collection.json** allí van a encontrar dividido en carpetas las requests, por fa lean los comentarios que deje en las carpetas y en algunas request también sobre todo para probar los criterios de aceptación de jornadas. Muy resumido lo que hice cree un archivo .sql por cada prueba en donde estan las consultas insert para dejar preparado el contexto, para que puedan probar cada criterio, pero para ello cada vez que prueben van a tener que cambiar la lectura del archivo sql en el archivo **application-dev.properties** 😅.

