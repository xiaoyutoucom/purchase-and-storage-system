package com.freight.entity.CPPH.Porder;

/**
 * Auto-generated: 2021-11-30 14:45:42
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Sender {

    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPhoneNumber(String phoneNumber) {
         this.phoneNumber = phoneNumber;
     }
     public String getPhoneNumber() {
         return phoneNumber;
     }

    public void setEmail(String email) {
         this.email = email;
     }
     public String getEmail() {
         return email;
     }

    public void setAddress(Address address) {
         this.address = address;
     }
     public Address getAddress() {
         return address;
     }

}