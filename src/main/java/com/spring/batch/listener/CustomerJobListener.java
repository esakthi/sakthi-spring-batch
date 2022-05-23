package com.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerJobListener implements JobExecutionListener {

    private Logger logger = LoggerFactory.getLogger(CustomerJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Before Execution, Job Parameters: " + jobExecution.getJobParameters());
        logger.info("Before Execution, Job Context: " + jobExecution.getExecutionContext());
        logger.info("Before Execution, Job Ins: " + jobExecution.getJobInstance());
        jobExecution.getExecutionContext().put("customerName", "Sakthivadivel");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("After Execution, Job Parameters: " + jobExecution.getJobParameters());
        logger.info("After Execution, Job Context: " + jobExecution.getExecutionContext());
        logger.info("After Execution, Job Ins: " + jobExecution.getJobInstance());
    }
}
