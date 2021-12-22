//package com.freight.Factory;
//
//import com.freight.entity.Policy;
//import org.casbin.adapter.JDBCAdapter;
//import org.casbin.jcasbin.main.Enforcer;
//import org.casbin.jcasbin.model.Model;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @author : yx-0176
// * @description
// * @date : 2021/1/20
// */
//@Component
//public class EnforcerFactory implements InitializingBean {
//
//    private String modelpath="classpath:conf/authz_model.conf";
//
//    private Enforcer enforcer;
//
//    private EnforcerConfigProperties enforcerConfigProperties;
//
//    private final String MODEL ="[request_definition]\n" +
//            "r = sub, obj, act\n" +
//            "\n" +
//            "[policy_definition]\n" +
//            "p = sub, obj, act\n" +
//            "\n" +
//            "[role_definition]\n" +
//            "g = _, _\n" +
//            "\n" +
//            "[policy_effect]\n" +
//            "e = some(where (p.eft == allow))\n" +
//            "\n" +
//            "[matchers]\n" +
//            "m = r.sub == p.sub && keyMatch2(r.obj, p.obj) && regexMatch(r.act, p.act) || g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act || g(r.sub, p.sub) && keyMatch2(r.obj, p.obj) && regexMatch(r.act, p.act)";
//
//    @Autowired
//    public EnforcerFactory(EnforcerConfigProperties enforcerConfigProperties) {
//        this.enforcerConfigProperties = enforcerConfigProperties;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        enforcerConfigProperties = enforcerConfigProperties;
//        //从数据库读取策略
//        JDBCAdapter jdbcAdapter = new JDBCAdapter(enforcerConfigProperties.getDriverClassName(),
//                enforcerConfigProperties.getDatasourceUrl(),
//                enforcerConfigProperties.getDatasourceUserName(),
//                enforcerConfigProperties.getDatabasePassword());
//        Model model = new Model();
////        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
////        Resource resource = resolver.getResource(modelpath);
//        //model.loadModel(resource.getFile().getCanonicalPath());
//        model.loadModelFromText(MODEL);
//        enforcer = new Enforcer(model, jdbcAdapter);
//    }
//
//    /**
//     * 权限校验
//     *
//     * @param rvals 校验参数
//     * @return
//     */
//    public boolean enforce(Object... rvals) {
//        return enforcer.enforce(rvals);
//    }
//
//    /**
//     * 添加权限策略
//     *
//     * @param policy 策略参数
//     * @return 是否添加成功
//     */
//    public boolean addPolicy(Policy policy) {
//        return enforcer.addPolicy(policy.getSub(), policy.getObj(), policy.getAct());
//    }
//
//    /**
//     * 删除权限策略
//     *
//     * @param policy
//     * @return
//     */
//    public boolean removePolicy(Policy policy) {
//        return enforcer.removePolicy(policy.getSub(), policy.getObj(), policy.getAct());
//    }
//
//    /**
//     * 权限是否存在
//     *
//     * @param policy 权限参数
//     * @return 是否存在当前权限
//     */
//    public boolean hasPolicy(Policy policy) {
//        return enforcer.hasPolicy(policy.getSub(), policy.getObj(), policy.getAct());
//    }
//
//
//    /**
//     * 添加角色继承
//     *
//     * @param rSub 角色名
//     * @param pSub 权限名
//     * @return 是否继承
//     */
//    public boolean addRoleForUser(String rSub, String pSub) {
//        return enforcer.addRoleForUser(rSub,pSub);
//    }
//
//
//    /**
//     * 获取角色下的权限
//     * @param sub
//     * @return
//     */
//    public List<String> getRolesForPolicy(String sub) {
//        return enforcer.getRolesForUser(sub);
//    }
//
//    /**
//     * 移除角色权限
//     * @param rSub 角色名
//     * @param pSub 权限名
//     * @return 是否继承
//     */
//    public boolean deleteRoleForUser(String rSub,String pSub){
//        return enforcer.deleteRoleForUser(rSub, pSub);
//    }
//}
