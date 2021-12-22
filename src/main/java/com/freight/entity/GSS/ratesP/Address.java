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
public class Address {
//    @JsonProperty(value = "BuildingName")
//    private String BuildingName;
    @JsonProperty(value = "StreetAddress")
    private String StreetAddress;
    @JsonProperty(value = "Suburb")
    private String Suburb;
    @JsonProperty(value = "City")
    private String City;
    @JsonProperty(value = "PostCode")
    private String PostCode;
    @JsonProperty(value = "CountryCode")
    private String CountryCode;
}