package com.urbanairship.api.client;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OAuthCredentials {
    private final String clientId;
    private final Optional<String> clientSecret;
    private final Optional<String> assertionJWT;
    private final String grantType = "client_credentials";
    private final List<String> ipAddresses;
    private final List<String> scopes;
    private final Optional<String> sub;

    private OAuthCredentials(Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.assertionJWT = builder.assertionJWT;
        this.ipAddresses = builder.ipAddresses;
        this.scopes = builder.scopes;
        this.sub = builder.sub;
    }

    public static Builder newBuilder(String clientId) {
        return new Builder(clientId);
    }

    public String getClientId() {
        return clientId;
    }

    public Optional<String> getClientSecret() {
        return clientSecret;
    }

    public Optional<String> getAssertionJWT() {
        return assertionJWT;
    }

    public String getGrantType() {
        return grantType;
    }

    public List<String> getIpAddresses() {
        return ipAddresses;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public Optional<String> getSub() {
        return sub;
    }

    /**
     * Converts the OAuth credentials to a list of NameValuePair objects suitable for URL encoding.
     *
     * @return List of NameValuePair objects representing the OAuth credentials.
     */
    public List<NameValuePair> toParams() {
        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("grant_type", grantType));
        assertionJWT.ifPresent(a -> params.add(new BasicNameValuePair("assertion", a)));
        ipAddresses.forEach(ipAddress -> params.add(new BasicNameValuePair("ipaddr", ipAddress)));
        scopes.forEach(scope -> params.add(new BasicNameValuePair("scope", scope)));
        sub.ifPresent(s -> params.add(new BasicNameValuePair("sub", s)));

        return params;
    }

    public static class Builder {
        private final String clientId;
        private Optional<String> clientSecret = Optional.empty();
        private Optional<String> assertionJWT = Optional.empty();
        private List<String> ipAddresses = new ArrayList<>();
        private List<String> scopes = new ArrayList<>();
        private Optional<String> sub = Optional.empty();

        public Builder(String clientId) {
            this.clientId = clientId;
        }

        public Builder setClientSecret(String clientSecret) {
            this.clientSecret = Optional.ofNullable(clientSecret);
            return this;
        }

        public Builder setAssertionJWT(String assertionJWT) {
            this.assertionJWT = Optional.ofNullable(assertionJWT);
            return this;
        }

        public Builder setIpAddresses(List<String> ipAddresses) {
            this.ipAddresses = ipAddresses;
            return this;
        }

        public Builder setScopes(List<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder setSub(String sub) {
            this.sub = Optional.of(sub);
            return this;
        }

        public OAuthCredentials build() {
            if (clientId == null) {
                throw new IllegalStateException("clientId must not be null");
            }

            if (!clientSecret.isPresent() && !assertionJWT.isPresent()) {
                throw new IllegalStateException("Either clientSecret or assertionJWT must be provided");
            }

            if (assertionJWT.isPresent() && (clientSecret.isPresent() || sub.isPresent() || !scopes.isEmpty() || !ipAddresses.isEmpty())) {
                throw new IllegalStateException("assertionJWT should not be set with clientSecret, sub, scopes, or ipAddresses.");
            }

            if (clientSecret.isPresent() && !sub.isPresent()) {
                throw new IllegalStateException("sub is required when clientSecret is provided.");
            }

            return new OAuthCredentials(this);
        }
    }
}
