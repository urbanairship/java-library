package com.urbanairship.api.client;

import org.apache.http.NameValuePair;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OAuthCredentialsTest {

    @Test
    public void testSetClientSecretWithAssertionAlreadySet() {
        try {
            OAuthCredentials.newBuilder("testClient")
                    .setAssertionJWT("testAssertion")
                    .setClientSecret("testSecret")
                    .build();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            assertEquals("assertionJWT should not be set with clientSecret, sub, scopes, or ipAddresses.", e.getMessage());
        }
    }

    @Test
    public void testSetAssertionWithScopesAlreadySet() {
        try {
            OAuthCredentials.newBuilder("testClient")
                    .setScopes(Arrays.asList("test"))
                    .setAssertionJWT("testAssertion")
                    .build();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            assertEquals("assertionJWT should not be set with clientSecret, sub, scopes, or ipAddresses.", e.getMessage());
        }
    }

    @Test
    public void testSetClientSecretWithoutSubSet() {
        try {
            OAuthCredentials.newBuilder("testClient")
                    .setClientSecret("test")
                    .build();
            fail("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            assertEquals("sub is required when clientSecret is provided.", e.getMessage());
        }
    }

    @Test
    public void testToRequestBodyBasicAuth() {
        OAuthCredentials credentials = OAuthCredentials.newBuilder("testClient")
                .setClientSecret("testSecret")
                .setScopes(Arrays.asList("scope1", "scope2"))
                .setIpAddresses(Arrays.asList("ip125", "ip213"))
                .setSub("testSub")
                .build();

        String expected = "grant_type=client_credentials&ipaddr=ip125&ipaddr=ip213&scope=scope1&scope=scope2&sub=testSub";
        assertEquals(expected, toParams(credentials.toParams()));
    }


    @Test
    public void testToRequestBodyAssertionJWT() {
        OAuthCredentials credentials = OAuthCredentials.newBuilder("testClient")
                .setAssertionJWT("testAssertion")
                .build();

        String expected = "grant_type=client_credentials&assertion=testAssertion";
        assertEquals(expected, toParams(credentials.toParams()));
    }

    private String toParams(List<NameValuePair> params) {
        return params.stream()
                .map(param -> param.getName() + "=" + param.getValue())
                .collect(Collectors.joining("&"));
    }

}

