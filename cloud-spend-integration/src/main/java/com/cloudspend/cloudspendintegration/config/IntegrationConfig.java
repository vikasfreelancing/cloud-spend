package com.cloudspend.cloudspendintegration.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ConfigurationProperties("api-config")
@Getter
@Setter
@ToString
@PropertySources({
        @PropertySource(value = {"classpath:api-${spring.profiles.active}.properties"}),
        /*
         * the below file is added so as to provide a consolidated application properties file to the QA
         */
        @PropertySource(value = "classpath:application-configuration.properties", ignoreResourceNotFound = true)
})

public class IntegrationConfig implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(IntegrationConfig.class);

    private Expensify expensify;

    @Override
    public void afterPropertiesSet() throws Exception {

        LOGGER.info("Integration Properties ==> " + this.toString());
    }

    @Getter
	@Setter
	@ToString
	public static class Expensify {
		private String url;
	}
}
