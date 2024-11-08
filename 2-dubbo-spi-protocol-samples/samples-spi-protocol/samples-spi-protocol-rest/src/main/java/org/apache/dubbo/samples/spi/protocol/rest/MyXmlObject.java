package org.apache.dubbo.samples.spi.protocol.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MyXmlObject")
@XmlAccessorType(XmlAccessType.FIELD)
public class MyXmlObject {
    @XmlElement
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
