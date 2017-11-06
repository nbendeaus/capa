# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured docker services

### Service registry and configuration server:
- [JHipster Registry](http://localhost:8761)

### Applications and dependencies:
- capaApp (gateway application)
- capaApp's mysql database
- capaApp's elasticsearch search engine
- employeeService (microservice application)
- employeeService's mysql database
- employeeService's elasticsearch search engine
- outlookService (microservice application)
- outlookService's mysql database
- outlookService's elasticsearch search engine

### Additional Services:

- [JHipster Console](http://localhost:5601)
Once started, it will be necessary to create the index pattern using the UI and start the `jhipster-import-dashboards` container again with: `docker-compose up jhipster-import-dashboards`
- [Zipkin](http://localhost:9400)
