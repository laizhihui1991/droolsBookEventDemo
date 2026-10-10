package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class TemperatureEvent {

    private String deviceId;
    private double value;
    private long timestamp;

    public TemperatureEvent(String deviceId, double value, long timestamp) {
        this.deviceId = deviceId;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TemperatureEvent{" +
                "deviceId='" + deviceId + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
