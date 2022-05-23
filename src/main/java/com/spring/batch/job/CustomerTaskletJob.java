package com.spring.batch.job;

import com.spring.batch.listener.CustStepListener;
import com.spring.batch.listener.CustomerJobListener;
import com.spring.batch.service.CreditCheckTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerTaskletJob {

    private Logger logger = LoggerFactory.getLogger(CustomerTaskletJob.class);

    @Autowired
    private CreditCheckTask creditCheckTask;
    @Autowired
    private CustomerJobListener customerJobListerner;
    @Autowired
    private CustStepListener custStepListener;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public Job getCustomerTaskletJob(){
        return jobBuilderFactory.get("customerJob")
                .incrementer(new RunIdIncrementer())
                .start(cusJobStep())
                .next(cusJobStepTwo())
                .next(cusJobCreditCheckStep())
                //.listener(customerJobListerner)
                .build();
    }

    private Step cusJobStep() {
        return stepBuilderFactory.get("cusJobStepOne")
                .tasklet(cusTaskletTaskStart())
                //.listener(custStepListener)
                .build();
    }

    private Step cusJobStepTwo() {
        return stepBuilderFactory.get("cusJobStepTwo")
                .tasklet(cusTaskletTaskTwo()).build();
    }

    private Step cusJobCreditCheckStep() {
        return stepBuilderFactory.get("creditCheckStep")
                .tasklet(creditCheckTask).build();
    }

    private Tasklet cusTaskletTaskStart() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                logger.info("This is a first task executed from customer first step");
                return RepeatStatus.FINISHED;
            }
        };
    }

    private Tasklet cusTaskletTaskTwo() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                logger.info("This is a second task executed from customer second step");
                return RepeatStatus.FINISHED;
            }
        };
    }
}
