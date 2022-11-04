package com.imwj.springframework.context.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.context.ConfigurableApplicationContext;
import com.imwj.springframework.core.io.DefaultResourceLoader;

/**
 * @author wj
 * @create 2022-11-04 15:39
 */
public class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {


    @Override
    public void refresh() throws BeansException {

    }
}
