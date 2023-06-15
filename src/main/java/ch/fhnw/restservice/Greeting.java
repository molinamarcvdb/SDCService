// Declare the package
package ch.fhnw.restservice;

/**

 Represents a greeting.

 This class is used to create greeting objects with an ID and content.
 */
public class Greeting {

	/**

	 The ID of the greeting.
	 */
	private final long id;

	/**

	 The content of the greeting.
	 */
	private final String content;

	/**

	 Constructs a greeting object with the specified ID and content.
	 @param id The ID of the greeting.
	 @param content The content of the greeting.
	 */
	public Greeting(long id, String content) {
		this.id = id; // Assign the id
		this.content = content; // Assign the content
	}

	/**

	 Retrieves the ID of the greeting.
	 @return The ID of the greeting.
	 */
	public long getId() {
		return id; // Return the id
	}

	/**

	 Retrieves the content of the greeting.
	 @return The content of the greeting.
	 */
	public String getContent() {
		return content; // Return the content
	}
}
