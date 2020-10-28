package com.athjx.commonutils.utils;

import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static final Log logger = LogFactory.getLog(SpringContextHolder.class);

    private static ApplicationContext context;

    /**
     * 获得到Bean对象
     *
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            logger.warn(e);
            return null;
        }
    }

    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        Validate.validState(context != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        setContext(applicationContext);
    }

    /**
     * 给context赋值，为了防止static变量在实例方法中出现
     *
     * @param applicationContext
     * @return
     * @author：刘朋
     * @date：2018/8/16 15:29
     */
    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}