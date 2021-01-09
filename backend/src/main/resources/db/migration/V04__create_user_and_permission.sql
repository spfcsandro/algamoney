CREATE TABLE user (
	code BIGINT(20) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission (
	code BIGINT(20) PRIMARY KEY,
	description VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_permission (
	user_code BIGINT(20) NOT NULL,
	permission_code BIGINT(20) NOT NULL,
	PRIMARY KEY (user_code, permission_code),
	FOREIGN KEY (user_code) REFERENCES user(code),
	FOREIGN KEY (permission_code) REFERENCES permission(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user (code, name, email, password) values (1, 'Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO user (code, name, email, password) values (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permission (code, description) values (1, 'ROLE_CREATE_CATEGORY');
INSERT INTO permission (code, description) values (2, 'ROLE_FILTER_CATEGORY');

INSERT INTO permission (code, description) values (3, 'ROLE_CREATE_PERSON');
INSERT INTO permission (code, description) values (4, 'ROLE_DELETE_PERSON');
INSERT INTO permission (code, description) values (5, 'ROLE_FILTER_PERSON');

INSERT INTO permission (code, description) values (6, 'ROLE_CREATE_TRANSACTION');
INSERT INTO permission (code, description) values (7, 'ROLE_DELETE_TRANSACTION');
INSERT INTO permission (code, description) values (8, 'ROLE_FILTER_TRANSACTION');

-- admin
INSERT INTO user_permission (user_code, permission_code) values (1, 1);
INSERT INTO user_permission (user_code, permission_code) values (1, 2);
INSERT INTO user_permission (user_code, permission_code) values (1, 3);
INSERT INTO user_permission (user_code, permission_code) values (1, 4);
INSERT INTO user_permission (user_code, permission_code) values (1, 5);
INSERT INTO user_permission (user_code, permission_code) values (1, 6);
INSERT INTO user_permission (user_code, permission_code) values (1, 7);
INSERT INTO user_permission (user_code, permission_code) values (1, 8);

-- maria
INSERT INTO user_permission (user_code, permission_code) values (2, 2);
INSERT INTO user_permission (user_code, permission_code) values (2, 5);
INSERT INTO user_permission (user_code, permission_code) values (2, 8);