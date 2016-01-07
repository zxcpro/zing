package org.zxc.zing.client.provider;


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
}
