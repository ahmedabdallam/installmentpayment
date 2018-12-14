package com.test.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getConnection() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate getJDBCTemplate() {
		return new JdbcTemplate(getConnection());
	}
}
