CREATE TABLE transaction (
	code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	due_date DATE NOT NULL,
	payment_date DATE,
	value DECIMAL(10,2) NOT NULL,
	observation VARCHAR(100),
	type VARCHAR(20) NOT NULL,
	category_code BIGINT(20) NOT NULL,
	person_code BIGINT(20) NOT NULL,
	FOREIGN KEY (category_code) REFERENCES category(code),
	FOREIGN KEY (person_code) REFERENCES person(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEIVEMENT', 1, 1);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'EXPENSES', 2, 2);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Top Club', '2017-06-10', null, 120, null, 'RECEIVEMENT', 3, 3);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'RECEIVEMENT', 3, 4);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('DMAE', '2017-06-10', null, 200.30, null, 'EXPENSES', 3, 5);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'RECEIVEMENT', 4, 6);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Bahamas', '2017-06-10', null, 500, null, 'RECEIVEMENT', 1, 7);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'EXPENSES', 4, 8);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'EXPENSES', 3, 9);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'RECEIVEMENT', 5, 10);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Café', '2017-06-10', null, 8.32, null, 'EXPENSES', 1, 5);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'EXPENSES', 5, 4);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'EXPENSES', 4, 3);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'EXPENSES', 4, 2);
INSERT INTO transaction (description, due_date, payment_date, value, observation, type, category_code, person_code) values ('Lanche', '2017-06-10', null, 10.20, null, 'EXPENSES', 4, 1);