package com.algaworks.algamoney.api.repository.transaction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algaworks.algamoney.api.model.Category_;
import com.algaworks.algamoney.api.model.Person_;
import com.algaworks.algamoney.api.model.Transaction;
import com.algaworks.algamoney.api.model.Transaction_;
import com.algaworks.algamoney.api.repository.filter.TransactionFilter;
import com.algaworks.algamoney.api.repository.projection.TransactionResume;

public class TransactionRepositoryImpl implements TransactionRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Transaction> filter(TransactionFilter transactionFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		
		Predicate[] predicates = restriction(transactionFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Transaction> query = manager.createQuery(criteria);
		createRestrictionPageable(query, pageable);
		
		return new PageImpl(query.getResultList(), pageable, total(transactionFilter)) ;
	}

	private long total(TransactionFilter transactionFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		
		Predicate[] predicates = restriction(transactionFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void createRestrictionPageable(TypedQuery<?> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRecordPage = pageable.getPageSize();
		int firstRecordPage = currentPage * totalRecordPage;
		
		query.setFirstResult(firstRecordPage);
		query.setMaxResults(totalRecordPage);
	}

	private Predicate[] restriction(TransactionFilter transactionFilter, CriteriaBuilder builder,
			Root<Transaction> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(transactionFilter.getDescription())) {
			predicates.add(builder.like(
					builder.lower(root.get(Transaction_.description)), "%" + transactionFilter.getDescription().toLowerCase() + "%"));
		}
		
		if (transactionFilter.getDueDateOf() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Transaction_.dueDate), transactionFilter.getDueDateOf()));
		}
		
		if (transactionFilter.getDueDateTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Transaction_.dueDate), transactionFilter.getDueDateTo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	@Override
	public Page<TransactionResume> resume(TransactionFilter transactionFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TransactionResume> criteria = builder.createQuery(TransactionResume.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		
		criteria.select(builder.construct(TransactionResume.class
				, root.get(Transaction_.code), root.get(Transaction_.description)
				, root.get(Transaction_.dueDate), root.get(Transaction_.paymentDate)
				, root.get(Transaction_.value), root.get(Transaction_.type)
				, root.get(Transaction_.category).get(Category_.name)
				, root.get(Transaction_.person).get(Person_.name)));
		
		Predicate[] predicates = restriction(transactionFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<TransactionResume> query = manager.createQuery(criteria);
		createRestrictionPageable(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(transactionFilter));
	}


}
