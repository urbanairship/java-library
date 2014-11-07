/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.AudienceSegment;

import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.Predicate;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class AudienceSegmentDeserializer extends JsonDeserializer<AudienceSegment> {

    public static final AudienceSegmentDeserializer INSTANCE =
            new AudienceSegmentDeserializer(OperatorDeserializer.INSTANCE, PredicateDeserializer.INSTANCE);

    private static final String MISSING_DISPLAY_NAME = "Audience segment requires a display name";
    private static final String MISSING_CRITERIA = "Segment criteria must contain at least one predicate";
    private static final String INVALID_SEGMENT = "An audience segment must consist of a display name and criteria";

    private final OperatorDeserializer operatorDeserializer;
    private final PredicateDeserializer predicateDeserializer;

    private AudienceSegmentDeserializer(OperatorDeserializer operatorDeserializer, PredicateDeserializer predicateDeserializer) {
        this.operatorDeserializer = operatorDeserializer;
        this.predicateDeserializer = predicateDeserializer;
    }

    @Override
    public AudienceSegment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        String displayName = null;
        RootCriteria rootCriteria = null;
        Long count = null;

        while (token != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME) {
                throw new InvalidAudienceSegmentException(INVALID_SEGMENT);
            }

            String tokenName = jp.getCurrentName();
            jp.nextToken();
            if (tokenName.equals("id")) {
                // Ignore, audience segments used to have ids but now we don't expose the ID on the segment object anymore.  So,
                // we a segment that we receive that has an id field is valid, but we ignore the id
            }
            else if (tokenName.equals("display_name")) {
                displayName = parseString(jp);
            }
            else if (tokenName.equals("criteria")) {
                rootCriteria = parseRootCriteria(jp, ctxt);
            }
            else if (tokenName.equals("count")) {
                count = Long.parseLong(parseString(jp));
            }
            else {
                throw new InvalidAudienceSegmentException(INVALID_SEGMENT);
            }

            token = jp.nextToken();
        }

        if (StringUtils.isBlank(displayName)) {
            throw new InvalidAudienceSegmentException(MISSING_DISPLAY_NAME);
        }
        if (rootCriteria == null) {
            throw new InvalidAudienceSegmentException(MISSING_CRITERIA);
        }

        AudienceSegment.Builder builder = AudienceSegment.newBuilder().setDisplayName(displayName);
        if (rootCriteria.isOperator()) {
            builder.setRootOperator(rootCriteria.getOperator());
        }
        else if (rootCriteria.isPredicate()) {
            builder.setRootPredicate(rootCriteria.getPredicate());
        }

        if (count != null) {
            builder.setCount(count);
        }

        return builder.build();
    }

    private String parseString(JsonParser jp) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token != JsonToken.VALUE_STRING) {
            return null;
        }

        return jp.getText();
    }

    private Long parseLong(JsonParser jp) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token != JsonToken.VALUE_STRING) {
            return null;
        }

        return jp.getValueAsLong();
    }

    private RootCriteria parseRootCriteria(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        if (token != JsonToken.FIELD_NAME) {
            throw new InvalidAudienceSegmentException(INVALID_SEGMENT);
        }

        String key = jp.getText();
        if (operatorDeserializer.isValidOperatorKey(key)) {
            Operator operator = operatorDeserializer.deserialize(jp, ctxt);
            return new RootCriteria(operator);
        }

        Predicate predicate = predicateDeserializer.deserialize(jp, ctxt);
        return new RootCriteria(predicate);
    }

    private static class RootCriteria {

        private final Predicate predicate;
        private final Operator operator;

        private RootCriteria(Predicate predicate) {
            this.predicate = predicate;
            this.operator = null;
        }

        private RootCriteria(Operator operator) {
            this.operator = operator;
            this.predicate = null;
        }

        public boolean isOperator() {
            return operator != null;
        }

        public boolean isPredicate() {
            return predicate != null;
        }

        public Predicate getPredicate() {
            return predicate;
        }

        public Operator getOperator() {
            return operator;
        }
    }

}
