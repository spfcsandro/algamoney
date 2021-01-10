package com.algaworks.algamoney.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.algamoney.api.enums.TransactionType;

import lombok.Data;

@Data
public class TransactionResume {

	private Long code;
	private String description;
	private LocalDate dueDate;
	private LocalDate paymentDate;
	private BigDecimal value;
	private TransactionType type;
	private String category;
	private String person;
	
	public TransactionResume(Long code, String description, LocalDate dueDate, LocalDate paymentDate,
			BigDecimal value, TransactionType type, String category, String person) {
		this.code = code;
		this.description = description;
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
		this.value = value;
		this.type = type;
		this.category = category;
		this.person = person;
	}
}
