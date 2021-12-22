package com.freight;



import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class Auto {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
//    robin_bridge_insepection,robin_bridge_insepectionrecord,smart_bridge_archives,smart_bridge_component,smart_bridge_basicinfo,smart_bridge_construct,smart_bridge_disease,smart_bridge_maintenance,smart_bridge_people,smart_bridge_structureinfo,smart_bridge_TUNNEL,smart_bridge_techconass,smart_bridge_attach,smart_bridge_mprocess
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("yuxin");
        gc.setOpen(false);
        gc.setFileOverride(true);//是否覆盖
        gc.setServiceName("%service");//去i前缀
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:postgresql://ls-bfc1b6c29516ee27932a03603b519c171866fd1a.c6ap3u3sr0zb.ap-southeast-2.rds.amazonaws.com:5432/td-freight_testing?characterEncoding=UTF8&useUnicode=true&serverTimezone=GMT%2B8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("dbmasteruser");
        dsc.setPassword("2#==7X)JT;,9m9uGq{6:&b|ek?Iv%L},");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("brideProject");
        pc.setParent("com.freight");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setNaming(NamingStrategy.no_change);
        strategy.setColumnNaming(NamingStrategy.no_change);
        strategy.setTablePrefix("robin_bridge_");//去掉表前缀
        strategy.setTablePrefix("smart_bridge_");//去掉表前缀
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        //strategy.setRestControllerStyle(true);
        //自动填充
        TableFill addDate = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateDate = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(addDate);
       tableFills.add(updateDate);
        strategy.setTableFillList(tableFills);
        //乐观锁
        //strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);//驼峰命名
        strategy.setControllerMappingHyphenStyle(true);//下划线
        mpg.setStrategy(strategy);
        mpg.execute();

    }
}
