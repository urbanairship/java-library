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
                .setAppKey(appKey)
                .setAppSecret(appSecret)
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
            .setAppKey(appKey)
            .setAppSecret(appSecret)
            .build();

The APIClient handles the interaction between the client and the API. The client will throw an
exception if there is an issue with the request, or if it is improperly configured.

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

SchedulePayload
===============

Sending a scheduled push notification via the API simply adds the
extra step of wrapping a PushPayload in a SchedulePayload.

First, create a PushPayload using the steps outlined above. Then
create a SchedulePayload and send it to the API. The message is
scheduled for delivery at current time plus 60 seconds.

.. code-block:: java

   // Create a PushPayload
   PushPayload payload = PushPayload.newBuilder().build();

   // Add it to a SchedulePayload
   Schedule schedule = Schedule.newBuilder()
                               .setScheduledTimestamp(DateTime.now().plusSeconds(60))
                               .build();

   SchedulePayload schedulePayload = SchedulePayload.newBuilder()
                                                    .setName("v3 Scheduled Push Test")
                                                    .setPushPayload(payload)
                                                    .setSchedule(schedule)
                                                    .build();

Dates and times are handled by the `Joda-Time
<http://joda-time.sourceforge.net>`_ library. Scheduled pushes require
time to be in ISO format, which is handled by the DateTime library.
Here's an example set for a particular month, day and time. See the
Joda-Time documentation for more examples.

.. code-block:: java
   DateTime dt = new DateTime(2013,7,22,11,57);

Attempting to schedule a push for a previous time will result in a
HTTP 400 response and an APIResponseException.


