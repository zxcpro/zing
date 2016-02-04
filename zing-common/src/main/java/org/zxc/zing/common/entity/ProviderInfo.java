package org.zxc.zing.common.entity;


/**
 * Created by xuanchen.zhao on 15-12-11.
 */

public class ProviderInfo {
    private String address;
    private int port;

    public ProviderInfo(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderInfo that = (ProviderInfo) o;

        if (port != that.port) return false;
        if (!address.equals(that.address)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProviderInfo{");
        sb.append("address='").append(address).append('\'');
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }
}
