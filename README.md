Proyecto creado por Lorenzo Lopez 
SpringBoot 3.2.3
Java 17
React 18.2.0
npm 9.5.1
Node 18.16.1

Como correr el proyecto:

Metodo 1:

mvn clean install
java -jar .\target\fullstack-exercise-lorenzo-lopez-0.0.1-SNAPSHOT.jar
(O correr el archivo .jar desde el IDE)

Corriendo mvn install se empaqueta tambien el frontend dentro del ejecutable gracias al plugin frontend-maven-plugin.
Se puede acceder al proyecto desde el navegador desde el puerto 8080:
http://localhost:8080

Metodo 2 (desarrollo):
Instalar y correr el frontend en modo desarrollo
npm i
npm run dev

Ejecutar el backend desde el ide

El frontend va a correr en el puerto 5173 (vite default port)
El backend va a correr en el puerto 8080 (springboot default port)

Desde el navegador acceder desde el puerto 5173 para usar el servidor de desarrollo.
Para ver los cambios del frontend se debe refrescar la pagina
Para ver los cambios del backend se debe volver a ejecutar la aplicacion
El package.json usa la propertie "proxy" para conectarse con el backend a su puerto correspondiente.


En caso de no poder correr el proyecto contactarse con lore64210@gmail.com
