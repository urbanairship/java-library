package com.urbanairship.api.experiments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.experiments.parse.ExperimentObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ExperimentRequestTest {

    private final static ObjectMapper mapper = ExperimentObjectMapper.getInstance().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private final static String EXPERIMENT_PATH = "/api/experiments/";

    ExperimentRequest createRequest;
    ExperimentRequest validateRequest;
    String jsonPayload;

    @Before
    public void setup() throws Exception {
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
                .setAudience(Selectors.namedUser("birdperson"))
                .addVariant(variantOne)
                .addVariant(variantTwo)
                .build();

        jsonPayload = mapper.writeValueAsString(experiment);

        createRequest = ExperimentRequest.newRequest(experiment);
        validateRequest = ExperimentRequest.newRequest(experiment).setValidateOnly(true);
    }

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(createRequest.getContentType(), ContentType.APPLICATION_JSON);
        Assert.assertEquals(validateRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(createRequest.getHttpMethod(), Request.HttpMethod.POST);
        Assert.assertEquals(validateRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        Assert.assertEquals(createRequest.getRequestHeaders(), headers);
        Assert.assertEquals(validateRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(createRequest.getRequestBody(), jsonPayload);
        Assert.assertEquals(validateRequest.getRequestBody(), jsonPayload);

    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + EXPERIMENT_PATH);
        Assert.assertEquals(createRequest.getUri(baseURI), expectedUri);

        expectedUri = URI.create("https://go.urbanairship.com" + EXPERIMENT_PATH + "validate/");
        Assert.assertEquals(validateRequest.getUri(baseURI), expectedUri);
    }

    @Test
    public void testResponseParser() throws Exception {
        final ResponseParser<ExperimentResponse> responseParser = new ResponseParser<ExperimentResponse>() {
            @Override
            public ExperimentResponse parse(String response) throws IOException {
                return mapper.readValue(response, ExperimentResponse.class);
            }
        };

        String createJson =
                "{" +
                        "\"ok\" : true," +
                        "\"operation_id\": \"9ce808c8-7176-45dc-b79e-44aa74249a5a\"," +
                        "\"experiment_id\" : \"ef34a8d9-0ad7-491c-86b0-aea74da15161\"" +
                        "}";

        String validateJson =
                "{" +
                "\"ok\" : true" +
                "}";


        Assert.assertEquals(createRequest.getResponseParser().parse(createJson), responseParser.parse(createJson));
        Assert.assertEquals(validateRequest.getResponseParser().parse(validateJson), responseParser.parse(validateJson));
    }
}
