package common;

import bean.UserService;
import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author wj
 * @create 2022-11-09 17:39
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
