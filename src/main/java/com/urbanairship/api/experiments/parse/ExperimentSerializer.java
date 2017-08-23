/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.experiments.model.Experiment;

import java.io.IOException;

public class ExperimentSerializer extends JsonSerializer<Experiment> {

    @Override
    public void serialize(Experiment experiment, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", experiment.getAudience());
        if (experiment.getDeviceTypes().isAll()) {
            jgen.writeStringField("device_types", "all");
        } else {
            jgen.writeObjectField("device_types", experiment.getDeviceTypes().getDeviceTypes().get());
        }
        jgen.writeObjectField("variants", experiment.getVariants());
        if (experiment.getName().isPresent()) {
            jgen.writeObjectField("name", experiment.getName().get());
        }
        if (experiment.getDescription().isPresent()) {
            jgen.writeObjectField("description", experiment.getDescription().get());
        }
        if (experiment.getControl().isPresent()) {
            jgen.writeObjectField("control", experiment.getControl().get());
        }

        jgen.writeEndObject();
    }
}
