#-------------- [PESOS DE IMAGENES] -----------#
#  maven:3-jdk-8                        500MB 
#  maven:3-jdk-8-alpine                 122MB 
#  openjdk:8                            488MB
#  openjdk:8-slim                       284MB
#  openjdk:8-alpine                     105MB
#  openjdk:8-jdk-slim                   284MB 
#  openjdk:8-jdk-alpine                 105MB 
#  adoptopenjdk/openjdk8:alpine-slim    90.2MB
#-------------- [PESOS DE IMAGENES] -----------#

#//----------------------------------------------------------------//#
#//------------------------  [COMPILACION] ------------------------//#
#//----------------------------------------------------------------//#
#1. PARTIR DE LA 'IMAGEN BASE' (CONSTRUCTOR): 
FROM maven:3-jdk-8-alpine as CONSTRUCTOR 

#2. CREAR DIRECTORIO 'build' (DENTRO DEL CONTENEDOR):   
RUN mkdir -p /build

#3. CREAR DIRECTORIO (DENTRO DEL CONTENEDOR):  
WORKDIR /build

#4. COPIAR 'pom.xml' A DIRECTORIO 'build' (DENTRO DEL CONTENEDOR):  
COPY pom.xml /build

#5. DESCARGAR DEPENDENCIAS 'MAVEN' (DENTRO DEL CONTENEDOR): 
RUN mvn -B dependency:resolve dependency:resolve-plugins

#6. COPIAR 'src' A DIRECTORIO '/build/src' (DENTRO DEL CONTENEDOR):  
COPY src /build/src

#7. EJECUTAR 'MAVEN' (DENTRO DEL CONTENEDOR):  
RUN mvn clean package


#//----------------------------------------------------------------//#
#//-------------------------  [EJECUCION] -------------------------//#
#//----------------------------------------------------------------//#
#8. PARTIR DE LA 'IMAGEN BASE' (RUNTIME): 
FROM adoptopenjdk/openjdk8:alpine-slim as RUNTIME

#9. DOCUMENTAR: 
MAINTAINER cesar guerra cesarricardo_guerra19@hotmail.com

#10. EXPONER PUERTO '8080': 
EXPOSE 8080

#11. CREAR 'VARIABLE DE ENTORNO' 'APP_HOME': 
ENV APP_HOME /app

#12. CREAR 'VARIABLE DE ENTORNO' 'JAVA_OPTS':  
ENV JAVA_OPTS=""

#13. CREAR DIRECTORIO 'BASE': 
RUN mkdir $APP_HOME

#14. CREANDO DIRECTORIO PARA 'ARCHIVOS DE CONFIGURACION': 
RUN mkdir $APP_HOME/config

#15. CREAR DIRECTORIO PARA 'LOGs': 
RUN mkdir $APP_HOME/log

#16. CREAR 'VOLUME' PARA 'ARCHIVOS DE CONFIGURACION': 
VOLUME $APP_HOME/config

#17. CREAR 'VOLUME' PARA 'LOGs':  
VOLUME $APP_HOME/log

#18. COPIAR 'COMPILADO' (DENTRO DEL CONTENEDOR):   
COPY --from=CONSTRUCTOR /build/target/*.jar app.jar

#19. INSTALANDO 'SUDO, NANO, CURL, SIEGE':
RUN apk add -u sudo 
RUN apk add -u nano
RUN apk add -u curl
 
#20. INICIAR EL 'MICROSERVICIO': 
ENTRYPOINT [ "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
