package ch.fhnw.restservice;

import java.util.List;

public class DataObject {

    private Long id;
    private String device;
    private Long experimentId;
    private String sensorId;
    private List<Float> data;
    private Long timestamp;

    public Long getExperimentId() {
        return experimentId;
    }
    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
