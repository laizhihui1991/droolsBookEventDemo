package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Duration;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("duration")
public class LoadingEvent {
    private String shipmentId;
    private long startTimestamp;
    private long duration;

    public LoadingEvent(String shipmentId, long startTimestamp, long duration) {
        this.shipmentId = shipmentId;
        this.startTimestamp = startTimestamp;
        this.duration = duration;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "LoadingEvent{" +
                "shipmentId='" + shipmentId + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", duration=" + duration +
                '}';
    }
}
