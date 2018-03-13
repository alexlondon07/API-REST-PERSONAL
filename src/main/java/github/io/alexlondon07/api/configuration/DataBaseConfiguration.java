package github.io.alexlondon07.api.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration {

	@Bean	
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean	 sessionfactoryBean = new LocalSessionFactoryBean();
		sessionfactoryBean.setDataSource(dataSource());
		sessionfactoryBean.setPackagesToScan("github.io.alexlondon07.api.models");
		sessionfactoryBean.setHibernateProperties(hibernateProperties());
		return sessionfactoryBean;
	}
		
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");				
		dataSource.setUrl("jdbc:mysql://localhost:3306/api_accounting");
		dataSource.setUsername("api_accounting");
		dataSource.setPassword("api_accounting");	
		
		return dataSource;
	}
	
	
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("show_sql", "true");
		return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
		return hibernateTransactionManager;
	}
}
