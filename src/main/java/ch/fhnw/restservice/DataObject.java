// Import libraries and packages
package ch.fhnw.restservice;

import java.util.List;

/**

 Represents a data object.
 This class is used to store information about a data object.
 */
public class DataObject {
    // Class variables
    private Long id;
    private String device;
    private Long experimentId;
    private String sensorId;
    private List<Float> data;
    private Long timestamp;

    /**
     Retrieves the experiment ID associated with the data object.
     @return The experiment ID.
     */
    public Long getExperimentId() {
        return experimentId;
    }

    /**
     Sets the experiment ID associated with the data object.
     @param experimentId The experiment ID to set.
     */
    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    /**
     Retrieves the unique identifier of the data object.
     @return The ID of the data object.
     */
    public Long getId() {
        return id;
    }

    /**
     Sets the unique identifier of the data object.
     @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     Retrieves the device associated with the data object.
     @return The device name.
     */
    public String getDevice() {
        return device;
    }

    /**
     Sets the device associated with the data object.
     @param device The device name to set.
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     Retrieves the sensor ID associated with the data object.
     @return The sensor ID.
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     Sets the sensor ID associated with the data object.
     @param sensorId The sensor ID to set.
     */
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     Retrieves the data values associated with the data object.
     @return The list of data values.
     */
    public List<Float> getData() {
        return data;
    }

    /**
     Sets the data values associated with the data object.
     @param data The list of data values to set.
     */
    public void setData(List<Float> data) {
        this.data = data;
    }

    /**
     Retrieves the timestamp of when the data object was recorded.
     @return The timestamp of the data object.
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     Sets the timestamp of when the data object was recorded.
     @param timestamp The timestamp to set.
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
