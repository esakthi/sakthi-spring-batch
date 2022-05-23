package com.spring.batch.job;

import com.spring.batch.config.CustomersJobConfig;
import com.spring.batch.listener.CustStepListener;
import com.spring.batch.service.CustomerItemProcessor;
import com.spring.batch.service.CustomerItemReader;
import com.spring.batch.service.CustomerItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerChunkJob {

    private Logger logger = LoggerFactory.getLogger(CustomerChunkJob.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustStepListener custStepListener;

    @Autowired
    private CustomerItemReader customerItemReader;
    @Autowired
    private CustomerItemWriter customerItemWriter;
    @Autowired
    private CustomerItemProcessor customerItemProcessor;

    public Job getCustomerChunkJob(){
        return jobBuilderFactory.get("customerChunkJob")
                .incrementer(new RunIdIncrementer())
                .start(cusJobChunkStepOne())
                .build();
    }

    private Step cusJobChunkStepOne() {
        return stepBuilderFactory.get("cusChunkStepOne")
                .<String, String>chunk(4)
                //.listener(custStepListener)
                .reader(customerItemReader)
                .processor(customerItemProcessor)
                .writer(customerItemWriter)
                .build();
    }
}
