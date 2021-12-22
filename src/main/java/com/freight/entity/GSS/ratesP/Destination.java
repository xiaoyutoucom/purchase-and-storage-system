package com.freight.entity.GSS.ratesP; /**
  * Copyright 2021 bejson.com 
  */


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2021-10-09 10:57:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Destination {
//    @JsonProperty(value = "Id")
//    private int Id;
    @JsonProperty(value = "Name")
    private String Name;
    @JsonProperty(value = "Address")
    private Address Address;
//    @JsonProperty(value = "ContactPerson")
//    private String ContactPerson;
    @JsonProperty(value = "PhoneNumber")
    private String PhoneNumber;
    @JsonProperty(value = "Email")
    private String Email;
//    @JsonProperty(value = "DeliveryInstructions")
//    private String DeliveryInstructions;


}