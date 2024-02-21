package com.example.springbatch.job.processor;

import com.example.springbatch.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ImportCustomerProcessor implements ItemProcessor<CustomerDto, CustomerDto> {

    @Override
    public CustomerDto process(CustomerDto customerDto) throws Exception {
        log.info(customerDto.toString());
        return customerDto;
    }
}
