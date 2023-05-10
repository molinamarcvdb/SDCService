package ch.fhnw.restservice;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


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

        List<DataObject> dataObjects = data.get(experimentId);
        dataObjects.add(dObj);

        // save the data objects to a file
        String filename = "experiment_" + experimentId + ".txt";
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            writer.println("Experiment ID: " + experimentId);
            for (DataObject dataObject : dataObjects) {
                writer.println("Data object ID: " + dataObject.getId());
                writer.println("Device: " + dataObject.getDevice());
                writer.println("Sensor ID: " + dataObject.getSensorId());
                writer.println("Data: " + dataObject.getData());
                writer.println("Timestamp: " + dataObject.getTimestamp());
                writer.println();
            }
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
