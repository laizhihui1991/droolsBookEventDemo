package com.test.drools.pojo.operators.during;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("endTimestamp - startTimestamp")
public class ChildPlayEvent {
    private String id;
    private long startTimestamp;
    private long endTimestamp;

    public ChildPlayEvent(String id, long start, long end) {
        this.id = id; this.startTimestamp = start; this.endTimestamp = end;
    }
    public String getId() { return id; }
    public long getStartTimestamp() { return startTimestamp; }
    public long getEndTimestamp() { return endTimestamp; }
}