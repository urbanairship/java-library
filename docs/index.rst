#########################
Urban Airship Java Client
#########################

************
Introduction
************

This is a Java library for using the `Urban Airship API v3 <http://docs.urbanairship.com/api/ua.html>`__.

Installation
============

Add the dependency to your pom.xml:

.. parsed-literal::

        <dependency>
            <groupId>com.urbanairship</groupId>
            <artifactId>java-client</artifactId>
            <version>VERSION</version>
        </dependency>

Replace VERSION with |release| or another version you want to use.

Alternatively, you can build a jar with  ``mvn package``  and add the
jar to your classpath.

Logging
=======

Logging is done using the `Simple Logging Facade for Java <http://www.slf4j.org>`_.
Using the logging facade allows for flexibility in logging choices. For example,
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

Note the logging framework plus the adapter. For more info, see the `Simple Logging Facade documentation <http://www.slf4j.org/manual.html>`__

Simply add the log handler of your choice. Again, with log4j:

.. code-block:: java

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.apache.log4j.BasicConfigurator;

    Logger logger = LoggerFactory.getLogger("identifier");
    BasicConfigurator.configure(); // Use any configuration you need.
    logger.debug("Log all the things!");

Development
===========

Source code is available on `Github <https://github.com/urbanairship/java-library/>`__.
Tests are located in the standard test directory and use JUnit.


Basic Workflow
==============

The Urban Airship Java client library streamlines API requests.
API interactions all follow the same basic workflow.

#. Configure a payload using appropriate Java model classes.
#. Configure an APIClient to authenticate, send, and return a
   response.
#. Handle the response or exception appropriately.


***************************************
Defining and Sending Push Notifications
***************************************

Sending push notifications requires the following basic steps. This pattern
is present throughout the Urban Airship API:

#. Create a payload that represents the data you want to send to the API, in this case, a push notification.
#. Create an authenticated communication channel using an APIClient.
#. Dispatch the data payload.
#. Handle the response, which will either be successful or contain an exception with error details.

Here is an example of sending a Push message immediately to an iOS
audience.

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

        // Try/Catch for any issues, any non-200 response, or non-library-related exceptions
        try {
            APIClientResponse<APIPushResponse> response = apiClient.push(payload);
            logger.debug(String.format("Response %s", response.toString()));
        }
        catch (APIRequestException ex) {
            logger.error(String.format("APIRequestException " + ex));
            logger.error("Something wrong with the request " + ex.toString());
        }
        catch (IOException e) {
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

The ``APIClient`` handles the interaction between the client and the API. The client will throw an
exception if there is an issue with the request, or if it is improperly configured.


Proxy Support
=============

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

The ``PushPayload`` is comprised of three pieces:

  - ``Audience and Selectors``
  - ``Notifications``
  - ``DeviceTypes``

The first is the Audience. The audience
is composed of Selectors, which can be compound or atomic (not compound). Selectors
provide logicial combinations of AND, OR, and NOT.

Audience and Selectors
======================

The Selectors and DeviceType classes provide factory methods that can be used together
to create an Audience Selector. To send to all users with the tag "kittens".

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
        "or": [
            {
                "and": [
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

Notifications are the second part of the ``PushPayload``. Notifications
are configured for each type of device you would like to
send a message to. A Notification for an iOS device contains options
for ``alert``, ``badge``, ``sound``, ``content_available``, or ``extra``. Other platforms,
e.g., Android, may offer different configurations based on available features. 

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

The final part of the ``PushPayload`` is ``DeviceTypes``, which defines the 
platform you're sending to, e.g., iOS or Amazon.
Messages can be segregated by device types. Set the device types you
want to send to using a ``DeviceTypeData`` object. Here's an example of
sending a message to iOS and Android.

.. code-block:: java

   DeviceTypeData deviceTypeData  = DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID);
   
The ``DeviceTypeData`` class has several convenience methods for working with
``DeviceTypes``. 

Send Push
=========

Sends a push notification to a specified device or list of devices.

.. code-block:: java

  APIClientResponse<APIPushResponse> response = apiClient.push(payload);

  String operationID = response.getApiResponse().getOperationId().get();  // Operation ID
  List<String> pushIDs = response.getApiResponse().getPushIds().get();    // List of Push IDs

Validation
==========

Accepts the same range of push payloads as the Push API, but parses and validates only, without sending any pushes.

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

    String operationID = response.getApiResponse().getOperationId().get();  // Operation ID
    List<String> pushIDs = response.getApiResponse().getPushIds().get();    // List of Push IDs

********
Schedule
********

Send Scheduled Push
===================

Sending a scheduled push notification via the API simply adds the
extra step of wrapping a ``PushPayload`` in a ``SchedulePayload``.

First, create a ``PushPayload`` using the steps outlined above. Then
create a ``SchedulePayload`` and send it to the API. The message in the following
example is scheduled for delivery at current time plus 60 seconds.

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

    // Operation ID
    String operationID = response.getApiResponse().getOperationId();
    
    // List of SchedulePayloads
    List<SchedulePayload> listOfPayloads = response.getApiResponse().getSchedulePayloads();
    
    // List of Schedule URLs
    List<String> listOfScheduleURLs = response.getApiResponse().getScheduleUrls();

Optionally, scheduled pushes can be configured to be delivered at the device's local time.
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

Scheduled pushes may not be scheduled for a time that has already passed.  Doing so will
result in a HTTP 400 response and an APIResponseException.

List Schedules
==============

List all existing schedules.

.. code-block:: java

    APIClientResponse<APIListAllSchedulesResponse> response = apiClient.listAllSchedules();

    APIListAllSchedulesResponse obj = response.getApiResponse();

    // Number of scheduled pushes in this response
    int count = obj.getCount();

    // Total number of scheduled pushes in the app
    int totalCount = obj.getTotal_Count();

    // URL for the next page of schedule pushes
    String nextPage = obj.getNext_Page();

    // List of SchedulePayloads
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

    // Operation ID
    String operationID = response.getApiResponse().getOperationId();
    
    // List of SchedulePayloads
    List<SchedulePayload> listOfPayloads = response.getApiResponse().getSchedulePayloads();
    
    // List of Schedule URLs
    List<String> listOfScheduleURLs = response.getApiResponse().getScheduleUrls();

The response is a APIScheduleResponse representing the updated state.

Delete Schedule
===============

Delete a schedule resource, which will result in no more pushes being sent.  If the 
resource is successfully deleted, the response does not include a body.

.. code-block:: java

    String id = "the_id_of_the_schedule_to_delete";
    HttpResponse response = apiClient.deleteSchedule(id);

    //Returns 204 on success
    int status = response.getStatusLine().getStatusCode();    


****
Tags
****

Tag Listing
===========

List tags that exist for this application.

.. code-block:: java
  
    APIClientResponse<APIListTagsResponse> response = apiClient.listTags();

    // List of Tags
    List<String> tags = response.getApiResponse().getTags();


Tag Creation
============

Explicitly create a tag with no devices associated with it.

.. code-block:: java

  String newTag = "California";
  HttpResponse response = apiClient.createTag(newTag);

  // Returns 200 if tag already exists
  // Returns 201 if tag was created
  // Returns 400 if tag is invalid
  int status = response.getStatusLine().getStatusCode();

Adding and Removing Devices from a Tag
======================================

Add or remove one or more devices to a particular tag.

.. code-block:: java
    
    String tag = "California";

    AddRemoveDeviceFromTagPayload payload = AddRemoveDeviceFromTagPayload.newBuilder()
        .setIOSChannels(AddRemoveSet.newBuilder()
            .add("01234567-890a-bcde-f012-34567890abc0")
            .add("01234567-890a-bcde-f012-34567890abc1")
            .add("01234567-890a-bcde-f012-34567890abc3")
            .add("01234567-890a-bcde-f012-34567890abc5")
            .add("01234567-890a-bcde-f012-34567890abc7")
            .build())
        .build();

    HttpResponse response = apiClient.addRemoveDevicesFromTag(tag, payload);

    // Returns 200 if the devices are being added or removed from this tag.
    // Returns 401 if authorization credentials are incorrect.
    int status = response.getStatusLine().getStatusCode();

Deleting a Tag
==============

Deletes a tag and removes it from devices.

.. code-block:: java
    
    HttpResponse response = apiClient.deleteTag(tag);

    // Returns 204 if the tag has been removed.
    // Returns 401 if authorization credentials are incorrect.
    // Returns 404 if the tag was not found or has already been removed.
    int status = response.getStatusLine().getStatusCode();

Batch Modification of Tags
==========================

Modify the tags for a number of devices.

.. code-block:: java
    
    BatchTagSet bts = BatchTagSet.newBuilder()
        .setDevice(BatchTagSet.DEVICEIDTYPES.IOS_CHANNEL, "ios_channel_to_tag_2")
        .addTag("GrumpyCat")
        .addTag("Kitties")
        .addTag("Puppies")
        .build();

    HttpResponse response = apiClient.batchModificationOfTags(BatchModificationPayload.newBuilder()
        .addBatchObject(bts)
        .build()
    );

    // Returns 200 if tags are being applied
    // Returns 400 if batch tag request was invalid.
    // Returns 401 if authorization credentials are incorrect.
    int status = response.getStatusLine().getStatusCode();

*******
Reports
*******

Individual Push Response Statistics
===================================

Returns detailed reports information about a specific push notification.

.. code-block:: java

    APIClientResponse<SinglePushInfoResponse> response = client.listIndividualPushResponseStatistics("pushID");

    SinglePushInfoResponse obj = response.getApiResponse();

    // Push UUID
    UUID pushUUID = obj.getPushUUID();

    // Push Time
    DateTime pushTime = obj.getPushTime();

    // Push Type
    SinglePushInfoResponse.PushType pushType = obj.getPushType();

    // Direct Responses 
    int directResponses = obj.getDirectResponses();

    // Sends
    int sends = obj.getSends();

    // Group ID, if available
    UUID groupID = obj.getGroupID().get();



Response Listing
================

Get a list of all pushes, plus basic response information, in a given timeframe.

.. code-block:: java

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
    DateTime end = start.plus(Period.hours(48));

    // Start and end date times are required parameters
    // Optional parameter: limit of 5
    // Optional parameter: begin with the id of "start_push"
    APIClientResponse<APIReportsListingResponse> response =
        client.listReportsResponseListing(start, end, Optional.of(5), Optional.of("start_push"));

    APIReportsListingResponse obj = response.getApiResponse();

    // Next page of responses, if available.
    String nextPage = obj.getNextPage();

    // List of detailed information about specific push notifications.
    List<SinglePushInfoResponse> listPushes = obj.getSinglePushInfoResponseObjects();


App Opens Report
================

Get the number of users who have opened your app within the specified time period.

.. code-block:: java
  
    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
    DateTime end = start.plus(Period.hours(48));

    // Gets app opens from start to end by month.
    // Other possible values for precision are hourly and daily.
    APIClientResponse<ReportsAPIOpensResponse> response = client.listAppsOpenReport(start, end, "monthly");

    ReportsAPIOpensResponse obj = response.getApiResponse();

    // Returns a list of Open objects
    List<Opens> listOpens = obj.getObject();

    // Get first open object
    Open openObj = listOpens.get(0);

    // Get number of Android opens
    long android = openObj.getAndroid();

    // Get number of IOS opens
    long ios = openObj.getIos();

    // Get time corresponding to the result
    DateTime time = openObj.getDate();


Time in App Report
==================

Get the average amount of time users have spent in your app within the specified time period.

.. code-block:: java

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
    DateTime end = start.plus(Period.hours(48));

    // Gets time in app report from start to end by month.
    // Other possible values for precision are hourly and daily.
    APIClientResponse<ReportsAPITimeInAppResponse> response = client.listTimeInAppReport(start, end, "monthly");

    ReportsAPITimeInAppResponse obj = response.getApiResponse();

    // Returns a list of TimeInApp objects
    List<TimeInApp> listTimeInApp = obj.getObject();

    // Get first TimeInApp object
    TimeInApp timeInAppObj = listTimeInApp.get(0);

    // Get amount of time in app for Android
    float android = timeInAppObj.getAndroid();

    // Get amount of time in app for iOS
    float ios = timeInAppObj.getIos();

    // Get time corresponding to the result.
    DateTime time = timeInAppObj.getDate();


Statistics
==========

Return hourly counts for pushes sent for this application.

JSON format
-----------

.. code-block:: java
    
    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
    DateTime end = start.plus(Period.hours(48));

    // JSON result is deserialized to a list of AppStats objects
    APIClientResponse<List<AppStats>> response = client.listPushStatistics(start, end);

    // Get list of AppStat objects
    List<AppStats> listStats = response.getApiResponse();

    // Retrieve first object in list
    AppStats as = listStats.get(0);

    // Get the start date corresponding to this set of hourly counts
    DateTime start = as.getStart();

    // Get IOS counts
    int ios = as.getiOSCount();

    // Get BlackBerry counts
    int blackberry = as.getBlackBerryCount();

    // Get C2DM counts
    int c2dm = as.getC2DMCount();

    // Get GCM counts
    int gcm = as.getGCMCount();

    // Get Windows 8 counts
    int windows8 = as.getWindows8Count();

    // Get Windows Phone 8 counts
    int windowsPhone8 = as.getWindowsPhone8Count();

CSV format
----------

.. code-block:: java
    
    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
    DateTime end = start.plus(Period.hours(48));

    APIClientResponse<String> response = client.listPushStatisticsInCSVString(start, end);

    // CSV Response String
    String csv = response.getApiResponse();


Per Push Reporting
==================

Retrieve data specific to the performance of an individual push.

Detail
------

Get all the analytics detail for a specific push ID.

.. code-block:: java

  String pushID = "push_id";

  // Fetches the analytics detail for a given push id
  APIClientResponse<PerPushDetailResponse> response = apiClient.listPerPushDetail(pushID);

  // Get PerPushDetailResponse object
  PerPushDetailResponse obj = response.getApiResponse();

  // Get App Key
  String appKey = obj.getAppKey();

  // Get Push ID
  UUID pushID = obj.getPushID();

  // Get time created, if available
  DateTime created = obj.getCreated().get();

  // Get Push Body, if available
  Base64ByteArray pushBody = obj.getPushBody().get();

  // Get number of rich deletions
  long richDeletions = obj.getRichDeletions();

  // Get number of rich responses
  long richResponses = obj.getRichResponses();

  // Get number of rich sends
  long richSends = obj.getRichSends();

  // Get number of sends
  long sends = obj.getSends();

  // Get number of direct responses
  long directResponses = obj.getDirectResponses();

  // Get number of influenced responses
  long influencedResponses = obj.getInfluencedResponses();

  // Get Map of Platform counts
  Map<PlatformType, PerPushCounts> platformCountMap = obj.getPlatforms();

  // Get IOS platform counts
  PerPushCounts iosCounts = platformCountMap.get(PlatformType.IOS);

  // Get IOS platform direct responses
  long iosDirectResponses = iosCounts.getDirectResponses();

  // Get IOS influenced responses
  long iosInfluencedResponses = iosCounts.getInfluencedResponses();

  // Get IOS sends
  long iosSends = iosCounts.getSends();


Series
------

Get all the analytics detail for a specific push ID over time.

.. code-block:: java

    // Fetches the analytics detail for a given push id over time and precision
    APIClientResponse<PerPushSeriesResponse> response = 
        apiClient.listPerPushSeries(id, "MONTHLY", DateTime.parse("2013-07-01T00:00:00.000-07:00"), DateTime.now());

    // Get PerPushSeriesResponse object
    PerPushSeriesResponse obj = response.getApiResponse();

    // Get App Key
    String appKey = obj.getAppKey();

    // Get Push ID
    UUID pushID = obj.getPushID();

    // Get start time
    DateTime start = obj.getStart();

    // Get end time
    DateTime end = obj.getEnd();

    // Get precision
    String precision = obj.getPrecision();

    // Get List of PlatformCounts objects
    List<PlatformCounts> counts = obj.getCounts();

    // Get timestamp 
    DateTime = counts.getTime();

    // Get Map of push counts
    Map<PlatformType, PerPushCounts> pushPlatforms = counts.getPushPlatforms();

    // Get IOS platform counts
    PerPushCounts iosCounts = pushPlatforms.get(PlatformType.IOS);

    // Get IOS platform direct responses
    long iosDirectResponses = iosCounts.getDirectResponses();

    // Get IOS influenced responses
    long iosInfluencedResponses = iosCounts.getInfluencedResponses();

    // Get Map of rich push counts
    Map<PlatformType, RichPerPushCounts> richPushPlatforms = counts.getRichPushPlatforms();

    // Get IOS rich platform counts
    RichPerPushCounts iosRichCounts = richPushPlatforms.get(PlatformType.IOS);

    // Get IOS rich platform sends
    long iosRichSends = iosRichCounts.getSends();

    // Get IOS rich platform responses
    long iosRichResponses = iosRichCounts.getResponses();


******************
Device Information
******************

Individual Device Lookup
========================

Get information on an individual channel.

.. code-block:: java

    String channel = "channel_id";

    APIClientResponse<APIListSingleChannelResponse> response = apiClient.listChannel(channel);

    APIListSingleChannelResponse obj = response.getApiResponse();

    ChannelView cv = obj.getChannelObject();

    // Get the channel ID
    String channelID = cv.getChannelId();

    // Get the creation date, expressed in milliseconds since Unix epoch time
    long created = cv.getCreatedMillis();

    // Get the string representing the device type
    String deviceType = cv.getDeviceType().toString();

    // Get a set of tags associated with the channel
    Set<String> tags = cv.getTags();

    // Get the string representing the alias, if available
    String alias = cv.getAlias().get();

    // Get the background status, if available
    boolean background = cv.getBackground().get();

    // Get the date of last registration, expressed in milliseconds since Unix epoch time, if available
    long lastRegistration = cv.getLastRegistrationMillis().get();

    // Get the push address, if available
    String pushAddress = cv.getPushAddress().get();

    // get the IosSettings object, if available
    IosSettings iosSettings = cv.getIosSettings().get();



Device Listing
==============

Fetch channels registered to this application, along with associated metadata.

.. code-block:: java

    APIClientResponse<APIListAllChannelsResponse> response = apiClient.listAllChannels();

    // Get URL of next page of results, if available
    String nextPage = response.getApiResponse().getNextPage().get();

    // Get a list of ChannelView objects, each representing a channel
    List<ChannelView> channelViewList = response.getApiResponse().getChannelObjects();

    // Grab the first ChannelView object in the list
    ChannelView cv = channelViewList.get(0);

    // Get the channel ID
    String channelID = cv.getChannelId();

    // Get the creation date, expressed in milliseconds since Unix epoch time
    long created = cv.getCreatedMillis();

    // Get the string representing the device type
    String deviceType = cv.getDeviceType().toString();

    // Get a set of tags associated with the channel
    Set<String> tags = cv.getTags();

    // Get the string representing the alias, if available
    String alias = cv.getAlias().get();

    // Get the background status, if available
    boolean background = cv.getBackground().get();

    // Get the date of last registration, expressed in milliseconds since Unix epoch time, if available
    long lastRegistration = cv.getLastRegistrationMillis().get();

    // Get the push address, if available
    String pushAddress = cv.getPushAddress().get();

    // get the IosSettings object, if available
    IosSettings iosSettings = cv.getIosSettings().get();


********
Segments
********

Segments Information
====================

List All Segments
-----------------

List all of the segments for the application.

.. code-block:: java

    APIClientResponse<APIListAllSegmentsResponse> response = apiClient.listAllSegments();

    // Get URL of next page of results, if available
    String nextPage = response.getApiResponse().getNextPage();
    
    // Get a list of SegmentInformation objects, each representing a separate segment
    List<SegmentInformation> segmentInformations = response.getApiResponse().getSegments();

    // Get the first SegmentInformation in the list
    SegmentInformation si = segmentInformations.get(0);

    // Get the creation date, expressed in milliseconds since Unix epoch time
    Long creationDate = si.getCreationDate();

    // Get the modification date, expressed in milliseconds since Unix epoch time
    Long modificationDate = si.getModificationDate();

    // Get the display name of the segment
    String displayName = si.getDisplayName();

    // Get the ID of the segment
    String id = si.getId();


List Single Segment
-------------------

Fetch information about a particular segment.

.. code-block:: java
  
    // Request to fetch information about a particular segment by segment id
    APIClientResponse<AudienceSegment> response = apiClient.listSegment("a656186e-1263-4d45-964b-44e46faa2e00");

    // Get AudienceSegment object
    AudienceSegment obj = response.getApiResponse();

    // Get display name
    String displayName = obj.getDisplayName();

    // Get Operator
    Operator operator = obj.getRootOperator();

    // Get Predicate
    Predicate predicate = obj.getRootPredicate();

    // Get count
    long count = obj.getCount();

Segment Creation
================

Creates a new segment.

Helper Methods
--------------

The following helper methods are useful in reducing the verboseness of creating an operator object

.. code-block:: java

    private TagPredicate buildTagPredicate(String tag) {
         return TagPredicateBuilder.newInstance().setTag(tag).build();
    }

    private TagPredicate buildTagPredicate(String tag, String tagClass) {
         return TagPredicateBuilder.newInstance().setTag(tag).setTagClass(tagClass).build();
    }

Operator Construction
---------------------

The following is an example of how to build a complex operator

.. code-block:: java

    DateTime end = new DateTime(new Date());
    String endString = DateTimeFormats.DAYS_FORMAT.print(end);
    DateTime start = end.minusDays(5);
    String startString = DateTimeFormats.DAYS_FORMAT.print(start);

    Operator op = Operator.newBuilder(OperatorType.AND)
        .addPredicate(new LocationPredicate(new com.urbanairship.api.segments.model.LocationIdentifier(LocationAlias.newBuilder()
            .setAliasType("us_state")
            .setAliasValue("OR")
            .build()),
            new com.urbanairship.api.segments.model.DateRange(DateRangeUnit.DAYS, startString, endString), PresenceTimeframe.ANYTIME))
        .addPredicate(new LocationPredicate(new com.urbanairship.api.segments.model.LocationIdentifier(LocationAlias.newBuilder()
            .setAliasType("us_state")
            .setAliasValue("CA")
            .build()),
            new RecentDateRange(DateRangeUnit.MONTHS, 3), PresenceTimeframe.ANYTIME))
        .addOperator(Operator.newBuilder(OperatorType.OR)
            .addPredicate(buildTagPredicate("tag1"))
            .addPredicate(buildTagPredicate("tag2"))
            .build())
        .addOperator(Operator.newBuilder(OperatorType.NOT)
            .addPredicate(buildTagPredicate("not-tag"))
            .build()
        )
        .addOperator(Operator.newBuilder(OperatorType.NOT)
            .addOperator(Operator.newBuilder(OperatorType.AND)
                 .addPredicate(
                      new LocationPredicate(new com.urbanairship.api.segments.model.LocationIdentifier(LocationAlias.newBuilder()
                           .setAliasType("us_state")
                           .setAliasValue("WA")
                           .build()), new com.urbanairship.api.segments.model.DateRange(DateRangeUnit.MONTHS, "2011-05", "2012-02"),
                           PresenceTimeframe.ANYTIME))
                 .addPredicate(buildTagPredicate("woot"))
                 .build()
            )
            .build()
        )
        .build();

Creating the Segment Object
---------------------------

.. code-block:: java

    AudienceSegment segment = AudienceSegment.newBuilder()
        .setDisplayName(DateTime.now().toString())
        .setRootOperator(op)
        .build();

Making the Request
------------------

.. code-block:: java

    HttpResponse response = apiClient.createSegment(segment);

    // Returns 201 on success
    int status = response.getStatusLine().getStatusCode();

Update Segment
==============

Change the definition fo the segment.

.. code-block:: java

    String id = "segment_id";

    AudienceSegment payload = AudienceSegment.newBuilder()
        .setDisplayName("**CHANGED**")
        .setRootPredicate(TagPredicateBuilder.newInstance().setTag("CHANGE").build())
        .build();

    HttpResponse response = apiClient.changeSegment(id, payload);

    // Returns 200 on success
    int status = response.getStatusLine().getStatusCode();


Delete Segment
==============

Remove the segment.

.. code-block:: java

    String id = "segment_id";

    HttpResponse response = apiClient.deleteSegment(id);

    // Returns 204 on success
    int status = response.getStatusLine().getStatusCode();

********
Location
********

Location Boundary Information
=============================

Search for a location and return its information.

.. code-block:: java
    
    // Search for a location by name
    APIClientResponse<APILocationResponse> response = apiClient.queryLocationInformation("San Francisco");

    // Search for a location by name and type
    APIClientResponse<APILocationResponse> response = apiClient.queryLocationInformation("San Francisco", "city");

    // Search for a location by centroid point
    Point portland = Point.newBuilder()
        .setLatitude(45.52)
        .setLongitude(-122.681944)
        .build();

    APIClientResponse<APILocationResponse> response = client.queryLocationInformation(portland);

    // Search for a location by centroid point and type
    APIClientResponse<APILocationResponse> response = client.queryLocationInformation(portland, "city");

    // Search for a location by bounded box
    BoundedBox california = new BoundedBox(Point.newBuilder()
        .setLatitude(32.5343)
        .setLongitude(-124.4096)
        .build(), Point.newBuilder()
            .setLatitude(42.0095)
            .setLongitude(-114.1308)
            .build());

    APIClientResponse<APILocationResponse> response = apiClient.queryLocationInformation(california);

    // Search for a location by bounded box and type
    APIClientResponse<APILocationResponse> response = apiClient.queryLocationInformation(california, "province");


    // Get a list of Location objects
    List<Location> listOfLocations = response.getApiResponse().getFeatures().get();

    // Grab the first item in the list
    Location location = listOfLocations.get(0);

    // Get the location ID
    String locationID = location.getLocationId();

    // Get the location type
    String locationType = location.getLocationType();

    // Get the properties JSON String
    String propertiesJSONString = location.getPropertiesJsonString();

    // Get the properties JSON as a JsonNode
    JsonNode propertiesJSONNode = location.getPropertiesJsonNode();

    // If available, get a bounded box of the location
    BoundedBox box = location.getBounds().get();

    // If available, get a centroid point of the location
    Point point = location.getCentroid().get();


**********
Exceptions
**********

These are the primary exceptions that are possible in the client library.


APIRequestException
===================

``APIRequestExceptions`` are thrown in cases where the server returns a non-200
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
        // Exception thrown here
    }

The code above will throw an ``APIResponseException``

::

    1717 [main] ERROR com.urbanairship.api  - APIRequestException
    APIRequestException:
    Message:Unauthorized
    HttpResponse:HTTP/1.1 401 Unauthorized ......
    Error:APIError:Unauthorized
    Code:Optional.absent()

APIErrorDetails
===============

The ``APIErrorDetails`` object contains information on errors for requests
that are syntactically valid but are otherwise malformed. For example,
setting the platform value for a ``PushPayload`` to include both
``DeviceType.IOS and DeviceType.ANDROID but only providing a single
IOSDevicePayload for the notification would result in an error.``


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
    catch (APIRequestException ex) {
         logger.error(String.format("APIRequestException " + ex));
         logger.error("Exception " + ex.toString());

         APIError apiError = ex.getError().get();
         APIErrorDetails apiErrorDetails = apiError.getDetails().get();
         logger.error("Error " + apiError.getError());
         logger.error("Error details " + apiErrorDetails.getError());

    }
    catch (IOException e) {
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


The ``APIRequestException`` contains both the raw ``HttpResponse`` from the
underlying Apache request and the APIError. The APIError is specific
to Urban Airship functionality, and the ``APIErrorDetails`` provides
extended details for badly formed API requests. Providing this level
of detail allows for more customization. 


APIParsingException
===================

``APIParsingExceptions`` are thrown in response to parsing errors while
serializing or deserializing JSON. If this is thrown outside of
development it is most likely an issue with the library or the server,
and should be sent to the Urban Airship support team. Please include
as much information as possible, including the operation id if
present, and the request or API operation that threw the exception.

IOException
===========

In the context of this library, IOExceptions are thrown by the Apache
HttpComponents library, usually in response to a problem with the HTTP connection.
See the `Apache documentation <https://hc.apache.org>`__ for more
details.
