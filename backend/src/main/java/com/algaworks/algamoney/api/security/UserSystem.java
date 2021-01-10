package com.algaworks.algamoney.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.algaworks.algamoney.api.model.UserModel;


public class UserSystem extends User {

	private static final long serialVersionUID = 1L;

	private UserModel userModel;

	public UserSystem(UserModel userModel, Collection<? extends GrantedAuthority> authorities) {
		super(userModel.getEmail(), userModel.getPassword(), authorities);
		this.userModel = userModel;
	}

	public UserModel getUserModel() {
		return userModel;
	}

}