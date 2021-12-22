package com.freight.utils;

import com.freight.common.R;
import com.freight.entity.AccessToken;
import com.freight.entity.CPPH.Rtarck.Items;
import com.freight.entity.Freight_booking_log;
import com.freight.entity.Freight_process_log;
import com.freight.entity.ReturnBack;
import com.freight.entity.fliway.CONSIGNMENT_RESPONSE;
import com.freight.entity.fliway.fliwayQuote;
import com.freight.entity.fliway.track.EVENT;
import com.freight.entity.fliway.track.MILESTONE;
import com.freight.entity.orderBody.linesList;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.entity.quoteBody.items;
import com.freight.interfaceBG.OrderInterface;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.freight.service.FREIGHT_PROCESS_LOGervice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class fliwayUtil implements OrderInterface {
    //private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${fliway.fliway-url}")
    public String fliwayrates;
    @Value("${fliway.fliway_username}")
    public String fliway_username;
    @Value("${fliway.fliway_password}")
    public String fliway_password;
    @Value("${fliway.fliwayquote-url}")
    public String fliwayquote;
    @Value("${fliway.fliway_order_url}")
    public String fliway_order_url;
    @Value("${fliway.fliway_order_Basic}")
    public String fliway_order_Basic;
    @Value("${fliway.fliway_tracking_url}")
    public String fliway_tracking_url;
    @Autowired
    private SaveUtil su;
    InterfaceCall call=new InterfaceCall();
    @Resource
    FREIGHT_BOOKING_LOGervice logservice;
    @Resource
    FREIGHT_PROCESS_LOGervice pslogservice;
    @Autowired
    private BodyUtil bdu;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    //fliway询价
    public float rates(OrderBody Parameters) {
        try {


            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/x-www-form-urlencoded");
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
            postParameters.add("client_id", "fliwaytracking");
            postParameters.add("client_secret", "secret");
            postParameters.add("grant_type", "password");
            postParameters.add("scope", "openid");
            postParameters.add("username", fliway_username);
            postParameters.add("password", fliway_password);
            ReturnBack Tokenback = call.postjson(fliwayrates,headers,postParameters);

            AccessToken at = gson.fromJson(Tokenback.getValue(), AccessToken.class);
            String ftoken = at.getAccess_token();//获取token
            //调用接口查价
            headers.set("Authorization","Bearer "+ ftoken);
            //参数赋值
            Map<String, Object> rates = new HashMap<String, Object>();
            //测试数据
            rates.put("accountId", Parameters.getAccountId());
            rates.put("fromSuburbName", Parameters.getFrom().getSuburb());
            rates.put("toSuburbName", Parameters.getTo().getSuburb());
            rates.put("numberPieces", Parameters.getNumberPieces());
            float weight=0f,volume=0f;
            //for(int i=0; i<Parameters.getItems().size(); i++)
            {
                items it = Parameters.getItems();
                weight+=it.getWeight();
                volume+=it.getHeight() * it.getLength() * it.getWidth();
            }
            rates.put("weight", weight);
            rates.put("volume", volume);
            rates.put("fromPostCode", Parameters.getFrom().getPostCode());
            rates.put("toPostcode", Parameters.getTo().getPostCode());
            ReturnBack ratesback=call.getjson(fliwayquote+"?accountId={accountId}&fromSuburbName={fromSuburbName}&toSuburbName={toSuburbName}&numberPieces={numberPieces}&weight={weight}&volume={volume}&fromPostCode={fromPostCode}&toPostcode={toPostcode}",headers,rates);
            JSONArray jsonArray = JSONArray.fromObject(ratesback.getValue());
            List<fliwayQuote> back = (List<fliwayQuote>)JSONArray.toCollection(jsonArray,fliwayQuote.class);
            if(back==null)
            {
                //logger.error("fliway查找数据为空");
                return 0;
            }
//            else
//            {
//                back.sort(Comparator.comparing(fliwayQuote::getCost));//之前估计已经排序以防万一自己排一边  按照单价升序.reversed()
//            }

            fliwayQuote abOne = back.get(0);//获取最便宜的一个站点

            return abOne.getCost();
        }
        catch(Exception e){
            //logger.error(e.toString());
            return 0;
        }
    }

    //fliway下单的方法
    public R orders(orderBody FormData) {//

        Freight_booking_log log = new  Freight_booking_log();

       Freight_process_log pslog = new Freight_process_log();
//        pslog.setOrder_ref(Order_ref);
//        pslog.setOrder_type("fliway");
        try {
            //转换from数据
            FormData = bdu.bodyfromjdbc(FormData);
            HttpHeaders headers = new HttpHeaders();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            headers.setContentType(MediaType.APPLICATION_XML);
            String line = "";
            for(int i = 0; i< FormData.getLinesList().size(); i++)
            {
                linesList linel = FormData.getLinesList().get(i);
                line += "<GOODS>\n" +
                        "<TYPE>CAR</TYPE>\n" +
                        "<DESCRIPTION>"+linel.getDescription()+"</DESCRIPTION>\n" +
                        "<WEIGHT>"+linel.getWeight()+"</WEIGHT>\n" +
                        "<CUBE>"+linel.getVolume()+"</CUBE>\n" +
                        "<PIECES>1</PIECES>\n" +
                        "</GOODS>";
            }
            String Order_ref="TDF1"+FormData.getStoreId()+new Date().getTime()/1000;
            String xml ="<CONSIGNMENTINFO>\n" +
                    "<ACCOUNT>"+FormData.getACCOUNT()+"</ACCOUNT>\n" +
                    "<JOB/>\n" +
                    "<CONNOTE>"+Order_ref+"</CONNOTE>\n" +
                    "<PHONE>"+FormData.getFromMobile()+"</PHONE>\n" +
                    "<CONSIGNOR>"+FormData.getFromName()+"</CONSIGNOR>\n" +
                    "<FROMADD1>"+FormData.getFromAddress1()+"</FROMADD1>\n" +
                    "<FROMADD2/>\n" +
                    "<FROMSUBURB>"+FormData.getFromSuburb()+"</FROMSUBURB>\n" +
                    "<FROMPOSTCODE>"+FormData.getFromPostcode()+"</FROMPOSTCODE>\n" +
                    "<PICKUPINSTRUCTION/>\n" +
                    "<READYTIME/>\n" +
                    "<VEHICLE/>\n" +
                    "<FROMCONTACTMOBILE/>\n" +
                    "<FROMCONTACTEMAIL/>\n" +
                    "<FROMADDRESSTYPE>BUS</FROMADDRESSTYPE>\n" +
                    "<CONSIGNMENTINFODETAIL>\n" +
                    "<CONSIGNEE>Gail McLeod</CONSIGNEE>\n" +
                    "<TOADD1>"+FormData.getToAddress1()+"</TOADD1>\n" +
                    "<TOADD2/>\n" +
                    "<TOSUBURB>"+FormData.getToSuburb()+"</TOSUBURB>\n" +
                    "<TOPOSTCODE>"+FormData.getToPostcode()+"</TOPOSTCODE>\n" +
                    "<DELIVERYINSTRUCTION/>\n" +
                    "<TOCONTACTNAME>"+FormData.getToName()+"</TOCONTACTNAME>\n" +
                    "<TOCONTACTPHONE/>\n" +
                    "<TOCONTACTMOBILE>"+FormData.getToMobile()+"</TOCONTACTMOBILE>\n" +
                    "<INSURANCEOPTION/>\n" +
                    "<TOTALINSURANCE/>\n" +
                    "<INSURANCEAUTH/>\n" +
                    "<CON_NOTES/>\n" +
                    "<SERVICE_LEVEL/>\n" +
                    "<JOB_TYPE/>\n" +
                    "<PRIMARYPRODUCT>CBM</PRIMARYPRODUCT>\n" +
                    "<PICKUPVEHICLE/>\n" +
                    "<DELIVERYVEHICLE/>\n" +
                    "<RDD/>\n" +
                    "<TOCONTACTEMAIL>"+FormData.getToEmail()+"</TOCONTACTEMAIL>\n" +
                    "<TOADDRESSTYPE>RES</TOADDRESSTYPE>\n" +
                    "<ATL>N</ATL>\n" +
                    line +
                    "<TICKETS>\n" +
                    "<TICKET>"+Order_ref+"0001</TICKET>\n" +
                    "</TICKETS>\n" +
                    "</CONSIGNMENTINFODETAIL>\n" +
                    "</CONSIGNMENTINFO>\n" ;
            pslog.setMessage(xml);
            pslog.setOrder_type("fliway");
            save_process_Log(pslog);
            ReturnBack back = call.postobject(fliway_order_url,headers,xml,fliway_order_Basic);
            //xml="<?xml version='1.0' encoding='utf-8'?>\n"+xml;
            //String url = su.SaveS3xml(xml,Order_ref);
            if(!back.isSuccess())
            {
                //日志内容赋值

                log.setOrder_ref(Order_ref);
                log.setOrder_company("fliway");
                log.setConnote_number(FormData.getACCOUNT());
                Float weight=0f,cbm=0f;
                for(int i=0;i<FormData.getLinesList().size();i++)
                {
                    float Volume=(float)Math.round(Float.parseFloat(FormData.getLinesList().get(i).getVolume())*10000)/10000;
                    float Weight=(float)Math.round(Float.parseFloat(FormData.getLinesList().get(i).getWeight())*10000)/10000;
                    weight+=Volume;
                    cbm+=Weight;
                }
                log.setOrder_weight(weight);
                log.setOrder_cbm(cbm);
//                log.setOrder_value(rorder.getCalculatedChargeIncludingGst());
                log.setOrder_postcode_to(FormData.toPostcode);
                log.setOrder_postcode_from(FormData.fromPostcode);
                log.setOrder_region_to(FormData.toSuburb);
                log.setOrder_region_from(FormData.fromSuburb);
                saveLog(log);
                //logger.error("fliway下单失败"+back.getValue());
                return R.error().message("fliway下单失败"+back.getValue());
            }
            CONSIGNMENT_RESPONSE userTest = (CONSIGNMENT_RESPONSE) XMLUtil.convertXmlStrToObject(CONSIGNMENT_RESPONSE.class, back.getValue());
//            userTest.URL=url;
            saveLog(log);
            return R.ok().message("fliway下单成功").data(userTest);
            //成功后需保存数据库库，数据库暂未设计
        }
        catch (Exception e) {
//            pslog.setMessage(e.toString());
//            save_process_Log(pslog);
            log.setIs_success(0);
            saveLog(log);
            //logger.error(e.toString());
            return R.error().message("fliway下单失败"+e.toString());
        }
    }

    @Override
    public R tracking(trackBody body) {
        String back = "";
        Freight_process_log pslog = new Freight_process_log();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");
            String base64Credentials = new String(Base64.encodeBase64(fliway_order_Basic.getBytes()));
            String authHeader = "Basic " + base64Credentials;
            headers.set("Authorization", authHeader);
            String rbody = call.getjson(fliway_tracking_url+body.getConsignmentId(),headers);
            Gson gson = new Gson();
            MILESTONE userTest = (MILESTONE) XMLUtil.xmlToObject(MILESTONE.class, rbody);
            List<Items> listlist = new ArrayList<Items>();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);
            //SimpleDateFormat formatTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i = 0;i < userTest.getEVENT().size();i++)
            {
                EVENT ets =userTest.getEVENT().get(i);
                Items item =new Items();
                item.setTicket(ets.getTICKET());
                item.setEventType(ets.getDESCRIPTION());
                item.setEventDescription(ets.getCONSIGNMENT());
                Date datatime = sDateFormat.parse(ets.getCREATEDON());
                item.setCreatedOn(datatime);
                listlist.add(item);
            }
            listlist.sort(Comparator.comparing(Items::getCreatedOn).reversed());

            return R.ok().message("查询成功").data(listlist);//返回下单成功后的数据
        }
        catch(Exception e) {
            return R.ok().message("查询失败").data(e.toString());//返回下单成功后的数据
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
