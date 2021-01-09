package com.algaworks.algamoney.api.model;

import com.algaworks.algamoney.api.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaction.class)
public abstract class Transaction_ {

	public static volatile SingularAttribute<Transaction, Long> code;
	public static volatile SingularAttribute<Transaction, String> observation;
	public static volatile SingularAttribute<Transaction, Person> person;
	public static volatile SingularAttribute<Transaction, LocalDate> dueDate;
	public static volatile SingularAttribute<Transaction, String> description;
	public static volatile SingularAttribute<Transaction, LocalDate> paymentDate;
	public static volatile SingularAttribute<Transaction, TransactionType> type;
	public static volatile SingularAttribute<Transaction, Category> category;
	public static volatile SingularAttribute<Transaction, BigDecimal> value;

	public static final String CODE = "code";
	public static final String OBSERVATION = "observation";
	public static final String PERSON = "person";
	public static final String DUE_DATE = "dueDate";
	public static final String DESCRIPTION = "description";
	public static final String PAYMENT_DATE = "paymentDate";
	public static final String TYPE = "type";
	public static final String CATEGORY = "category";
	public static final String VALUE = "value";

}

