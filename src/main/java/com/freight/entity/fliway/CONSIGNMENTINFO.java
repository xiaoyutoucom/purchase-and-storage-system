package com.freight.entity.fliway;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CONSIGNMENTINFO") //XML文件中的根标识
@Data
public class CONSIGNMENTINFO {
    public String ACCOUNT;


    public String JOB;


    public String CONNOTE;

    public String AUTHORISED;


    public String PHONE;


    public String CONSIGNOR;


    public String FROMADD1;


    public String FROMADD2;


    public String FROMSUBURB;


    public String FROMPOSTCODE;

    public String PICKUPINSTRUCTION;


    public String FROMCONTACTNAME;


    public String FROMCONTACTPHONE;


    public String READYTIME;


    public String VEHICLE;

    public String FROMCONTACTMOBILE;


    public String FROMCONTACTEMAIL;


    public String FROMADDRESSTYPE;


    public CONSIGNMENTINFODETAIL CONSIGNMENTINFODETAIL;

}
