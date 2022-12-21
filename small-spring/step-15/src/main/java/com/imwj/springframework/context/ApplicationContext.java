package com.imwj.springframework.context;

import com.imwj.springframework.beans.factory.HierarchicalBeanFactory;
import com.imwj.springframework.beans.factory.ListableBeanFactory;
import com.imwj.springframework.core.io.ResourceLoader;

/**
 * @author wj
 * @create 2022-11-04 15:39
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
