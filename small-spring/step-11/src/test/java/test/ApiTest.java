package test;

import bean.IUserDao;
import bean.UserService;
import cn.hutool.core.io.IoUtil;
import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.PropertyValue;
import com.imwj.springframework.beans.PropertyValues;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.config.BeanReference;
import com.imwj.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.imwj.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.imwj.springframework.context.support.ClassPathXmlApplicationContext;
import com.imwj.springframework.core.io.DefaultResourceLoader;
import com.imwj.springframework.core.io.Resource;
import common.MyBeanFactoryPostProcessor;
import common.MyBeanPostProcessor;
import event.CustomEvent;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wj
 * @create 2022-10-11 17:29
 */
public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }

}
