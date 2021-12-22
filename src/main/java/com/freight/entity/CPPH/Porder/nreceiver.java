package com.freight.entity.CPPH.Porder;

import java.util.List;

/**
 * Auto-generated: 2021-11-30 15:8:37
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class nreceiver {

    private String name;
    private String textNumber;
    private List<EmailAddresses> emailAddresses;
    private List<EventTypes> eventTypes;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setTextNumber(String textNumber) {
         this.textNumber = textNumber;
     }
     public String getTextNumber() {
         return textNumber;
     }

    public void setEmailAddresses(List<EmailAddresses> emailAddresses) {
         this.emailAddresses = emailAddresses;
     }
     public List<EmailAddresses> getEmailAddresses() {
         return emailAddresses;
     }

    public void setEventTypes(List<EventTypes> eventTypes) {
         this.eventTypes = eventTypes;
     }
     public List<EventTypes> getEventTypes() {
         return eventTypes;
     }

}