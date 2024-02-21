package com.example.springbatch.job.config;

import com.example.springbatch.dto.CustomerDto;
import com.example.springbatch.job.constant.ImportCustomerConstant;
import com.example.springbatch.job.processor.ImportCustomerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ImportCustomerJobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean(name = ImportCustomerConstant.IMPORT_CUSTOMER_JOB)
    public Job getImportCustomerJob(@Qualifier(ImportCustomerConstant.STEP_READ_CSV) Step readCsvStep) {
        return new JobBuilder(ImportCustomerConstant.IMPORT_CUSTOMER_JOB, jobRepository).start(readCsvStep).build();
    }

    @Bean(name = ImportCustomerConstant.STEP_READ_CSV)
    protected Step readCsvStep(
        @Qualifier("importCustomerItemReader") ItemReader<CustomerDto> itemReader,
        @Qualifier("importCustomerItemProcessor") ItemProcessor<CustomerDto, CustomerDto> processor,
        @Qualifier("importCustomerItemWriter") ItemWriter<CustomerDto> itemWriter
    ) {
        return new StepBuilder(ImportCustomerConstant.STEP_READ_CSV, jobRepository)
            .<CustomerDto, CustomerDto>chunk(10, transactionManager)
            .reader(itemReader)
            .processor(processor)
            .writer(itemWriter)
            .build();
    }

    @Bean("importCustomerItemProcessor")
    public ItemProcessor<CustomerDto, CustomerDto> itemProcessor() {
        return new ImportCustomerProcessor();
    }
}
