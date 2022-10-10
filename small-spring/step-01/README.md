# 创建一个简单的spring容器
> spring管理bean的核心即：BeanDefinition、BeanFactory

* `BeanDefinition`：关于bean的描述，当前只定义了一个Object用于存放bean对象

* `BeanFactory`：创建bean的工厂类，通过beanName + BeanDefinition来创建对应的bean

* 类关系：  

![类关系](img/spring-01.png)