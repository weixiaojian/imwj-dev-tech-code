package bean;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.*;
import com.imwj.springframework.context.ApplicationContext;
import com.imwj.springframework.context.ApplicationContextAware;

/**
 * @author wj
 * @create 2022-10-11 17:28
 */
public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private String beanName;
    private ClassLoader classLoader;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String uId;
    private String company;
    private String location;
    private UserDao userDao;


    public String queryUserInfo() {
        return userDao.queryUserName(uId)+", 公司："+company+", 地点"+location;
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("UserService Bean Name is：" + beanName);
        this.beanName = beanName;
    }
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("UserService ClassLoader：" + classLoader);
        this.classLoader = classLoader;
    }
    @Override
    public void setApplicationContextAware(ApplicationContext applicationContext) throws BeansException {
        System.out.println("UserService applicationContext：" + applicationContext);
        this.applicationContext = applicationContext;
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        System.out.println("UserService beanFactory：" + beanFactory);
        this.beanFactory = beanFactory;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getBeanName() {
        return beanName;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
