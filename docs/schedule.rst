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
--------------

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
---------------

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
---------------

Delete a schedule resource, which will result in no more pushes being sent.  If the 
resource is successfully deleted, the response does not include a body.

.. code-block:: java

    String id = "the_id_of_the_schedule_to_delete";
    HttpResponse response = apiClient.deleteSchedule(id);

    int status = response.getStatusLine().getStatusCode();    //Returns 204 on success



