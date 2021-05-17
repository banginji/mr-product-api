# mr-product-api
My Retail Product API

### What this application is about
This application is a poc of a products API which aggregates data (price and title) from multiple sources and returns it to the invoker in JSON format

### Tech Stack
- JDK v16
- Java
- GraphQL
- Spring Boot framework
- MongoDB

### Completeness
#### Are problems addressed?
The problem, which is to provide aggregated data to clients, has been addressed by providing an API for the clients to invoke and obtain the data.
They also have the capability to update the price of the item

#### Is it production ready?
The application can be deployed and scaled horizontally as required. The data store used should be decided though because each data store is
tailored for a particular use case and the right one has to be chosen based on a consensus

### Work regarding scaling the code
The application can be containerized and deployed as containers in a cluster which can either grow
in size or run lesser number of containers based on the traffic to the application. For this an appropriate data store has to be
chosen as well to handle the load while keeping in mind the capacity of the redsky servers

### Testing
Unit and integration tests have been written to make sure that the functionality is maintained in the code

### Instructions for running the service
Requirements to run the code
- JDK v16 (not required locally if docker is used)
- Docker

Two ways to run the service:
- The project can be run using the command. The requirement for this would be running mongodb locally
>./gradlew bootRun

- The project can also be run on docker containers (service and mongodb) locally using the command
>./gradlew clean build && docker-compose up

## Request Response examples
### Get Request
##### Example Request
>curl -v http://localhost:8080/product/1
##### Example Response for product with title data in redsky and price in local no-sql data store available
```
{
  "id": 13860428,
  "title": "The Big Lebowski (Blu-ray)",
  "price": {
    "value": 2342.23,
    "currencyCode": "USD"
  }
}
```
##### Example Response for product with no available title data in redsky and available price in local no-sql data store
200
```
{
  "id": 123,
  "title": null,
  "price": {
    "value": 14.23,
    "currencyCode": "USD"
  }
}
```
##### Example Response for product with no available title data in redsky and no available price in local no-sql data store
200
```
{
  "id": 1,
  "title": null,
  "price": null
}
```

### Update Request
##### Request
>curl -v -XPUT http://localhost:8080/product/13860428 -d '{"id": 13860428, "value": 2342.23}' -H "Content-type: application/json"
##### Response:
204

### GraphQL
Optional graphql endpoint to get price and title data is also available. It has been implemented by keeping in mind that the responsibility to specify the fields the invoker needs is left to the invoker and hence the implementation is structured in such a way that expensive operations like network calls are not made if fields that require the network calls are not asked for by the invoker 
##### Request
>curl -v http://localhost:8080/graphql -d '{"query": "query { getProductInfo(id: 13860427) { title price { value currencyCode } } }" }' -H "Content-type: application/json"
##### Response
200
```
{
  "data": {
    "getProductInfo": {
      "title": "Conan the Barbarian (DVD)",
      "price": null
    }
  }
}
```