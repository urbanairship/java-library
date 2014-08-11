package com.urbanairship.api.push.parse.audience;

import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.SelectorCategory;
import com.urbanairship.api.push.model.audience.CompoundSelector;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.ArrayList;

public class SelectorDeserializer extends JsonDeserializer<Selector> {

    private static final Logger log = LogManager.getLogger(SelectorDeserializer.class);

    private static final FieldParserRegistry<Selector, SelectorReader> FIELD_PARSERS
        = new MapFieldParserRegistry<Selector, SelectorReader>(
            new CaseInsensitiveMap(
            ImmutableMap.<String, FieldParser<SelectorReader>>builder()
            // Value selectors
            .put("tag", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.TAG, parser, context);
                    }
                })
            .put("alias", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.ALIAS, parser, context);
                    }
                })
            .put("segment", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.SEGMENT, parser, context);
                    }
                })

            // Device ID value selectors
            .put("device_token", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.DEVICE_TOKEN, parser, context);
                    }
                })
            .put("device_pin", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.DEVICE_PIN, parser, context);
                    }
                })
            .put("apid", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.APID, parser, context);
                    }
                })
            .put("wns", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.WNS, parser, context);
                    }
                })
            .put("mpns", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readValueSelector(SelectorType.MPNS, parser, context);
                    }
                })

            // Compound selectors
            .put("and", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readCompoundSelector(SelectorType.AND, parser, context);
                    }
                })
            .put("or", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readCompoundSelector(SelectorType.OR, parser, context);
                    }
                })
            .put("not", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readCompoundSelector(SelectorType.NOT, parser, context);
                    }
                })
            .put("location", new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readLocationSelector(parser, context);
                    }
                })
            .build()),

            // Default parser
            new FieldParser<SelectorReader>() {
                    @Override
                    public void parse(SelectorReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                        reader.readExtraField(parser, context);
                    }
            }
            );

    public static final SelectorDeserializer INSTANCE = new SelectorDeserializer();

    private final StandardObjectDeserializer<Selector, ?> deserializer;

    public SelectorDeserializer() {
        deserializer = new StandardObjectDeserializer<Selector, SelectorReader>(
            FIELD_PARSERS,
            new Supplier<SelectorReader>() {
                @Override
                public SelectorReader get() {
                    return new SelectorReader();
                }
            }
            );
    }

    @Override
    public Selector deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonToken token = parser.getCurrentToken();
        // log.debug("deserialize(): " + token.name());

        if (token == JsonToken.VALUE_STRING) {
            return readAtomicSelector(parser, context);
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
    private Selector readAtomicSelector(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getText();
        SelectorType type = SelectorType.getSelectorTypeFromIdentifier(value);

        if (type != null) {
            return Selectors.atomic(type);
        } else {
            throw new APIParsingException(String.format("Unknown atomic selector '%s'", value));
        }
    }
}
