########
Segments
########


**************
Create Segment
**************

To create a segment, use the ``SegmentRequest.newRequest()`` method:

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


**************
Lookup Segment
**************

To get information on a particular segment, use the
``SegmentLookupRequest.newRequest("<segment_id>")`` method:

.. sourcecode:: java

   SegmentLookupRequest request = SegmentLookupRequest.newRequest("<segment_id>")
   Response<SegmentView> response = client.execute(request);

   // Get the segment criteria
   Selector criteria = response.getBody().get().getCriteria();
   // Get the segment display name
   String displayName = response.getBody().get().getDisplayName();


*************
List Segments
*************

To get a list of all segments, use the ``SegmentListingRequest.newRequest()`` method:

.. sourcecode:: java

   SegmentListingRequest request = SegmentListingRequest.newRequest();
   Response<SegmentListingResponse> response = client.execute(request);

   // Get the first segment in the list
   SegmentView segment = request.getBody().get().getSegmentObjects().get(0);

   // Get the segment display name
   String displayName = segment.getDisplayName();
   // Get the segment creation date
   long creationDate = segment.getCreationDate();
   // Get the segment modification date
   long modificationDate = segment.getModificationDate();
   // Get the segment id
   String id = segment.getSegmentId();


**************
Update Segment
**************

To update a segment, use the ``SegmentRequest.newRequest("<segment_id>")`` method:

.. sourcecode:: java

   SegmentRequest request = SegmentRequest.newRequest("<segment_id>");

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


**************
Delete Segment
**************

To delete a segment, use the ``SegmentDeleteRequest.newRequest("<segment_id>")`` method:

.. sourcecode:: java

   SegmentDeleteRequest request = SegmentDeleteRequest.newRequest("<segment_id>")
   Response<String> response = client.execute(request);
