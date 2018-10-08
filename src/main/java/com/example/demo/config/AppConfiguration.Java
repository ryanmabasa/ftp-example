package com.astro.fb.wss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "datasource")
public class AppConfiguration {

	@NestedConfigurationProperty
	private CentralRepository central_repository;

	@NestedConfigurationProperty
	private Proxy proxy;


	public Proxy getProxy() {
		return proxy;
    }

	public void setProxy(Proxy proxy){
		this.proxy = proxy;
	}

	public void setCentralRepository(CentralRepository central_repository){
		this.central_repository = central_repository;
	}

	public CentralRepository getCentralRepository(){
		return central_repository;
	}


}
