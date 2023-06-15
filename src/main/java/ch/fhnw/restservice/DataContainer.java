// Import libraries and packages
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

/**

 A container class for storing experimental data.
 */
public class DataContainer {
    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(DataContainer.class);

    // Singleton instance of DataContainer
    private static DataContainer instance;

    // AtomicLong for generating unique IDs
    private final AtomicLong idgen = new AtomicLong();

    // Map to store experiment names and their corresponding IDs
    private Map<String, Long> experiments = new HashMap<>();

    // Map to store experiment IDs and their associated data objects
    private Map<Long, List<DataObject>> data = new HashMap<>();

    /**
     Retrieves the single instance of DataContainer.
     @return The single instance of DataContainer.
     */
    public static DataContainer getInstance() {
        if (instance == null) {
            instance = new DataContainer();
        }
        return instance;
    }

    /**
     Private constructor to enforce the single pattern.
     */
    private DataContainer() {
    }

    /**

     Creates a new experiment with the specified name.

     @param name The name of the experiment.

     @return {@code true} if the experiment is created successfully, {@code false} otherwise.
     */
    public Boolean createExperiment(String name) {
        // Check if the experiment already exists
        Long identifier = experiments.get(name);
        if (identifier == null) {
            // Generate a new ID for the experiment
            identifier = idgen.incrementAndGet();

            // Add the experiment to the experiments map
            experiments.put(name, identifier);

            // Create an empty list for storing data objects
            List<DataObject> dataContainer = new ArrayList<>();

            // Add the data container to the data map
            data.put(identifier, dataContainer);

            return true;
        } else {
            LOG.warn("Experiment " + name + " already exists!");
        }
        return false;
    }

    /**

     Retrieves the experiment ID given the experiment name.
     @param name The name of the experiment.
     @return The ID of the experiment, or {@code null} if not found.
     */
    public Long getExperimentId(String name) {
        return experiments.get(name);
    }

    /**

     Retrieves the experiment name given the experiment ID.
     @param experimentId The ID of the experiment.
     @return The name of the experiment, or {@code null} if not found.
     */
    public String getExperimentName(Long experimentId) {
        for (Map.Entry<String, Long> entry : experiments.entrySet()) {
            if (entry.getValue().equals(experimentId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**

     Stores the data object for a specific experiment.
     @param experimentId The ID of the experiment.
     @param dObj The data object to store.
     @throws Exception If the provided data object is null, the experiment ID does not exist,
     or the experiment ID does not match with the data object's experiment ID.

     */
    public void storeData(Long experimentId, DataObject dObj) throws Exception {
        // Check if the data object is null
        if (dObj == null) {
            throw new Exception("Provided data object is null");
        }

        // Check if the experiment ID exists
        if (!experiments.values().contains(experimentId)) {
            throw new Exception("Experiment ID " + experimentId + " does not exist!");
        }

        // Check if the experiment ID matches the information in the data object
        if (!experimentId.equals(dObj.getExperimentId())) {
            throw new Exception(
                    "Experiment ID " + experimentId + " does not match with data experiment ID " + dObj.getExperimentId()
            );
        }

        // Get the experiment name
        String experimentName = getExperimentName(experimentId);
        if (experimentName == null) {
            throw new Exception("No experiment found with ID " + experimentId);
        }

        // Create a directory for the experiment if it doesn't exist
        File databaseDirectory = new File("Database");
        if (!databaseDirectory.exists()) {
            databaseDirectory.mkdir();
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

        // Save the data object to a file
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

    /**
     * Retrieves the data objects for a specific experiment.
     *
     * @param experimentId The ID of the experiment.
     * @return The list of data objects associated with the experiment.
     */
    public List<DataObject> getData(Long experimentId) {
        return data.get(experimentId);
    }

    /**
     * Deletes an experiment given its ID.
     *
     * @param experimentId The ID of the experiment to delete.
     * @return {@code true} if the experiment is deleted successfully, {@code false} otherwise.
     */
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
            LOG.info("Experiment with ID " + experimentId + " deleted");
            return true;
        } else {
            LOG.warn("Experiment with ID " + experimentId + " not found");
            return false;
        }
    }
}
