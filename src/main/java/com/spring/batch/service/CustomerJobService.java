package com.spring.batch.service;

import com.spring.batch.config.CustomersJobConfig;
import com.spring.batch.job.CsvToJdbcLoaderJob;
import com.spring.batch.job.CustomerChunkJob;
import com.spring.batch.job.CustomerTaskletJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerJobService {

    private Logger logger = LoggerFactory.getLogger(CustomerJobService.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private CustomerTaskletJob custTaskletJob;
    @Autowired
    private CustomerChunkJob custChunkJob;
    @Autowired
    private CsvToJdbcLoaderJob csvToJdbcLoaderJob;

    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void taskletJobScheduler(){
        String jobName = "Tasklet";
        startJobByName(jobName);
    }

    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void chunkJobScheduler(){
        String jobName = "Chunk";
        startJobByName(jobName);
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void csvToJdbcJobScheduler(){
        String jobName = "csv2jdbc";
        startJobByName(jobName);
    }

    @Async
    public void startJobByName(String jobName){
        try{
            JobExecution jobExecution = null;
            Map<String, JobParameter> jpMap = new HashMap<>();
            jpMap.put("CurrentTime", new JobParameter(System.currentTimeMillis()));

            JobParameters jobParameters = new JobParameters(jpMap);

            if( jobName != null && jobName.contains("Tasklet")){
                jobExecution = jobLauncher.run(custTaskletJob.getCustomerTaskletJob(), jobParameters);
            }else if( jobName != null && jobName.contains("Chunk")){
                jobExecution = jobLauncher.run(custChunkJob.getCustomerChunkJob(), jobParameters);
            }else if( jobName != null && jobName.contains("csv2jdbc")){
                jobExecution = jobLauncher.run(csvToJdbcLoaderJob.csvToJdbcLoaderJob(), jobParameters);
            }
            logger.info("Job started from Job service, Job ID : " + jobExecution.getJobId() + "");
        }catch (Exception e){
            logger.error("Error while launching the job ",e);
        }
    }
}
