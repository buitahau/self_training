package com.example.springbatch.job.config;

import com.example.springbatch.dto.CustomerDto;
import com.example.springbatch.job.constant.ImportCustomerConstant;
import com.example.springbatch.job.listener.SkipCheckingListener;
import jakarta.el.PropertyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ImportCustomerFlowJobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean(name = ImportCustomerConstant.IMPORT_CUSTOMER_FLOW_JOB)
    public Job getImportCustomerJob(
        @Qualifier(ImportCustomerConstant.IMPORT_CUSTOMER_FLOW_STEP_READ_CSV) Step readCsvStep
    ) {
        return new JobBuilder(ImportCustomerConstant.IMPORT_CUSTOMER_FLOW_JOB, jobRepository)
            .start(readCsvStep)
            .on("FAILED")
            .to(sendErrorNotification())
            .from(readCsvStep)
            .on(ImportCustomerConstant.COMPLETED_WITH_SKIP_STATUS)
            .to(printSkipRecords())
            .next(sendWarningNotification())
            .from(readCsvStep)
            .on("*")
            .to(sendSuccessNotification())
            .end()
            .build();
    }

    private Step printSkipRecords() {
        return new StepBuilder("printSkipRecords", jobRepository)
            .tasklet(tasklet("printSkipRecords"), transactionManager)
            .build();
    }

    private Step sendErrorNotification() {
        return new StepBuilder("sendErrorNotification", jobRepository)
            .tasklet(tasklet("sendErrorNotification"), transactionManager)
            .build();
    }

    private Step sendWarningNotification() {
        return new StepBuilder("sendWarningNotification", jobRepository)
            .tasklet(tasklet("sendWarningNotification"), transactionManager)
            .build();
    }

    private Step sendSuccessNotification() {
        return new StepBuilder("sendSuccessNotification", jobRepository)
            .tasklet(tasklet("sendSuccessNotification"), transactionManager)
            .build();
    }

    public Tasklet tasklet(String name) {
        return (contribution, chunkContext) -> {
            log.info("Executing tasklet {}", name);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean(name = ImportCustomerConstant.IMPORT_CUSTOMER_FLOW_STEP_READ_CSV)
    protected Step readCsvStep(
        @Qualifier("importCustomerItemReader") ItemReader<CustomerDto> itemReader,
        @Qualifier("importCustomerItemProcessor") ItemProcessor<CustomerDto, CustomerDto> processor,
        @Qualifier("importCustomerItemWriter") ItemWriter<CustomerDto> itemWriter
    ) {
        return new StepBuilder(ImportCustomerConstant.IMPORT_CUSTOMER_FLOW_STEP_READ_CSV, jobRepository)
            .<CustomerDto, CustomerDto>chunk(10, transactionManager)
            .listener(new SkipCheckingListener())
            .reader(itemReader)
            .processor(processor)
            .writer(itemWriter)
            .faultTolerant()
            .skipLimit(2)
            .skip(PropertyNotFoundException.class)
            .build();
    }
}
