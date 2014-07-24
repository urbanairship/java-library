package com.urbanairship.api.schedule.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

// No Unit Tests for this Class
public class ScheduleResponseObject {

    @JsonProperty("url")
    private String url;

    @JsonProperty("schedule")
    private Map<String, Object> schedule;

    @JsonProperty("push")
    private Map<String, Object> push;

    @JsonProperty("push_ids")
    private List<String> push_ids;

    public String getUrl() { return url; }
    public Map<String, Object> getSchedule() { return schedule; }
    public Map<String, Object> getPushPayload() { return push; }
    public List<String> getPushIds() { return push_ids; }

    public void setUrl(String inurl) { url = inurl; }
    public void setSchedule(Map<String, Object> sch) { schedule = sch; }
    public void setPush(Map<String, Object> inpush) { push = inpush; }
    public void setPushIds(List<String> pushids) { push_ids = pushids; }

    @Override
    public String toString() {
        return "ScheduleResponseObject{" +
                "url='" + url + '\'' +
                ", schedule=" + schedule +
                ", push=" + push +
                ", push_ids=" + push_ids +
                '}';
    }
}
