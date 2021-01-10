package com.algaworks.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreateResourceEvent;
import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoney.api.model.Transaction;
import com.algaworks.algamoney.api.repository.TransactionRepository;
import com.algaworks.algamoney.api.repository.filter.TransactionFilter;
import com.algaworks.algamoney.api.repository.projection.TransactionResume;
import com.algaworks.algamoney.api.service.TransactionService;
import com.algaworks.algamoney.api.service.exception.PersonInexistenteOuInativaException;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FILTER_TRANSACTION') and #oauth2.hasScope('read')")
	public Page<Transaction> listAll(TransactionFilter transactionFilter, Pageable pageable) {
		return transactionRepository.filter(transactionFilter, pageable);
	}
	
	@GetMapping(params = "resume")
	@PreAuthorize("hasAuthority('ROLE_FILTER_TRANSACTION') and #oauth2.hasScope('read')")
	public Page<TransactionResume> reume(TransactionFilter transactionFilter, Pageable pageable) {
		return transactionRepository.resume(transactionFilter, pageable);
	}
	
	@GetMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_FILTER_TRANSACTION') and #oauth2.hasScope('read')")
	public ResponseEntity<Transaction> findByCode(@PathVariable Long code) {
		Optional<Transaction> transaction = transactionRepository.findById(code);
		return transaction.isPresent() ? 
				ResponseEntity.ok(transaction.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CREATE_TRANSACTION') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Transaction> save(@Valid @RequestBody Transaction transaction, HttpServletResponse response){
		Transaction transactionSave = transactionService.save(transaction);
		publisher.publishEvent(new CreateResourceEvent(this, response, transaction.getCode()));
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionSave);
	}
	
	@ExceptionHandler({ PersonInexistenteOuInativaException.class })
	@PreAuthorize("hasAuthority('ROLE_DELETE_TRANSACTION') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> handlePersonInexistenteOuInativaException(PersonInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long code) {
		transactionRepository.deleteById(code);
	}
	
	@PutMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_CREATE_TRANSACTION')")
	public ResponseEntity<Transaction> update(@PathVariable Long code, @Valid @RequestBody Transaction transaction) {
		try {
			Transaction transactionSave = transactionService.update(code, transaction);
			return ResponseEntity.ok(transactionSave);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
