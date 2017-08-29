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

****************
Delete A/B Tests
****************

To delete a A/B Test, use the ``ExperimentDeleteRequest.newRequest()`` method.

.. sourcecode:: java

   ExperimentDeleteRequest experimentDeleteRequest = ExperimentDeleteRequest.newRequest("experimentId");
   Response<ExperimentResponse> response = client.execute(experimentDeleteRequest);