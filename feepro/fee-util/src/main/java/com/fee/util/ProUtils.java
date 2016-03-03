/*
 * Copyright (C) 2015 上海富捷信息技术有限公司, All Rights Reserved.
*/
package com.fee.util;

import jodd.bean.BeanCopy;
import jodd.bean.BeanUtil;
import jodd.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Bean操作.
 */
public class ProUtils {

    private static final Logger LOG = Logger.getLogger(ProUtils.class);

    /**
     * 拷贝相同的属性，如果属性源的属性值为空就不用拷贝。
     * 如果属性名以　_ 或者　$ 开头会被忽略。
     */
    public static void copySameProperties(Object destination, Object source, String... excludeProperties) {
        String[] excludes = getExcludes(source, excludeProperties);
        BeanCopy.beans(source, destination)
                .ignoreNulls(true)
                .exclude(excludes)
                .copy();
    }

    private static String[] getExcludes(Object source, String... excludeProperties) {
        List<String> excludes = new ArrayList<>();
        Field[] declaredFields = source.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            if (isSystemField(name)) {
                excludes.add(name);
            }
        }

        excludes.addAll(Arrays.asList(excludeProperties));
        return excludes.toArray(new String[excludes.size()]);
    }

    /**
     * 根据传入的参数生成一个UUID
     *
     * @param params
     * @return
     */
    public static String generateUUID(String... params) {
        String originString = StringUtil.join(params);
        UUID uuid = UUID.nameUUIDFromBytes(originString.getBytes());
        String uuidValue = uuid.toString();
        return replaceDash(uuidValue);
    }

    private static String replaceDash(String value) {
        return value.replace("-", "");
    }

    /**
     * Create a random UUID without "-"
     *
     * @return UUID string object.
     */
    public static String randomUUID() {
        String uuidValue = UUID.randomUUID().toString();
        return replaceDash(uuidValue);
    }

    /**
     * Make the first char of string object to lower case.
     */
    public static String lowerCaseFirstChar(String value) {
        if (StringUtil.isEmpty(value)) {
            return "";
        }
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }

    /**
     * Check the field name is start with "_" or "$".
     *
     * @param name the field name.
     * @return true if field name start with "_" or "$", false otherwise.
     */
    public static boolean isSystemField(String name) {
        if (StringUtil.isEmpty(name)) {
            return false;
        }
        return name.startsWith("_") || name.startsWith("$");
    }


    /**
     * 根据指定参数值获取对应的枚举对象
     *
     * @param enumClazz  枚举的class
     * @param paramValue 参数的value
     * @param paramName  具体的参数名字 默认值为"value"
     */
    public static <T> T getEnumByParameterValue(Class<T> enumClazz, String paramValue, String... paramName) {
        String realName = (paramName.length == 0) ? "value" : paramName[0];
        Class<? extends Enum> enumTmp = enumClazz.asSubclass(Enum.class);
        Object[] constants = enumTmp.getEnumConstants();
        for (Object tmp : constants) {
            String value = String.valueOf(BeanUtil.getProperty(tmp, realName));
            if (value.equals(paramValue)) {
                return (T) tmp;
            }
        }
        throw new IllegalArgumentException(paramValue + " is illegal");
    }


    /**
     * 指定外部配置文件的路径
     * <br/>
     * 把配置文件application.properties文件放到这个目录下，可以覆盖默认的配置。
     */

    public static Properties springExtentionConfig() {
        return springExtentionConfig(null);
    }

    public static Properties springExtentionConfig(String configPath) {
        InputStream inputStream = ProUtils.class.getClassLoader().getResourceAsStream("location.properties");
        Properties props = new Properties();
        try {
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (props.getProperty("spring.config.location") == null && configPath != null) {
            props.put("spring.config.location", configPath);
        }
        LOG.info("LQ9836110: use spring.config.location=" + props.getProperty("spring.config.location"));
        return props;
    }





    /**
     * 测试URL是否可用
     *
     * @param urlStr 要测试的url字符串
     * @return 可用为true 否则为false
     */
    public static boolean isUrlAvaliable(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int state = connection.getResponseCode();
            return true;
        } catch (Exception e) {
            LOG.warn("WX001e0:WARN urlStr test connection is error : "+e+",url is "+urlStr);
        }
        return false;
    }


    public static <T> T copyProperties(Object source, Class<T> targetClass) throws BeansException {
        T targetInstance = null;
        try {
            targetInstance = targetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, targetInstance);
        return targetInstance;
    }

    public static String formateBigDecimal(BigDecimal bigDecimal) {
        bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }
}
