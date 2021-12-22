package com.freight.entity.mainfreight.OrderP;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Consignment {
    public String ConsignmentDate;

    public String ConsignmentNo;

    public Emails Emails;

    public String ProfileName;

    public Party Party;

    public Reference Reference;

    public String Carrier;

    public String ServiceRequired;

    public String ServiceType;

    public Booking Booking;

    public String DeliveryInstructions;

    public Line Line;

}
