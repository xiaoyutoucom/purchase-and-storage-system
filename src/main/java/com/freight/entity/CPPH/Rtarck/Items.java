/**
 * Copyright 2021 bejson.com
 */
package com.freight.entity.CPPH.Rtarck;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2021-12-13 7:29:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Items {

    private String consignmentId;
    private int partNumber;
    private String barcode;
    private String eventType;
    private String eventDescription;
    private String eventDescriptionDetail;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+12")
    private Date createdOn;
    private String createdBy;
    private double lat;
    private double lon;
    private String carrierName;
    private String branchCode;
    private String runNumber;
    private List<Attachments> attachments;
    private String trackingUrl;
    private String relatedItemUrl;
    private String Ticket;
}