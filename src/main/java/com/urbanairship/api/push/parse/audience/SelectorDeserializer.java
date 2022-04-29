/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.Selectors;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class SelectorDeserializer extends JsonDeserializer<Selector> {

    private static final FieldParserRegistry<Selector, SelectorReader> FIELD_PARSERS = new MapFieldParserRegistry<Selector, SelectorReader>(
            getRegistryMap(),
            // Default parser
            (reader, parser, context) -> reader.readExtraField(parser)
    );

    public static final SelectorDeserializer INSTANCE = new SelectorDeserializer();

    private final StandardObjectDeserializer<Selector, ?> deserializer;

    public SelectorDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new SelectorReader()
        );
    }

    @Override
    public Selector deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken token = parser.getCurrentToken();
        // log.debug("deserialize(): " + token.name());

        if (token == JsonToken.VALUE_STRING) {
            return readAtomicSelector(parser);
        } else {
            return deserializer.deserialize(parser, context);
        }
    }

    /**
     * Read an atomic selector from the parse stream. This is called
     * when we get a plain string where we're expecting a selector
     * (i.e. this will only be called situations where "all" or
     * "triggered" would be appropriate, NOT when parsing a list of
     * strings in an implicit-OR expression.
     */
    private Selector readAtomicSelector(JsonParser parser) throws IOException {
        String value = parser.getText();
        SelectorType type = SelectorType.getSelectorTypeFromIdentifier(value);

        if (type != null) {
            return Selectors.atomic(type);
        } else {
            throw new APIParsingException(String.format("Unknown atomic selector '%s'", value));
        }
    }

    private static Map<String, FieldParser<SelectorReader>> getRegistryMap() {
        Map<String, FieldParser<SelectorReader>> registryMap = ImmutableMap.<String, FieldParser<SelectorReader>>builder()
                // Value selectors
                .put("tag", (reader, parser, context) -> reader.readValueSelector(SelectorType.TAG, parser, context))
                .put("alias", (reader, parser, context) -> reader.readValueSelector(SelectorType.ALIAS, parser, context))
                .put("named_user", (reader, parser, context) -> reader.readValueSelector(SelectorType.NAMED_USER, parser, context))
                .put("segment", (reader, parser, context) -> reader.readValueSelector(SelectorType.SEGMENT, parser, context))
                .put("static_list", (reader, parser, context) -> reader.readValueSelector(SelectorType.STATIC_LIST, parser, context))
                .put("attribute", (reader, parser, context) -> reader.readValueSelector(SelectorType.ATTRIBUTE, parser, context))
                .put("subscription_list", (reader, parser, context) -> reader.readValueSelector(SelectorType.SUBSCRIPTION_LIST, parser, context))

                        // Device ID value selectors
                .put("device_token", (reader, parser, context) -> reader.readValueSelector(SelectorType.DEVICE_TOKEN, parser, context))
                .put("apid", (reader, parser, context) -> reader.readValueSelector(SelectorType.APID, parser, context))
                .put("wns", (reader, parser, context) -> reader.readValueSelector(SelectorType.WNS, parser, context))
                .put("ios_channel", (reader, parser, context) -> reader.readValueSelector(SelectorType.IOS_CHANNEL, parser, context))
                .put("amazon_channel", (reader, parser, context) -> reader.readValueSelector(SelectorType.AMAZON_CHANNEL, parser, context))
                .put("android_channel", (reader, parser, context) -> reader.readValueSelector(SelectorType.ANDROID_CHANNEL, parser, context))
                .put("channel", (reader, parser, context) -> reader.readValueSelector(SelectorType.CHANNEL, parser, context))

                        // Compound selectors
                .put("and", (reader, parser, context) -> reader.readCompoundSelector(SelectorType.AND, parser, context))
                .put("or", (reader, parser, context) -> reader.readCompoundSelector(SelectorType.OR, parser, context))
                .put("not", (reader, parser, context) -> reader.readCompoundSelector(SelectorType.NOT, parser, context))
                .put("location", (reader, parser, context) -> reader.readLocationSelector(parser))
                .build();

        TreeMap<String, FieldParser<SelectorReader>> caseInsensitiveRegistryMap = new TreeMap<String, FieldParser<SelectorReader>>(String.CASE_INSENSITIVE_ORDER);
        caseInsensitiveRegistryMap.putAll(registryMap);

        return caseInsensitiveRegistryMap;
    }
}
