package com.algaworks.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreateResourceEvent;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public List<Person> listAll(){
		return personRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Person> save(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person personSave = personRepository.save(person);
		publisher.publishEvent(new CreateResourceEvent(this, response, person.getCode()));
		return ResponseEntity.status(HttpStatus.CREATED).body(personSave);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Person> findById(@PathVariable Long code) {
		 Optional<Person> person = personRepository.findById(code);
			return person.isPresent() ? 
					ResponseEntity.ok(person.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long code) {
		 personRepository.deleteById(code);
	}
	
	@PutMapping("/{code}")
	public ResponseEntity<Person> update(@PathVariable Long code, @Valid @RequestBody Person person) {
		Person personSave = personService.update(code, person);
		return ResponseEntity.ok(personSave);
	}
	
	@PutMapping("/{code}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long code, @RequestBody Boolean active) {
		personService.updateActive(code, active);
	}
}
