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

