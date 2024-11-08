package org.apache.dubbo.samples.spi.protocol.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyObject {

    @JsonProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
