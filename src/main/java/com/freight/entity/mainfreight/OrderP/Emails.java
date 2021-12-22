package com.freight.entity.mainfreight.OrderP;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Emails {
    public EDIEmail EDIEmail;
}
