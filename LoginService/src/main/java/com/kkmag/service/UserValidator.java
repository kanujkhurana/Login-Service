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
			err.setCode("false");
			return err;
		}
		err.setCode("true");
		
		return err;
	}

	public ServiceError validateLogin(Person person) {
		// TODO Auto-generated method stub
		
		ServiceError err = new ServiceError();
		
		Person per = personRep.findByUsername(person.getUsername());
		
		if(per != null){
		if(!(per.getPassword().equals(person.getPassword()))){
			err.setDescription(ErrorCodes.badCredentials);
			err.setCode("false");
			return err;
		}
		
		
		err.setCode("true");
		return err;
	}
		else{
			err.setDescription(ErrorCodes.invalidUsername);
			err.setCode("false");
			return err;
		}
	}
}
