package com.algaworks.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.model.Transaction;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.repository.TransactionRepository;
import com.algaworks.algamoney.api.service.exception.PersonInexistenteOuInativaException;

@Service
public class TransactionService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired 
	private TransactionRepository transactionRepository;

	public Transaction save(Transaction transaction) {
		Optional<Person> person = personRepository.findById(transaction.getPerson().getCode());
		if (!person.isPresent() || person.get().isInactive()) {
			throw new PersonInexistenteOuInativaException();
		}
		
		return transactionRepository.save(transaction);
	}
}
