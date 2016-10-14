#######################################
Defining and Sending Push Notifications
#######################################


*************************
Defining the Push Payload
*************************

The ``PushPayload`` is comprised of three pieces:

- ``Audience and Selectors``
- ``Notifications``
- ``DeviceTypes``

The first is the Audience. The audience is composed of Selectors, which can be compound or
atomic (not compound). Selectors provide logical combinations of AND, OR, and NOT.


Audience and Selectors
======================

The Selectors and DeviceType classes provide factory methods that can be used together
to create an Audience Selector. To send to all users with the tag "kittens".

.. code-block:: java

     Selectors.tag("kittens")

Or to users with the tag "kittens" in your "crm" tag group

 .. code-block:: java

     Selectors.tagWithGroup("kittens", "crm")

You can also send to multiple tags, such as "kittens" and "puppies"

.. code-block:: java

    Selectors.tags("kittens", "puppies")

More complex logic is possible:

.. code-block:: java

   Selector andSelector = Selectors.and(Selectors.tag("puppies"), Selectors.tag("kittens"));
   Selector notSelector = Selectors.not(Selectors.tag("fish"));
   Selector compound = Selectors.or(andSelector, notSelector);

The ``compound`` selector above produces the following payload:

.. code-block:: json

   {
     "audience": {
       "or": [
         {
           "and": [
             {"tag": "puppies"},
             {"tag": "kittens"}
           ]
         },
         {
           "not": [
             {"tag": "fish"}
           ]
         }
       ]
     }
   }

which will send messages to users who either have the tags "puppies" and
"kittens" or don't have the tag "fish".


Notifications
=============

Notifications are the second part of the ``PushPayload``. Notifications are configured for each
type of device you would like to send a message to. A Notification for an iOS device contains
options for ``alert``, ``badge``, ``sound``, ``content_available``, ``extra``, ``expiry``,
``priority``, ``category``, or ``interactive``. Other platforms, e.g., Android, may offer
different configurations based on available features.

Here's an example of an iOS notification with an alert, a badge, and an extra key/value pair:

.. code-block:: java

   // Set up badge data, which can be a value, increment, decrement, etc.
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
       .setAudience(Selectors.all())
       .setNotification(notification)
       .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
       .build();

This will generate and send a payload similar to the following:

.. code-block:: json

   {
     "audience": "all",
     "device_types": ["ios"],
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

Here's another example of an iOS notification implementing expiry and interactive notifications:

.. code-block:: java

    PushExpiry expiry = PushExpiry.newBuilder()
        .setExpirySeconds(3600)
        .build();

    Interactive interactive = Interactive.newBuilder()
        .setType("ua_yes_no_foreground")
        .setButtonActions(ImmutableMap.of(
            "yes",
            Actions.newBuilder()
                .addTags(new AddTagAction(TagActionData.single("tag1")))
                .build(),
            "no",
            Actions.newBuilder()
                .addTags(new AddTagAction(TagActionData.single("tag2")))
                .build()))
        .build();

    IOSDevicePayload iosPayload = IOSDevicePayload.newBuilder()
        .setAlert("alert")
        .setExpiry(expiry)
        .setInteractive(interactive)
        .build();

    PushPayload payload = PushPayload.newBuilder()
        .setAudience(Selectors.iosChannel(channel))
        .setNotification(Notifications.notification(iosPayload))
        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
        .build();

Which will generate the following JSON payload:

.. code-block:: json

  {
      "audience": {
          "ios_channel": "50614f67-498b-49df-b832-a046de0ec6ec"
      },
      "device_types": [
          "ios"
      ],
      "notification": {
          "ios": {
              "alert": "alert",
              "expiry" :3600,
              "interactive": {
                  "type": "ua_yes_no_foreground",
                  "button_actions": {
                      "yes": {
                          "add_tag": "tag1"
                      },
                      "no": {
                          "add_tag": "tag2"
                      }
                  }
              }
          }
      }
  }

Here's an example of an iOS notification utilizing rich media (iOS 10+):

.. code-block:: java

    Crop crop = Crop.newBuilder()
            .setHeight(0.2f)
            .setWidth(0.2f)
            .setX(0.1f)
            .setY(0.1f)
            .build();

    Options options = Options.newBuilder()
            .setTime(10)
            .setCrop(crop)
            .build();

    Content content = Content.newBuilder()
            .setBody("content body")
            .setTitle("content title")
            .setSubtitle("content subtitle")
            .build();

    MediaAttachment mediaAttachment = MediaAttachment.newBuilder()
            .setUrl("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif")
            .setOptions(options)
            .setContent(content)
            .build();

    IOSDevicePayload iosPayload = IOSDevicePayload.newBuilder()
            .setAlert("alert")
            .setTitle("title")
            .setSubtitle("subtitle")
            .setMediaAttachment(mediaAttachment)
            .setMutableContent(true)
            .build();

    PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.iosChannel(channel))
            .setNotification(Notifications.notification(iosPayload))
            .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
            .build();

Which will generate the following JSON payload:

.. code-block:: json

  {
      "audience": {
          "ios_channel": "50614f67-498b-49df-b832-a046de0ec6ec"
      },
      "device_types": [
          "ios"
      ],
      "notification": {
          "ios": {
              "alert": "alert",
              "title": "title",
              "subtitle": "subtitle",
              "mutable_content": true,
              "media_attachment": {
                  "url": "https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif",
                  "options": {
                      "time": 10,
                      "crop": {
                          "x": 0.1,
                          "y": 0.1,
                          "width": 0.2,
                          "height": 0.2
                      }
                  },
                  "content": {
                      "body": "content body",
                      "title": "content title",
                      "subtitle": "content subtitle"
                  }
              }
          }
      }
  }

DeviceTypes
===========

The final part of the ``PushPayload`` is ``DeviceTypes``, which defines the platform you're
sending to, e.g., iOS or Android. Messages can be segregated by device types. Set the device
types you want to send to using a ``DeviceTypeData`` object. Here's an example of sending a
message to iOS and Android:

.. code-block:: java

   DeviceTypeData deviceTypeData  = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID);

This corresponds to the following payload:

.. sourcecode:: json

   {
     "device_types": ["ios", "android"]
   }


*********
Send Push
*********

We use the ``PushRequest.newRequest(<push_payload>)`` method for sending pushes:

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

   PushRequest request = PushRequest.newRequest(payload);
   Response<PushResponse> response = client.execute(request);
   String operationID = response.getApiResponse().getOperationId().get();  // Operation ID
   List<String> pushIDs = response.getApiResponse().getPushIds().get();    // List of Push IDs


*************
Validate Push
*************

To validate a push payload, use the ``PushRequest.newRequest(<push_payload>).setValidateOnly(true)``
method:

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

   PushRequest request = PushRequest.newRequest(payload).setValidateOnly(true);
   Response<PushResponse> response = client.execute(payload);
   String operationID = response.getApiResponse().getOperationId().get();     // Operation ID
   List<String> pushIDs = response.getApiResponse().getPushIds().get();       // List of Push IDs
