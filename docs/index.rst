.. Urban Airship Java Client documentation master file, created by
   sphinx-quickstart on Tue Jul 16 12:21:44 2013.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

#########################
Urban Airship Java Client
#########################

************
Introduction
************

This is a Java library for using the `Urban Airship
<http://urbanairship.com/>`_ web service API for push notifications.

Installation
============

Add the dependency to your pom.xml.

.. code-block:: xml

        <dependency>
            <groupId>com.urbanairship</groupId>
            <artifactId>java-client</artifactId>
            <version>0.2.3</version>
        </dependency>

Alternatively, you can build a jar with  ``mvn package``  and add the
jar to your classpath.

Logging
=======

Logging is done using the `Simple Logging Facade for Java <http://www.slf4j.org>`_
Using the logging facade allow for flexibility in logging choices. For example,
to use log4j, you would add the following to your pom.xml

.. code-block:: xml

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.5</version>
    </dependency>

    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>

Note the logging framework plus the adapter. For more info, see the facade `documentation <http://www.slf4j.org/manual.html>`_
In code, simply add the log handler of your choice. Again, with log4j:

.. code-block:: java

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.apache.log4j.BasicConfigurator;

    Logger logger = LoggerFactory.getLogger("identifier");
    BasicConfigurator.configure(); // Use any configuration you need.
    logger.debug("Log all the things!");

Development
===========

Source code is available on Github.
Tests are located in the standard test directory and use JUnit


Basic Work Flow
===============

The Urban Airship Java client streamlines API requests. API
interactions all follow the same basic work flow.

#. Configure a payload using the appropriate Java model classes
#. Configure an APIClient to authenticate, send, and return a
   response.
#. Handle the response or exception appropriately.


***************************************
Defining and Sending Push Notifications
***************************************

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



*********
APIClient
*********

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


****
Push
****

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
======================

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
=============

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
===========

The final part of the PushPayload is the DeviceTypes. 
Messages can be segregated by device types. You can set the device types you
want to send to using a DeviceTypeData object. Here's an example of
sending a message to iOS and Android.

.. code-block:: java

   DeviceTypeData deviceTypeData  = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID);
   
The DeviceTypeData class has several convenience methods for working with
DeviceTypes. 

Validation
==========

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

********
Schedule
********

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

   APIClientResponse<APIScheduleResponse> response = apiClient.schedule(schedulePayload);

Optionally, scheduled pushes can be configured to be delievered at the device local time.
This is done by calling a different method when building your Schedule object.

.. code-block:: java 

    Schedule schedule = Schedule.newBuilder()
                             .setLocalScheduledTimestamp(DateTime.now().plusSeconds(60))
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

List Schedules
==============

List all existing schedules.

.. code-block:: java

    APIClientResponse<APIListAllSchedulesResponse> response = apiClient.listAllSchedules();

    APIListAllSchedulesResponse obj = response.getApiResponse();
    int count = obj.getCount();
    int totalCount = obj.getTotal_Count();
    String nextPage = obj.getNext_Page();
    List<SchedulePayload> listOfSchedules = obj.getSchedules();

    // You can specify a url string for nextPage

    APIClientResponse<APIListAllSchedulesResponse> nextPageResponse = 
    apiClient.listAllSchedules(nextPage);

    // You can also specify a starting id, limit and order

    APIClientResponse<APIListAllSchedulesResponse> constrainedResponse = 
    apiClient.listAllSchedules("5c69320c-3e91-5241-fad3-248269eed104", 10, "asc");



Update Schedule
===============

Update the state of a single schedule resource.

.. code-block:: java

    String id = "the_id_of_the_schedule_to_update";

    SchedulePayload sp = SchedulePayload.newBuilder()
          .setName("Booyah Sports")
          .setSchedule(Schedule.newBuilder()
                  .setScheduledTimestamp(DateTime.now().plusYears(1))
                  .build())
          .setPushPayload(PushPayload.newBuilder()
                  .setAudience(Selectors.tags("spoaaaarts", "Beyonce", "Nickelback"))
                  .setNotification(Notification.newBuilder()
                          .setAlert("Booyah!")
                          .build())
                  .setDeviceTypes(DeviceTypeData.all())
                  .build())
          .build();

    APIClientResponse<APIScheduleResponse> = apiClient.updateSchedule(sp, id);

The response is a APIScheduleResponse representing the updated state.

Delete Schedule
===============

Delete a schedule resource, which will result in no more pushes being sent.  If the 
resource is successfully deleted, the response does not include a body.

.. code-block:: java

    String id = "the_id_of_the_schedule_to_delete";
    HttpResponse response = apiClient.deleteSchedule(id);

    int status = response.getStatusLine().getStatusCode();    //Returns 204 on success


****
Tags
****

*******
Reports
*******

******************
Device Information
******************

********
Segments
********

********
Location
********

**********
Exceptions
**********

These are the primary exceptions that are possible in the client
library.


APIRequestException
===================

APIRequestExceptions are thrown in cases where the server returns a non 200
response.

.. code-block:: java

   APIClient apiClient = APIClient.newBuilder()
                                  .setKey("pvNYHR9ZSGGk1LwuPl4kQWw")
                                  .setSecret("badFoo")
                                  .build();
   // Setup request
    try {
        APIClientResponse<APIPushResponse> response = apiClient.push(payload);
        logger.debug(String.format("Response %s", response.toString()));
    }
    catch (APIRequestException ex){
        // Exeption thrown here
    }

The code above will throw an APIResponseException

::

    1717 [main] ERROR com.urbanairship.api  - APIRequestException
    APIRequestException:
    Message:Unauthorized
    HttpResponse:HTTP/1.1 401 Unauthorized ......
    Error:APIError:Unauthorized
    Code:Optional.absent()

APIErrorDetails
===============

The APIErrorDetails object contains information on errors for requests
that are syntactically valid but are otherwise malformed. For example,
setting the platform value for a PushPayload to include both
DeviceType.IOS and DeviceType.ANDROID but only providing a single
IOSDevicePayloadf for the notification would be an error.


.. code-block:: json

 // This is a syntactically valid request, but is missing an
 // Android payload override.
 {
     "audience" : "all",
     "device_types" : [ "ios", "android" ],
     "notification" : {
         "ios" : {
             "alert" : "Boo"
         }
     }
 }

This will thrown an APIException that can be used to log or debug
errors.

.. code-block:: java

    try {
      APIClientResponse<APIPushResponse> response = apiClient.push(payload);
      logger.debug(String.format("Response %s", response.toString()));
    }
    catch (APIRequestException ex){
      logger.error(String.format("APIRequestException " + ex));
      logger.error("Exception " + ex.toString());

      APIError apiError = ex.getError().get();
      APIErrorDetails apiErrorDetails = apiError.getDetails().get();
      logger.error("Error " + apiError.getError());
      logger.error("Error details " + apiErrorDetails.getError());

    }
    catch (IOException e){
      logger.error("IOException in API request " + e.getMessage());
    }



will produce

::

 1722 [main] ERROR com.urbanairship.api  - Exception
 APIRequestException:
 Message:Bad Request
 HttpResponse:HTTP/1.1 400 Bad Request [Content-Type: application/vnd.urbanairship+json; version=3, Server: Jetty(8.0.y.z-SNAPSHOT), X-Request-Id: ff38e040-f310-11e2-9f25-d4bed9a88504, Date: Mon, 22 Jul 2013 20:55:10 GMT, Transfer-Encoding:  chunked, Connection: close, Connection: Transfer-Encoding]
 Error:APIError:Could not parse request body.
 Code:Optional.of(40000)
 Details:
 APIErrorDetails:
 Path:Optional.absent()
 Error:DeviceType 'android' was referenced by 'device_types', but no payload was provided.
 Optional Location:Optional.absent()
 1722 [main] ERROR com.urbanairship.api  - Error Could not parse request body.
 1722 [main] ERROR com.urbanairship.api  - Error details DeviceType
 'android' was referenced by 'device_types', but no payload was
 provided.


The APIRequestException contains both the raw HttpResponse from the
underlying Apache request and the APIError. The APIError is specific
to Urban Airship functionality, and the APIErrorDetails provides
extended details for badly formed API requests. Providing this level
of detail allows for more customization. 


APIParsingException
===================

APIParsingExceptions are thrown in response to parsing errors while
serializing or deserializing JSON. If this is thrown outside of
development it is most likely an issue with the library or the server,
and should be sent to the Urban Airship support team. Please include
as much information as possible, including the operation id if
present, and the request or API operation that threw the exception.

IOException
===========

In the context of this library, IOExceptions are thrown by the Apache
HttpComponents library, usually in response to a problem with the HTTP connection.
See the Apache `documentation <https://hc.apache.org>`_ for more
details.


