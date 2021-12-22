package com.freight.entity.mainfreight.OrderP;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Party {
    public String Role;

    public String Code;

    public String Name;

    public String Address1;

    public String Address2;

    public String Suburb;

    public String City;

    public String Phone;

    public String PostCode;

}
