package com.spring.batch.config;

import com.spring.batch.job.CustomerChunkJob;
import com.spring.batch.job.CustomerTaskletJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class CustomersJobConfig {

    /*private Logger logger = LoggerFactory.getLogger(CustomersJobConfig.class);

    @Autowired
    private CustomerTaskletJob customerTaskletJob;
    @Autowired
    private CustomerChunkJob customerChunkJob;

    @Bean
    public Job custTaskletJob() {
        return customerTaskletJob.getCustomerTaskletJob();
    }

    @Bean
    public Job custChunkJob() {
        return customerChunkJob.getCustomerChunkJob();
    }*/

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.universitydatasource")
    public DataSource universitydatasource() {
        return DataSourceBuilder.create().build();
    }

}
