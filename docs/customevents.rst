#############
Custom Events
#############

**********************
Configuring the Client
**********************

You must set a bearer token on the UrbanAirshipClient for custom events requests.

.. code-block:: java

   UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
       .setKey("your-app-key-here")
       .setSecret("your-app-secret-here")
       .setBearerToken("your-bearer-token-here")
       .build();

If only custom event requests are being made, the secret is optional.

*******************
Create Custom Event
*******************

To create a custom event, use the ``CustomEventRequest.newRequest()`` method:

.. sourcecode:: java

   SegmentRequest request = SegmentRequest.newRequest();

   // Define the segment criteria
   Selector andSelector = Selectors.tags("java", "lib");
   Selector compound = Selectors.or(andSelector, Selectors.not(Selectors.tag("mfd")));
   DateRange dateRange = Selectors.weeks(3);
   Selector location = Selectors.location("us_zip", "97214", dateRange);
   Selector locationCriteria = Selectors.or(compound, location);

   // Set the request criteria and display name, and execute the request.
   request.setCriteria(locationCriteria);
   request.setDisplayName("UAJavaLib");
   Response<String> response = client.execute(request);

   // Urban Airship channel identifier for the user who triggered the event.
   CustomEventUser customEventUser = CustomEventUser.newBuilder()
                   .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                   .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                   .build();

   // The body object which describes the user action.
   CustomEventBody customEventBody = CustomEventBody.newBuilder()
           .setName("purchased")
           .setValue(new BigDecimal(120.49))
           .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
           .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
           .setInteractionType("url")
           .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
           .build();

   // The date and time when the event occurred.
   DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);


   CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
           .setCustomEventBody(customEventBody)
           .setCustomEventUser(customEventUser)
           .setOccurred(occurred)
           .build();

   CustomEventRequest customEventRequest = CustomEventRequest.newRequest(customEventPayload);

   Response<CustomEventResponse> response = client.execute(customEventRequest);
