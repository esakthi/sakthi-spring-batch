package com.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustStepListener implements StepExecutionListener {

    private Logger logger = LoggerFactory.getLogger(CustStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("Before CustStepListener Execution, Job Parameters: " + stepExecution.getJobParameters());
        logger.info("Before CustStepListener Execution, Job Context: " + stepExecution.getExecutionContext());
        logger.info("Before CustStepListener Execution, Job Ins: " + stepExecution.getJobExecution().getJobInstance());
        stepExecution.getExecutionContext().put("country", "IND");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("Before CustStepListener Execution, Job Parameters: " + stepExecution.getJobParameters());
        logger.info("Before CustStepListener Execution, Job Context: " + stepExecution.getExecutionContext());
        logger.info("Before CustStepListener Execution, Job Ins: " + stepExecution.getJobExecution().getJobInstance());

        return null;
    }
}
