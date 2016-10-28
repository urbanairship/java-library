#########
Schedules
#########


*******************************
Create a Scheduled Notification
*******************************

You can use the ``ScheduleRequest.newRequest(<schedule>, <push_payload>)`` method to create
a scheduled notification:

.. sourcecode:: java

    PushPayload pushPayload = PushPayload.newBuilder()
        .setAudience(Selectors.all())
        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
        .setNotification(Notifications.alert("Hello next week!"))
        .build();

   DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusDays(7);

   Schedule schedule = Schedule.newBuilder()
       .setScheduledTimestamp(dateTime)
       .build();

   ScheduleRequest scheduleRequest = ScheduleRequest.newRequest(schedule, pushPayload);
   Response<ScheduleResponse> response = client.execute(scheduleRequest);


*****************
Update a Schedule
*****************

You can use the ``ScheduleRequest.newUpdateRequest(<schedule>, <push_payload>, "<schedule_id>")``
method to update a scheduled notification:

.. sourcecode:: java

    PushPayload pushPayload = PushPayload.newBuilder()
        .setAudience(Selectors.all())
        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
        .setNotification(Notifications.alert("Hello next week!"))
        .build();

   DateTime dateTime = DateTime.now(DateTimeZone.UTC).plusDays(7);

   Schedule schedule = Schedule.newBuilder()
       .setScheduledTimestamp(dateTime)
       .build();

   ScheduleRequest scheduleRequest = ScheduleRequest.newUpdateRequest(schedule, pushPayload, "schedule_1234");
   Response<ScheduleResponse> response = client.execute(scheduleRequest);


***************
Lookup Schedule
***************

To lookup a schedule, use the ``ScheduleListingRequest.newRequest("<schedule_id>")`` method:

.. sourcecode:: java

   ListSchedulesRequest request = ScheduleListingRequest.newRequest("schedule_1234");
   Response<ListAllSchedulesResponse> response = client.execute(request);
   SchedulePayload schedule = response.getBody().get().getSchedules().get(0);

   // Get the schedule's name
   Optional<String> name = schedule.getName();
   // Get the push IDs
   Set<String> pushIds = schedule.getPushIds();
   // Get the scheduled time
   Schedule sched = schedule.getSchedule();
   // Get the associated push payload
   PushPayload payload = schedule.getPushPayload();
   // Get the URL
   Optional<String> url = schedule.getUrl();


**************
List Schedules
**************

To view a list of all created schedules, use the ``ScheduleListingRequest.newRequest()`` method:

.. sourcecode:: java

   ListSchedulesRequest request = ScheduleListingRequest.newRequest();
   Response<ListAllSchedulesResponse> response = client.execute(request);

   // Get the list of schedules
   List<SchedulePayload> schedules = response.getBody().get().getSchedules();


*****************
Delete a Schedule
*****************

To delete a schedule, use the ``ScheduleDeleteRequest.newRequest("<schedule_id>")`` method:

.. sourcecode:: java

   DeleteScheduleRequest request = ScheduleDeleteRequest.newRequest("schedule_1234");
   Response<String> response = client.execute(request);
