// Import libraries and packages
package ch.fhnw.restservice;

import java.util.List;

// Define public class to use for each data object
public class DataObject {
    // Class variables
    private Long id;
    private String device;
    private Long experimentId;
    private String sensorId;
    private List<Float> data;
    private Long timestamp;

    // Getter and Setter methods for experimentId
    public Long getExperimentId() {
        return experimentId;
    }
    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    // Getter and Setter methods for id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter methods for device
    public String getDevice() {
        return device;
    }
    public void setDevice(String device) {
        this.device = device;
    }

    // Getter and Setter methods for sensorId
    public String getSensorId() {
        return sensorId;
    }
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    // Getter and Setter methods for data
    public List<Float> getData() {
        return data;
    }
    public void setData(List<Float> data) {
        this.data = data;
    }

    // Getter and Setter methods for timestamp
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
