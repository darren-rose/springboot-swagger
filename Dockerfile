FROM openjdk:8u151-jdk

ADD target/mergesort-*.jar app.jar
CMD [ "java", "-jar", "/app.jar" ]

EXPOSE 8888

