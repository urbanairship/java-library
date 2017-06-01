package com.urbanairship.api.push;

import com.urbanairship.api.client.*;
import com.urbanairship.api.reports.PushDetailRequest;
import com.urbanairship.api.reports.model.PushDetailResponse;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by jennifermiller on 5/28/17.
 */
public class RandomTest {

    public static void main(String args[]) {

        try {
            RandomTest test = new RandomTest();
            test.perPushReport();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void perPushReport() throws IOException {

        UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
                .setKey("ISex_TTJRuarzs9-o_Gkhg")
                .setSecret("nDq-bQ3CT92PqCIXNtQyCQ")
                .build();


        String pushID = UUID.fromString("a7b0b6c6-bdb7-4ac7-8ff3-78648b0ca8ee").toString();

        try {
            PushDetailRequest request = PushDetailRequest.newRequest()
                    .addPushId(pushID);
            Response<List<PushDetailResponse>> response = client.execute(request);

            System.out.println("" + response.getBody().get().toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        client.close();

    }
}