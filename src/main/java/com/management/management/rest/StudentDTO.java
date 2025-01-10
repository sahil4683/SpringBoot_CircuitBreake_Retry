package com.management.management.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentDTO {

    private Long id;

    private String name;

    private Integer age;

    private Integer room;

    private Long department;

}