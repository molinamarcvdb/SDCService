// Import necessary libraries
package ch.fhnw.restservice;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**

 The controller class for the SDC (Sensor Data Collector) REST service.
 This class handles various endpoints for interacting with experiments and data.
 */
@RestController
public class SDCController {

	/**

	 The template for greeting messages.
	 */
	private static final String template = "Hello, %s!";

	/**

	 The counter for the number of greetings.
	 */
	private final AtomicLong counter = new AtomicLong();

	/**

	 The logger for this class.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SDCController.class);

	/**

	 Retrieves a greeting message.
	 @param name The name to include in the greeting message.
	 @return A greeting object.
	 */
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	/**

	 Creates a new experiment.
	 @param name The name of the experiment.
	 @return The result of the creation operation.
	 */
	@PostMapping("/experiment")
	public Boolean createExperiment(@RequestParam(value = "name") String name) {
		Boolean result = DataContainer.getInstance().createExperiment(name);
		LOG.info("create new experiment " + name + ": " + result);
		return result;
	}

	/**

	 Retrieves the ID of an experiment.
	 @param name The name of the experiment.
	 @return The ID of the experiment.
	 */
	@GetMapping("/experiment")
	public Long getExperimentId(@RequestParam(value = "name") String name) {
		Long id = DataContainer.getInstance().getExperimentId(name);
		LOG.info("retrieve id for experiment " + name + ": " + id);
		return id;
	}

	/**

	 Retrieves data of an experiment.
	 @param experimentId The ID of the experiment.
	 @return A list of data objects.
	 */
	@GetMapping("/data")
	public List<DataObject> getData(@RequestParam(value = "experimentid") Long experimentId) {
		List<DataObject> result = DataContainer.getInstance().getData(experimentId);
		return result;
	}

	/**

	 Stores data for an experiment.
	 @param experimentId The ID of the experiment.
	 @param dObj The data object to store.
	 */
	@PostMapping("/data")
	public void storeData(@RequestParam(value = "experimentid") Long experimentId, @RequestBody DataObject dObj) {
		LOG.info("store data for experiment " + experimentId);
		LOG.info("Received data object: " + dObj.toString());
		try {
			DataContainer.getInstance().storeData(experimentId, dObj);
		} catch (Exception ex) {
			LOG.error("store data for experiment " + experimentId + ": " + ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

	/**

	 Deletes an experiment.
	 @param experimentId The ID of the experiment to delete.
	 */
	@PostMapping("/delete")
	public void deleteExperiment(@RequestParam(value = "experimentid") Long experimentId) {
		LOG.info("delete experiment " + experimentId);
		try {
			boolean deleted = DataContainer.getInstance().deleteExperiment(experimentId);
			if (!deleted) {
				throw new RuntimeException("Experiment not found");
			}
		} catch (Exception ex) {
			LOG.error("delete experiment " + experimentId + ": " + ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}
}

