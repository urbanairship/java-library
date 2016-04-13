/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public interface FieldParser<T extends JsonObjectReader> {

    void parse(T reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

}
