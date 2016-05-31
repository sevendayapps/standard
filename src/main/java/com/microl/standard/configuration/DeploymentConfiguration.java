package com.microl.standard.configuration;

import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vietlk on 31/05/2016.
 */


public class DeploymentConfiguration
                extends Configuration
                implements AutoConfigurable {

    private Map<String, String> configs = new HashMap<String, String>();

    @PostConstruct
    public void init() {
        // based on instructions, load the configuration

        // setConfigs
    }






    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }
}
