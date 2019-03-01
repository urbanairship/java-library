package com.urbanairship.api.templates;

import com.urbanairship.api.client.ClientException;
import com.urbanairship.api.client.Response;
import com.urbanairship.api.client.UrbanAirshipClient;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.templates.model.PartialPushPayload;
import com.urbanairship.api.templates.model.TemplateResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TemplateBadRequestTest {

    @Test
    public void testErrorResponseParsing() throws IOException {
        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
                .setKey("ISex_TTJRuarzs9-o_Gkhg")
                .setSecret("nDq-bQ3CT92PqCIXNtQyCQ")
                .build();

        PartialPushPayload partialPushPayload = PartialPushPayload.newBuilder()
                .setNotification(Notification.newBuilder()
                        .setAlert("Hello {{FIRST_NAME}} {{LAST_NAME}}, this is a test!")
                        .build()
                )
                .build();

        TemplateRequest request = TemplateRequest.newRequest("template-id-123")
                .setPush(partialPushPayload);

        try {
            Response<TemplateResponse> response = client.execute(request);
        } catch (RuntimeException e) {
            ClientException ex = (ClientException) e.getCause().getCause();
            Assert.assertEquals(400, ex.getStatusCode());
            Assert.assertEquals("\"id\" must be a valid GUID", ex.getError().get().getError());
        }
    }
}
