package com.test.drools.pojo.operators.during;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("endTimestamp - startTimestamp")
public class MovieScreeningEvent {

    private String id;
    private long startTimestamp;
    private long endTimestamp;

    public MovieScreeningEvent() {}

    public MovieScreeningEvent(String id, long startTimestamp, long endTimestamp) {
        this.id = id;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public String getId() { return id; }
    public long getStartTimestamp() { return startTimestamp; }
    public long getEndTimestamp() { return endTimestamp; }

    public void setId(String id) { this.id = id; }
    public void setStartTimestamp(long startTimestamp) { this.startTimestamp = startTimestamp; }
    public void setEndTimestamp(long endTimestamp) { this.endTimestamp = endTimestamp; }
}
