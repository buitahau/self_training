package com.example.springbatch.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private Date birthday;

    private boolean status;

    private String city;

    private String country;

    @Override
    public String toString() {
        return (
            "CustomerDto{" +
            "id='" +
            id +
            '\'' +
            ", firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", gender='" +
            gender +
            '\'' +
            ", birthday=" +
            birthday +
            ", status=" +
            status +
            ", city='" +
            city +
            '\'' +
            ", country='" +
            country +
            '\'' +
            '}'
        );
    }
}
