########
Location
########


*****************************
Location Boundary Information
*****************************

Use the ``LocationRequest`` class to retrieve location boundary information.

**Example: Name Lookup**

.. sourcecode:: java

   LocationRequest request = LocationRequest.newRequest("Chicago")
       .setType("city");
   Response<LocationResponse> response = client.execute(request);


**Example: Lat/Long Lookup**

.. sourcecode:: java

   Point point = Point.newBuilder()
       .setLatitude(41.8781)
       .setLongitude(87.6298)
       .build();

   LocationRequest request = LocationRequest.newRequest(point)
       .setType("city");
   Response<LocationResponse> response = client.execute(request);

**Example: Bounding Box Lookup**

.. sourcecode:: java

   Point cornerOne = Point.newBuilder()
       .setLatitude(41.8781)
       .setLongitude(87.6298)
       .build();

   Point cornerTwo = Point.newBuilder()
       .setLatitude(42.8781)
       .setLongitude(88.6298)
       .build();

   BoundedBox box = BoundedBox.newBuilder()
       .setCornerOne(cornerOne)
       .setCornerTwo(cornerTwo)
       .build();

   LocationRequest request = LocationRequest.newRequest(box)
       .setType("province");
   Response<LocationResponse> response = client.execute(request);
