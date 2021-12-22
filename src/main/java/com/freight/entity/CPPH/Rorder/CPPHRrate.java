package com.freight.entity.CPPH.Rorder;

import java.util.List;

/**
 * Auto-generated: 2021-11-30 14:30:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CPPHRrate {

    private List<String> warnings;
    private String consignmentId;
    private String trackingId;
    private double calculatedChargeExcludingGst;
    private double gst;
    private double calculatedChargeIncludingGst;
    private CustomerReferences customerReferences;
    private List<Items> items;
    private Label label;
    private boolean nonUrbanDelivery;
    private boolean noSplitDelivery;
    private String deliveryBranchCode;
    private String deliveryRun;
    public void setWarnings(List<String> warnings) {
         this.warnings = warnings;
     }
     public List<String> getWarnings() {
         return warnings;
     }

    public void setConsignmentId(String consignmentId) {
         this.consignmentId = consignmentId;
     }
     public String getConsignmentId() {
         return consignmentId;
     }

    public void setTrackingId(String trackingId) {
         this.trackingId = trackingId;
     }
     public String getTrackingId() {
         return trackingId;
     }

    public void setCalculatedChargeExcludingGst(double calculatedChargeExcludingGst) {
         this.calculatedChargeExcludingGst = calculatedChargeExcludingGst;
     }
     public double getCalculatedChargeExcludingGst() {
         return calculatedChargeExcludingGst;
     }

    public void setGst(double gst) {
         this.gst = gst;
     }
     public double getGst() {
         return gst;
     }

    public void setCalculatedChargeIncludingGst(double calculatedChargeIncludingGst) {
         this.calculatedChargeIncludingGst = calculatedChargeIncludingGst;
     }
     public double getCalculatedChargeIncludingGst() {
         return calculatedChargeIncludingGst;
     }

    public void setCustomerReferences(CustomerReferences customerReferences) {
         this.customerReferences = customerReferences;
     }
     public CustomerReferences getCustomerReferences() {
         return customerReferences;
     }

    public void setItems(List<Items> items) {
         this.items = items;
     }
     public List<Items> getItems() {
         return items;
     }

    public void setLabel(Label label) {
         this.label = label;
     }
     public Label getLabel() {
         return label;
     }

    public void setNonUrbanDelivery(boolean nonUrbanDelivery) {
         this.nonUrbanDelivery = nonUrbanDelivery;
     }
     public boolean getNonUrbanDelivery() {
         return nonUrbanDelivery;
     }

    public void setNoSplitDelivery(boolean noSplitDelivery) {
         this.noSplitDelivery = noSplitDelivery;
     }
     public boolean getNoSplitDelivery() {
         return noSplitDelivery;
     }

    public void setDeliveryBranchCode(String deliveryBranchCode) {
         this.deliveryBranchCode = deliveryBranchCode;
     }
     public String getDeliveryBranchCode() {
         return deliveryBranchCode;
     }

    public void setDeliveryRun(String deliveryRun) {
         this.deliveryRun = deliveryRun;
     }
     public String getDeliveryRun() {
         return deliveryRun;
     }

}