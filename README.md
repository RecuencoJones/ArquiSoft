# Backend

Ejecución
------

Para ejecutar el backend:

1. Generar el .war con `gradle war`
2. Desplegar el fichero .war vía tomcat u otro, en `http://localhost:8080/myusick/`
3. La aplicación es accesible desde `http://localhost:8080/myusick/`

   El index.html se abre automáticamente, con eso el frontend queda desplegado
   
4. Los endpoints son accesibles desde `http://localhost:8080/myusick/api/`

   Adicionalmente, esa URL muestra el directorio de endpoints

API
------

Directorio de servicios REST
=====

| Verb | URI | Consumes | Response-Type | Definition |
|------------|--------------|--------------|-------------|------------|
| __GET__ | / |  | text/plain | Lista el directorio de servicios |
| __POST__ | /auth | application/x-www-form-urlencoded | application/json | Autentica a un usuario |
| __POST__ | /register | application/json | application/json | Registra a un usuario |
| __GET__ | /profile/{userid} | PathParam(userid: int) | application/json | Saca los datos de perfil del usuario {userid} |
| __POST__ | /newgroup | application/json | application/json | Crea un grupo para un usuario |
| __POST__ | /newtag | application/json | application/json | Crea y/o añade un tag a un usuario |
| __POST__ | /post | application/json | application/json | Crea un post para un usuario |
| __GET__ | /post/{id} | PathParam(id: int) | application/json | Busca el comentario cuyo id es {id} |
| __PUT__ | /follow/{seguidor}/{seguido} | PathParam(seguidor: int, seguido: int) | application/json | El usuario {seguidor} sigue a {seguido} |
| __DELETE__ | /unfollow/{seguidor}/{seguido} | PathParam(seguidor: int, seguido: int) | application/json | El usuario {seguidor} deja de seguir a {seguido} |
| __GET__ | /isfollowing/{seguidor}/{seguido} | PathParam(seguidor: int, seguido: int) | application/json | Comprueba si el usuario {seguidor} sigue a {seguido} |
| __PUT__ | /band/apply/{bandid}/{userid} | PathParam(bandid: int, userid: int) | application/json | Agrega un nuevo miembro {userid} pendiente de aceptacion a la banda {bandid} |
| __DELETE__ | /band/leave/{bandid}/{userid} | PathParam(bandid: int, userid: int) | application/json | Elimina un nuevo miembro {userid} existente de la banda {bandid} |
| __PUT__ | /band/accept/{bandid}/{userid} | PathParam(bandid: int, userid: int) | application/json | Acepta un nuevo miembro {userid} en la banda {bandid} |
| __DELETE__ | /band/reject/{bandid}/{userid} | PathParam(bandid: int, userid: int) | application/json | Rechaza un nuevo miembro {userid} en la banda {bandid} |
| __GET__ | /band/applicants/{bandid} | PathParam(bandid: int) | application/json | Busca los miembros pendientes de aceptacion de la banda cuyo id es {bandid} |
| __GET__ | /groups/{userid} | PathParam(userid: int) | application/json | Busca los grupos del usuario cuyo id es {userid} |
| __GET__ | /last/{userid} | PathParam(userid: int) | application/json | Busca los ultimos mensajes de los publicantes a los cuales esta suscrito el usuario cuyo id es {userid} |
| __GET__ | /search/person/{term} | PathParam(term: string) | application/json | Busca todas las personas cuyo nombre incluya {term} |
| __GET__ | /search/group/{term} | PathParam(term: string) | application/json | Busca todos los grupos cuyo nombre incluya {term} |
| __GET__ | /search/tag/{term} | PathParam(term: string) | application/json | Busca todos los publicantes que tengan la etiqueta {term} |
| __GET__ | /search/skill/{term} | PathParam(term: string) | application/json | Busca todas las personas que tengan la aptitud {term} |
| __POST__ | /edit/profile | application/json | application/json | Edita el perfil de un publicante con los valores recibidos |

Directorio de servicios SSE
=====

| Verb | URI | Consumes | Response-Type | Definition |
|------------|--------------|--------------|-------------|------------|
| __GET__ | /ws/sub/{id} | PathParam(id: int) | text/event-stream | Suscribe a un usuario a su canal |
| __DELETE__ | /ws/unsub/{id} | PathParam(id: int) | text/event-stream | Desuscribe a un usuario de su canal |

Compatibilidad en navegadores
=====

- Chrome > 6
- Safari > 6
- Firefox > 6
- Opera > 11.5
- IE > 9 NO SSE

# Frontend

AngularJS
------
Magic doc

# Configuración de despliegue Tomcat con IntelliJ

1. Importar proyecto como __Proyecto Gradle__
2. Crear una nueva configuración de ejecución __Run &gt; Edit Configurations...__
3. Seleccionar __Tomcat Server &gt; local__
4. En __Application Server__ pulsar __Configure...__
5. Pulsar __+__ y seleccionar el __HOME__ de Tomcat (recomendable xampp) y aceptamos en ambas ventanas
6. En la ventana __Run/Debug configurations__ (la primera de todas) damos nombre a esta configuración
7. En la pestaña __deployment__ pulsamos __+ &gt; External Source...__ y seleccionamos __&lt;proyecto&gt;/build/libs/&lt;proyecto&gt;.war__
8. Una vez seleccionado en __Application context:__ escribimos __/&lt;proyecto&gt;__
9. __[OPCIONAL]__ En el diálogo __Before launch:__ podemos agregar la tarea de __gradle war__

   Esto nos evita tener que construir el .war antes de lanzar el servidor, pero igualmente, antes de redesplegar va a haber que crear el .war de nuevo, así que no tiene mucha importancia
   
10. Aplicamos, aceptamos y estamos listos para desplegar :D
