package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties
public class CentralRepository {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String temp_url;
	private String upload_url;

	public String getPassword() {
		return password;
	}


	public String getTemp_url() {
		return temp_url;
	}


	public void setTemp_url(String temp_url) {
		this.temp_url = temp_url;
	}


	public String getUpload_url() {
		return upload_url;
	}


	public void setUpload_url(String upload_url) {
		this.upload_url = upload_url;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

}
