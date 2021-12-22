package com.freight.entity.mainfreight.Rtarck;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-12-16 11:27:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Rtarck {
    private String ourReference;
    private String yourReference;
    private String serviceType;
    private String trackingUrl;
    private List<RelatedItems> relatedItems;
    private String signedBy;
    private List<Events> events;
}