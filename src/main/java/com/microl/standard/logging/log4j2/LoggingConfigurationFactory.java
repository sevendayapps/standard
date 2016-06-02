package com.microl.standard.logging.log4j2;

import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by vietlk on 02/06/2016.
 */

@Plugin(name = "LoggingConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class LoggingConfigurationFactory extends ConfigurationFactory {


    @Override
    protected String[] getSupportedTypes() {
        return new String[0];
    }

    @Override
    public org.apache.logging.log4j.core.config.Configuration getConfiguration(
                                                                    ConfigurationSource configurationSource) {
        return null;
    }
}
