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

Breaking that down into it's component pieces, here are the following main components.

APIClient
=========

.. code-block:: java

    APIClient apiClient = APIClient.newBuilder()
            .setKey(appKey)
            .setSecret(appSecret)
            .build();

The APIClient handles the interaction between the client and the API. The client will throw an
exception if there is an issue with the request, or if it is improperly configured.

Optionally, a client can be created with proxy server support.

.. code-block:: java

    APIClient proxyClient = APIClient.newBuilder()
          .setKey(appKey)
          .setSecret(appSecret)
              .setProxyInfo(ProxyInfo.newBuilder()
              .setProxyHost(new HttpHost("host"))
              .setProxyCredentials(new UsernamePasswordCredentials("user", "password"))
              .build())
          .build();

PushPayload
===========

The PushPayload is comprised of three pieces:

  - Audience and Selectors
  - Notifications
  - DeviceTypes

The first is the Audience. The audience
is composed of Selectors, which can be compound or atomic (not compound). Selectors
provide logicial combinations of AND, OR, and NOT.

Audience and Selectors
----------------------

The Selectors and DeviceType classes provide factory methods that can be used together
to create an Audience Selector. To send to all users with the tag
"kittens".

.. code-block:: java

     Selectors.tag("kittens")

Or users who like kittens and puppies

.. code-block:: java

    Selectors.tags("kittens", "puppies")

More complex logic is possible

.. code-block:: java

   Selector andSelector = Selectors.tags("puppies", "kittens");
   Selector notSelector = Selectors.not(Selectors.tag("fish"));
   Selector compound = Selectors.or(andSelector, notSelector);

produces the output

.. code-block:: json

   {
    "audience": {
        "and": [
            {
                "or": [
                    {
                        "tag": "puppies"
                    },
                    {
                        "tag": "kittens"
                    }
                ]
            },
            {
                "not": [
                    {
                        "tag": "fish"
                    }
                ]
            }
        ]
    },
    "device_types": [
        "ios"
    ],
    "notification": {
        "alert": "API v3"
    }

which will send messages to users who have the tags "puppies" or
"kittens" but not "fish".

Notifications
-------------

Notifications are the second part of the PushPayload. Notifications
are configured for each type of device you would like to
send a message to. An Notification for an iOS device contains options
for alert, badge, sound, content_available, or extra. Other devices
offer different configurations based on available features. Here's an
example of an iOS notification with an alert, a badge, and extra key
value pairs.

.. code-block:: java

   // Setup badge data, can be a value, increment,
   //decrement, etc.
   IOSBadgeData badgeData = IOSBadgeData.newBuilder()
                                        .setValue(5)
                                        .setType(IOSBadgeData.Type.VALUE)
                                        .build();
                  
    IOSDevicePayload iosPayload = IOSDevicePayload.newBuilder()
                                                  .setAlert("iOS Alert")
                                                  .setBadge(badgeData)
                                                  .addExtraEntry("Key", "Value")
                                                  .build();
    Notification notification = Notifications.notification(iosPayload);

    PushPayload payload = PushPayload.newBuilder()
                                     .setAudience(Selectors.deviceToken(deviceToken))
                                     .setNotification(notification)      
                                     .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                                     .build();

This will generate and send a payload similar to the following

.. code-block:: json

  {
      "audience": "ALL",
      "device_types": [
          "ios"
      ],
      "notification": {
          "ios": {
              "alert": "iOS Alert",
              "badge": 5,
              "extra": {
                  "Key": "Value"
              }
          }
      }
  }

DeviceTypes
-----------

The final part of the PushPayload is the DeviceTypes. 
Messages can be segregated by device types. You can set the device types you
want to send to using a DeviceTypeData object. Here's an example of
sending a message to iOS and Android.

.. code-block:: java

   DeviceTypeData deviceTypeData  = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID);
   
The DeviceTypeData class has several convenience methods for working with
DeviceTypes. 

Validation
----------

Accepts the same range of push payloads as the Push API, but parse and validate only, without sending any pushes.

.. code-block:: java

    PushPayload payload = PushPayload.newBuilder()
        .setAudience(Selectors.all())
        .setNotification(Notification.newBuilder()
                .addDeviceTypeOverride(DeviceType.IOS, IOSDevicePayload.newBuilder()
                        .setAlert("Background Push Priority 5")
                        .setContentAvailable(true)
                        .setPriority(5)
                        .build())
                .build())
        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
        .build();

    APIClientResponse<APIPushResponse> response = apiClient.validate(payload);
    
