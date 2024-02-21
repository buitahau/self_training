package com.example.springbatch.config;

import javax.sql.DataSource;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
            .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
            .build();
        /*
        // TODO: add liquibase to generate batch table
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.h2.Driver");
        dataSource.url("jdbc:h2:mem:dbspringbatch");
        dataSource.username("sa");
        dataSource.password("sa");
        return dataSource.build();
         */
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean(name = "jobRepository")
    public JobRepository getJobRepository(
        @Qualifier("transactionManager") PlatformTransactionManager platformTransactionManager
    ) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(platformTransactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "jobLauncher")
    public JobLauncher getJobLauncher(@Qualifier("jobRepository") JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
