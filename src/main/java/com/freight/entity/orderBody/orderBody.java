package com.freight.entity.orderBody;

import lombok.Data;

import java.util.List;

@Data
public class orderBody {
    public String storeId;
    public String carrierName;
    public String ACCOUNT;
    public String customerId;
    private double fromlat;
    private double fromlon;
    private double tolat;
    private double tolon;
    private String notificationsName;
    private String notificationsNumber;
    private String notificationsEmail;
    public String fromMobile;//phoneNumber
    public String consignmentDate ;
    public String consignmentNo ;
    public String profileName ;
    public String ChargeCode ;
    public String partyChargeSenderName ;
    public String phoneNumberSender ;
    public String SenderCode ;
    public String consignee ;
    public String consigner ;

    public String fromAddress1  ;
    public String fromAddress2  ;
    public String fromSuburb  ;//partyChargeSenderSuburb
    public String fromCity  ;//partyChargeSenderCity
    public String  fromPostcode  ;
    public String fromName  ; //name
    public String fromEmail  ; //email

    public String  fromReference  ;//receiverRef
    public String notes ; //DeliveryDesc

    public String toAddress1  ;
    public String  toAddress2  ;
    public String  toSuburb  ;//partyReveiverSuburb
    public String   toCity  ; //partyReveiverCity
    public String   toPostcode ;
    public String   toReference ; //senderRef
    public String   toContact  ;//partyReveiverName
    public String  toEmail ;
    public String toName;
    public String toMobile;//phoneNumber
    // trackingNumber => $tracking_number_display,
    public String latitude  ;
    public String longitude  ;
    public List<linesList> linesList  ;
    public String serviceRequired ;//serviceRequired
}
