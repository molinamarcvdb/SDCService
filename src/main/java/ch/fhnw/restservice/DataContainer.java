package ch.fhnw.restservice;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.io.PrintWriter;
import java.io.File;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataContainer {

	private static final Logger LOG = LoggerFactory.getLogger(DataContainer.class);
	
    private static DataContainer instance;
    private final AtomicLong idgen = new AtomicLong();

    private Map<String, Long> experiments = new HashMap<>();
    private Map<Long, List<DataObject>> data = new HashMap();

    public static DataContainer getInstance() {
        if (instance == null) {
            instance = new DataContainer();
        }
        return instance;
    }

    private DataContainer() {
        // singleton
    }

    public Boolean createExperiment(String name) {
        Long identifier = experiments.get(name);
        if (identifier == null) {
            identifier = idgen.incrementAndGet();
            experiments.put(name, identifier);
            List<DataObject> dataContainer = new ArrayList<>();
            data.put(identifier, dataContainer);
            return true;
        } else {
        	LOG.warn("Experiment " + name + " already exist!");
        }
        return false;
    }

    public Long getExperimentId(String name) {
        return experiments.get(name);
    }

    public String getExperimentName(Long experimentId) {
        for (Map.Entry<String, Long> entry : experiments.entrySet()) {
            if (entry.getValue().equals(experimentId)) {
                return entry.getKey();
            }
        }
        return null; // Return null if no experiment with the given ID is found
    }


    public void storeData(Long experimentId, DataObject dObj) throws Exception {
        // check if data object is not null
        if (dObj == null) {
            throw new Exception("provided data object is null");
        }

        // check if experiment exists
        if (!experiments.values().contains(experimentId)) {
            throw new Exception("experiment id " + experimentId + " does not exist!");
        }

        // check if experiment id matches the information in the data
        if (!experimentId.equals(dObj.getExperimentId())) {
            throw new Exception(("experiment id " + experimentId + " does not match with data experiment id " + dObj.getExperimentId()));
        }

        // Get the experiment name
        String experimentName = getExperimentName(experimentId);
        if (experimentName == null) {
            throw new Exception("No experiment found with id " + experimentId);
        }

        // Create a directory for the experiment if it doesn't exist
        File DatabaseDirectory = new File("Database");
        if (!DatabaseDirectory.exists()) {
            DatabaseDirectory.mkdir();
        }

        // Create a directory for the experiment if it doesn't exist
        File experimentDirectory = new File("Database/" + experimentName + "_" + experimentId);
        if (!experimentDirectory.exists()) {
            experimentDirectory.mkdir();
        }

        // Create a directory for the sensor type if it doesn't exist
        String sensorId = dObj.getSensorId();
        File sensorDirectory = new File(experimentDirectory, sensorId);
        if (!sensorDirectory.exists()) {
            sensorDirectory.mkdir();
        }

        // save the data object to a file
        String filename = sensorDirectory.getPath() + "/dataobject_" + dObj.getId() + ".txt";
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            writer.println("Experiment ID: " + experimentId);
            writer.println("Data object ID: " + dObj.getId());
            writer.println("Data: " + dObj.getData());
            writer.println("Timestamp: " + dObj.getTimestamp());
            writer.println();
            System.out.println("File created: " + filename);
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }





    public List<DataObject> getData(Long experimentId) {
        return data.get(experimentId);
    }

    public boolean deleteExperiment(Long experimentId) {
        if (experimentId == null) {
            return false;
        }

        // Find the experiment key by its ID
        String experimentKey = null;
        for (Map.Entry<String, Long> entry : experiments.entrySet()) {
            if (entry.getValue().equals(experimentId)) {
                experimentKey = entry.getKey();
                break;
            }
        }

        // If the experiment exists, remove it from both the experiments and data maps and return true
        if (experimentKey != null) {
            experiments.remove(experimentKey);
            data.remove(experimentId);
            LOG.info("Experiment with id " + experimentId + " deleted");
            return true;
        } else {
            LOG.warn("Experiment with id " + experimentId + " not found");
            return false;
        }
    }
}
