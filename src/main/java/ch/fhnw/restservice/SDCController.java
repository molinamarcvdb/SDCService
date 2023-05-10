package ch.fhnw.restservice;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

@RestController
public class SDCController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	private static final Logger LOG = LoggerFactory.getLogger(SDCController.class);

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@PostMapping("/experiment")
	public Boolean createExperiment(@RequestParam(value = "name") String name) {
		Boolean result = DataContainer.getInstance().createExperiment(name);
		LOG.info("create new experiment " + name + ": " + result);
		return result;
	}

	@GetMapping("/experiment")
	public Long getExperimentId(@RequestParam(value = "name") String name) {
		Long id = DataContainer.getInstance().getExperimentId(name);
		LOG.info("retrieve id for experiment " + name + ": " + id);
		return id;
	}

	@GetMapping("/data")
	public List<DataObject> getData(@RequestParam(value = "experimentid") Long experimentId) {
		List<DataObject> result = DataContainer.getInstance().getData(experimentId);
		return result;
	}

	@PostMapping("/data")
	public void storeData(@RequestParam(value = "experimentid") Long experimentId, @RequestBody DataObject dObj) {
		LOG.info("store data for experiment " + experimentId);
		LOG.info("Received data object: " + dObj.toString());
		try {
			String filePath = "file.txt"; // set the file path
			DataContainer.getInstance().storeData(experimentId, dObj);
		} catch (Exception ex) {
			LOG.error("store data for experiment " + experimentId + ": " + ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

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


