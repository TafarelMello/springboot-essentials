package com.tafarelmello.springbootessentials.domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Student extends AbstractEntity {

	@NotEmpty(message = "O campo nome é obrigatório.")
	private String name;

	@NotEmpty(message = "O campo e-mail é obrigatório.")
	@Email(message = "E-mail inválido.")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
