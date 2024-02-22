package com.example.springbatch.job.processor;

import com.example.springbatch.dto.CustomerDto;
import jakarta.el.PropertyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ImportCustomerProcessor implements ItemProcessor<CustomerDto, CustomerDto> {

    @Override
    public CustomerDto process(CustomerDto customerDto) throws Exception {
        log.info(customerDto.toString());
        if (StringUtils.isBlank(customerDto.getId())) {
            throw new PropertyNotFoundException("Not found id property");
        }
        if (StringUtils.isBlank(customerDto.getFirstName()) && StringUtils.isBlank(customerDto.getLastName())) {
            throw new RuntimeException("Missing first name and last name");
        }
        return customerDto;
    }
}
