package com.algaworks.algamoney.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserModel.class)
public abstract class UserModel_ {

	public static volatile SingularAttribute<UserModel, String> password;
	public static volatile SingularAttribute<UserModel, Long> code;
	public static volatile ListAttribute<UserModel, Permission> permissions;
	public static volatile SingularAttribute<UserModel, String> name;
	public static volatile SingularAttribute<UserModel, String> email;

	public static final String PASSWORD = "password";
	public static final String CODE = "code";
	public static final String PERMISSIONS = "permissions";
	public static final String NAME = "name";
	public static final String EMAIL = "email";

}

