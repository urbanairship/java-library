/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.mpns.MPNSPush;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class MPNSBatchingIntervalDeserializer extends JsonDeserializer<MPNSPush.BatchingInterval> {
    public static final MPNSBatchingIntervalDeserializer INSTANCE = new MPNSBatchingIntervalDeserializer();
    @Override
    public MPNSPush.BatchingInterval deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();
        MPNSPush.BatchingInterval batchingInterval = MPNSPush.BatchingInterval.get(value);
        if (batchingInterval == null) {
            APIParsingException.raise("Unrecognized MPNS batching interval " + value, jp);
        }
        return batchingInterval;
    }
}
