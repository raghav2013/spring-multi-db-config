package com.simplidata.multidbconfig.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"com.simplidata.multidbconfig.company.data"},
        entityManagerFactoryRef = "companyEntityManager",
        transactionManagerRef = "companyTransactionManager")
public class CompanyDataSourceConfig {
    

        
        @Autowired
    private Environment env;
 
        
    @Bean
    public LocalContainerEntityManagerFactoryBean companyEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(companyDatasource());
        em.setPackagesToScan(new String[]{"com.simplidata.multidbconfig.company.data"});
        em.setPersistenceUnitName("companyEntityManager");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String,String> properties = new HashMap<String,String>();
        properties.put("hibernate.show_sql",
                "true");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public DataSource companyDatasource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("company.datasource.url"));
        dataSource.setUsername(env.getProperty("company.datasource.username"));
        dataSource.setPassword(env.getProperty("company.datasource.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager companyTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
            companyEntityManager().getObject());
        return transactionManager;
    }
}