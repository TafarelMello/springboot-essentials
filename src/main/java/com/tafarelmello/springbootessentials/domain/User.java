package com.tafarelmello.springbootessentials.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends AbstractEntity {

	@NotEmpty(message = "Usuário é um campo obrigatório.")
	@Column(unique = true)
	private String username;

	@NotEmpty(message = "Senha é um campo obrigatório.")
	@JsonIgnore
	private String password;

	@NotEmpty(message = "Nome é um campo obrigatório.")
	private String name;

	@NotEmpty
	private boolean admin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
