package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("duration")
public class QualityCheckEvent {
    private String workOrderId;
    private long startTimestamp;
    private long duration;

    public QualityCheckEvent() {}

    public QualityCheckEvent(String workOrderId, long startTimestamp, long duration) {
        this.workOrderId = workOrderId;
        this.startTimestamp = startTimestamp;
        this.duration = duration;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "QualityCheckEvent{" +
                "workOrderId='" + workOrderId + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", duration=" + duration +
                '}';
    }
}