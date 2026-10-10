package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Duration;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("duration")
public class RefundEvent {
    private String orderId;
    private long startTimestamp;
    private long duration;

    public RefundEvent() {}

    public RefundEvent(String orderId, long startTimestamp, long duration) {
        this.orderId = orderId;
        this.startTimestamp = startTimestamp;
        this.duration = duration;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public long getStartTimestamp() { return startTimestamp; }
    public void setStartTimestamp(long startTimestamp) { this.startTimestamp = startTimestamp; }

    public long getDuration() { return duration; }
    public void setDuration(long duration) { this.duration = duration; }

    @Override
    public String toString() {
        return "RefundEvent{" +
                "orderId='" + orderId + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", duration=" + duration +
                '}';
    }
}
