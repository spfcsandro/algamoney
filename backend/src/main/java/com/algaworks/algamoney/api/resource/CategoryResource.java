package com.algaworks.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreateResourceEvent;
import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FILTER_CATEGORY') and #oauth2.hasScope('read')")
	public List<Category> listAll(){
		return categoryRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_CATEGORY') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Category> save(@Valid @RequestBody Category category, HttpServletResponse response){
		Category categorySave =	categoryRepository.save(category);
		publisher.publishEvent(new CreateResourceEvent(this, response, category.getCode()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
	}
	
	@GetMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_FILTER_CATEGORY') and #oauth2.hasScope('read')")
	public ResponseEntity<Category> findByCode(@PathVariable Long code) {
		Optional<Category> category = categoryRepository.findById(code);
		return category.isPresent() ? 
				ResponseEntity.ok(category.get()) : ResponseEntity.notFound().build();
	}
}
