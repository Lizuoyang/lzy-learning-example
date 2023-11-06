package com.lzy.example.mybatis.plus.general;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.lzy.example.mybatis.plus.general.PropertiesUtil.getProperty;

/**
 * @ClassName MyBatisPlusGeneratorAnt
 * @Description mybatis-plus-general 逆向工程生成数据库映射类启动类
 * @Author LiZuoYang
 * @Date 2021/12/2 11:23
 **/
public class MyBatisPlusGeneratorAnt {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append(tip);
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入 " + tip + "!");
    }

    /**
     * @author lizuoyang
     * @description 获取生成的主键的ID类型
     * @date 15:39 2020/11/9
     * @param value
     * @return com.baomidou.mybatisplus.annotation.IdType
     **/
    public static IdType getIdType(String value) {
        IdType idType = null;
        switch (value) {
            case "0":
                idType = IdType.AUTO;
                break;
            case "1":
                idType = IdType.NONE;
                break;
            case "3":
                idType = IdType.ASSIGN_ID;
                break;
            case "4":
                idType = IdType.ASSIGN_UUID;
                break;
            default:
                idType = IdType.INPUT;
                break;
        }
        return idType;
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir") + "\\" + getProperty("project.module.name");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(PropertiesUtil.getProperty("project.module.author"));
        gc.setOpen(false);
        //实体命名方式 例如：%sEntity 生成 UserEntity
        if (StringUtils.checkValNotNull(getProperty("entity.endwith"))) {
            gc.setEntityName("%s" + getProperty("entity.endwith"));
        }

        //mapper 命名方式 例如：%sMapper 生成 UserMapper
        gc.setMapperName("%s" + getProperty("mapper.endwith"));
        //service 命名方式 例如：%sBusiness 生成 UserBusiness
        gc.setServiceName("%s" + getProperty("service.endwith"));
        //service impl 命名方式 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setServiceImplName("%s" + getProperty("service.endwith") + getProperty("service.impl.endwith"));
        //controller 命名方式 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setControllerName("%s" + getProperty("controller.endwith"));
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        //是否开启 baseResultMap
        gc.setBaseResultMap(Boolean.valueOf(getProperty("base.result.map")));
        //是否开启 baseColumnList
        gc.setBaseColumnList(Boolean.valueOf(getProperty("base.column.list")));
        gc.setIdType(getIdType(getProperty("id_type")));
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(getProperty("datasource.url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(getProperty("datasource.driver.name"));
        dsc.setUsername(getProperty("datasource.username"));
        dsc.setPassword(getProperty("datasource.password"));
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        /**
         * 设置包名，如果有不同的模块，可以使用上面的设置
         */
        pc.setParent(getProperty("parent.package.name"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/" + getProperty("mapper.xml.package.name") +  "/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + getProperty("mapper.endwith") + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        String likeTable = scanner("是否使用模糊匹配表名? (Y/N)");
        if (Objects.equals(likeTable.toUpperCase(), "Y")) {
            strategy.setLikeTable(new LikeTable(scanner("请输入表名前缀或者关键字进行匹配")));
        } else {
            strategy.setInclude(getTables(scanner("请输入表名全名进行匹配，多个表名请用英文逗号分隔开，输入all匹配所有表")));
        }
        strategy.setControllerMappingHyphenStyle(true);
        if (StringUtils.checkValNotNull(getProperty("table_prefix"))) {
            strategy.setTablePrefix(getProperty("table_prefix"));
        }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    // 处理 all 情况
    protected static String[] getTables(String tables) {
        return "all".equals(tables) ? new String[]{} : tables.split(",");
    }
}
