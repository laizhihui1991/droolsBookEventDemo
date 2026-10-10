package com.test.drools.pojo;

import org.kie.api.definition.type.*;

@Role(Role.Type.EVENT)
@Timestamp("eventTime")  // 事件发生时间
@Duration("duration")    // 事件持续时间
@Expires("1s")           // 事件过期时间
@ClassReactive
public class VoiceCallEvent {

    private String caller;       // 主叫号码
    private long duration;       // 通话时长，单位毫秒
    private long eventTime;      // 事件发生时间

    public VoiceCallEvent() {}

    public VoiceCallEvent(String caller, long eventTime,long duration) {
        this.caller = caller;
        this.duration = duration;
        this.eventTime = eventTime;
    }

    public String getCaller() { return caller; }
    public void setCaller(String caller) { this.caller = caller; }

    public long getDuration() { return duration; }
    public void setDuration(long duration) { this.duration = duration; }

    public long getEventTime() { return eventTime; }
    public void setEventTime(long eventTime) { this.eventTime = eventTime; }

    @Override
    public String toString() {
        return "VoiceCallEvent{" +
                "caller='" + caller + '\'' +
                ", duration=" + duration +
                ", eventTime=" + eventTime +
                '}';
    }
}
