/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience;

import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.SelectorCategory;
import com.urbanairship.api.push.model.audience.BasicSelector;
import com.urbanairship.api.push.model.audience.BasicCompoundSelector;
import com.urbanairship.api.push.model.audience.BasicValueSelector;
import com.urbanairship.api.push.model.audience.location.LocationSelector;
import com.urbanairship.api.common.parse.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.base.Optional;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class SelectorReader implements JsonObjectReader<Selector> {

    private BasicSelector.Builder basic = null;
    private BasicValueSelector.Builder value = null;
    private BasicCompoundSelector.Builder compound = null;
    private ImmutableMap.Builder<String, String> extra = null;
    private LocationSelector location = null;

    public SelectorReader() {
    }

    public void readValueSelector(SelectorType type, JsonParser parser, DeserializationContext context) throws IOException {
        // log.debug("readValueSelector()");
        if (value != null || compound != null ) {
            APIParsingException.raise(String.format("Specified more than one type of selector ('%s' was unexpected)", type.getIdentifier()), parser);
        }
        JsonToken token = parser.getCurrentToken();

        // Simple case, it's a simple value selector
        if (token == JsonToken.VALUE_STRING) {
            value = BasicValueSelector.newBuilder()
                .setType(type)
                .setValue(parser.getText());

            // Complex case, it's an implicit OR expression. Read a
            // list of strings and transform it into an OR of value
            // selectors.
        } else if (token == JsonToken.START_ARRAY) {

            compound = BasicCompoundSelector.newBuilder()
                .setType(SelectorType.OR);

            List<String> values = readListOfStrings(parser, context);
            for (String value : values) {
                Selector child = Selectors.value(type, value);
                Validation.validate(child);
                compound.addSelector(child);
            }
        }
    }

    public void readCompoundSelector(SelectorType type, JsonParser parser, DeserializationContext context) throws IOException {
        // log.debug("readCompoundSelector() begin: " + parser.getCurrentToken());
        if (value != null || compound != null ) {
            APIParsingException.raise(String.format("Specified more than one type of selector ('%s' was unexpected'", type.getIdentifier()), parser);
        }
        compound = BasicCompoundSelector.newBuilder()
            .setType(type);
        List<Selector> children = readListOfSelectors(parser, context);
        for (Selector child : children) {
            Validation.validate(child);
        }
        compound.addAllSelectors(children);
        // log.debug("readCompoundSelector() end: " + parser.getCurrentToken());
    }

    public void readLocationSelector(JsonParser parser, DeserializationContext context) throws IOException {
        location = parser.readValueAs(LocationSelector.class);
    }

    public void readExtraField(JsonParser parser, DeserializationContext context) throws IOException {
        if (extra == null) {
            extra = ImmutableMap.builder();
        }
        extra.put(parser.getCurrentName(), parser.getText());
    }

    public List<Selector> readListOfSelectors(JsonParser parser, DeserializationContext context) throws IOException {
        ArrayList<Selector> selectors = new ArrayList();
        JsonToken token = parser.getCurrentToken();
        // log.debug("readListOfSelectors() begin: " + token);
        if (token == JsonToken.START_ARRAY) {
            token = parser.nextToken();

            while (token != JsonToken.END_ARRAY) {
                selectors.add(readSelectorInList(parser, context));
                token = parser.nextToken();
            }
        } else if (token == JsonToken.START_OBJECT) {
            selectors.add(readSelectorInList(parser, context));
        }
        // log.debug("readListOfSelectors() end: " + token);
        return selectors;
    }

    private Selector readSelectorInList(JsonParser parser, DeserializationContext context) throws IOException {
        Selector selector = SelectorDeserializer.INSTANCE.deserialize(parser, context);
        if (selector.getType().getCategory() == SelectorCategory.ATOMIC) {
            APIParsingException.raise(String.format("Atomic selector '%s' cannot appear in a compound selector.",
                                                    selector.getType().getIdentifier()), parser);
        }
        Validation.validate(selector);
        return selector;
    }

    public List<String> readListOfStrings(JsonParser parser, DeserializationContext context) throws IOException {
        ArrayList<String> strings = new ArrayList();
        JsonToken token = parser.getCurrentToken();
        // log.debug("readListOfStrings() - " + token);
        if (token == JsonToken.START_ARRAY) {
            token = parser.nextToken();
        }
        while (token != JsonToken.END_ARRAY) {
            if (token != JsonToken.VALUE_STRING) {
                APIParsingException.raise(String.format("Expected a string, but got '%s'", token), parser);
            }
            strings.add(parser.getText());
            token = parser.nextToken();
        }
        return strings;
    }

    @Override
    public Selector validateAndBuild() throws IOException {
        try {
            if (basic != null) {
                return Validation.validate(basic.build());
            } else if (value != null) {
                if (extra != null) {
                    value.addAllAttributes(extra.build());
                }
                return Validation.validate(value.build());
            } else if (compound != null) {
                return Validation.validate(compound.build());
            } else if (location != null ) {
                return location;
            } else {
                throw new APIParsingException("Empty selector.");
            }
        } catch (APIParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
