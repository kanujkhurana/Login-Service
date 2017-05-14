package com.kkmag.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkmag.objects.Person;

public interface PersonRepository extends MongoRepository<Person, String>{
	
	Person findByUsername(String Username);

	

}
