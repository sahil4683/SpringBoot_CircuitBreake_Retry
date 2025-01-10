package com.management.management.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
public class HomeResource {
	
	@Autowired
	StudentService service;

    @GetMapping("/index")
    public String index() {
        return "\"Hello World!\"";
    }

    @GetMapping("/CircuitBreaker")
    public List<StudentDTO> getAllStudentCircuitBreaker() {    	
    	return service.getAllStudentCircuitBreaker();
    }
    
    @GetMapping("/Retry")
    public List<StudentDTO> getAllStudentRetry() {    	
    	return service.getAllStudentRetry();
    }

}
