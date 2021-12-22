package com.freight.entity.mainfreight.OrderP;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageHeader {
    public String MessageType;

    public String MessageVersion;

    public int SenderID;

    public int RecipientID;

    public String Prepared;

    public int MessageID;

    public int MFTID;

}
