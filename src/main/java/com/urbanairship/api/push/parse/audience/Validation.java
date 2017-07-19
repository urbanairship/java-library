/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.audience.CompoundSelector;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorCategory;
import com.urbanairship.api.push.model.audience.ValueSelector;

public class Validation {

    public static Selector validate(Selector s) throws APIParsingException {
        if (s instanceof ValueSelector) {
            return validate((ValueSelector)s);
        } else if (s instanceof CompoundSelector) {
            return validate((CompoundSelector)s);
        }
        return s;
    }

    public static ValueSelector validate(ValueSelector v) throws APIParsingException {
        if (v.getType().getCategory() != SelectorCategory.VALUE) {
            throw new APIParsingException(String.format("Selector type '%s' cannot take a value.", v.getType().getIdentifier()));
        }
        switch (v.getType()) {
          case APID:
          case WNS:
          case MPNS:
              // TODO: make this better
              if (v.getValue().length() < 16) {
                  throw new APIParsingException("Invalid APID");
              }
              break;
          case DEVICE_TOKEN:
              // TODO: validate Apple device tokens
              break;
          case TAG:
          case ALIAS:
          case STATIC_LIST:
          case SEGMENT:
              // TODO: are there any restrictions on tag, alias, and
              // segment names?
              break;
        }
        return v;
    }

    public static CompoundSelector validate(CompoundSelector s) throws APIParsingException {
        if (s.getType().getCategory() != SelectorCategory.COMPOUND) {
            throw new APIParsingException(String.format("Selector type '%s' cannot take an array of values.", s.getType().getIdentifier()));
        }
        return s;
    }
}
