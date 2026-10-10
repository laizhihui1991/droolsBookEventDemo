package com.test.drools.pojo;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Duration;

@Role(Role.Type.EVENT)
@Timestamp("eventTime")
@Duration("duration")
public class TransactionEvent {
    private String transactionId;
    private String userId;
    private String status;
    private long eventTime;
    private long duration;

    public TransactionEvent() {}

    public TransactionEvent(String transactionId, String userId, String status, long eventTime, long duration) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.status = status;
        this.eventTime = eventTime;
        this.duration = duration;
    }

    // Getter & Setter
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getEventTime() { return eventTime; }
    public void setEventTime(long eventTime) { this.eventTime = eventTime; }
    public long getDuration() { return duration; }
    public void setDuration(long duration) { this.duration = duration; }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", eventTime=" + eventTime +
                ", duration=" + duration +
                '}';
    }
}
