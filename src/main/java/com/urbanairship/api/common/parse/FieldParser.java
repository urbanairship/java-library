/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public interface FieldParser<T extends JsonObjectReader> {

    void parse(T reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

}
