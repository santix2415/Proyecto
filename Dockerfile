FROM openjdk:17
COPY "./target/Reservas-proyectos-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8044
ENTRYPOINT [ "java", "-jar", "app.jar" ]
