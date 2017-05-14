package com.kkmag.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kkmag.objects.AddressesMag;
import com.kkmag.objects.CustomerMag;
import com.kkmag.objects.Person;
import com.kkmag.objects.RegionMag;
import com.kkmag.objects.RegisterDetailMagen;
import com.kkmag.objects.Response;
import com.kkmag.repositories.PersonRepository;
import com.kkmag.service.MagServiceAccessor;
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

	@Autowired
	MagServiceAccessor mAccessor;

	/*
	 * @Autowired BCryptPasswordEncoder bcrypt;
	 */

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Response> createPerson(@RequestBody @Valid Person newPerson, BindingResult result) {

		ResponseEntity<Response> response = null;

		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			System.out.println(authority.toString());

		}
		err = userVaild.validate(newPerson);

		if (err.getCode() != null && ("true".equals(err.getCode()))) {
			personRepository.save(newPerson);
		}

		RegisterDetailMagen regDet = new RegisterDetailMagen();
		regDet = convertNewPersontoRegDetail(newPerson);

		/*
		 * Calling Magento Service to save/register person in Main DB
		 */

		mAccessor.save(regDet);

		res.setO(err);
		response = new ResponseEntity<Response>(res, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Response> Login(@RequestBody @Valid Person person, BindingResult result) {

		ResponseEntity<Response> response = null;

		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			System.out.println(authority.toString());

		}
		err = userVaild.validateLogin(person);

		res.setO(err);
		response = new ResponseEntity<Response>(res, HttpStatus.OK);

		return response;
	}

	public boolean allowed() {

		boolean accessallowed = false;

		return false;

	}

	private RegisterDetailMagen convertNewPersontoRegDetail(Person newPerson) {
		// TODO Auto-generated method stub

		RegisterDetailMagen regDet = new RegisterDetailMagen();

		CustomerMag cm = new CustomerMag();
		cm.setEmail(newPerson.getEmail());
		cm.setFirstname(newPerson.getLastName());
		cm.setLastname(newPerson.getLastName());

		RegionMag reg = new RegionMag();
		reg.setRegion("d");
		reg.setRegionCode("d");
		reg.setRegionId(99);
		AddressesMag[] ad = new AddressesMag[1];

		ad[0] = new AddressesMag();
		ad[0].setFirstname(newPerson.getFirstName());
		ad[0].setLastname(newPerson.getLastName());
		ad[0].setTelephone(newPerson.getPhone());
		ad[0].setRegion(reg);
		String[] street = { "d", "d" };
		ad[0].setStreet(street);
		ad[0].setCity("d");
		ad[0].setPostcode("d");
		ad[0].setCountryId("99");
		cm.setAddresses(ad);
		regDet.setCustomer(cm);
		regDet.setPassword(newPerson.getPassword());
		return regDet;
	}

}
