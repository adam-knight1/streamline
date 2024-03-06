# Welcome to Streamline!

Streamline is a task management application designed to help users keep track of their to-do lists and tasks. 
It features a robust backend built with Spring Boot and a responsive frontend.
In its current iteration, Streamline is making heavy use of AWS Lambda-Driven APIs to test the benefits of stateless functions.

## Features

- User authentication and session management with Spring Security
- Create and delete tasks.
- Organize tasks by completion status.
- Responsive web interface for desktop and mobile users.

## Prerequisites

To run this project, you will need:
- Docker installed on your machine.
- Java JDK 11 or higher.
- Node.js 14 or higher.

## Installation and Run

1. Clone the repository: https://github.com/adam-knight1/streamline.git
2. Run the backend with ./gradlew bootRun
3. Deploy the docker container by running ./local-dynamodb.sh
4. Run ./deployDev.sh -- This script will set up the development environment, including the necessary databases and backend services.
5. Enter 'cd frontend' in the project directory, and then 'npm start'
6. Once the application is running, you can access it at http://localhost:8080/

## Testing

- Testing of various aspects of the application, when fully implemented, can be accomplished by navigating to
test folders for each module (service, lambda, integration) and pressing the 'run' triangle at the top
- Individual tests can be run in the same manner.

# Future Direction
- Please note that this project is continually being updated on a daily basis.  Please check GitHub for the latest
version.


