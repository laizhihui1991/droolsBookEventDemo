package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Duration;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("duration")
public class CustomerSupportEvent {
    private String orderId;
    private long startTimestamp;
    private long duration;

    public CustomerSupportEvent(String orderId, long startTimestamp, long duration) {
        this.orderId = orderId;
        this.startTimestamp = startTimestamp;
        this.duration = duration;
    }

    public String getOrderId() { return orderId; }
    public long getStartTimestamp() { return startTimestamp; }
    public long getDuration() { return duration; }

    @Override
    public String toString() {
        return "CustomerSupportEvent{" +
                "orderId='" + orderId + '\'' +
                ", start=" + startTimestamp +
                ", duration=" + duration +
                '}';
    }
}
