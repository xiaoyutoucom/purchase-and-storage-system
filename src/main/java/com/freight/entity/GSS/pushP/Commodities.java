package com.freight.entity.GSS.pushP; /**
  * Copyright 2021 bejson.com 
  */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2021-10-09 16:9:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Commodities {
    @JsonProperty(value = "HarmonizedCode")
    private String HarmonizedCode;
    @JsonProperty(value = "Description")
    private String Description;
    @JsonProperty(value = "UnitValue")
    private double UnitValue;
    @JsonProperty(value = "Units")
    private int Units;
    @JsonProperty(value = "UnitKg")
    private int UnitKg;
    @JsonProperty(value = "Country")
    private String Country;
    @JsonProperty(value = "Currency")
    private String Currency;


}