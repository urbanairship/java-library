############
Static Lists
############


******************
Create Static List
******************

To create a static list, use the ``StaticListRequest.newRequest("<list_name>")`` method:

.. sourcecode:: java

   StaticListRequest request = StaticListRequest.newRequest("platinum_members")
           .setDescription("Subscribers with platinum status.")
           .addExtra("cool", "extras")
           .addExtra("another", "extra");

   Response<String> response = client.execute(request);


******************
Upload Static List
******************

To upload a static list, use the ``StaticListUpload.newRequest("<list_name>", "<file_path>")``
method:

.. sourcecode:: java

   File dataDirectory = new File("src/data");
   String filePath = dataDirectory.getAbsolutePath() + "/platinum.csv";
   StaticListUploadRequest request = StaticListUploadRequest.newRequest("platinum_members", filePath);
   Response<String> response = client.execute(request);


******************
Update Static List
******************

To update a list's metadata, use the ``StaticListRequest.newUpdateRequest("<list_name>")`` method:

.. sourcecode:: java

   StaticListRequest request = StaticListRequest.newUpdateRequest("test_list2")
        .setDescription("Subscribers with platinum status.")
        .addExtra("new", "extra");

   Response<String> response = client.execute(request);


******************
Lookup Static List
******************

To retrieve a list's information, use the ``StaticListLookupRequest.newRequest("<list_name>")`` method:

.. sourcecode:: java

   StaticListLookupRequest request = StaticListLookupRequest.newRequest("platinum_members")
   Response<StaticListView> response = client.execute(request);

   // Get the static list
   StaticListView staticList = response.getBody().get();
   // Get the number of channels associated with the list
   Integer channelCount = staticList.getChannelCount();
   // Get the list's creation date
   DateTime creationDate = staticList.getCreationDate();
   // Get the list's description
   Optional<String> description = staticList.getDescription();
   // Get the extras associated with the list
   Optional<ImmutableMap<String, String>> extras = staticList.getExtras();
   // Get the date the list was last updated
   DateTime lastUpdated = staticList.getLastUpdated();
   // Get the list's name
   String name = staticList.getName();
   // Get the list's status
   String status = staticList.getStatus();


*****************
List Static Lists
*****************

To list all of your static lists, use the ``StaticListListingRequest.newRequest()`` method:

.. sourcecode:: java

   StaticListListingRequest request = StaticListListingRequest.newRequest()
       .type(StaticListListingRequest.ListType.lifecycle);
   Response<StaticListListingResponse> response = client.execute(request);

   // Get the first static list in the list
   StaticListView staticList = response.getBody().get().getStaticListObjects().get(0);


******************
Delete Static List
******************

To delete a static list, use the ``StaticListDeleteRequest.newRequest()`` method:

.. sourcecode:: java

   StaticListDeleteRequest request = StaticListDeleteRequest.newRequest("platinum_members");
   Response<String> response = client.execute(req);
