package com.test.drools.pojo.operators.during;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Duration;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("endTimestamp - startTimestamp")
public class LunchServiceEvent {

    private String id;
    private long startTimestamp;
    private long endTimestamp;

    public LunchServiceEvent() {}
    public LunchServiceEvent(String id, long startTimestamp, long endTimestamp) {
        this.id = id;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }
    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public long getStartTimestamp() { return startTimestamp; }
    public void setStartTimestamp(long startTimestamp) { this.startTimestamp = startTimestamp; }
    public long getEndTimestamp() { return endTimestamp; }
    public void setEndTimestamp(long endTimestamp) { this.endTimestamp = endTimestamp; }
}