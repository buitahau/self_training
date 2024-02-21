package com.example.springbatch.job;

import com.example.springbatch.job.constant.ImportCustomerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImportCustomerRunningJob {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(ImportCustomerConstant.IMPORT_CUSTOMER_JOB)
    private Job importCustomerJob;

    @Scheduled(fixedRate = 10000)
    public void run() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("jobId", "ImportCustomerJob_" + System.currentTimeMillis())
            .addString("fileName", "src/main/resources/input/record_21022023.csv")
            .toJobParameters();
        JobExecution execution = jobLauncher.run(importCustomerJob, jobParameters);
        log.info("Finish importing customer job with status {}", execution.getStatus());
    }
}
