package com.management.management.rest;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class StudentService {
	
	private int counter = 1;

	@CircuitBreaker(name = "studentService", fallbackMethod = "fallbackGetAllStudent")
	public List<StudentDTO> getAllStudentCircuitBreaker() {
		counter = counter + 1;
		System.out.println("counter: "+counter);
		if (Math.random() < 0.7) { // Simulate failure 70% of the time
			System.err.println("Service is unavailable");
	        throw new RuntimeException("Service is unavailable");
	    }
		counter = 0;
		return Stream.of(
				StudentDTO.builder().id(1l).name("Sahil").age(27).department(1l).build(),
				StudentDTO.builder().id(2l).name("Sanjay").age(53).department(2l).build(),
				StudentDTO.builder().id(1l).name("Ravindra").age(47).department(1l).build()
				).toList();
	}

	@Retry(name = "studentService", fallbackMethod = "fallbackGetAllStudent")
	public List<StudentDTO> getAllStudentRetry() {
		counter = counter + 1;
		System.out.println("counter: "+counter);
		if (Math.random() < 0.7) { // Simulate failure 70% of the time
			System.err.println("Service is unavailable");
	        throw new RuntimeException("Service is unavailable");
	    }
		counter = 0;
		return Stream.of(
				StudentDTO.builder().id(1l).name("Sahil").age(27).department(1l).build(),
				StudentDTO.builder().id(2l).name("Sanjay").age(53).department(2l).build(),
				StudentDTO.builder().id(1l).name("Ravindra").age(47).department(1l).build()
				).toList();
	}
	
	public List<StudentDTO> fallbackGetAllStudent(RuntimeException throwable) {
	    return List.of();
	}

}
