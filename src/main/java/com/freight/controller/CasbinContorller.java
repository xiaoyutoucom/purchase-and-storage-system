//package com.freight.controller;
//
//import com.freight.Factory.EnforcerFactory;
//import com.freight.common.R;
//import com.freight.entity.Policy;
//import io.swagger.annotations.ApiImplicitParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class CasbinContorller {
//    @Autowired
//    private EnforcerFactory enforcerFactory;
//    @RequestMapping(value="/anon/addPolicy",produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiImplicitParam(name = "username", value = "username", required = true, dataType = "String")
//    public R addPolicy(String username){
//        Policy py=new Policy();
//        py.setSub(username);
//        py.setAct("GET");
//        py.setObj("/api/v1/*");
//       boolean ok = enforcerFactory.addPolicy(py);
//        Policy py2=new Policy();
//        py2.setSub(username);
//        py2.setAct("POST");
//        py2.setObj("/api/v1/*");
//        boolean ok2 = enforcerFactory.addPolicy(py2);
//       if(ok&&ok2)
//       {
//           return R.ok().message("成功");
//       }
//       else
//       {
//           return R.error().message("失败");
//       }
//    }
//    @RequestMapping(value="/anon/removePolicy",produces = MediaType.APPLICATION_JSON_VALUE)
//    public R removePolicy(Policy p){
//        boolean ok =enforcerFactory.removePolicy(p);
//        if(ok)
//        {
//            return R.ok().message("成功");
//        }
//        else
//        {
//            return R.error().message("失败");
//        }
//    }
//    @RequestMapping(value="/anon/getRolesForPolicy",produces = MediaType.APPLICATION_JSON_VALUE)
//    public R getRolesForPolicy(String sub){
//        List<String> list =enforcerFactory.getRolesForPolicy(sub);
//        if(list.size()>0)
//        {
//            return R.ok().message("成功").data(list);
//        }
//        else
//        {
//            return R.error().message("未获取到权限");
//        }
//    }
//    @GetMapping("/test")
//    public void test1(){
//        System.out.println("通过");
//    }
//
//}
