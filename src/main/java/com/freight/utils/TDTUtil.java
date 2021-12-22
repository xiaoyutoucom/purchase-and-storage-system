package com.freight.utils;

import com.freight.common.R;
import com.freight.entity.Freight_booking_log;
import com.freight.entity.Freight_process_log;
import com.freight.entity.Rates;
import com.freight.entity.ReturnBack;
import com.freight.entity.TDT.TDTRates.Data;
import com.freight.entity.TDT.TDTRates.TDTRates;
import com.freight.entity.TDT.TDTtoken;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.entity.quoteBody.from;
import com.freight.interfaceBG.OrderInterface;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.freight.service.FREIGHT_PROCESS_LOGervice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


@Component
public class TDTUtil implements OrderInterface {
    //private Logger logger = LoggerFactory.getLogger(this.getClass());
    InterfaceCall call=new InterfaceCall();
    @Resource
    FREIGHT_BOOKING_LOGervice logservice;
    @Value("${tdt.tdt_token_url}")
    public String tdt_token_url;
    @Value("${tdt.tdt_token_key}")
    public String tdt_token_key;
    @Value("${tdt.tdt_token_secret}")
    public String tdt_token_secret;
    //tdt询价
    @Value("${tdt.tdt_rates_url}")
    public String tdt_rates_url;
    //tdt下单
    @Value("${tdt.tdt_order_url}")
    public String tdt_order_url;
    @Autowired
    private BodyUtil bdu;
    @Resource
    FREIGHT_PROCESS_LOGervice pslogservice;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public String gettoken() {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("key", tdt_token_key);
        requestBody.put("secret", tdt_token_secret);
        ReturnBack jsond = call.getjson(tdt_token_url,null,requestBody);
        TDTtoken at = gson.fromJson(jsond.getValue(), TDTtoken.class);
        String token = at.getData().getToken();
        return token;
    }
    //tdt询价
    public float rates(OrderBody postParameters) {
        try {
            String token = gettoken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization",token);
            Map<String, Object> rates = new HashMap<String, Object>();
            rates.put("postCode", postParameters.getTo().getPostCode());
            float weight = 0f;
            //for(int i = 0; i < postParameters.getItems().size(); i++)
            {
                weight += postParameters.getItems().getWeight();
            }
            rates.put("value", weight);
            ReturnBack Rates = call.getjson(tdt_rates_url,headers,rates);
            TDTRates tdtrates = gson.fromJson(Rates.getValue(), TDTRates.class);
            Data tdtdata = tdtrates.getData();
            //float temp = 0f;
            float shippingCostFromAkl = tdtdata.getShippingCostFromAkl();
            float shippingCostFromHam = tdtdata.getShippingCostFromHam();
            float shippingCostFromChc = tdtdata.getShippingCostFromChc();
            List<com.freight.entity.Rates> list = new ArrayList<>();
            Rates Akl = new Rates();
            if(shippingCostFromAkl>0.0f) {
                Akl.setType("shippingCostFromAkl");
                Akl.setValue(shippingCostFromAkl);
                list.add(Akl);
            }
            Rates Ham = new Rates();
            if(shippingCostFromHam>0.0f) {
                Ham.setType("shippingCostFromHam");
                Ham.setValue(shippingCostFromHam);
                list.add(Ham);
            }
            Rates Chc = new Rates();
            if(shippingCostFromChc>0.0f) {
                Chc.setType("shippingCostFromChc");
                Chc.setValue(shippingCostFromChc);
                list.add(Chc);
            }
            if(list.size()==0)
            {
                return 0;
            }
            float min = list.get(0).getValue();
            Rates changeRT = list.get(0);
            for(int j=0; j<list.size(); j++)
            {
                if(min > list.get(j).getValue()&&list.get(j).getValue()!=0.0f) {
                    min = list.get(j).getValue();
                    changeRT = list.get(j);
                }
            }
            return changeRT.getValue();
        }
        catch(Exception e){

            return 0;
        }
    }
    //tdt下单
    public R orders(orderBody postParameters) {
        Freight_booking_log log = new  Freight_booking_log();
        postParameters = bdu.bodyfromjdbc(postParameters);
        String Order_ref="TDT1"+postParameters.getStoreId()+new Date().getTime()/1000;
        //保存日志信息
        log.setOrder_weight(Float.valueOf(postParameters.getLinesList().get(0).getWeight()) );
        log.setOrder_cbm(Float.valueOf(postParameters.getLinesList().get(0).getVolume()) );
        log.setOrder_ref(Order_ref);
        log.setOrder_postcode_to(postParameters.toPostcode);
        log.setOrder_postcode_from(postParameters.fromPostcode);
        log.setOrder_region_to(postParameters.toSuburb);
        log.setOrder_region_from(postParameters.fromSuburb);
        log.setOrder_ref(Order_ref);
//        Freight_process_log pslog = new Freight_process_log();
//        pslog.setOrder_ref(Order_ref);
//
//        pslog.setOrder_type("TDT");
        try {
            //获取token
            String token = gettoken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization",token);
            headers.set("Content-type", "application/json;charset=utf-8");
            ReturnBack back = call.postjson(tdt_order_url, headers, postParameters);
            if(!back.isSuccess())
            {
//                pslog.setMessage(back.getValue());
//                save_process_Log(pslog);
                log.setIs_success(0);
                saveLog(log);

                return R.error().message("TDTmainfreigh下单失败"+back.getValue());
            }
            log.setIs_success(1);
            saveLog(log);
            return R.ok().message("TDT下单成功").data(back);
        }
        catch(Exception e){
//            pslog.setMessage(e.toString());
//            save_process_Log(pslog);
            log.setIs_success(0);
            saveLog(log);

            return R.error().message("TDTmainfreigh下单失败"+e.toString());
        }
    }

    @Override
    public R tracking(trackBody body) {
        return null;
    }

    @Override
    public void saveLog(Freight_booking_log log) {
        logservice.save(log);
    }
    @Override
    public void save_process_Log(Freight_process_log log) {
        pslogservice.save(log);
    }
}
