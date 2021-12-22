package com.freight.utils;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.freight.common.R;
import com.freight.entity.CPPH.Porder.*;
import com.freight.entity.CPPH.Prates.CPPHPrate;
import com.freight.entity.CPPH.Prates.DeliveryAddress;
import com.freight.entity.CPPH.Rbody;
import com.freight.entity.CPPH.Rorder.CPPHRorder;
import com.freight.entity.CPPH.Rrates.CPPHRrate;
import com.freight.entity.CPPH.Rtarck.Items;
import com.freight.entity.CPPH.Rtarck.Rtracking;
import com.freight.entity.CPPH.Rtoken;
import com.freight.entity.Freight_booking_log;
import com.freight.entity.Freight_freight3placcount;
import com.freight.entity.Freight_process_log;
import com.freight.entity.ReturnBack;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.interfaceBG.OrderInterface;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.freight.service.FREIGHT_FREIGHT3PLACCOUNTervice;
import com.freight.service.FREIGHT_PROCESS_LOGervice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class CPPHUtil implements OrderInterface {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
    InterfaceCall call=new InterfaceCall();
    @Resource
    private FREIGHT_FREIGHT3PLACCOUNTervice FREIGHT3PLACCOUNT;
    @Value("${CPPH.token-url}")
    public String tokenurl;
    @Value("${CPPH.base_url}")
    public String base_url;
    @Value("${CPPH.clientId}")
    public String clientId;
    @Resource
    FREIGHT_BOOKING_LOGervice logservice;
    @Resource
    FREIGHT_PROCESS_LOGervice pslogservice;
    @Autowired
    private BodyUtil bdu;
    @Autowired
    S3Util s3;
    @Value("${AWS.bucketName}")
    String bucketName;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private ReturnBack pushback;
    private String token;

    //G
    public float rates(OrderBody ob) {
        try {
            CPPHPrate cpphp = new CPPHPrate();
            cpphp.setSaturdayDelivery(false);
            cpphp.setTxtNotifications(true);
            StandardItems item = new StandardItems();
            item.setVolume(ob.getItems().getVolume());
            item.setWeight(ob.getItems().getWeight());
            List<StandardItems> list = new ArrayList();
            list.add(item);
            cpphp.setStandardItems(list);
            DeliveryAddress dafrom = new DeliveryAddress();
            dafrom.setLat(ob.getFrom().getLat());
            dafrom.setLon(ob.getFrom().getLon());
            cpphp.setDeliveryAddress(dafrom);
            DeliveryAddress dato= new DeliveryAddress();
            dato.setLat(ob.getFrom().getLat());
            dato.setLon(ob.getFrom().getLon());
            cpphp.setPickupAddress(dato);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");
            headers.set("Authorization", "Bearer "+token);
            ReturnBack back = call.postjson(base_url+"/"+ob.getCarrierName()+"/customers/"+ob.getCustomerId()+"/rates",headers,cpphp);
            CPPHRrate rrate = gson.fromJson(back.getValue(), CPPHRrate.class);
            return rrate.getTotalRateExcludingGst();//返回下单成功后的数据
        }
        catch(Exception e){

            return 0f;
        }
    }
    //G
    public CPPHRrate ratesobject(OrderBody ob) {
        try {
            CPPHPrate cpphp = new CPPHPrate();
            cpphp.setSaturdayDelivery(false);
            cpphp.setTxtNotifications(true);
            StandardItems item = new StandardItems();
            item.setVolume(ob.getItems().getVolume());
            item.setWeight(ob.getItems().getWeight());
            List<StandardItems> list = new ArrayList();
            list.add(item);
            cpphp.setStandardItems(list);
            DeliveryAddress dafrom = new DeliveryAddress();
            dafrom.setLat(ob.getFrom().getLat());
            dafrom.setLon(ob.getFrom().getLon());
            cpphp.setDeliveryAddress(dafrom);
            DeliveryAddress dato= new DeliveryAddress();
            dato.setLat(ob.getFrom().getLat());
            dato.setLon(ob.getFrom().getLon());
            cpphp.setPickupAddress(dato);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");
            headers.set("Authorization", "Bearer "+token);
            String shipmentsjson = JSONObject.fromObject(cpphp).toString();
            ReturnBack back = call.postjson(base_url+"/"+ob.getCarrierName()+"/customers/"+ob.getCustomerId()+"/rates",headers,cpphp);
            CPPHRrate rrate = gson.fromJson(back.getValue(), CPPHRrate.class);
            return rrate;//返回下单成功后的数据
        }
        catch(Exception e){
            return null;
        }
    }
    public R tracking(trackBody body) {
        String back = "";
        try {
            token = GetToken();
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("warehouse", body.getStoreId());
            Freight_freight3placcount ft = FREIGHT3PLACCOUNT.getOne(wrapper);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");
            headers.set("Authorization", "Bearer " + token);
             back = call.getjson(base_url + "/" + ft.getCompany() + "/customers/" + ft.getCustomerid() + "/consignments/" + body.getConsignmentId() + "/items/" + body.getConsignmentId() + ".1/tracking-events", headers);
            Rtracking rorder = gson.fromJson(back, Rtracking.class);
            rorder.getItems().sort(Comparator.comparing(Items::getCreatedOn).reversed());//之前估计已经排序以防万一自己排一边  按照单价升序.reversed()
            return R.ok().message("查询成功").data(rorder.getItems());//返回下单成功后的数据
        }
        catch(Exception e) {
            return R.ok().message("查询失败").data(back);//返回下单成功后的数据
        }

    }
    //获取token 的方法
    public String GetToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //headers.set("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
            params.add("grant_type","client_credentials");
            ReturnBack back = call.postobject(tokenurl+"?grant_type=client_credentials",headers,null,clientId);
            Rtoken rtoken = gson.fromJson(back.getValue(), Rtoken.class);
            return rtoken.getAccess_token();
        }
        catch(Exception e){
            return null;
        }
    }
    //下单方法
    public R orders(orderBody FormData) {

        Freight_booking_log log = new  Freight_booking_log();
        Freight_process_log pslog = new Freight_process_log();
//        pslog.setOrder_ref(Order_ref);
//        pslog.setOrder_type("GSS");
        try {

            //转换from数据
            FormData = bdu.bodyfromjdbc(FormData);

            token =GetToken();

            //下单功能编写
            CPPHPorder orderP = new CPPHPorder();
            PCustomerReferences cr = new PCustomerReferences();
            cr.setOrderReference(FormData.getFromReference());
            orderP.setCustomerReferences(cr);
            orderP.setConsignmentReference(FormData.getConsignmentNo());
            orderP.setServiceStandard("OVERNIGHT");
            if(FormData.getCarrierName().equals("PostHaste")) {
                if (FormData.getConsigner().equals("christchurch")) {
                    if (Integer.parseInt(FormData.getToPostcode()) < 7000)
                        orderP.setServiceStandard("Two Day");
                } else if (Integer.parseInt(FormData.getToPostcode()) >= 7000) {
                    orderP.setServiceStandard("Two Day");
                }
            }
            if(FormData.getCarrierName().equals("castleparcels")) {
                if (FormData.getConsigner().equals("christchurch")) {
                    if (Integer.parseInt(FormData.getToPostcode()) < 7000)
                        orderP.setServiceStandard("Two Day");
                }
            }
            Label lable = new Label();
            lable.setFormat("PDF");
            lable.setSize("FREThermal");
            orderP.setLabel(lable);

            List<StandardItems> list = new ArrayList();
            for(int i=0;i<FormData.getLinesList().size();i++)
            {
                StandardItems item = new StandardItems();
                float Volume=(float)Math.round(Float.parseFloat(FormData.getLinesList().get(i).getVolume())*10000)/10000;
                float Weight=(float)Math.round(Float.parseFloat(FormData.getLinesList().get(i).getWeight())*10000)/10000;
                item.setVolume(Volume);
                item.setWeight(Weight);
                list.add(item);
            }

            orderP.setStandardItems(list);
            Sender sd = new Sender();
            sd.setName(FormData.getFromName());
            sd.setEmail(FormData.getFromEmail());
            sd.setPhoneNumber(FormData.getFromMobile());
            Address addf = new Address();
            addf.setPostCode(FormData.getFromPostcode());
            addf.setTown(FormData.getFromCity());
            addf.setSuburb(FormData.getFromSuburb());
            addf.setStreet(FormData.getFromAddress1());
            addf.setLat(FormData.getFromlat());
            addf.setLon(FormData.getFromlon());
            sd.setAddress(addf);
            orderP.setSender(sd);
            Receiver rv = new Receiver();
            rv.setName(FormData.getToName());
            rv.setEmail(FormData.getToEmail());
            rv.setPhoneNumber(FormData.getToMobile());
            Address addt = new Address();
            addt.setPostCode(FormData.getToPostcode());
            addt.setTown(FormData.getToCity());
            addt.setSuburb(FormData.getToSuburb());
            addt.setStreet(FormData.getToAddress1());
            addt.setLat(FormData.getTolat());
            addt.setLon(FormData.getTolon());
            rv.setAddress(addt);
            orderP.setReceiver(rv);
            Notifications noti = new Notifications();
            nreceiver rcv = new nreceiver();
            rcv.setName(FormData.getNotificationsName());
            rcv.setTextNumber(FormData.getNotificationsNumber());
            EmailAddresses emsdd = new EmailAddresses();
            emsdd.setEmailaddress(FormData.getNotificationsEmail());
            List<EmailAddresses> emsddlist = new ArrayList();
            emsddlist.add(emsdd);
            rcv.setEmailAddresses(emsddlist);
            List<EventTypes> etlist = new ArrayList<EventTypes>();
            EventTypes et=new EventTypes();
            et.setEventtype("PICKUP");
            etlist.add(et);
            EventTypes et1=new EventTypes();
            et1.setEventtype("ONBOARD");
            etlist.add(et1);
            EventTypes et2=new EventTypes();
            et2.setEventtype("DELIVERY");
            etlist.add(et2);
            rcv.setEventTypes(etlist);
            noti.setReceiver(rcv);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");
            headers.set("Authorization", "Bearer "+token);
            String shipmentsjson = JSONObject.fromObject(orderP).toString();
            //            //日志
            Freight_process_log pslogc = new Freight_process_log();
            pslogc.setMessage(shipmentsjson);
//            pslogc.setOrder_ref(base_url+"/"+FormData.getCarrierName()+"/customers/"+FormData.getCustomerId()+"/consignments");
            save_process_Log(pslogc);
            System.out.println(shipmentsjson);
            ReturnBack back = call.postjson(base_url+"/"+FormData.getCarrierName()+"/customers/"+FormData.getCustomerId()+"/consignments",headers,orderP);
            try {
                CPPHRorder rorder = gson.fromJson(back.getValue(), CPPHRorder.class);
                //日志内容赋值
                log.setOrder_ref(FormData.getConsignmentNo());
                log.setOrder_company("CPPH");
                log.setConnote_number(String.valueOf(rorder.getTrackingId()));
                Float weight=0f,cbm=0f;
                for(int i=0;i<rorder.getItems().size();i++)
                {
                    float Volume=(float)Math.round(Float.parseFloat(FormData.getLinesList().get(i).getVolume())*10000)/10000;
                    float Weight=(float)Math.round(Float.parseFloat(FormData.getLinesList().get(i).getWeight())*10000)/10000;
                    weight+=Volume;
                    cbm+=Weight;
                }
                log.setOrder_weight(weight);
                log.setOrder_cbm(cbm);
                log.setOrder_value(rorder.getCalculatedChargeIncludingGst());
                log.setOrder_postcode_to(FormData.toPostcode);
                log.setOrder_postcode_from(FormData.fromPostcode);
                log.setOrder_region_to(FormData.toSuburb);
                log.setOrder_region_from(FormData.fromSuburb);
                saveLog(log);
                //返回值初始化
                Rbody rb = new Rbody();
                rb.setConsignmentId(rorder.getConsignmentId());
                rb.setTrackingId(rorder.getTrackingId());
                rb.setCalculatedChargeIncludingGst(rorder.getCalculatedChargeIncludingGst());
                String[] item=new String[rorder.getItems().size()];
                for(int i=0;i<rorder.getItems().size();i++)
                {
                    item[i]=rorder.getItems().get(i).getConsignmentItemId();
                }
                rb.setConsignmentItemId(item);
                HttpHeaders headers2 = new HttpHeaders();
                headers2.set("Authorization", "Bearer "+token);
                File file = call.downpdf(rorder.getLabel().getHref(),headers2,rb.getConsignmentId()+".pdf");
                //上传文件
                s3.creatConnect();
                s3.getBacketObjects(bucketName);
                PutObjectResult pr= s3.creatObject(bucketName,"CPPH/pdf/", rb.getConsignmentId()+".pdf",file);
                String url =s3.getDownloadUrl(bucketName,"CPPH/pdf/"+rb.getConsignmentId()+".pdf");
                rb.setLabelurl(url);
                return R.ok().message("Gss下单成功").data(rb);//返回下单成功后的数据
            }catch(Exception e)
            {
                pslog.setMessage(back.getValue());
                pslog.setOrder_type("CPPH");
                save_process_Log(pslog);
                log.setIs_success(0);
                saveLog(log);
                return R.error().message("Gss下单失败"+back.getValue());
            }
        }
        catch(Exception e){
            pslog.setMessage(pushback.getValue());
            pslog.setOrder_type("CPPH");
            save_process_Log(pslog);
            return R.error().message("Gss下单失败"+e.toString());
        }
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
