package com.freight.controller;


import com.freight.common.R;
import com.freight.entity.Rates;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;
import com.freight.service.FREIGHT_BOOKING_LOGervice;
import com.freight.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuxin
 * @since 2021-10-05
 */
@RestController
@RequestMapping("api/v1/freight")
@Api(value = "AutoOrder", tags = "AutoOrder")
public class AutoOrderController {
    @Resource
    private FREIGHT_BOOKING_LOGervice logservice;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
    InterfaceCall call=new InterfaceCall();
    @Autowired
    private FreightUtil fu;
    @Autowired
    CPPHUtil cpph;
    @Autowired
    fliwayUtil fy;
    @Autowired
    mainfreightUtil mf;
    //@SaCheckLogin //同意登陆验证登陆验证暂时不起用
    @RequestMapping(value="/booking", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "下单接口")
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiImplicitParam(name = "freight_company", value = "下单类型（示例:CPPH、M2H、fliway、TDT、Courier）", required = true, dataType = "String")
    public R orders(@RequestBody orderBody FormData, String freight_company) {
        try {
            R r=fu.AutoOrder(freight_company,FormData);
            return r;//返回下单成功后的数据
        }
        catch (Exception e) {

            return R.error().message(e.toString());
        }
    }
    @RequestMapping(value="/tracking", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "订单追踪")
    @CrossOrigin(origins = "*",maxAge = 3600)

    public R tracking(trackBody body ) {
        try {
            R r = null;
            switch (body.getFreight_company()) {
                case "CPPH":
                    r= cpph.tracking(body);
                    break;
                case "Courier":
                    break;
                case "fliway":
                    r= fy.tracking(body);
                    break;
                case "M2H":
                    r= mf.tracking(body);
                    break;
                case "TDT":

                    break;
            }
            return r;
        }
        catch (Exception e) {
            return R.error().message(e.toString());
        }
    }
//    @RequestMapping(value="/quote", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "询价接口")
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiImplicitParam(name = "freight_company", value = "下单类型（示例:M2H、fliway、TDT、Courier）", required = false, dataType = "String")
    @RequestMapping(value="/quote", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public R fliwayrates(@RequestBody OrderBody FormData, String freight_company) {//
        try {
            List<Rates> rates=fu.AutoRates(freight_company,FormData);
            return R.error().message("询价成功").data(rates);
        }
        catch (Exception e) {

            return R.error().message(e.toString());
        }
    }
    //    @RequestMapping(value="/TDTbooking", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "TDT下单接口")
//    @CrossOrigin(origins = "*",maxAge = 3600)
////    @ApiImplicitParam(name = "type", value = "下单类型（示例:GSS、fliway、mainfreight、TDT）", required = true, dataType = "String")
//    public R orders(@RequestBody TDTOrderP FormData, String username) {
//        try {
//            R r=fu.AutoOrder("TDT",FormData);
//            return r;//返回下单成功后的数据
//        }
//        catch (Exception e) {
//            logger.error(e.toString());
//            return R.error().message(e.toString());
//        }
//    }
//    @PostMapping("/fliwayorders")
//    @ApiOperation(value = "fliway下单接口", notes = "fliway下单接口", code = 200, produces = "application/json", responseContainer = "List", response = R.class)
//    public R fliwayorders(@RequestBody String FormData) {//
//        try {
//            //mainfreight fliway
//            R r=fu.AutoOrder("mainfreight",FormData);
//            //保存日志
//            Freight_booking_log log=new Freight_booking_log();
//            log.setCreate_time(new Date());
//            logservice.save(log);
//            return r;
//            //成功后需保存数据库库，数据库暂未设计
//        }
//        catch (Exception e) {
//            logger.error(e.toString());
//            return R.error().message(e.toString());
//        }
//    }
}

