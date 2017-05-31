/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class ExperimentSerializer extends JsonSerializer<Experiment> {

    public static final ExperimentSerializer INSTANCE = new ExperimentSerializer();

    @Override
    public void serialize(Experiment experiment, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", experiment.getAudience());
        if (experiment.getDeviceType().isAll()) {
            jgen.writeStringField("device_types", "all");
        } else {
            jgen.writeObjectField("device_types", experiment.getDeviceType().getDeviceTypes().get());
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
