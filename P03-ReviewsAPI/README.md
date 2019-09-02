# Reviews API 
Supports operations for writing reviews and listing reviews for a product but with no sorting or filtering.

### Prerequisites
MySQL needs to be installed and configured. Instructions provided separately.

### Run the Code

* Login to mysql command line
```
$ mysql -u <user> -p
```

* Create 'reviews' database
```
mysql> create database reviews;
```

* Replace username and password with your own mysql username and password in the `src/main/resources/application.properties`
```
spring.datasource.username=<db username goes here>
spring.datasource.password=<db password goes here>
```

* Do maven clean and package code
```
$ mvn clean package
```

* Run the jar
```
$ java -jar target/reviews-0.0.1-SNAPSHOT.jar
```

### Operations
Swagger UI: http://localhost:8080/swagger-ui.html

#### Create a Product
`POST` `/products/`
```json
{
    "productName": "Apple MacBook"
}
```

#### Retrieve a Product by productId
`GET` `/products/{productId}`

#### Retrieve ALL Product
`GET` `/products/`

#### Create a Review for a product
`POST` `/reviews/products/{productId}`
```json
{
    "reviewStar": 5,
    "reviewText": "Great Product",
    "reviewUser": "Coder123"
}
```

#### Retrieve a Review by productId
`GET` `/reviews/products/{productId}`

#### Create a Comment for a review
`POST` `/comments/reviews/{reviewId}`
```json
{
    "commentText": "Expensive but good for coding.",
    "commentUser": "Coder123"
}
```

#### Retrieve a Comment by reviewId
`GET` `/comments/reviews/{reviewId}`

### Reference Documentation
Please refer to ReviewsAPI template for original README context:

* [ReviewsAPI Project Template](https://github.com/udacity/JDND/tree/course3_midterm_starter/P03-ReviewsAPI)