package com.freight.entity.fliway;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CONSIGNMENT_RESPONSE") //XML文件中的根标识
//@Data
public class CONSIGNMENT_RESPONSE {
    public String ACCOUNT;

    public String CONNOTE;

    public String STATUS;

//    public String URL;
}
