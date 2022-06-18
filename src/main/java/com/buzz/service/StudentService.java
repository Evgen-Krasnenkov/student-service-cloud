package com.buzz.service;

import com.buzz.dto.AddressResponse;
import com.buzz.dto.CreateStudentRequest;
import com.buzz.dto.StudentResponse;
import com.buzz.feignclient.AddressFeignClient;
import com.buzz.model.Student;
import com.buzz.repos.StudentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	StudentRepository studentRepository;
	AddressFeignClient addressFeignClient;

	CommonService commonService;

	public StudentService(StudentRepository studentRepository, AddressFeignClient addressFeignClient, CommonService commonService) {
		this.studentRepository = studentRepository;
		this.addressFeignClient = addressFeignClient;
		this.commonService = commonService;
	}

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);
		StudentResponse studentResponse = new StudentResponse(student);
		studentResponse.setAddressResponse(commonService.getAddressByStudentId(student.getAddressId()));
		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		Student student = studentRepository.findById(id).orElseThrow();
		StudentResponse studentResponse = new StudentResponse(student);
		studentResponse.setAddressResponse(commonService.getAddressByStudentId(student.getAddressId()));
		return studentResponse;
	}
}
