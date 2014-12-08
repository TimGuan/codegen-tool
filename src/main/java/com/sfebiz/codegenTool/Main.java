package com.sfebiz.codegenTool;

import net.pocrd.core.generator.ApiCodeGenerator;
import net.pocrd.core.generator.ApiSdkJavaGenerator;
import net.pocrd.core.generator.ApiSdkJavaScriptGenerator;
import net.pocrd.core.generator.ApiSdkObjectiveCGenerator;
import net.pocrd.define.SecurityType;
import net.pocrd.entity.CodeGenConfig;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by guankaiqiang on 2014/11/11.
 */
public class Main {
    public static enum SdkType {
        JAVA,
        OBJC,
        JS;
        public boolean isContained(String[] types) {
            for (String t : types) {
                boolean isContained = this.name().equalsIgnoreCase(t);
                if (isContained) {
                    return true;
                }
            }
            return false;
        }
    }
    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                throw new Exception("api sdk type must not be null or empty, no sdk will be generated!");
            } else {
                InputStream input = new FileInputStream(System.getProperty("user.dir") + "/conf/config.properties");
                Properties prop = null;
                if (input != null) {
                    prop = new Properties();
                    prop.load(input);
                }
                String site = prop.getProperty("net.pocrd.apiResURL");//默认适用开发环境
                String securitys = prop.getProperty("net.pocrd.apiSdkSecurityTypes");
                String modules = prop.getProperty("net.pocrd.apiSdkModules");
                List<SecurityType> securityTypes = null;
                List<String> moduleList = null;
                if (site == null || site.isEmpty()) {
                    System.out.println("net.pocrd.apiResURL is null or empty, will use http://115.28.145.123/info.api?raw to generate code!");
                    site = "http://115.28.145.123/info.api?raw";
                }
                if (securitys == null || securitys.isEmpty()) {
                    System.out.println("net.pocrd.apiSdkSecurityTypes is null or empty, will generate all api!");
                } else {
                    String[] se = securitys.split(",");
                    securityTypes = new LinkedList<SecurityType>();
                    for (String s : se) {
                        securityTypes.add(Enum.valueOf(SecurityType.class, s));
                    }
                }
                if (modules == null || modules.isEmpty()) {
                    System.out.println("net.pocrd.apiSdkModules is null or empty, will generate all api!");
                } else {
                    String[] ms = modules.split(",");
                    moduleList = new LinkedList<String>();
                    if (ms != null && ms.length != 0) {
                        for (String s : ms) {
                            moduleList.add(s);
                        }
                    }
                }
                CodeGenConfig.init(prop);
                ApiCodeGenerator generator = null;
                if (SdkType.JAVA.isContained(args)) {
                    generator = ApiSdkJavaGenerator.getInstance();
                    generator.setSecurityTypes(securityTypes);
                    generator.setApiGroups(moduleList);
                    generator.generateWithNetResource(site);
                }
                if (SdkType.OBJC.isContained(args)) {
                    generator = ApiSdkObjectiveCGenerator.getInstance();
                    generator.setSecurityTypes(securityTypes);
                    generator.setApiGroups(moduleList);
                    generator.generateWithNetResource(site);
                }
                if (SdkType.JS.isContained(args)) {
                    generator = ApiSdkJavaScriptGenerator.getInstance();
                    generator.setSecurityTypes(securityTypes);
                    generator.setApiGroups(moduleList);
                    generator.generateWithNetResource(site);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
