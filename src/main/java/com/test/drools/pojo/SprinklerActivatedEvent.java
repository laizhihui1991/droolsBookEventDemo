package com.test.drools.pojo;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class SprinklerActivatedEvent {
    private String location;
    private long timestamp;

    public SprinklerActivatedEvent() {}
    public SprinklerActivatedEvent(String location, long timestamp) {
        this.location = location;
        this.timestamp = timestamp;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "SprinklerActivatedEvent{" +
                "location='" + location + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
