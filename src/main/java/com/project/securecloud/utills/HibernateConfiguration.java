package com.project.securecloud.utills;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class HibernateConfiguration {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.min.pool.size}")
	private String minPoolSize;

	@Value("${spring.datasource.max.pool.size}")
	private String maxPoolSize;

	@Value("${spring.datasource.connection.timeout}")
	private String connectiontimeOut;

	@Value("${spring.datasource.idle.timeout}")
	private String idletimeOut;

	@Value("${spring.datasource.max.lifetime}")
	private String maxLifeTime;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private Object hibernateDialect;

	@Value("${spring.jpa.show-sql}")
	private Object hibernateShowSql;

	@Value("${spring.jpa.properties.hibernate.current_session_context_class}")
	private Object sessionContext;

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(String.format(url));
		config.setUsername(userName);
		config.setPassword(password);
		config.setMinimumIdle(Integer.parseInt(minPoolSize));
		config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
		config.setConnectionTimeout(Integer.parseInt(connectiontimeOut)); // 30 seconds
		config.setIdleTimeout(Integer.parseInt(idletimeOut)); // 5 minutes
		config.setMaxLifetime(Integer.parseInt(maxLifeTime)); // 5 minutes
		DataSource pool = new HikariDataSource(config);
		return pool;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
		Properties properties = new Properties();

		// See: application.properties
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.show_sql", hibernateShowSql);
		properties.put("current_session_context_class", //
				sessionContext);

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		// Package contain entity classes
		factoryBean.setPackagesToScan(new String[] { "" });
		factoryBean.setDataSource(dataSource);
		factoryBean.setHibernateProperties(properties);
		factoryBean.afterPropertiesSet();
		SessionFactory sf = factoryBean.getObject();
		log.info("## getSessionFactory: {}", sf);
		return sf;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

}