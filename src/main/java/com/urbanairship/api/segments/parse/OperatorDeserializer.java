/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;


import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.OperatorType;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class OperatorDeserializer extends JsonDeserializer<Operator> {

    public static final OperatorDeserializer INSTANCE = new OperatorDeserializer(PredicateDeserializer.INSTANCE);

    private static final int MAX_OPERATOR_DEPTH = 10;

    private static final String INVALID_OPERATOR_PAYLOAD = "Operator elements must have an array of constraints";
    private static final String INVALID_OPERATOR = "Segment criteria operator elements must contain the type key and an array of child constraints";
    private static final String INVALID_NOT_OPERATOR = "A \"not\" operator must have a single child that is either another operator or a predicate";
    private static final String OPERATOR_DEPTH_EXCEEDED = "Operators can only be nested " + MAX_OPERATOR_DEPTH + " levels deep";
    private static final String DOUBLE_NEGATIVE_OPERATOR = "The child of a not operator cannot be another not operator";

    private final OperatorChildrenDeserializerRegistry operatorChildrenDeserializerRegistry;

    private OperatorDeserializer(PredicateDeserializer predicateDeserializer) {
        this.operatorChildrenDeserializerRegistry = new OperatorChildrenDeserializerRegistry(this, predicateDeserializer);
    }

    public boolean isValidOperatorKey(String key) {
        return OperatorType.getOperatorTypeFromIdentifier(key) != null;
    }

    @Override
    public Operator deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return parseOperator(jp, ctxt, 1);
    }

    private Operator parseOperator(JsonParser jp, DeserializationContext ctxt, int depth) throws IOException {
        if (depth > MAX_OPERATOR_DEPTH) {
            throw new InvalidAudienceSegmentException(OPERATOR_DEPTH_EXCEEDED);
        }

        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        if (token != JsonToken.FIELD_NAME) {
            throw new InvalidAudienceSegmentException(INVALID_OPERATOR);
        }

        OperatorType operatorType = OperatorType.getOperatorTypeFromIdentifier(jp.getText());
        if (operatorType == null) {
            throw new InvalidAudienceSegmentException(INVALID_OPERATOR);
        }

        jp.nextToken();
        Operator operator = parseOperatorOfType(operatorType, jp, ctxt, depth);

        if (jp.nextToken() != JsonToken.END_OBJECT) {
            throw new InvalidAudienceSegmentException(INVALID_OPERATOR);
        }

        return operator;
    }

    private Operator parseOperatorOfType(OperatorType operatorType, JsonParser jp, DeserializationContext ctxt, int depth) throws IOException {
        Operator.Builder builder = Operator.newBuilder(operatorType);

        OperatorChildrenDeserializer childrenDeserializer = operatorChildrenDeserializerRegistry.getChildrenDeserializer(operatorType);
        childrenDeserializer.deserializeOperatorChildren(jp, ctxt, builder, depth);

        return builder.build();
    }

    private interface OperatorChildrenDeserializer {
        void deserializeOperatorChildren(JsonParser jp, DeserializationContext ctxt, Operator.Builder builder, int depth) throws IOException;
    }

    private static class OperatorChildrenDeserializerRegistry {

        private final NotOperatorChildDeserializer notOperatorChildDeserializer;
        private final StandardOperatorChildrenDeserializer standardOperatorChildrenDeserializer;

        private OperatorChildrenDeserializerRegistry(OperatorDeserializer operatorDeserializer, PredicateDeserializer predicateDeserializer) {
            notOperatorChildDeserializer = new NotOperatorChildDeserializer(operatorDeserializer, predicateDeserializer);
            standardOperatorChildrenDeserializer = new StandardOperatorChildrenDeserializer(operatorDeserializer, predicateDeserializer);
        }

        public OperatorChildrenDeserializer getChildrenDeserializer(OperatorType operatorType) {
            return (operatorType == OperatorType.NOT ? notOperatorChildDeserializer : standardOperatorChildrenDeserializer);
        }

    }

    private static class StandardOperatorChildrenDeserializer implements OperatorChildrenDeserializer {

        private final OperatorDeserializer operatorDeserializer;
        private final PredicateDeserializer predicateDeserializer;

        private StandardOperatorChildrenDeserializer(OperatorDeserializer operatorDeserializer, PredicateDeserializer predicateDeserializer) {
            this.operatorDeserializer = operatorDeserializer;
            this.predicateDeserializer = predicateDeserializer;
        }

        @Override
        public void deserializeOperatorChildren(JsonParser jp, DeserializationContext ctxt, Operator.Builder builder, int depth) throws IOException {
            int childrenRead = parseChildren(jp, ctxt, builder, depth);
            if (childrenRead == 0) {
                throw new InvalidAudienceSegmentException(INVALID_OPERATOR_PAYLOAD);
            }
        }

        private int parseChildren(JsonParser jp, DeserializationContext ctxt, Operator.Builder builder, int depth) throws IOException {
            JsonToken token = jp.getCurrentToken();
            if (token != JsonToken.START_ARRAY) {
                throw new InvalidAudienceSegmentException(INVALID_OPERATOR_PAYLOAD);
            }

            int childrenRead = 0;
            token = jp.nextToken();
            while (token != JsonToken.END_ARRAY) {
                if (token != JsonToken.START_OBJECT) {
                    throw new InvalidAudienceSegmentException(INVALID_OPERATOR_PAYLOAD + ". Location:" + jp.getCurrentLocation());
                }

                JsonToken childType = jp.nextToken();
                if (childType != JsonToken.FIELD_NAME) {
                    throw new InvalidAudienceSegmentException(INVALID_OPERATOR_PAYLOAD);
                }

                String constraintType = jp.getText();
                if (operatorDeserializer.isValidOperatorKey(constraintType)) {
                    builder.addOperator(operatorDeserializer.parseOperator(jp, ctxt, depth + 1));
                } else {
                    builder.addPredicate(predicateDeserializer.deserialize(jp, ctxt));
                }

                childrenRead++;

                token = jp.nextToken();
            }

            return childrenRead;
        }
    }

    private static class NotOperatorChildDeserializer implements OperatorChildrenDeserializer {

        private final OperatorDeserializer operatorDeserializer;
        private final PredicateDeserializer predicateDeserializer;

        private NotOperatorChildDeserializer(OperatorDeserializer operatorDeserializer, PredicateDeserializer predicateDeserializer) {
            this.operatorDeserializer = operatorDeserializer;
            this.predicateDeserializer = predicateDeserializer;
        }

        @Override
        public void deserializeOperatorChildren(JsonParser jp, DeserializationContext ctxt, Operator.Builder builder, int depth) throws IOException {
            JsonToken token = jp.getCurrentToken();
            if (token != JsonToken.START_OBJECT) {
                throw new InvalidAudienceSegmentException(INVALID_NOT_OPERATOR);
            }

            token = jp.nextToken();
            if (token != JsonToken.FIELD_NAME) {
                throw new InvalidAudienceSegmentException(INVALID_NOT_OPERATOR);
            }

            String key = jp.getText();
            if (operatorDeserializer.isValidOperatorKey(key)) {
                if (OperatorType.NOT.getIdentifier().equals(key)) {
                    throw new InvalidAudienceSegmentException(DOUBLE_NEGATIVE_OPERATOR);
                }
                builder.addOperator(operatorDeserializer.parseOperator(jp, ctxt, depth + 1));
            } else {
                builder.addPredicate(predicateDeserializer.deserialize(jp, ctxt));
            }
        }
    }
}
