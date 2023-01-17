# People Control API

API to create and control people

## Technologies

- Spring
- Java 11

## Getting Started

### Requirements

- Java 11

### Running

- Go to src/main/java/com/peoplemanagementapi/ and run "Main.java"

## APIs
### PERSON
- **POST** /v1/person
    - Creates a new person
    - Expects a request body such as {"name":"name", "birthDate":"2000-01-01", "addresses":[]}
- **PUT** /v1/person
    - Creates a new person
    - Expects a request body such as {"name":"name", "birthDate":"2000-01-01", "addresses":[]}
- **GET** /v1/person
    - Returns a list with all person. An empty list is returned if no people are found
    - Can receive query parameters such as page size and page number
- **GET** /v1/person/{id}
    - Returns a json with the person information
    - id variable expects an integer
### ADDRESS
- **POST** /v1/person/{personId}/address
    - Creates a new address
    - Expects a request body such as {"cityName": "Testpolis", "stateName": "UF", "streetName": "Test Avenue", "streetNumber": "1234", "zipCode": "12345"}
- **GET** /v1/person/{personId}/address
    - Returns a list with all addresses. An empty list is returned if no persons are found
- **PUT** /v1/person/{personId}/address/{addressId}
    - Set the informed address as main address for person