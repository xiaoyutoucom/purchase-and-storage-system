package com.freight.entity.CPPH;

import lombok.Data;

@Data
public class Rbody {
    private String consignmentId;
    private String trackingId ;
    private float calculatedChargeIncludingGst  ;
    private String labelurl ;
    private String[] consignmentItemId ;
}
