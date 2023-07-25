/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.Orchestration;
import com.urbanairship.api.push.model.OrchestrationType;

import java.io.IOException;
import java.util.List;

public class OrchestrationReader implements JsonObjectReader<Orchestration> {

    private final Orchestration.Builder builder;

    public OrchestrationReader() {
        this.builder = Orchestration.newBuilder();
    }

    public void readOrchestrationType(JsonParser parser) throws IOException {
        builder.setOrchestrationType(parser.readValueAs(OrchestrationType.class));
    }

    public void readOrchestrationChannelPriority(JsonParser parser) throws IOException {
        builder.addAllOrchestrationChannelPriority(parser.readValueAs(new TypeReference<List<String>>() {
        }));
    }

    @Override
    public Orchestration validateAndBuild() throws IOException {
        return builder.build();
    }
}
