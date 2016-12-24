package com.kkmag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.kkmag.objects.Person;
import com.kkmag.objects.Response;
import com.kkmag.repositories.PersonRepository;
import com.kkmag.service.UserValidator;
import com.kkmag.objects.ServiceError;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	
	@Autowired
	private UserValidator userVaild;
	
	@Autowired
	private ServiceError err;
	
	@Autowired
	private Response res;
	
	/*@Autowired
	BCryptPasswordEncoder bcrypt;*/
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public Response createPerson(@RequestBody Person newPerson){
		
		System.out.println("Inside Register");
		System.out.println(newPerson.getUsername());
		
		
		err = userVaild.validate(newPerson);
		
		if(err.getCode() != null){
		personRepository.save(newPerson);
		}
		
		res.setO(err);
		return res;
	}
	
}
