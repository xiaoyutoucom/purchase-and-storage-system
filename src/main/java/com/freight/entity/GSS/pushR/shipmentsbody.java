package com.freight.entity.GSS.pushR;

import lombok.Data;

@Data
public class shipmentsbody {
    private long SiteId;
    private String Connote;
    private double Cost;
    private int CarrierId;
    private long ConsignmentId;
    private String LabelA4;
    private String Labelpdf;
    private String trackingUrl;
}
