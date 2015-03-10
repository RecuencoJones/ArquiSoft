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

Directorio de endpoints

| Verb | URI | Response-Type | Definition |
|------------|--------------|-------------|------------|
| __GET__ | / | text/plain | Lista el directorio de endpoints |
| __GET__ | /hello/{name} | text/plain | Returns a list of stored values |

# Frontend

AngularJS
------
TODO doc

# Configuración de despliegue Tomcat con IntelliJ

1. Importar proyecto como __Proyecto Gradle__
2. Crear una nueva configuración de ejecución __Run &gt; Edit Configurations...__
3. Seleccionar __Tomcat Server &gt; local__
4. En __Application Server__ pulsar __Configure...__
5. Pulsar __+__ y seleccionar el home de Tomcat (recomendable xampp) y aceptamos en ambas ventanas
6. En la ventana __Run/Debug configurations__ damos nombre a esta configuración
7. En la pestaña __deployment__ pulsamos __+ &gt; External Source__ y seleccionamos __&lt;proyecto&gt;/build/libs/&lt;proyecto&gt;.war__
8. Una vez seleccionado en __Application context:__ escribimos __&lt;proyecto&gt;__
9. __[OPCIONAL]__ En el diálogo __Before launch:__ podemos agregar la tarea de __gradle war__

   Esto nos evita tener que construir el .war antes de lanzar el servidor, pero igualmente, antes de redesplegar va a haber que crear el .war de nuevo, así que no tiene mucha importancia
   
10. Aplicamos, aceptamos y estamos listos para desplegar :D