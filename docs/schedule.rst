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


