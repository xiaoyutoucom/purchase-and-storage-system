package com.freight.utils;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.freight.common.R;
import com.freight.entity.Freight_booking_log;
import com.freight.entity.Freight_process_log;
import com.freight.entity.GSS.pushP.shipmentsP;
import com.freight.entity.GSS.pushR.Consignments;
import com.freight.entity.GSS.pushR.OutputFiles;
import com.freight.entity.GSS.pushR.shipmentsR;
import com.freight.entity.GSS.pushR.shipmentsbody;
import com.freight.entity.GSS.ratesP.Address;
import com.freight.entity.GSS.ratesP.Destination;
import com.freight.entity.GSS.ratesP.GSSrates;
import com.freight.entity.GSS.ratesP.Packages;
import com.freight.entity.GSS.ratesR.Available;
import com.freight.entity.GSS.ratesR.GSSratesR;
import com.freight.entity.Rates;
import com.freight.entity.ReturnBack;
import com.freight.entity.orderBody.linesList;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.interfaceBG.OrderInterface;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.freight.service.FREIGHT_PROCESS_LOGervice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Component
public class GSSUtil implements OrderInterface {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
    InterfaceCall call=new InterfaceCall();
    //GSS访问配置
    @Value("${fileUrl}")
    public String fileUrl;
    @Value("${GSS.rates-url}")
    public String rates;
    @Value("${GSS.shipments-url}")
    public String shipmentsurl;
    @Value("${GSS.access_key}")
    public String access_key;
    @Value("${GSS.site_id}")
    public String site_id;
    @Resource
    FREIGHT_BOOKING_LOGervice logservice;
    @Resource
    FREIGHT_PROCESS_LOGervice pslogservice;
    @Autowired
    S3Util s3;
    @Value("${AWS.bucketName}")
    String bucketName;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private ReturnBack pushback;

    //GSS下单方法
    public float rates(OrderBody FormData) {
        try {
            GSSrates gss = new GSSrates();
            Address t = new Address();
            t.setCity(FormData.getTo().getCity());
            t.setPostCode(FormData.getTo().getPostCode());
            t.setSuburb(FormData.getTo().getSuburb());
            t.setCountryCode("NZ");
            List<Packages> pklist = new ArrayList<Packages>();
            //for(int i=0;i<FormData.getItems().size();i++) {
                Packages pg = new Packages();
                pg.setHeight(FormData.getItems().getHeight());
                pg.setWidth(FormData.getItems().getWidth());
                pg.setLength(FormData.getItems().getLength());
                pg.setKg(FormData.getItems().getWeight());
                pklist.add(pg);
            //}
            gss.setPackages(pklist);
            Destination dt = new Destination();

            dt.setAddress(t);
            gss.setDestination(dt);

            HttpHeaders headers = new HttpHeaders();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            headers.set("access_key", access_key);
            headers.set("site_id", site_id);
            headers.set("Content-type", "application/json;charset=utf-8");
            //region 获取GSS最便宜的邮政
            String FormDatajson = JSONObject.fromObject(gss).toString();
            //获取所有订单数据
            ReturnBack back = call.postjson(rates, headers, FormDatajson);
            GSSratesR gs = gson.fromJson(back.getValue(), GSSratesR.class);
            //取出数据
            List<Available> ab = gs.getAvailable();
            if (ab == null) {

                return 0f;
            } else {
                ab.sort(Comparator.comparing(Available::getCost));//之前估计已经排序以防万一自己排一边  按照单价升序.reversed()
            }
            //endregion
            //region 下单
            Available abOne = ab.get(0);//获取最便宜的一个站点
            String QuoteId = abOne.getQuoteId();
            Rates frrs = new Rates();
            frrs.setType(QuoteId);
            frrs.setValue(abOne.getCost());
            return abOne.getCost();//返回下单成功后的数据
        }
        catch(Exception e){

            return 0f;
        }
    }
    //GSS下单方法Feiqi
    public Rates GSSrates2(shipmentsP FormData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            headers.set("access_key", access_key);
            headers.set("site_id", site_id);
            headers.set("Content-type", "application/json;charset=utf-8");
            //region 获取GSS最便宜的邮政
//            FormData.setOrigin(null);

            //获取所有订单数据
            ReturnBack back = call.postjson(rates, headers,FormData);
            GSSratesR gs = gson.fromJson(back.getValue(), GSSratesR.class);
            //取出数据
            List<Available> ab = gs.getAvailable();
            if (ab == null) {

                return new Rates();
            } else {
                ab.sort(Comparator.comparing(Available::getCost));//之前估计已经排序以防万一自己排一边  按照单价升序.reversed()
            }
            //endregion
            //region 下单
            Available abOne = ab.get(0);//获取最便宜的一个站点
            String CarrierName = abOne.getCarrierName();
            Rates frrs = new Rates();
            frrs.setType(CarrierName);
            frrs.setValue(abOne.getCost());
            return frrs;//返回下单成功后的数据
        }
        catch(Exception e){

            return new Rates();
        }
    }
    //GSS下单方法
    public R orders(orderBody FormData) {

        Freight_booking_log log = new  Freight_booking_log();
        Freight_process_log pslog = new Freight_process_log();
//        pslog.setOrder_ref(Order_ref);
//        pslog.setOrder_type("GSS");
        try {
//            //日志
//            Freight_process_log psloga = new Freight_process_log();
//            String formda = JSONObject.fromObject(FormData).toString();
//            psloga.setMessage(formda);
//            save_process_Log(psloga);
            HttpHeaders headers = new HttpHeaders();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            headers.set("access_key", access_key);
            headers.set("site_id", site_id);
            headers.set("Content-type", "application/json;charset=utf-8");

            shipmentsP shipments = new shipmentsP();

            //收货地
            Destination dtt = new Destination();
            dtt.setName(FormData.getToName());
            dtt.setEmail(FormData.getToEmail());
            dtt.setPhoneNumber(FormData.getToMobile());
            Address addto = new Address();
            addto.setCountryCode("NZ");
            addto.setStreetAddress(FormData.getToAddress1());
            addto.setSuburb(FormData.getToSuburb());
            addto.setCity(FormData.getToCity());
            addto.setPostCode(FormData.getToPostcode());
            dtt.setAddress(addto);
            shipments.setDestination(dtt);

            Packages pg=new Packages();
            List<Packages> pglist = new ArrayList<Packages>();
            if(FormData.getLinesList()!=null) {
                //for (int i = 0; i < FormData.getLinesList().size(); i++) {
                //只有一个
                    linesList line = FormData.getLinesList().get(0);
                    pg.setHeight(Float.parseFloat(line.getHeight() == null ? "0" : line.getHeight()));
                    pg.setWidth(Float.parseFloat(line.getWidth() == null ? "0" : line.getWidth()));
                    pg.setLength(Float.parseFloat(line.getLength() == null ? "0" : line.getLength()));
                    pg.setKg(Float.parseFloat(line.getWeight() == null ? "0" : line.getWeight()));
                    pg.setName(line.getDescription());
                    pglist.add(pg);
                //}
            }
            shipments.setPackages(pglist);
//            //日志
//            Freight_process_log pslogb = new Freight_process_log();
            String FormDatajson = JSONObject.fromObject(shipments).toString();
//            pslogb.setMessage(FormDatajson);
//            save_process_Log(pslogb);
            //Rates r = GSSrates2(shipments);
            //发货地
            if(!FormData.getFromName().equals("")&&!FormData.getFromName().equals(null)) {
                Destination dtf = new Destination();
                dtf.setName(FormData.getFromName());
                dtf.setEmail(FormData.getFromEmail());
                Address add = new Address();
                add.setCountryCode("NZ");
                add.setStreetAddress(FormData.getFromAddress1());
                add.setSuburb(FormData.getFromSuburb());
                add.setCity(FormData.getFromCity());
                add.setPostCode(FormData.getFromPostcode());
                dtf.setAddress(add);
                shipments.setOrigin(dtf);
            }
            String CarrierName = "Post Haste";//r.getType();
            shipments.setCarrier(CarrierName);

            String[] output = new String[]{"LABEL_PDF","LABEL_PDF_100X150"};
            shipments.setOutputs(output);
            shipments.setDeliveryReference(FormData.getFromReference());
//            shipments.setService("*");
            String shipmentsjson = JSONObject.fromObject(shipments).toString();
//
//            //日志
//            Freight_process_log pslogc = new Freight_process_log();
//
//            pslogc.setMessage(shipmentsjson);
//            save_process_Log(pslogc);
//            System.out.println(shipmentsjson);
            //获取所有订单数据
             pushback = call.postjson(shipmentsurl, headers, shipmentsjson);
            if(!pushback.isSuccess())
            {
                pslog.setMessage("Gss下单失败");
                save_process_Log(pslog);

                return R.error().message("Gss下单失败"+pushback.getValue());
            }
            shipmentsR gsr = gson.fromJson(pushback.getValue(), shipmentsR.class);
            Consignments conment =gsr.getConsignments().get(0);
            String Order_ref=conment.getConnote();
            //日志内容赋值
            log.setOrder_ref(Order_ref);
            log.setOrder_company("Courier");
            log.setConnote_number(String.valueOf(conment.getConsignmentId()));
            log.setOrder_weight(pg.getKg());
            log.setOrder_cbm(pg.getHeight()*pg.getLength()*pg.getWidth());
            log.setOrder_value(conment.getCost());

            log.setOrder_postcode_to(FormData.toPostcode);
            log.setOrder_postcode_from(FormData.fromPostcode);
            log.setOrder_region_to(FormData.toSuburb);
            log.setOrder_region_from(FormData.fromSuburb);
            //endregion

            saveLog(log);
            //pdf转换为url
            shipmentsbody returngsr = generateBase64StringToFile(gsr);

            return R.ok().message("Gss下单成功").data(returngsr);//返回下单成功后的数据
        }
        catch(Exception e){
            pslog.setMessage(pushback.getValue());
            pslog.setOrder_type("Courier");
            save_process_Log(pslog);

            return R.error().message("Gss下单失败"+e.toString());
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
    /**
     * 将base64编码转换成PDF
     * @param base64sString 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *            2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *            3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *            4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     * @param fileName 文件名称
     */
    public shipmentsbody generateBase64StringToFile(shipmentsR gsr) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        shipmentsbody body = new shipmentsbody();
        try {
            s3.creatConnect();
            s3.getBacketObjects(bucketName);
            Consignments conment =gsr.getConsignments().get(0);
            String Order_ref=conment.getConnote()+".pdf";
            String Order_ref2=conment.getConnote()+"100X150.pdf";
            FileUtil fileutil =new FileUtil();
            File file =fileutil.getfile(conment.getOutputFiles().getLABEL_PDF()[0],Order_ref);
            File file2 =fileutil.getfile(conment.getOutputFiles().getLABEL_PDF_100X150()[0],Order_ref2);
            //上传文件
            PutObjectResult pr= s3.creatObject(bucketName,"GSS/pdf/", Order_ref,file);
            String url =s3.getDownloadUrl(bucketName,"GSS/pdf/"+Order_ref);
            PutObjectResult pr2= s3.creatObject(bucketName,"GSS/pdf/", Order_ref2,file2);
            String url2 =s3.getDownloadUrl(bucketName,"GSS/pdf/"+Order_ref2);
            OutputFiles outputf=new OutputFiles();
            outputf.setLABEL_PDF(new String[]{url});
            outputf.setLABEL_PDF_100X150(new String[]{url2});
            conment.setOutputFiles(outputf);
            //赋值body返回 格式化返回值
            body.setCarrierId(gsr.getCarrierId());
            body.setConnote(gsr.getConsignments().get(0).getConnote());
            body.setConsignmentId(gsr.getConsignments().get(0).getConsignmentId());
            body.setCost(gsr.getConsignments().get(0).getCost());
            body.setSiteId(gsr.getSiteId());
            body.setLabelA4(url);
            body.setLabelpdf(url2);
            body.setTrackingUrl(gsr.getConsignments().get(0).getTrackingUrl());
            return body;
        }
        catch (Exception e)
        {
            return body;
        }
        finally {

        }
    }
}
