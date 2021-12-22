package com.freight.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.freight.common.R;
import com.freight.entity.CPPH.Rtarck.Items;
import com.freight.entity.*;
import com.freight.entity.mainfreight.RateP.*;
import com.freight.entity.mainfreight.Rtarck.Events;
import com.freight.entity.mainfreight.Rtarck.Rtarck;
import com.freight.entity.mainfreight.mainfreightRates;
import com.freight.entity.orderBody.linesList;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.entity.quoteBody.items;
import com.freight.interfaceBG.OrderInterface;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.freight.service.FREIGHT_PROCESS_LOGervice;
import com.freight.service.Freight_podervice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class mainfreightUtil implements OrderInterface {
    //private Logger logger = LoggerFactory.getLogger(this.getClass());
    InterfaceCall call=new InterfaceCall();
    @Value("${mainfreight.mainfreight_order_url}")
    public String mainfreight_order_url;
    @Value("${mainfreight.mainfreight_order_Basic}")
    public String mainfreight_order_Basic;
    @Value("${mainfreight.mainfreight_tracking_url}")
    public String mainfreight_tracking_url;
    @Value("${mainfreight.mainfreight_tracking_Basic}")
    public String mainfreight_tracking_Basic;
    @Autowired
    private SaveUtil su;
    @Resource
    FREIGHT_BOOKING_LOGervice logservice;
    @Resource
    FREIGHT_PROCESS_LOGervice pslogservice;
    @Resource
    Freight_podervice podervice;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    //mainfliway询价
    public float rates(OrderBody postParameters) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");
            headers.set("Authorization", mainfreight_order_Basic);
            mainfreightRatesP main = new mainfreightRatesP();
            Account ac = new Account();
            ac.setCode(postParameters.getAccountId());
            main.setAccount(ac);
            ServiceLevel sl = new ServiceLevel();
            sl.setCode("M2H");
            main.setServiceLevel(sl);
            Origin og=new Origin();
            Date nowTime=new Date();
            SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            og.setFreightRequiredDateTime(time.format(nowTime));
            Address ad = new Address();
            ad.setCity(postParameters.getFrom().getCity());
            ad.setCountryCode("NZ");
            ad.setPostCode(postParameters.getFrom().getPostCode());
            ad.setSuburb(postParameters.getFrom().getSuburb());
            og.setAddress(ad);
            main.setOrigin(og);
            Destination dt=new Destination();
            Address adto = new Address();
            adto.setCity(postParameters.getTo().getCity());
            adto.setCountryCode("NZ");
            adto.setPostCode(postParameters.getTo().getPostCode());
            adto.setSuburb(postParameters.getTo().getSuburb());
            dt.setAddress(adto);
            main.setDestination(dt);
            List<FreightDetails> fdlist = new ArrayList<FreightDetails>();
            //for(int i=0; i<postParameters.getItems().size(); i++)
            {
                FreightDetails fd = new FreightDetails();
                items it = postParameters.getItems();
                fd.setWeight(it.getWeight()) ;
                fd.setVolume(it.getHeight()*it.getLength()*it.getWidth());
                fdlist.add(fd);
            }
            main.setFreightDetails(fdlist);
            String FormDatajson = JSONObject.fromObject(main).toString();
            ReturnBack Rates = call.postjson(mainfreight_order_url,headers,FormDatajson);
            mainfreightRates at = gson.fromJson(Rates.getValue(), mainfreightRates.class);
            if(at==null)
            {
                //logger.error("mainfreightRates查找数据为空");
                return 0;
            }
            int num = at.getCharges().size();
            return at.getCharges().get(num-1).getValue();
        }
        catch(Exception e){
            //logger.error(e.toString());
            return 0;
        }
    }
    public R tracking(trackBody body) {
        String back = "";
        Freight_process_log pslog = new Freight_process_log();
        try {
            Rtarck rt=null;
            String imgurl = "";
            QueryWrapper<Freight_pod> userQueryWrapper = Wrappers.query();
            Map<String , Object> map = new HashMap<>();
            map.put("trackingNo" , body.getConsignmentId());
            userQueryWrapper.allEq(map);
            Freight_pod fu = podervice.getOne(userQueryWrapper);
            if(fu!=null)
            {
                rt = gson.fromJson(fu.getReturnbody(), Rtarck.class);
                imgurl = fu.getRelateditemurl();
            }
            else
            {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-type", "application/json");
                headers.set("Authorization", mainfreight_tracking_Basic);
                String rbody = call.getjson(mainfreight_tracking_url + body.getConsignmentId(), headers);
                rbody = rbody.substring(1, rbody.length() - 1);
                Gson gson = new Gson();
                rt = gson.fromJson(rbody, Rtarck.class);

                //获取图片保存到s3 返回url地址
                if (rt.getRelatedItems().size() > 0) {
//                pslog.setMessage(rbody);
//                save_process_Log(pslog);
                    //for(int i=0; i<rt.getRelatedItems().size();i++)
                    {
                        String url = rt.getRelatedItems().get(0).getValue();
                        String Filename = body.getConsignmentId() + ".jpg";
                        imgurl = su.getfile(url, Filename);
                    }
                }
            //数据保存到数据库
            Freight_pod pod = new Freight_pod();
            pod.setRelateditemurl(imgurl);
            pod.setReturnbody(rbody);
            pod.setTrackingno(rt.getYourReference());
            pod.setRefno(rt.getOurReference());
            podervice.save(pod);
            }
            List<Items> listlist = new ArrayList<Items>();
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
            for(int i = 0;i < rt.getEvents().size();i++)
            {
                Events ets =rt.getEvents().get(i);
                Items item =new Items();
                item.setEventType(ets.getDisplayName());
                item.setTrackingUrl(rt.getTrackingUrl());
                item.setRelatedItemUrl(imgurl);
                String datatime=ets.getEventDateTime();
                if(datatime.indexOf('.')>-1)
                {
                    datatime=ets.getEventDateTime().substring(0,datatime.length()-3);
                }
                String datatimetype=datatime.replace('T',' ');
                Date date=sDateFormat.parse(datatimetype);
                item.setCreatedOn(date);
                listlist.add(item);
            }
            listlist.sort(Comparator.comparing(Items::getCreatedOn).reversed());
            return R.ok().message("查询成功").data(listlist);//返回下单成功后的数据
        }
        catch(Exception e) {
            return R.ok().message("查询失败").data(e.toString());//返回下单成功后的数据
        }

    }
    // mainfreigh下单的方法
    public R orders(orderBody from) {//
        Freight_booking_log log = new  Freight_booking_log();
        log.setOrder_company("M2H");
        Freight_process_log pslog = new Freight_process_log();
//        pslog.setOrder_ref(Order_ref);

        //pslog.setOrder_type("M2H");
        try {
            //传过来的参数
            Date d = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//            orderBody from = gson.fromJson(FormData, orderBody.class);
            //region
            String senderid = "";
            String chargecode= "";
            String sendercode= "";
            String storeId= "";
            String addressSender = "";
            String nameSender  = "";
            String suburbSender  = "";
            String citySender = "";
            String profileName  = "";
            String phoneNumberSender  = "";
            String store = from.getConsigner();
            if(store.equals("hamilton"))
            {
                senderid="TRADHAM";
                chargecode="TRADHAM";
                sendercode="TRADHAM";
                addressSender = "12 Sharpe Road";
                nameSender = "Trade Deopt Hamilton";
                suburbSender = "";
                citySender = "Rukuhia";
                profileName = "Hamilton";
                storeId="3";
            }
            if(store.equals("mangere"))
            {
                senderid="TRADEMNGRE";
                chargecode="TRADE14";
                sendercode="TRADEMNGRE";
                storeId="1";
                addressSender = "12 Andrew Baxter Drive";
                nameSender = "MAN Warehouse";
                suburbSender = "Mangere";
                citySender = "Auckland";
                profileName = "Trade Depot - Andrew Baxter";
            }
            if(store.equals("otahuhu"))
            {
                senderid="TRADEMANU";
                chargecode="TRADE14";
                sendercode="TRADEMANU";
                addressSender = "8 manu street";
                nameSender = "OTA Warehouse";
                suburbSender = "Otahuhu";
                citySender = "Auckland";
                profileName = "Trade Depot - Manu Street";
                storeId="1";
            }
            if(store.equals("christchurch"))
            {
                senderid="TRADE84";
                chargecode="TRADE84";
                sendercode="TRADE84";
                storeId="2";
                addressSender = "75 Main South Road";
                nameSender = "CHC Warehouse";
                suburbSender = "Sockburn";
                citySender = "Christchurch";
                profileName = "Christchurch - M2Home";
                phoneNumberSender = "03 974 9063";
            }
            if(store.equals("auckland"))
            {
                senderid="TRADE14";
                chargecode="TRADE14";
                sendercode="TRADE14";
                addressSender="306 Neilson Street";
                nameSender ="AKL Warehouse";
                suburbSender="Onehunga";
                citySender ="Auckland";
                profileName = "Trade Depot - Neilson Street";
                storeId="1";
            }
            //endregion
            //region
            from.consignmentDate = from.consignmentDate==(null)?"":from.consignmentDate;
            from.toEmail = from.toEmail==(null)?"":from.toEmail;
            from.profileName = from.profileName==(null)?"":from.profileName;
            from.partyChargeSenderName = from.partyChargeSenderName==(null)?"":from.partyChargeSenderName;
            from.fromAddress1 = from.fromAddress1==(null)?"":from.fromAddress1;
            from.fromAddress2 = from.fromAddress2==(null)?"":from.fromAddress2;
            from.fromSuburb = from.fromSuburb==(null)?"":from.fromSuburb;
            from.fromCity = from.fromCity==(null)?"":from.fromCity;
            from.phoneNumberSender = from.phoneNumberSender==(null)?"":from.phoneNumberSender;
            from.fromPostcode = from.fromPostcode==(null)?"":from.fromPostcode;
            from.toContact = from.toContact==(null)?"":from.toContact;
            from.toAddress1 = from.toAddress1==(null)?"":from.toAddress1;
            from.toAddress2 = from.toAddress2==(null)?"":from.toAddress2;
            from.toSuburb = from.toSuburb==(null)?"":from.toSuburb;
            from.toCity = from.toCity==(null)?"":from.toCity;
            from.toPostcode = from.toPostcode==(null)?"":from.toPostcode;
            from.toMobile= from.toMobile==(null)?"":from.toMobile;
            from.toReference= from.toReference==(null)?"":from.toReference;
            from.fromReference= from.fromReference==(null)?"":from.fromReference;
            from.serviceRequired= from.serviceRequired==(null)?"":from.serviceRequired;
            from.notes= from.notes==(null)?"":from.notes;
            //endregion
            String line = "";
            for(int i=0; i<from.getLinesList().size(); i++)
            {
                linesList linel = from.getLinesList().get(i);
                line += "<Line><Items>"+linel.unit+"</Items>\n" +
                        "\t<Description>"+linel.description+"</Description>\n" +
                        "\t<PackTypeCode>ITEM</PackTypeCode>\n" +
                        "\t<Measurement Property=\"CUBIC\">\n" +
                        "\t<Value>"+linel.volume+"</Value>\n" +
                        "\t<Unit>M3</Unit>\n" +
                        "\t</Measurement><Measurement Property=\"WEIGHT\"><Value>"
                        +linel.weight+"</Value><Unit>KG</Unit></Measurement></Line>";
            }
            String Order_ref="TM1"+storeId+new Date().getTime()/1000;
            //2020-06-23T11:19:44
            String xml ="<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<Message>\n" +
                    "<MessageHeader>\n" +
                    "<MessageType>MANIFEST</MessageType>\n" +
                    "<MessageVersion>02</MessageVersion>\n" +
                    "<SenderID>"+senderid+"</SenderID>\n" +
                    "<RecipientID>MAINFREIGHT</RecipientID>\n" +
                    "<Prepared>"+ft.format(d)+"</Prepared>\n" +
                    "<MessageID>TEST DW First</MessageID>\n" +
                    "<MFTID>3049302</MFTID>\n" +
                    "</MessageHeader>\n" +
                    "<MessageBody>\n" +
                    "<Consignments>\n" +
                    "<Consignment>\n" +
                    "<ConsignmentDate>"+from.consignmentDate+"</ConsignmentDate>\n" +
                    "<ConsignmentNo>"+Order_ref+"</ConsignmentNo>\n" +
                    "<Emails>\n" +
                    "<EDIEmail>\n" +
                    "<ToAddress>"+from.fromEmail+"</ToAddress>\n" +
                    "</EDIEmail>\n" +
                    "</Emails>\n" +
                    "<ProfileName>"+profileName+"</ProfileName>\n" +
                    "<Party Role='CHARGE'>\n" +
                    "<Code>"+chargecode+"</Code>\n" +
                    "<Name>"+nameSender +"</Name>\n" +
                    "<Address1>"+addressSender +"</Address1>\n" +
                    "<Address2>"+from.fromAddress2+"</Address2>\n" +
                    "<Suburb>"+suburbSender +"</Suburb>\n" +
                    "<City>"+citySender +"</City>\n" +
                    "<Phone>"+phoneNumberSender +"</Phone>\n" +
                    "</Party>\n" +
                    "<Party Role='SENDER'>\n" +
                    "<Code>"+sendercode+"</Code>\n" +
                    "<Name>"+nameSender +"</Name>\n" +
                    "<Address1>"+addressSender +"</Address1>\n" +
                    "<Address2>"+from.fromAddress2+"</Address2>\n" +
                    "<Suburb>"+suburbSender +"</Suburb>\n" +
                    "<City>"+citySender +"</City>\n" +
                    "<PostCode>"+from.fromPostcode+"</PostCode>\n" +
                    "<Phone>"+phoneNumberSender +"</Phone>\n" +
                    "</Party>\n" +
                    "<Party Role='RECEIVER'>\n" +
                    "<Name>"+from.toContact +"</Name>\n" +
                    "<Address1>"+from.toAddress1+"</Address1>\n" +
                    "<Address2>"+from.toAddress2+"</Address2>\n" +
                    "<Suburb>"+from. toSuburb+"</Suburb>\n" +
                    " <City>"+from.toCity+"</City>\n" +
                    "<PostCode>"+from.toPostcode+"</PostCode>\n" +
                    "<Phone>"+from.toMobile+"</Phone>\n" +
                    "</Party>\n" +
                    "<Reference Role=\"SENDER\">"+from.toReference+"</Reference>\n" +
                    "<Reference Role=\"RECEIVER\">"+from.fromReference+"</Reference>\n" +
                    "<Carrier>MF</Carrier>\n" +
                    "<ServiceRequired>"+from.serviceRequired+"</ServiceRequired>\n" +
                    "<ServiceType>LCL</ServiceType>\n" +
                    "<Booking>\n" +
                    "<Required>Required</Required>\n" +
                    "</Booking>\n" +
                    "<DeliveryInstructions>"+from.notes+"</DeliveryInstructions>\n" +
                    ""+line+ "\n"+
                    "</Consignment>\n" +
                    "</Consignments>\n" +
                    "<ControlTotal>\n" +
                    "<Total Units=\"CONSIGNMENTS\">1</Total>\n" +
                    "</ControlTotal>\n" +
                    "</MessageBody>\n" +
                    "</Message>" ;
            //日志订单号
            log.setOrder_weight(Float.valueOf(from.getLinesList().get(0).getWeight()) );
            log.setOrder_cbm(Float.valueOf(from.getLinesList().get(0).getVolume()) );
            log.setOrder_ref(Order_ref);
            log.setOrder_postcode_to(from.toPostcode);
            log.setOrder_postcode_from(from.fromPostcode);
            log.setOrder_region_to(from.toSuburb);
            log.setOrder_region_from(from.fromSuburb);
            boolean ok = su.Savexml(xml,Order_ref);
            Map<String, String> map =new HashMap<String, String>();
            map.put("ConsignmentNo",Order_ref);
            map.put("XmlContent",xml);
            if(ok) {
                saveLog(log);
                return R.ok().message("mainfreigh下单成功").data(map);
            }
            else
            {
                log.setIs_success(0);
                saveLog(log);
                pslog.setMessage("mainfreigh下单失败:XML文件保存失败");
                save_process_Log(pslog);
                //logger.error("mainfreigh下单失败：XML文件保存失败");
                return R.error().message("mainfreigh下单失败：XML文件保存失败");
            }
        }
        catch (Exception e) {
            pslog.setMessage(e.toString());
            save_process_Log(pslog);
            log.setIs_success(0);
            saveLog(log);
            //logger.error(e.toString());
            return R.error().message("mainfreigh下单失败"+e.toString());
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
