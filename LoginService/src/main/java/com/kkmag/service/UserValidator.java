package com.kkmag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kkmag.repositories.PersonRepository;
import com.kkmag.constants.ErrorCodes;
import com.kkmag.objects.ServiceError;
import com.kkmag.objects.Person;

@Component
public class UserValidator {

	@Autowired
	private PersonRepository personRep;
		
	public ServiceError validate(Person person){
		
		ServiceError err = new ServiceError();
		
		if(personRep.findByUsername(person.getUsername()) != null){
			err.setDescription(ErrorCodes.duplicateUsername);
			return err;
		}
		err.setCode("true");
		
		return err;
	}
}
