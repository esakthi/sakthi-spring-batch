package com.spring.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class CreditCheckTask implements Tasklet {

    private Logger logger= LoggerFactory.getLogger(CreditCheckTask.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        logger.info("Credit Check for the customer is completed by invoking third party API");
        logger.info("Job exe context inside Tasklet : " + chunkContext.getStepContext().getJobExecutionContext());

        logger.info("Step exe context inside Tasklet : " + chunkContext.getStepContext().getStepExecutionContext());

        return RepeatStatus.FINISHED;
    }
}
