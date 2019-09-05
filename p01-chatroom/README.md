# Chat Room

Chat room application implemented using WebSocket.

WebSocket is a communication protocol that makes it possible to establish a two-way communication channel between a
server and a client.

Message model is the message payload that will be exchanged between the client and the server. The Message
class covers:
1. ENTER
2. CHAT
3. LEAVE

## Table of Contents

1. [Implementation](#implementation)
1. [Contributing](#contributing)
1. [License](#license)


## Implementation

### Dependencies
The project requires the use of Maven, Spring Boot, and Java v8.

### Installation

### ChatroomTest Test Class
Before packaging and running the application:
* Replace the value of 'driverPath' variable in the ChatroomTest.java setup() with the correct path of you chromedriver.

### Run the application with command (Option 1)
```
mvn build; mvn spring-boot:run
```

### Run the application (Option 2)
* Do maven clean and package code
```
$ mvn clean package
```
* Run the jar
```
$ java -jar target/chatroom-0.0.1-SNAPSHOT.jar
```

### Usage
* Open http://localhost:8080/ in two or more separate windows
* Enter the chat room using two or more different username
* Notice 'Online Users' update to how many users are currently in the chat room
* Send a message and watch the other window receive the message
* Leave the chat room using the logout button

**[Back to top](#table-of-contents)**

## Contributing

Open an issue first to discuss potential changes/additions.

**[Back to top](#table-of-contents)**

## License

**[Back to top](#table-of-contents)**