package com.freight.entity.TDT.TDTOrder;

import lombok.Data;

import java.util.List;

@Data
public class TDTOrderP {

    private String accountId;
    private String consignee;
    private String consigner;
    private String fromAddress1;
    private String fromAddress2;
    private String fromCity;
    private String fromContact;
    private String fromCountry;
    private String fromEmail;
    private String fromMobile;
    private String fromPostcode;
    private String fromReference;
    private String fromSuburb;
    private int id;
    private int latitude;
    private List<LinesList> linesList;
    private int longitude;
    private String notes;
    private int shippingType;
    private int status;
    private String toAddress1;
    private String toAddress2;
    private String toCity;
    private String toContact;
    private String toCountry;
    private String toEmail;
    private String toMobile;
    private String toPostcode;
    private String toReference;
    private String toSuburb;
    private String trackingNumber;


}