package com.freight.entity.GSS.pushP; /**
  * Copyright 2021 bejson.com 
  */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.freight.entity.GSS.ratesP.Destination;
import com.freight.entity.GSS.ratesP.Packages;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-10-09 16:9:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class shipmentsP {
    @JsonProperty(value = "Service")
    private String Service;
    @JsonProperty(value = "DeliveryReference")
    private String DeliveryReference;
    @JsonProperty(value = "Outputs")
    private String[] Outputs;
    @JsonProperty(value = "Carrier")
    private String Carrier;
    @JsonProperty(value = "Origin")
    private Destination Origin;
    @JsonProperty(value = "Destination")
    private Destination Destination;
    @JsonProperty(value = "Packages")
    private List<Packages> Packages;
}