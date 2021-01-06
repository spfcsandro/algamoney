package com.algaworks.algamoney.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long code;
	@NotNull
	private String name;
	@Embedded
	private Address address;
	@NotNull
	private Boolean active;
	@JsonIgnore
	@Transient
	public boolean isInactive() {
		return !this.active;
	}
}
