.. Urban Airship Java Client documentation master file, created by
   sphinx-quickstart on Tue Jul 16 12:21:44 2013.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Urban Airship Java Client
=========================

This is a Java library for using the `Urban Airship
<http://urbanairship.com/>`_ web service API for push notifications.

Installation
============

Add the dependency to your pom.xml.

.. code-block:: xml

        <dependency>
            <groupId>com.urbanairship</groupId>
            <artifactId>java-client</artifactId>
            <version>0.1.2</version>
        </dependency>

Alternatively, you can build a jar with  ``mvn package``  and add the
jar to your classpath.

Logging
=======

Logging is done using the `Simple Logging Facade for Java <http://www.slf4j.org>`_
Using the logging facade allow for flexibility in logging choices. For example,
to use log4j, you would add the following to your pom.xml

.. code-block:: xml

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.5</version>
    </dependency>

    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>

Note the logging framework plus the adapter. For more info, see the facade `documentation <http://www.slf4j.org/manual.html>`_
In code, simply add the log handler of your choice. Again, with log4j:

.. code-block:: java

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.apache.log4j.BasicConfigurator;

    Logger logger = LoggerFactory.getLogger("identifier");
    BasicConfigurator.configure(); // Use any configuration you need.
    logger.debug("Log all the things!");

Development
===========

Source code is available on Github.
Tests are located in the standard test directory and use JUnit


Basic Work Flow
===============

The Urban Airship Java client streamlines API requests. API
interactions all follow the same basic work flow.

#. Configure a payload using the appropriate Java model classes
#. Configure an APIClient to authenticate, send, and return a
   response.
#. Handle the response or exception appropriately.


Contents:
=========

.. toctree::
   :maxdepth: 5

   push.rst
   schedule.rst
   exception.rst


Indices and tables
==================

* :ref:`genindex`
* :ref:`search`
