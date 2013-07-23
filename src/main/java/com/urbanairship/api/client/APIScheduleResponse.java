package com.urbanairship.api.client;

import java.util.List;

/**
 * Represents a response from the Urban Airship API for Scheduled Notifications.
 */
public class APIScheduleResponse {

    private final String operationId;
    private final List<String> scheduleUrls;

    /**
     * New APIScheduleResponse builder
     * @return Builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    private APIScheduleResponse(String operationId, List<String> scheduleUrls){
        this.operationId = operationId;
        this.scheduleUrls = scheduleUrls;
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
    public List<String> getScheduleUrls() {
        return scheduleUrls;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("APIScheduleResponse:");
        stringBuilder.append(String.format("\nOperationId:%s", operationId));
        stringBuilder.append(String.format("\nScheduleUrls:\n%s", scheduleUrls));
        return stringBuilder.toString();
    }

    /**
     * APIScheduleResponse Builder
     */
    public static class Builder {

        private String operationId;
        private List<String> scheduleUrls;

        private Builder() {}

        public Builder setOperationId(String operationId){
            this.operationId = operationId;
            return this;
        }

        public Builder setScheduleUrls(List<String> scheduleUrls){
            this.scheduleUrls = scheduleUrls;
            return this;
        }

        public APIScheduleResponse build(){
            return new APIScheduleResponse(this.operationId, this.scheduleUrls);
        }
    }
}
