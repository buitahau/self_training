package com.example.springbatch.job.item.reader;

import com.example.springbatch.dto.CustomerDto;
import com.example.springbatch.job.item.reader.mapper.CustomerFieldSetMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@Slf4j
public class ImportCustomerItemReaderConfig {

    @Bean(name = "importCustomerItemReader")
    @StepScope
    public FlatFileItemReader<CustomerDto> getImportCustomerItemReader(
        @Value("#{jobParameters}") Map<String, Object> jobParameters
    ) {
        FlatFileItemReader<CustomerDto> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] token = { "id", "firstname", "lastname", "email", "gender", "birthday", "status", "country", "city" };
        tokenizer.setNames(token);
        reader.setResource(getResource(jobParameters));
        DefaultLineMapper<CustomerDto> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    private Resource getResource(Map<String, Object> jobParameters) {
        String fileName = jobParameters.get("fileName").toString();
        return new FileSystemResource(fileName);
    }
}
