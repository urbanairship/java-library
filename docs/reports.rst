#######
Reports
#######


***************************
Platform Statistics Reports
***************************

The various reports that provide platform feedback are all handled by the
``PlatformStatsRequest`` class. This group of reports includes the App Opens Report, Time
in App Report, Opt-ins Report, Opt-outs Report, and Push Reports. Each of the following
requests requires a start date, end date, and precision.

.. sourcecode:: java

   DateTime start = new DateTime(2015, 10, 1, 12, 0, 0, 0);
   DateTime end = start.plus(Period.hours(48));

   // App Opens Report
   PlatformStatsRequest appOpensRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.APP_OPENS)
       .setStart(start)
       .setEnd(end)
       .setPrecision(Precision.HOURLY);

   // Time in App Report
   PlatformStatsRequest tiaRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.TIME_IN_APP)
       .setStart(start)
       .setEnd(end)
       .setPrecision(Precision.HOURLY);

   // Opt-ins Report
   PlatformStatsRequest optInsRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.OPT_INS)
       .setStart(start)
       .setEnd(end)
       .setPrecision(Precision.HOURLY);

   // Opt-outs Report
   PlatformStatsRequest optOutsRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.OPT_OUTS)
       .setStart(start)
       .setEnd(end)
       .setPrecision(Precision.HOURLY);

   // Push Report
   PlatformStatsRequest pushSendsRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.SENDS)
       .setStart(start)
       .setEnd(end)
       .setPrecision(Precision.HOURLY);

   Response<PlatformStatsResponse> appOpensResponse = client.execute(appOpensRequest);
   Response<PlatformStatsResponse> tiaResponse = client.execute(tiaRequest);
   Response<PlatformStatsResponse> optInsResponse = client.execute(optInsRequest);
   Response<PlatformStatsResponse> optOutsResponse = client.execute(optOutsRequest);
   Response<PlatformStatsResponse> pushSendsResponse = client.execute(pushSendsRequest);

   PlatformStats stats = appOpensResponse.getBody().get().getPlatformStatsObjects().get().get(0);
   // Get the number of iOS devices
   int ios = stats.getIos();
   // Get the number of Android devices
   int android = stats.getAndroid();
   // Get the time interval
   DateTime date = stats.getDate();


***********************************
Individual Push Response Statistics
***********************************

Use the ``PushInfoRequest.newRequst("<push_id>")`` class to get information on a particular
push id:

.. sourcecode:: java

   PushInfoRequest request = PushInfoRequest.newRequest("ca15a452-ad5d-4bd9-95bb-e190eeba32cd");
   Response<PushInfoResponse> response = client.execute(request);
   PushInfoResponse pushInfo = response.getBody().get()

   // Number of sends
   int sends = pushInfo.getSends();
   // Number of direct responses to the push
   int directResponses = pushInfo.getDirectResponses();
   // When the push was sent
   DateTime date = pushInfo.getPushTime();
   // The push type -- can be one of BROADCAST_PUSH, SCHEDULED_PUSH, TAG_PUSH, UNICAST_PUSH
   PushType type = pushInfo.getPushType();
   // The unique identifier for the push
   UUID pushId = pushInfo.getPushId();
   // The (optional) group ID
   Optional<UUID> groupId = pushInfo.getGroupId();


****************
Response Listing
****************

The ``PushListingRequest`` class is used to make requests to the ``/api/reports/responses/list``
endpoint:

.. sourcecode:: java

   DateTime start = new DateTime(2015, 10, 1, 12, 0, 0, 0);
   DateTime end = start.plus(Period.hours(48));

   PushListingRequest request = PushListingRequest.newRequest()
       .setStart(start)
       .setEnd(end)
       .setLimit(20);

   Response<PushListingResponse> response = client.execute(request);

   // Get the first item in an array of push info responses. You can use all of the getters
   // listed in the "Individual Push Response Statistics" section.
   PushInfoResponse pushInfo = response.getBody().get().getPushInfoList().get().get(0);


**********
Statistics
**********

The ``StatisticsRequest`` and ``StatisticsCsvRequest`` return application statistics:

.. sourcecode:: java

   DateTime start = new DateTime(2015, 10, 1, 12, 0, 0, 0);
   DateTime end = start.plus(Period.hours(48));

   // Return a list of StatisticsResponse objects
   StatisticsRequest request = StatisticsRequest.newRequest(start, end);
   Response<List<StatisticsResponse>> response = client.execute(request);
   // Return a csv string
   StatisticsCsvRequest request = StatisticsCsvRequest.newRequest(start, end);
   Response<String> csvResponse = client.execute(request);

   StatisticsResponse stats = response.getBody().get().get(0);
   // Get the start time
   DateTime start = stats.getStart();
   // Get the count of ios devices
   int ios = stats.getIosCount();
   // Get the count of BlackBerry devices
   int blackBerry = stats.getBlackBerryCount();
   // Get the count of C2DM devices
   int c2dm = stats.getC2dmCount();
   // Get the count of GCM devices
   int gcm = stats.getGcmCount();
   // Get the count of Windows 8 devices
   int windows8 = stats.getWindows8Count();
   // Get the count of Windows Phone 8 devices
   int windowsPhone8 = stats.getWindowsPhone8Count();
