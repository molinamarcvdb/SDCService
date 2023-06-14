// Declare the package
package ch.fhnw.restservice;

// Define the Greeting class
public class Greeting {

	// Declare the id and content as private final variables
	private final long id;
	private final String content;

	// Define the constructor for the Greeting class
	public Greeting(long id, String content) {
		this.id = id; // Assign the id
		this.content = content; // Assign the content
	}

	// Define a getter method for the id
	public long getId() {
		return id; // Return the id
	}

	// Define a getter method for the content
	public String getContent() {
		return content; // Return the content
	}
}
