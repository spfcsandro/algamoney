package com.algaworks.algamoney.api.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String zipCode;
	private String city;
	private String state;
}
