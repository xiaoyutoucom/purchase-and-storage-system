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
public class Packages {
    @JsonProperty(value = "Height")
    private float Height;
    @JsonProperty(value = "Length")
    private float Length;
//    @JsonProperty(value = "Id")
//    private int Id;
    @JsonProperty(value = "Width")
    private float Width;
    @JsonProperty(value = "Kg")
    private float Kg;
    @JsonProperty(value = "Name")
    private String Name;
//    @JsonProperty(value = "PackageCode")
//    private String PackageCode;
//    @JsonProperty(value = "Type")
//    private String Type;

}