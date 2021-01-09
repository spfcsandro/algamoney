package com.algaworks.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.api.model.Transaction;
import com.algaworks.algamoney.api.repository.transaction.TransactionRepositoryQuery;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryQuery {

}
