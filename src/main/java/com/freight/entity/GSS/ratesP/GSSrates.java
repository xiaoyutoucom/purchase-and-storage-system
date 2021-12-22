package com.freight.entity.GSS.ratesP; /**
  * Copyright 2021 bejson.com 
  */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-10-09 10:57:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class GSSrates {
    @JsonProperty(value = "DeliveryReference")
    private String DeliveryReference;
    @JsonProperty(value = "Destination")
    private Destination Destination;
    @JsonProperty(value = "IsSaturdayDelivery")
    private boolean IsSaturdayDelivery;
    @JsonProperty(value = "IsSignatureRequired")
    private boolean IsSignatureRequired;
    @JsonProperty(value = "IsResidentialDelivery")
    private boolean IsResidentialDelivery;
    @JsonProperty(value = "Packages")
    private List<Packages> Packages;

}