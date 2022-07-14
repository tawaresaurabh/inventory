# Plasmonics Backend

Repository to handle plasmonics inventory system data.

## Table of Contents

1. [Quick start guide](#quick-start-guide-for-local-development)
2. [Functionality](#functionality) 
3. [Swagger UI](#swagger-ui)
4. [Points of configuration](#points-of-configuration)
5. [Future considerations](#future-considerations) 


## Quick start guide for local development

### Start fake SMTP server

Please refer [Fake SMTP](https://github.com/gessnerfl/fake-smtp-server)


### Changes to VM options

While running from Intellij as an application, Add below line as VM option

    -Dspring.profiles.active=local



## Functionality
1. Create/Update/Delete items which can be maintained in the inventory.
2. Create item orders for an item to manage the quantity.
3. Create notification if quantity of an item goes below threshold.


## Swagger UI

Swagger UI can be accessed from `/swagger-ui/`

## Points of configuration

1. Recommended IDE is Intellij
2. Project has some configurations that are meant for local development and are marked with annotation
        

        @Profile("local")
   


3. The project already has an application-local.properties file which will be used for local development.

## Future Considerations

1. Adding spring security with user roles and permissions.
2. Adding JWT for authentication and authorization.
3. Adding Inventory warehouse object as a wrapper to Item object.