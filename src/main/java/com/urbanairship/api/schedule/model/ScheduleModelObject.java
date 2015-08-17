package com.urbanairship.api.schedule.model;

import com.urbanairship.api.common.model.APIModelObject;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;

public class ScheduleModelObject extends APIModelObject {

    @Override
    public String toJSON() {
        try {
            return ScheduleObjectMapper.getInstance().writeValueAsString(this);
        } catch ( Exception e ) {
            return toJSON(e);
        }
    }
}
