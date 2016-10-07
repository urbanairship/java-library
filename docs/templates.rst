#########
Templates
#########


.. _templates-create-template:

***************
Create Template
***************

To create a template, use the ``TemplateRequest.newRequest()`` method.

.. sourcecode:: java

   TemplateVariable titleVariable = TemplateVariable.newBuilder()
           .setKey("TITLE")
           .setName("Title")
           .setDescription("e.g. Mr, Ms, Dr, etc")
           .setDefaultValue("")
           .build();

   TemplateVariable firstNameVariable = TemplateVariable.newBuilder()
           .setKey("FIRST_NAME")
           .setName("First Name")
           .setDescription("Given name")
           .setDefaultValue(null)
           .build();

   TemplateVariable lastNameVariable = TemplateVariable.newBuilder()
           .setKey("LAST_NAME")
           .setName("Last Name")
           .setDescription("Family name")
           .setDefaultValue("")
           .build();

   PartialPushPayload partialPushPayload = PartialPushPayload.newBuilder()
           .setNotification(Notification.newBuilder()
                   .setAlert("Hello {{TITLE}} {{FIRST_NAME}} {{LAST_NAME}}, welcome to our loyalty program!")
                   .build()
           )
           .build();

   TemplateRequest request = TemplateRequest.newRequest()
           .setName("Template Name")
           .setDescription("A description")
           .addVariable(titleVariable)
           .addVariable(firstNameVariable)
           .addVariable(lastNameVariable)
           .setPush(partialPushPayload);

   Response<TemplateResponse> response = client.execute(request);

***************
Update Template
***************

To update a template, use the ``TemplateRequest.newRequest(<template-id>)`` method:

.. sourcecode:: java

   PartialPushPayload partialPushPayload = PartialPushPayload.newBuilder()
           .setNotification(Notification.newBuilder()
                   .setAlert("Hello {{FIRST_NAME}} {{LAST_NAME}}, this is a test!")
                   .build()
           )
           .build();

   TemplateRequest request = TemplateRequest.newRequest("template-id-123")
           .setPush(partialPushPayload);

   Response<TemplateResponse> response = client.execute(request);


****************
Push to Template
****************

To push to a template, use the ``TemplatePushRequest.newRequest()`` method. In the example
below, we use the template we created in the :ref:`templates-create-template` section.

.. sourcecode:: java

   TemplatePushPayload payload = TemplatePushPayload.newBuilder()
           .setAudience(Selectors.namedUser("named-user"))
           .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
           .setMergeData(TemplateSelector.newBuilder()
                   .setTemplateId("template-id-123")
                   .addSubstitution("FIRST_NAME", "James")
                   .addSubstitution("LAST_NAME", "Brown")
                   .addSubstitution("TITLE", "Mr.")
                   .build())
           .build();

   TemplatePushRequest request = TemplatePushRequest.newRequest()
           .addTemplatePushPayload(payload);

   Response<TemplateResponse> response = client.execute(request);


***************
Template Lookup
***************

To lookup a template, use the ``TemplateListingRequest.newRequest("template-id")`` method:

.. sourcecode:: java

   TemplateListingRequest request = TemplateListingRequest.newRequest("template-id")
   Response<TemplateListingResponse> response = client.execute(request);


****************
Template Listing
****************

To list all templates, use the ``TemplateListingRequest.newRequest()`` method:

.. sourcecode:: java

   TemplateListingRequest request = TemplateListingRequest.newRequest()
   Response<TemplateListingResponse> response = client.execute(request);


***************
Delete Template
***************

To delete a template, use the ``TemplateDeleteRequest.newRequest("template-id")`` method:

.. sourcecode:: java

   TemplateDeleteRequest request = TemplateDeleteRequest.newRequest("template-id");
   Response<TemplateResponse> response = client.execute(req);
