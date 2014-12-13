/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import org.apache.http.HttpHost;
import org.apache.http.auth.Credentials;

public final class ProxyInfo {

    private final HttpHost proxyHost;
    private final Optional<Credentials> proxyCredentials;

    private ProxyInfo(HttpHost proxyHost, Optional<Credentials> proxyCredentials) {
        this.proxyHost = proxyHost;
        this.proxyCredentials = proxyCredentials;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<Credentials> getProxyCredentials() {
        return proxyCredentials;
    }

    public HttpHost getProxyHost() {
        return proxyHost;
    }

    @Override
    public String toString() {
        return "ProxyInfo{" +
                "proxyHost=" + proxyHost +
                ", proxyCredentials=" + proxyCredentials +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(proxyHost, proxyCredentials);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProxyInfo other = (ProxyInfo) obj;
        return Objects.equal(this.proxyHost, other.proxyHost) && Objects.equal(this.proxyCredentials, other.proxyCredentials);
    }

    public static class Builder {

        private HttpHost proxyHost;
        private Credentials proxyCredentials;

        private Builder() {
        }

        public Builder setProxyHost(HttpHost value) {
            this.proxyHost = value;
            return this;
        }

        public Builder setProxyCredentials(Credentials value) {
            this.proxyCredentials = value;
            return this;
        }

        public ProxyInfo build() {
            return new ProxyInfo(proxyHost, Optional.fromNullable(proxyCredentials));
        }

    }
}
