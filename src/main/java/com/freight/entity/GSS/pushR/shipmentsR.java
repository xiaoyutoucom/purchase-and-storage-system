package com.freight.entity.GSS.pushR;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-10-09 16:53:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class shipmentsR {

    private int CarrierId;
    private String CarrierName;
    private boolean IsFreightForward;
    private boolean IsOvernight;
    private boolean IsSaturdayDelivery;
    private boolean IsRural;
    private boolean IsResidential;
    private boolean HasTrackPaks;
    private String Message;
    private String AddressLabelMessage;
    private String AddressLabelError;
    private List<String> Errors;
    private long SiteId;
    private boolean IncludePackingSlip;
    private List<Consignments> Consignments;
    private List<String> Downloads;
    private int CarrierType;
    private String AlertPath;
    private List<String> Notifications;
    private boolean HasSaturdayDeliveryLabel;
    private String SentToPrinter;
    private List<String> ExternalLinksToOpen;
    public void setCarrierId(int CarrierId) {
         this.CarrierId = CarrierId;
     }
     public int getCarrierId() {
         return CarrierId;
     }

    public void setCarrierName(String CarrierName) {
         this.CarrierName = CarrierName;
     }
     public String getCarrierName() {
         return CarrierName;
     }

    public void setIsFreightForward(boolean IsFreightForward) {
         this.IsFreightForward = IsFreightForward;
     }
     public boolean getIsFreightForward() {
         return IsFreightForward;
     }

    public void setIsOvernight(boolean IsOvernight) {
         this.IsOvernight = IsOvernight;
     }
     public boolean getIsOvernight() {
         return IsOvernight;
     }

    public void setIsSaturdayDelivery(boolean IsSaturdayDelivery) {
         this.IsSaturdayDelivery = IsSaturdayDelivery;
     }
     public boolean getIsSaturdayDelivery() {
         return IsSaturdayDelivery;
     }

    public void setIsRural(boolean IsRural) {
         this.IsRural = IsRural;
     }
     public boolean getIsRural() {
         return IsRural;
     }

    public void setIsResidential(boolean IsResidential) {
         this.IsResidential = IsResidential;
     }
     public boolean getIsResidential() {
         return IsResidential;
     }

    public void setHasTrackPaks(boolean HasTrackPaks) {
         this.HasTrackPaks = HasTrackPaks;
     }
     public boolean getHasTrackPaks() {
         return HasTrackPaks;
     }

    public void setMessage(String Message) {
         this.Message = Message;
     }
     public String getMessage() {
         return Message;
     }

    public void setAddressLabelMessage(String AddressLabelMessage) {
         this.AddressLabelMessage = AddressLabelMessage;
     }
     public String getAddressLabelMessage() {
         return AddressLabelMessage;
     }

    public void setAddressLabelError(String AddressLabelError) {
         this.AddressLabelError = AddressLabelError;
     }
     public String getAddressLabelError() {
         return AddressLabelError;
     }

    public void setErrors(List<String> Errors) {
         this.Errors = Errors;
     }
     public List<String> getErrors() {
         return Errors;
     }

    public void setSiteId(long SiteId) {
         this.SiteId = SiteId;
     }
     public long getSiteId() {
         return SiteId;
     }

    public void setIncludePackingSlip(boolean IncludePackingSlip) {
         this.IncludePackingSlip = IncludePackingSlip;
     }
     public boolean getIncludePackingSlip() {
         return IncludePackingSlip;
     }

    public void setConsignments(List<Consignments> Consignments) {
         this.Consignments = Consignments;
     }
     public List<Consignments> getConsignments() {
         return Consignments;
     }

    public void setDownloads(List<String> Downloads) {
         this.Downloads = Downloads;
     }
     public List<String> getDownloads() {
         return Downloads;
     }

    public void setCarrierType(int CarrierType) {
         this.CarrierType = CarrierType;
     }
     public int getCarrierType() {
         return CarrierType;
     }

    public void setAlertPath(String AlertPath) {
         this.AlertPath = AlertPath;
     }
     public String getAlertPath() {
         return AlertPath;
     }

    public void setNotifications(List<String> Notifications) {
         this.Notifications = Notifications;
     }
     public List<String> getNotifications() {
         return Notifications;
     }

    public void setHasSaturdayDeliveryLabel(boolean HasSaturdayDeliveryLabel) {
         this.HasSaturdayDeliveryLabel = HasSaturdayDeliveryLabel;
     }
     public boolean getHasSaturdayDeliveryLabel() {
         return HasSaturdayDeliveryLabel;
     }

    public void setSentToPrinter(String SentToPrinter) {
         this.SentToPrinter = SentToPrinter;
     }
     public String getSentToPrinter() {
         return SentToPrinter;
     }

    public void setExternalLinksToOpen(List<String> ExternalLinksToOpen) {
         this.ExternalLinksToOpen = ExternalLinksToOpen;
     }
     public List<String> getExternalLinksToOpen() {
         return ExternalLinksToOpen;
     }

}