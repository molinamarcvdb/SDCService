// Import necessary libraries
package ch.fhnw.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**

 The main class for the REST service application.
 This class is responsible for starting the Spring Boot application.
 */

@SpringBootApplication
public class RestServiceApplication {
    /**

     The main method of the application.
     This method serves as the entry point of the application.
     @param args The command line arguments.
     */
    public static void main(String[] args) {
        // Run the Spring Boot application
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
