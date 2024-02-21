package com.example.springbatch.job.item.reader.mapper;

import com.example.springbatch.dto.CustomerDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFieldSetMapper implements FieldSetMapper<CustomerDto> {

    @Override
    public CustomerDto mapFieldSet(FieldSet fieldSet) throws BindException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(fieldSet.readString("id"));
        customerDto.setFirstName(fieldSet.readString("firstname"));
        customerDto.setLastName(fieldSet.readString("lastname"));
        customerDto.setEmail(fieldSet.readString("email"));
        customerDto.setGender(fieldSet.readString("gender"));
        customerDto.setBirthday(fieldSet.readDate("birthday"));
        customerDto.setCity(fieldSet.readString("city"));
        customerDto.setCountry(fieldSet.readString("country"));
        return customerDto;
    }
}
