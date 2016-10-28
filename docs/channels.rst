########
Channels
########


**************
Lookup Channel
**************

To lookup a specific channel, use the ``ChannelRequest.newRequest("<channel_id>")`` method:

.. sourcecode:: java

   ChannelRequest request = ChannelRequest.newRequest("channel_id_123");
   Response<ChannelResponse> response = client.execute(request);
   ChannelView channel = response.getBody().get().getChannelView().get();

   // The channel ID
   String channelId = channel.getChannelId();
   // The channel type -- one of IOS, ANDROID, or ADM
   ChannelType channelType = channel.getChannelType();
   // Whether the channel is installed or not
   boolean installed = channel.isInstalled();
   // Whether the channel is opted in to push or not
   boolean optedIn = channel.isOptIn();
   // Whether background push is enabled on the device
   Optional<Boolean> background = channel.getBackground();
   // The push address associated with the channel
   Optional<String> pushAddress = channel.getPushAddress();
   // When the channel was created
   DateTime created = channel.getCreated();
   // The date at which the channel was last registered
   DateTime lastRegistration = channel.getLastRegistration();
   // The alias (potentially) associated with the channel
   Optional<String> alias = channel.getAlias();
   // The tags associated with the channel
   ImmutableSet<String> tags = channel.getTags();
   // The tag groups associated with the channl
   ImmutableMap<String, ImmutableSet<String>> tagGroups = channel.getTagGroups();
   // An IosSettings object
   Optional<IosSettings> iosSettings = channel.getIosSettings();


*************
List Channels
*************

To list all channels, use the ``ChannelRequest.newRequest()`` method:

.. sourcecode:: java

   ChannelRequest request = ChannelRequest.newRequest();
   Response<ChannelResponse> response = client.execute(request);
   ImmutableList<ChannelView> channels = response.getBody().get().getChannelView().get();


************
Channel Tags
************

To add tags use the ``ChannelTagRequest`` class. In the following example, we add the tags
*loyalty*, *platinum*, and *sports*, and remove the tags *gold* and *news*:

.. sourcecode:: java

   Set<String> add = new HashSet<String>();
   tags.add("loyalty");
   tags.add("platinum");
   tags.add("sports");

   Set<String> remove = new HashSet<String>();
   tags.add("gold");
   tags.add("news");

   ChannelTagRequest request = ChannelTagRequest.newRequest()
       .addIOSChannels("56071f7c-921f-4981-9568-b5f7cef427cd", "a74897b2-3ff3-4741-8b69-1d739fc3830f")
       .addAndroidChannel("ecf68576-c7ac-48cc-9aaa-94b63e6dccda")
       .addTags(add)
       .removeTags(remove);

   Response<String> response = client.execute(request);
