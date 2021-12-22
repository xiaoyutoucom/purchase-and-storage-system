//package com.freight.controller;
//
//
//import cn.dev33.satoken.annotation.SaCheckLogin;
//import com.freight.common.R;
//import com.freight.entity.Aaa;
//import com.freight.service.AAAervice;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.mybatis.logging.Logger;
//import org.mybatis.logging.LoggerFactory;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author yuxin
// * @since 2021-10-05
// */
//@RestController
//@RequestMapping("/aaa")
//public class AaaController {
//    @Resource
//    private AAAervice attendanceservice;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    public String vaildnull(Aaa Obj){
//        if (Obj == null) {
//            return "参数有误添加失败";
//        }
////        QueryWrapper<Attendance> wrapper = new QueryWrapper();
////        wrapper.eq("bridgecode", Obj.getBmmc());
////        Attendance ad = attendanceservice.getOne(wrapper);
////        if(Obj.getBmid()==null ||(ad!=null&&!ad.getBmid().equals(Obj.getBmid()))){
////            if(ad!=null){
////                return "编码已存在";
////            }
////        }
//        return null;
//    }
//    /**
//     * 增加一个项目
//     *
//     */
////    @SaCheckLogin
//    @PostMapping("/add")
//    @ApiOperation(value = "新增", notes = "添加一个新项目", code = 200, produces = "application/json", responseContainer = "List", response = R.class)
//    @ApiImplicitParams({
////            @ApiImplicitParam(value = "编号", name = "bridgecode", required = true, dataType = "String", paramType = "query"),
////            @ApiImplicitParam(value = "档案号", name = "recordcode", required = true, dataType = "String", paramType = "query"),
////            @ApiImplicitParam(value = "存档案", name = "cda", required = true, dataType = "String", paramType = "query")
//    })
//    public R add(Aaa FormData) {
//        try {
//            String vaild = vaildnull(FormData);
//            if (vaild != null) {
//                return R.error().message(vaild).data(null);
//            }
//
//            boolean Flag = attendanceservice.save(FormData);
//
//            if (Flag) {
//                return R.ok().message("添加成功").data(FormData);
//            } else {
//                return R.error().message("添加失败").data(FormData);
//            }
//        }
//        catch (Exception e) {
//            return R.error().message("添加异常");
//        }
//    }
//}
//
