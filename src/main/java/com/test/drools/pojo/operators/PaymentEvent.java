package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class PaymentEvent {
    private String orderId;
    private long timestamp;
    public PaymentEvent() {}
    public PaymentEvent(String orderId, long timestamp) {
        this.orderId = orderId;
        this.timestamp = timestamp;
    }
    public String getOrderId() {
        return orderId;
    }
    public long getTimestamp() {
        return timestamp;
    }
    @Override
    public String toString() {
        return "PaymentEvent{orderId='" + orderId + "', timestamp=" + timestamp + "}";
    }
}