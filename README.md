# Experiment Data Management Service

This is a Java-based RESTful API service for managing experiments and their data. The service is built using Spring Boot and is designed to collect data from an Android app.

## Features

- Create and delete experiments
- Retrieve an experiment's ID
- Get or store data for an experiment

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 8 or higher
- Maven

### Installing

1. Clone the repository
    ```bash
    git clone https://github.com/yourusername/experiment-data-management-service.git
    ```
2. Navigate to the project directory
    ```bash
    cd experiment-data-management-service
    ```
3. Build the project
    ```bash
    mvn clean install
    ```
4. Run the application
    ```bash
    java -jar target/experiment-data-management-service-0.0.1-SNAPSHOT.jar
    ```

## API Endpoints

- `GET /greeting`: Returns a greeting message
- `POST /experiment`: Creates a new experiment
- `GET /experiment`: Retrieves an experiment's ID
- `GET /data`: Retrieves data for an experiment
- `POST /data`: Stores data for an experiment
- `POST /delete`: Deletes an experiment

## Contributing

Please read [CONTRIBUTING.md](https://github.com/yourusername/experiment-data-management-service/blob/main/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/yourusername/experiment-data-management-service/blob/main/LICENSE.md) file for details
