package com.freight.utils;

import com.freight.common.R;
import com.freight.entity.Rates;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class FreightUtil  {

    @Resource
    FREIGHT_BOOKING_LOGervice logservice;
    @Autowired
    CPPHUtil cpph;
    //GSS访问配置
    @Autowired
    GSSUtil gu;
    //fy访问配置
    @Autowired
    fliwayUtil fy;
    //mainfreight询价
    @Autowired
    mainfreightUtil mf;
    //tdt
    @Autowired
    TDTUtil tdtu;

//    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    //询价接口
    public List<Rates> AutoRates(String freight_company,OrderBody FormData) {
        R r = null;

        List<Rates> list = new ArrayList<>();
        //tdt的价格(TDT可以送的话优先优先)
        if(freight_company==null||freight_company.equals("TDT")) {
            float tdt = tdtu.rates(FormData);
            if (tdt > 0.0f) {
                Rates tdtrrs = new Rates();
                tdtrrs.setType("TDT");
                tdtrrs.setValue(tdt);
                list.add(tdtrrs);
//            return R.ok().message("询价成功").data(tdtrrs);
            }
        }
        //GSS下单 如果包裹都是小件 走GSS
//        GSSrates gss = gson.fromJson(FormData, GSSraties.class);
//        boolean ifsmall = true;
//        //for(int i = 0; i < FormData.getItems().size(); i++)
//        {
//            if(FormData.getItems().getWeight()>20f)
//            {
//                ifsmall = false;
//            }
//        }
        if(freight_company==null||freight_company.equals("Courier")) {
            float gssr = gu.rates(FormData);
            if (gssr > 0.0f) {
                Rates gsrt = new Rates();
                gsrt.setType("Courier");
                gsrt.setValue(gssr);
                list.add(gsrt);
//                return  R.ok().message("询价成功").data(gsrt);
            }
        }
        //含有大件 并且不是TDT选择最便宜的
        //fr的价格
        if(freight_company==null||freight_company.equals("fliway")) {
            float frCost = fy.rates(FormData);
            if (frCost > 0.0f) {
                Rates frrs = new Rates();
                frrs.setType("fliway");
                frrs.setValue(frCost);
                list.add(frrs);
            }
        }
        //mfr的价格
        if(freight_company==null||freight_company.equals("M2H")) {
            float mfr = mf.rates(FormData);
            if (mfr > 0.0f) {
                Rates mfrrs = new Rates();
                mfrrs.setType("M2H");
                mfrrs.setValue(mfr);
                list.add(mfrrs);
            }
        }
        if(list.size()==0)
        {
            return null;
        }
        //获取最小值
//        float min = list.get(0).getValue();
//        Rates changeRT = list.get(0);
//        for(int j=0; j<list.size(); j++)
//        {
//                if(min > list.get(j).getValue()&&list.get(j).getValue()!=0.0f) {
//                    min = list.get(j).getValue();
//                    changeRT = list.get(j);
//                }
//        }
        //排序
        list.sort(Comparator.comparing(Rates::getValue));//之前估计已经排序以防万一自己排一边  按照单价升序.reversed()
        return  list;
    }
    //下单方法
    public R AutoOrder(String type, orderBody FormData) {
        R r = null;
        switch (type) {
            case "CPPH":
                //GSSrates gss = gson.fromJson(FormData.toString(), GSSrates.class);
                //GSSrates gss = (GSSrates)FormData;
//                String gss = (String)FormData;
                r= cpph.orders(FormData);
                break;
            case "Courier":
                //GSSrates gss = gson.fromJson(FormData.toString(), GSSrates.class);
                //GSSrates gss = (GSSrates)FormData;
//                String gss = (String)FormData;
                r= gu.orders(FormData);
                break;
            case "fliway":
//                String fw = (String)FormData;
                r= fy.orders(FormData);
                break;
            case "M2H":
//                String mfw = (String)FormData;
                r= mf.orders(FormData);
                break;
            case "TDT":
//                String tdt = (String)FormData;
                //r= tdtu.orders(FormData);
                break;
        }
        return r;
    }
}
