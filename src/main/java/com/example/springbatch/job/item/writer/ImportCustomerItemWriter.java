package com.example.springbatch.job.item.writer;

import com.example.springbatch.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImportCustomerItemWriter implements ItemWriter<CustomerDto> {

    @Override
    public void write(Chunk<? extends CustomerDto> chunk) throws Exception {
        log.info("write chunk size {}", chunk.size());
    }
}
