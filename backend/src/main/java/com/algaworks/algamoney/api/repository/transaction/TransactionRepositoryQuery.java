package com.algaworks.algamoney.api.repository.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Transaction;
import com.algaworks.algamoney.api.repository.filter.TransactionFilter;
import com.algaworks.algamoney.api.repository.projection.TransactionResume;

public interface TransactionRepositoryQuery {

	public Page<Transaction> filter(TransactionFilter transactionFilter, Pageable pageable);
	public Page<TransactionResume> resume(TransactionFilter transactionFilter, Pageable pageable);
}
