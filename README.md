# Plasmonics Backend

Repository to handle plasmonics inventory system data.

## Table of Contents

1. [Quick start guide](#quick-start-guide-for-local-development)
2. [Functionality](#functionality) 
3. [Swagger UI](#swagger-ui)
4. [Points of configuration](#points-of-configuration)
5. [Security](#security)
6. [Static Analysis](#static-analysis)
7. [Future considerations](#future-considerations) 


## Quick start guide for local development

### Start fake SMTP server

Please refer [Fake SMTP](https://github.com/gessnerfl/fake-smtp-server)


### Changes to VM options

While running from Intellij as an application, Add below line as VM option

    -Dspring.profiles.active=local

The local profile will insert 3 roles and 3 users on startup and can be used for creating data.
#### Roles are ROLE_ADMIN, ROLE_MANAGER and ROLE_VIEWER.

#### Username/passwords are ADMIN/ADMIN , MANAGER/MANAGER and VIEWER/VIEWER

These can be used to create auth. tokens and create data.

## Functionality
1. Create/Update/Delete items which can be maintained in the inventory.
2. Create item orders for an item to manage the quantity.
3. Create notification if quantity of an item goes below threshold.


## Swagger UI

1. OpenApi specifications have been added and can be accessed from `/swagger-ui/`

## Points of configuration

1. Recommended IDE is Intellij
2. Project has some configurations that are meant for local development and are marked with annotation

        @Profile("local")
3. The project already has an application-local.properties file which will be used for local development.



##  Security

1. Spring security has been added using JJWT
2. Project has a dummy key property called plasmonics.backend.jwtSecret in application-local.properties
3. This key should be an environment variable in production systems
4. Pre-authorizations are yet to be defined.


##  Static Analysis

1. PMD static analysis has been added with standard best practises configuration.
2. If the static analysis fails , build will fail and report will be generated.

## Future Considerations

1. Adding Inventory warehouse object as a wrapper to Item object.
2. Improving code quality by using Java 11 apis.
3. Refactoring code.
4. Test cases for different scenarios and unit tests.