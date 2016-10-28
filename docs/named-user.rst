###########
Named Users
###########


********************
Associate Named User
********************

To associate channels to a Named User, use the ``NamedUserRequest`` class:

.. sourcecode:: java

   NamedUserRequest request = NamedUserRequest.newAssociateRequest()
           .setChannel("ee4b5101-164c-485c-ad91-68b1d3d753cc", ChannelType.IOS)
           .setChannel("0ab7d6f0-0f61-4963-afe0-5ef53735b00d", ChannelType.ANDROID)
           .setNamedUserId("id-1234");

   Response<String> response = client.execute(request);


***********************
Disassociate Named User
***********************

To disassociate channels from a Named User, use the ``NamedUserRequest`` class:

.. sourcecode:: java

   NamedUserRequest request = NamedUserRequest.newDisassociateRequest()
           .setChannel("ee4b5101-164c-485c-ad91-68b1d3d753cc", ChannelType.IOS)
           .setChannel("0ab7d6f0-0f61-4963-afe0-5ef53735b00d", ChannelType.ANDROID);

   Response<String> response = client.execute(request);


*****************
Lookup Named User
*****************

To lookup a named user, use the ``NamedUserListRequest.newRequest("<named_user_id>")`` method:

.. sourcecode:: java

   NamedUserListingRequest request = NamedUserListingRequest.newRequest("id-1234");
   Response<NamedUserListingResponse> response = client.execute(request);
   NamedUserView namedUser = response.getBody().get().getNamedUserView().get();

   // The named user ID
   String namedUserId = namedUser.getNamedUserId();
   // Map of tag groups and the associated sets of tags
   ImmutableMap<String, ImmutableSet<String>> namedUserTags = namedUser.getNamedUserTags();
   // All channel objects associated with the named user
   ImmutableSet<ChannelView> channelViews = namedUser.getChannelViews();


****************
List Named Users
****************

To lookup a named user, use the ``NamedUserListRequest.newRequest()`` method:

.. sourcecode:: java

   NamedUserListingRequest request = NamedUserListingRequest.newRequest();
   Response<NamedUserListingResponse> response = client.execute(request);
   ImmutableList<NamedUserView> namedUsers = response.getBody().get().getNamedUserViews().get();


***************
Named User Tags
***************

To execute tag operations on a named user, use the ``NamedUserTagRequest`` class.

**Example: Add Tags**

The ``addTags("<tag_group>", <tag_set>)`` method is used for adding tags:

.. sourcecode:: java

   Set<String> tags = new HashSet<String>();
   tags.add("loyalty");
   tags.add("platinum");
   tags.add("sports");

   NamedUserTagRequest request = NamedUserTagRequest.newRequest()
           .addNamedUsers("user-1", "user-2", "user-3")
           .addTags("device", tags);
   Response<String> response = client.execute(request);


**Example: Remove Tags**

The ``removeTags("<tag_group>", <tag_set>)`` method is used for removing tags:

.. sourcecode:: java

   Set<String> tags = new HashSet<String>();
   tags.add("loyalty");
   tags.add("platinum");
   tags.add("sports");

   NamedUserTagRequest request = NamedUserTagRequest.newRequest()
           .addNamedUsers("user-1", "user-2", "user-3")
           .removeTags("device", tags);
   Response<String> response = client.execute(request);

**Example: Set Tags**

The ``setTags("<tag_group>", <tag_set>)`` method is used to wipe the current set of tags
on the device with the provided set:

.. sourcecode:: java

   Set<String> tags = new HashSet<String>();
   tags.add("loyalty");
   tags.add("platinum");
   tags.add("sports");

   NamedUserTagRequest request = NamedUserTagRequest.newRequest()
           .addNamedUsers("user-1", "user-2", "user-3")
           .setTags("device", tags);
   Response<String> response = client.execute(request);
