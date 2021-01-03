package com.algaworks.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	public Person update(Long code, Person person) {
		Person personSave = this.personRepository.findById(code)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
	
		BeanUtils.copyProperties(person, personSave, "code");
		return this.personRepository.save(personSave);
	}

	public void updateActive(Long code, Boolean active) {
		Person personSave = buscarPersonPeloCodigo(code);
		personSave.setActive(active);
		personRepository.save(personSave);
	}
	
	private Person buscarPersonPeloCodigo(Long code) {
		Person personSave = this.personRepository.findById(code)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		return personSave;
	}
}
