/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSCycleTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSFlipTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSIconicTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSTileData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class MPNSTileSerializer extends JsonSerializer<MPNSTileData> {
    private final MPNSCycleTileSerializer cycleTileSerializer = new MPNSCycleTileSerializer();
    private final MPNSFlipTileSerializer flipTileSerializer = new MPNSFlipTileSerializer();
    private final MPNSIconicTileSerializer iconicTileSerializer = new MPNSIconicTileSerializer();

    @Override
    public void serialize(MPNSTileData tile, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (tile instanceof MPNSCycleTileData) {
            cycleTileSerializer.serialize((MPNSCycleTileData)tile, jgen, provider);
        } else if (tile instanceof MPNSFlipTileData) {
            flipTileSerializer.serialize((MPNSFlipTileData)tile, jgen, provider);
        } else if (tile instanceof MPNSIconicTileData) {
            iconicTileSerializer.serialize((MPNSIconicTileData)tile, jgen, provider);
        }
    }
}
