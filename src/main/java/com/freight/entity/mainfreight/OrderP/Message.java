package com.freight.entity.mainfreight.OrderP;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)

// XML文件中的根标识
@XmlRootElement(name = "Message")
@Data
public class Message {
    public MessageHeader MessageHeader;
    public MessageBody MessageBody;
}
