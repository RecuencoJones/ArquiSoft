# Backend

Ejecución
------

Para ejecutar el backend:

1. Generar el .war con `gradle war`
2. Desplegar el fichero .war vía tomcat u otro, en `http://localhost:8080/myusick/`
3. La aplicación es accesible desde `http://localhost:8080/myusick/`

   El index.html se abre automáticamente, con eso el frontend queda desplegado
   
4. Los endpoints son accesibles desde `http://localhost:8080/myusick/api/`

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
