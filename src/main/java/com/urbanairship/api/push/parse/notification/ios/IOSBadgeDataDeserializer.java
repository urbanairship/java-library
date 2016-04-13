/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;

public class IOSBadgeDataDeserializer {

    public static final IOSBadgeDataDeserializer INSTANCE = new IOSBadgeDataDeserializer();

    private IOSBadgeDataDeserializer() { }

    private static String[] INCREMENT_PREFIXES = { "+", "-" };

    public IOSBadgeData deserialize(JsonParser parser) throws IOException {
        try {
            JsonToken token = parser.getCurrentToken();
            switch (token) {
              case VALUE_STRING:
                  String valueString = parser.getText();
                  if (StringUtils.isBlank(valueString)) {
                      APIParsingException.raise("'badge' value cannot be empty", parser);
                  }
                  if (StringUtils.startsWithAny(valueString, INCREMENT_PREFIXES)) {
                      String numberString = valueString.substring(1);
                      int value = Integer.parseInt(numberString);
                      boolean negative = valueString.startsWith("-");
                      return IOSBadgeData.newBuilder()
                          .setType(negative ? IOSBadgeData.Type.DECREMENT : IOSBadgeData.Type.INCREMENT)
                          .setValue(value)
                          .build();
                  } else if (valueString.equals("auto")) {
                      return IOSBadgeData.newBuilder()
                          .setType(IOSBadgeData.Type.AUTO)
                          .build();
                  } else {
                      APIParsingException.raise(String.format("'%s' is not a valid badge value.", valueString), parser);
                  }
                  break;

              case VALUE_NUMBER_INT:
                  int value = parser.getIntValue();
                  return IOSBadgeData.newBuilder()
                      .setType(IOSBadgeData.Type.VALUE)
                      .setValue(value)
                      .build();

              default:
                  APIParsingException.raise(String.format("Unexpected badge token '%s'", token.name()), parser);
            }
        }
        catch ( APIParsingException e ) {
            throw e;
        } catch ( Exception e ) {
            APIParsingException.raise(e.getMessage(), parser);
        }
        return null;
    }
}
