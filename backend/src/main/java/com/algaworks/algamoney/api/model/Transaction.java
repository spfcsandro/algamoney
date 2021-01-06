package com.algaworks.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.algaworks.algamoney.api.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long code;
	@NotNull
	private String description;
	@NotNull
	@Column(name = "due_date")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dueDate;
	@Column(name = "payment_date")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate paymentDate;
	@NotNull
	private BigDecimal value;
	@NotNull
	private String observation;
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "category_code")
	private Category category;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "person_code")
	private Person person;
}
