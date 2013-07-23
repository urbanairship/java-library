/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Represents a response from the Urban Airship API for Push Notifications.
 */
public class APIPushResponse {

    private final String operationId;
    private final List<String> pushIds;

    public APIPushResponse (String operationId, List<String> pushIds) {
        this.operationId = operationId;
        this.pushIds = pushIds;
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     * @return Operation id for this API request
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * List of push id's, one for every actual push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     * @return List of push ids.
     */
    public List<String> getPushIds() {
        return pushIds;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nAPIPushResponse\n");
        stringBuilder.append("OperationId:");
        stringBuilder.append(operationId);
        stringBuilder.append("\nPushIds:");
        stringBuilder.append(pushIds.toString());
        return stringBuilder.toString();
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    /**
     * APIPushResponse Builder
     */
    public static class Builder {
        private String operationId;
        private List<String> pushIds;

        private Builder (){}

        public Builder setOperationId(String operationId){
            this.operationId = operationId;
            return this;
        }

        public Builder setPushIds(List<String>pushIds){
            this.pushIds = pushIds;
            return this;
        }

        public APIPushResponse build(){
            return new APIPushResponse(operationId, pushIds);

        }
    }
}
