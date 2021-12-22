package com.freight.entity.CPPH.Porder;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-11-30 14:45:42
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class CPPHPorder {
    private PCustomerReferences customerReferences;
    private String consignmentReference;
    private String serviceStandard;
    private Sender sender;
    private Notifications notifications;
    private Receiver receiver;
    private List<StandardItems> standardItems;
    private Label label;
}