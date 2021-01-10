package com.algaworks.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

	public Transaction update(Long code, Transaction transaction) {
		Transaction transactionSalvo = buscarTransactionExistente(code);
		if (!transaction.getPerson().equals(transactionSalvo.getPerson())) {
			validarPerson(transaction);
		}

		BeanUtils.copyProperties(transaction, transactionSalvo, "code");

		return transactionRepository.save(transactionSalvo);
	}

	private void validarPerson(Transaction transaction) {
		Optional<Person> person = null;
		if (transaction.getPerson().getCode() != null) {
			person = personRepository.findById(transaction.getPerson().getCode());
		}

		if (person == null || person.get().isInactive()) {
			throw new PersonInexistenteOuInativaException();
		}
	}

	private Transaction buscarTransactionExistente(Long codigo) {
		Optional<Transaction> transactionSalvo = transactionRepository.findById(codigo);
		if (transactionSalvo == null) {
			throw new IllegalArgumentException();
		}
		return transactionSalvo.get();
	}
}
