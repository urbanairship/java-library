package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.reports.model.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ResponseDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();


    @Test
   public void testSomeJson() throws IOException {

       String json =
               "{\n" +
               "  \"android\": {\n" +
               "    \"direct\":1337,\n" +
               "    \"influenced\":9999\n" +
               "  },\n" +
               "  \"date\":\"2013-07-01 00:00:00\",\n" +
               "  \"ios\": {\n" +
               "    \"direct\":7331,\n" +
               "    \"influenced\":8888\n" +
               "  }\n" +
               "}";

       System.out.println(json);
       Response test = mapper.readValue(json, Response.class);
       assertNotNull(test);
       System.out.println(test);
   }
}
