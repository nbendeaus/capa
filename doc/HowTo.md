# Capa Application 

Capa is a tool for managing and handling capacity of your team within your Project.


## How to run the application? 

### DEVELOPMENT 

__WINDOWS__


Registry: 

- Navigate into the registry directory 
- Start the registry with the `mvnw` command 
- The registry is reachable under: _http://localhost:8761_

Gateway: 

- Navigate into the gateway directory
- Start the gateway with the `mvnw` command 
- The gateway is reachable under: _http://localhost:8080_

Services: 

- Navigate into the service directory 
- Start the service with the `mvnw` command 


__LINUX__

* Open a new Docker Console with the __Docker Quickstart Terminal__ and wait until Docker get an IP address. 
* Navigate into the the gateway directory and type: 
`docker-compose -f /src/main/docker/consul.yml up`  



# Generate Entity for Microservice 

* Model the Datamodel with __JDL Studio__ (https://jhipster.github.io/jdl-studio/) from Jhipster or over the command line with `yo jhipster:entity <nameOfEnity>` 
    * For more information about JDL Studio please refer to https://jhipster.github.io/jdl/
    
* Process 
    1. generate or create entties in microservice application 
    2. navigate to gateway root directory and create the same entities with the command line 
    

Note: do not foget the `microservice * with <nameOfService>` line in the .jh File! Otherwise you are not able to generate a entity for the related entity! 

