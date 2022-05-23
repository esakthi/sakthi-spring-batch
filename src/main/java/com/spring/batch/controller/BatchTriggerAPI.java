package com.spring.batch.controller;

import com.spring.batch.service.CustomerJobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class BatchTriggerAPI {

    @Autowired
    private CustomerJobService customerJobService;

    @Autowired
    private JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName)throws Exception{
        customerJobService.startJobByName(jobName);
        return "Job Started .... ";
    }

    @GetMapping("/stop/{jobExecutionId}")
    public String startJob(@PathVariable long jobExecutionId)throws Exception{
        jobOperator.stop(jobExecutionId);
        return "Job Stopped for the Job execution ID : " + jobExecutionId;
    }
}
