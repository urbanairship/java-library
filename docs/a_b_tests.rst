#########
A/B Tests
#########

****************
Create A/B Tests
****************

To create a A/B Test, use the ``ExperimentRequest.newRequest()`` method.

.. sourcecode:: java

   Variant variantOne = Variant.newBuilder()
                   .setPushPayload(VariantPushPayload.newBuilder()
                           .setNotification(Notification.newBuilder()
                                   .setAlert("Hello there!")
                                   .build()
                           )
                           .build())
                   .build();

   Variant variantTwo = Variant.newBuilder()
           .setPushPayload(VariantPushPayload.newBuilder()
                   .setNotification(Notification.newBuilder()
                           .setAlert("Boogaloo")
                           .build()
                   )
                   .build())
           .build();

   Experiment experiment = Experiment.newBuilder()
           .setName("Another test")
           .setDescription("Its a test!")
           .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
           .setAudience(Selectors.namedUser("NamedUserID"))
           .addVariant(variantOne)
           .addVariant(variantTwo)
           .build();

   ExperimentRequest request = ExperimentRequest.newRequest(experiment);

   Response<ExperimentResponse> response = client.execute(request);

You can also specify a time to schedule the A/B Test by setting a Schedule object to each variant:

.. sourcecode:: java

    Schedule schedule = Schedule.newBuilder()
                    .setScheduledTimestamp(DateTime.now().plusDays(1))
                    .build();

    Variant variantOne = Variant.newBuilder()
            .setPushPayload(VariantPushPayload.newBuilder()
                    .setNotification(Notification.newBuilder()
                            .setAlert("Hello there!")
                            .build()
                    )
                    .build())
            .setSchedule(schedule)
            .build();

    Variant variantTwo = Variant.newBuilder()
            .setPushPayload(VariantPushPayload.newBuilder()
                    .setNotification(Notification.newBuilder()
                            .setAlert("Boogaloo")
                            .build()
                    )
                    .build())
            .setSchedule(schedule)
            .build();

****************
Delete A/B Tests
****************

To delete a scheduled A/B Test, use the ``ExperimentDeleteRequest.newRequest()`` method.

.. sourcecode:: java

   ExperimentDeleteRequest experimentDeleteRequest = ExperimentDeleteRequest.newRequest("experimentId");
   Response<ExperimentResponse> response = client.execute(experimentDeleteRequest);

Note that experiments can only be deleted before they start.