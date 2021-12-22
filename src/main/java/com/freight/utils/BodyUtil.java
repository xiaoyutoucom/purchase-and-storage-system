package com.freight.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.freight.entity.Freight_freight3placcount;
import com.freight.entity.Freight_warehouse;
import com.freight.entity.orderBody.orderBody;
import com.freight.service.FREIGHT_FREIGHT3PLACCOUNTervice;
import com.freight.service.FREIGHT_WAREHOUSEervice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BodyUtil {
    @Resource
    private FREIGHT_FREIGHT3PLACCOUNTervice FREIGHT3PLACCOUNT;
    @Resource
    private FREIGHT_WAREHOUSEervice WAREHOUSE;
    public orderBody bodyfromjdbc(orderBody FormData) {
        String store = FormData.getConsigner();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("warehouse", store);
        Freight_freight3placcount ft = FREIGHT3PLACCOUNT.getOne(wrapper);
        Freight_warehouse wh = WAREHOUSE.getOne(wrapper);
        if(ft!=null)
        {
            FormData.setCarrierName(ft.getCompany());
            FormData.setCustomerId(ft.getCustomerid());
        }
        if(wh!=null)
        {
            FormData.setACCOUNT(wh.getFliwayaccount());
            FormData.setStoreId(wh.getStoreid());
            FormData.setFromMobile(wh.getPhonenumber());
            FormData.setFromlat(Double.parseDouble(wh.getLat()));
            FormData.setFromlon(Double.parseDouble(wh.getLon()));
            FormData.setFromAddress1(wh.getAddressline());
            FormData.setFromSuburb(wh.getSuburb());
            FormData.setFromCity(wh.getCity());
            FormData.setFromPostcode(wh.getPostcode());
        }

        return FormData;
    }
    public orderBody bodyfrom(orderBody FormData) {
        String store = FormData.getConsigner();
        if(store.equals("hamilton"))
        {
            FormData.setFromMobile("+6496361111");
            FormData.setFromlat(-37.85112177083266);
            FormData.setFromlon(175.3420135651223);
            FormData.setFromAddress1("12 Sharpe Road");
            FormData.setFromSuburb("");
            FormData.setFromCity("Rukuhia");
        }
//        if(store.equals("mangere"))
//        {
//            FormData.setFromMobile("+6496361111");
//            FormData.setFromlat(-36.9732700614422);
//            FormData.setFromlon(174.78039736416824);
//            FormData.setFromAddress1("12 Andrew Baxter Drive");
//            FormData.setFromSuburb("Mangere");
//            FormData.setFromCity("Auckland");
//        }
        if(store.equals("otahuhu"))
        {
            FormData.setFromMobile("+6496361111");
            FormData.setFromlat(-36.94756757958915);
            FormData.setFromlon(174.83008427890627);
            FormData.setFromAddress1("8 manu street");
            FormData.setFromSuburb("Otahuhu");
            FormData.setFromCity("Auckland");
        }
        if(store.equals("christchurch"))
        {
            FormData.setFromMobile("+039749063");
            FormData.setFromlat(-43.53532186730182);
            FormData.setFromlon(174.80686682215227);
            FormData.setFromAddress1("75 Main South Road");
            FormData.setFromSuburb("Sockburn");
            FormData.setFromCity("Christchurch");
        }
        if(store.equals("auckland"))
        {
            FormData.setFromMobile("+6496361111");
            FormData.setFromlat(-36.92414268202295);
            FormData.setFromlon(174.80686682215227);
            FormData.setFromAddress1("306 Neilson Street");
            FormData.setFromSuburb("Onehunga");
            FormData.setFromCity("Auckland");
        }
      return FormData;
    }
}
