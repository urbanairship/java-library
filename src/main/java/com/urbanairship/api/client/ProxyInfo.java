/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;


/**
 * Wrapper class for ProxyServer metadata.
 */
public final class ProxyInfo {

    private final ProxyInfoProtocol protocol;
    private final String host;
    private final Integer port;
    private final String principal;
    private final String password;

    private ProxyInfo(Builder builder) {
        this.protocol = builder.protocol;
        this.host = builder.host;
        this.port = builder.port;
        this.principal = builder.principal;
        this.password = builder.password;
    }

    /**
     * Builder factory method.
     *
     * @return ProxyInfo Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the proxy protocol.
     *
     * @return The protocol.
     */
    public String getProtocol() {
        return protocol.toString().toLowerCase();
    }

    /**
     * Get the proxy host.
     *
     * @return The host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Get the proxy port.
     *
     * @return The port.
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Get the proxy principal.
     *
     * @return The principal.
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * Get the proxy password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ProxyInfo{" +
            "protocol='" + protocol + '\'' +
            ", host='" + host + '\'' +
            ", port=" + port +
            ", principal=" + principal +
            ", password=" + password +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProxyInfo)) return false;

        ProxyInfo proxyInfo = (ProxyInfo) o;

        if (host != null ? !host.equals(proxyInfo.host) : proxyInfo.host != null) return false;
        if (password != null ? !password.equals(proxyInfo.password) : proxyInfo.password != null) return false;
        if (port != null ? !port.equals(proxyInfo.port) : proxyInfo.port != null) return false;
        if (principal != null ? !principal.equals(proxyInfo.principal) : proxyInfo.principal != null) return false;
        if (protocol != null ? !protocol.equals(proxyInfo.protocol) : proxyInfo.protocol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (principal != null ? principal.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private ProxyInfoProtocol protocol = ProxyInfoProtocol.HTTPS;
        private String host;
        private int port = -1;
        private String principal = null;
        private String password = null;

        private Builder() {
        }

        /**
         * Set the proxy protocol. Can be https or http.
         *
         * @param protocol The protocol.
         * @return Builder
         */
        public Builder setProtocol(ProxyInfoProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        /**
         * Set the proxy host.
         *
         * @param host The host.
         * @return Builder
         */
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * Set the proxy port.
         *
         * @param port The port.
         * @return Builder
         */
        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        /**
         * Set the proxy principal. This is optional.
         *
         * @param principal The principal.
         * @return Builder
         */
        public Builder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        /**
         * Set the proxy password. This is optional.
         *
         * @param password The password.
         * @return Builder
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Build a ProxyInfo object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. Proxy host must be set.
         * 2. Proxy protocol must be set - defaults to https.
         * 3. Proxy port must be set.
         * </pre>
         *
         * @return ProxyInfo object.
         */
        public ProxyInfo build() {
            Preconditions.checkArgument(StringUtils.isNotEmpty(host), "Proxy host must be set.");
            Preconditions.checkNotNull(protocol, "Proxy protocol must be set.");
            Preconditions.checkArgument(port > 0, "Proxy port must be set.");

            return new ProxyInfo(this);
        }

    }

    /**
     * Enum of available Urban Airship API protocols.
     */
    public enum ProxyInfoProtocol {
        HTTP, HTTPS
    }
}
