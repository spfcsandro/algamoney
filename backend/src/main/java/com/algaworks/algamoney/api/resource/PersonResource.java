package com.algaworks.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping
	public List<Person> listAll(){
		return personRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Person> criar(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person personSalva = personRepository.save(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
				.buildAndExpand(personSalva.getCode()).toUri();
			response.setHeader("Location", uri.toASCIIString());
			
			return ResponseEntity.created(uri).body(personSalva);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Person> buscarPeloCodigo(@PathVariable Long code) {
		 Optional<Person> person = personRepository.findById(code);
			return person.isPresent() ? 
					ResponseEntity.ok(person.get()) : ResponseEntity.notFound().build();
	}
}
