CREATE TABLE user(
        id        Int  Auto_increment  NOT NULL ,
        lastName  Varchar (150) NOT NULL ,
        firstName Varchar (150) NOT NULL ,
        email     Varchar (150) NOT NULL
	,CONSTRAINT user_PK PRIMARY KEY (id)
);


CREATE TABLE account(
        id      Int  Auto_increment  NOT NULL ,
          accountNumber  Varchar(250) UNIQUE NOT NULL ,
        amount  Decimal NOT NULL ,
        type    Varchar (20) NOT NULL ,
        userId Int NOT NULL
	,CONSTRAINT account_PK PRIMARY KEY (id)
	,CONSTRAINT account_user_FK FOREIGN KEY (userId) REFERENCES user(id)
);





CREATE TABLE operation(
        id      Int  Auto_increment  NOT NULL ,
        userId            Int NOT NULL ,
        accountId       Int NOT NULL ,
        operationDate Date NOT NULL ,
        amount        Decimal NOT NULL ,
        balance       Decimal NOT NULL ,
        type          Varchar (20) NOT NULL
	,CONSTRAINT operation_PK PRIMARY KEY (id)
	,CONSTRAINT operation_account_FK FOREIGN KEY (accountId) REFERENCES account(id)
	,CONSTRAINT operation_user0_FK FOREIGN KEY (userId) REFERENCES user(id)
);

INSERT INTO user (id,lastName, firstName, email)
 VALUES (1,'Kumba', 'Sylvain', 'kumba@gmail.com');

INSERT INTO account (id,accountNumber,amount, type, userId)
 VALUES (1, 'FR76XXX',15000, 'CURRENT_ACCOUNT',1);