Defining and Sending Push Notifications
=======================================

Sending push notifications requires the following basic steps. This pattern
is present throughout the Urban Airship API:

#. Create a payload that represents the data you want to send to the API, in this case, a Push notification.
#. Create an authenticated communication channel, using an APIClient.
#. Dispatch the data payload.
#. Handle the response, which will either be successful, or contain an exception with error details.

Here is an example of sending a Push message immediately to an iOS
audience for an application.

.. code-block:: java

    // Import some things
    import com.urbanairship.api.client.*;
    import com.urbanairship.api.push.model.DeviceType;
    import com.urbanairship.api.push.model.DeviceTypeData;
    import com.urbanairship.api.push.model.PushPayload;
    import com.urbanairship.api.push.model.audience.Selectors;
    import com.urbanairship.api.push.model.notification.Notifications;

    public void sendPush(){

        String appKey = "applicationKey";
        String appSecret = "applicationMasterSecret";

        // Build and configure an APIClient
        APIClient apiClient = APIClient.newBuilder()
                .setKey(appKey)
                .setSecret(appSecret)
                .build();

        // Setup a payload for the message you want to send
        PushPayload payload = PushPayload.newBuilder()
                                         .setAudience(Selectors.all())
                                         .setNotification(Notifications.alert("API v3"))
                                         .setDeviceType(DeviceTypeData.of(DeviceType.IOS))
                                         .build();
        // Try/Catch for any issues, any non 200 response, or non library
        // related exceptions
        try {
            APIClientResponse<APIPushResponse> response = apiClient.push(payload);
            logger.debug(String.format("Response %s", response.toString()));
        }
        catch (APIRequestException ex){
            logger.error(String.format("APIRequestException " + ex));
            logger.error("Something wrong with the request " + ex.toString());
        }
        catch (IOException e){
            logger.error("IOException in API request " + e.getMessage());
        }

    }