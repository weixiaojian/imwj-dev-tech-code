package com.imwj.design;

/**
 * 备忘录类
 * 对原有配置类的扩展，可以设置和获取配置信息
 * @author wj
 * @create 2023-06-13 11:28
 */
public class ConfigMemento {

    private ConfigFile configFile;

    public ConfigMemento(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }
}
