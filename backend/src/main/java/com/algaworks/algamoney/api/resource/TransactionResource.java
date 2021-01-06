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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreateResourceEvent;
import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoney.api.model.Transaction;
import com.algaworks.algamoney.api.repository.TransactionRepository;
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
	public List<Transaction> listAll() {
		return transactionRepository.findAll();
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Transaction> findByCode(@PathVariable Long code) {
		Optional<Transaction> transaction = transactionRepository.findById(code);
		return transaction.isPresent() ? 
				ResponseEntity.ok(transaction.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Transaction> save(@Valid @RequestBody Transaction transaction, HttpServletResponse response){
		Transaction transactionSave = transactionService.save(transaction);
		publisher.publishEvent(new CreateResourceEvent(this, response, transaction.getCode()));
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionSave);
	}
	
	@ExceptionHandler({ PersonInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePersonInexistenteOuInativaException(PersonInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
}
