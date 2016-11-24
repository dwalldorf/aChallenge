# audibene challenge

## Description
This application is written in Java8, using the spring-boot-parent package.

javax.inject is used to benefit from spring-boot's autowiring features while staying compliant to javax standards.

#### API
spring-boot-starter-web package is used to provide a RESTful HTTP api.

#### Persistence
`hibernate` and `spring-boot-starter-data-jpa` are used. Data is stored in a file based h2db.

#### Trade-offs and simplifications
Models of persisted entities contain only the minimum set of properties to make the application runnable and meaningful to use.

The application has no concept of an audiologist. Having multiple appointments at the same time (with multiple audiologist)
would technically be possible but lead to confusion. The model would have to be updated.

Routes like `/appointments/next_week` break the rules of RESTful APIs but very much simplify the usage in this case.

The API communicates with database models to the outside world. A production-ready application would probably separate
internal and external data representations to hide private or unnecessary data or provide additional information.

There is only one unit- and one API integration-test to demonstrate testing approach.

## Use it
Run the application via `mvn spring-boot:run` from the project root.
You can access the API at [localhost:8080](http://localhost:8080).

#### Customer interfaces
* `POST /customers` - create a new customer
* `GET /customers` - retrieve all customers
* `GET /customers/{id}` - retrieve a specific customer

**Example to create a customer:**

    POST /customers
    Content-Type: application/json
    Accept: application/json
    Body:

    {
        "firstName": "Max",
        "lastName": "Mustermann"
    }

#### Appointment interface
* `POST /appointments/{customerId}`- create a new appointment
* `GET /appointments` - get all appointments
* `GET /appointments/{customerId}/next` - get next appointment of customer
* `GET /appointments/{customerId}/last` - get last appointment of customer
* `GET /appointments/next_week` - get next weeks appointments

**Example to create an appointment:**

Customer with `{customerId}` must exist.

    POST /appointments/{customerId}
    Content-Type: application/json
    Accept: application/json
    Body:

    {
        "date": "2016-11-29T13:30:00"
    }

## Test it
Execute test by running `mvn verify`.