package com.test.drools.pojo.operators;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

/**
 * 扣款流水事件
 */
@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
public class DebitTransactionEvent {
    private String id;
    private long startTimestamp;
    private long endTimestamp;

    public DebitTransactionEvent() {}

    public DebitTransactionEvent(String id, long startTimestamp, long endTimestamp) {
        this.id = id;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public String getId() {
        return id;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String toString() {
        return "DebitTransactionEvent{id=" + id +
                ", start=" + startTimestamp +
                ", end=" + endTimestamp + "}";
    }
}
