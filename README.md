# Mergesort web service #

### What is this repository for? ###

* This repository provides a web service that implements the mergesort algorithm.

### How do I get set up? ###

### Run via Maven

* `mvn clean test`
* `mvn clean spring-boot:run`

#### Run via Docker
* `mvn clean package`
* `./docker-build.sh`
* `./docker-run.sh`

### Rest API

Visit the following to view the api documentation:

`http://localhost:8888/swagger-ui.html`

### Testing

* `curl -v -XPOST -H 'Content-type: application/json' http://localhost:8888/mergesort/  -d [3,5,1,83,51,99]`
* `curl -v http://localhost:8888/mergesort/executions`
* `curl -v http://localhost:8888/mergesort/executions/1`

### Who do I talk to? ###

* Darren Rose
* darrenwrose@gmail.com
