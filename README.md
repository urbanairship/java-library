Urban Airship Java Client Library
=================================

This is the official supported Java library for the Urban Airship API.


Documentation
=============

General documentation can be found here: http://docs.urbanairship.com/
Java client library documentation can be found here: http://docs.urbanairship.com/reference/libraries/java/index.html


Installation
====================


Manual installation
-------------------

Clone the repository, and use

```
    mvn package
```

to build the jar. Add the jar, located at a path similar to:

```
    target/java-client-<version>.jar
```

If you would like a copy of the javadocs, use

```
    mvn javadoc:javadoc
```


Maven Installation
------------------

Add the following to your pom.xml

```xml
    <!-- Urban Airship Library Dependency-->
    <dependency>
        <groupId>com.urbanairship</groupId>
        <artifactId>java-client</artifactId>
        <version>VERSION</version>
        <!-- Replace VERSION with the version you want to use -->
    </dependency>
```
