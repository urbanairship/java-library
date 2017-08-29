/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.urbanairship.api.common.model.APIModelObject;
import com.urbanairship.api.experiments.parse.ExperimentObjectMapper;

public class ExperimentModelObject extends APIModelObject {
    @Override
    public String toJSON() {
        try {
            return ExperimentObjectMapper.getInstance().writeValueAsString(this);
        } catch ( Exception e ) {
            return toJSON(e);
        }
    }
}
