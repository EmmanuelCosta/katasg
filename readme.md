***********#
#   Projet Bank Account kata     #
***********#

This is a maven project with jdk 11
It implements 3 user stories :
* Deposit
* Withraw
* Historic



# How to use it

* Construct and build the project 
* Import the project in your favorite IDE

* From your IDE perform a first mvn clean install after you have successfully 

* Main class is : kata.sg.Application

* It is a spring boot rest api
with this exposing root (see kata.sg.rest.AccountController for more details)


* From the target jar
java -jar target/kataSg-1.0-SNAPSHOT.jar

# API REST Endpoint

* URL : http://localhost:8080
* Swagger : http://localhost:8080/swagger-ui.html#/

## Deposit


* API METHOD : POST 
* API ENDPOINT : localhost:8080/api/account/deposit 
* API TYPE : JSON
* API BODY : {  "accountNumber":"FR76XXX", "amount":"5000" } 

## Withdraw


* API METHOD : POST 
* API ENDPOINT : localhost:8080/api/account/withdraw 
* API TYPE : JSON
* API BODY : {  "accountNumber":"FR76XXX",  "amount":"5000"} 
## Historic


* API METHOD : GET 
* API ENDPOINT : localhost:8080/api/account/historic?accountNbr=FR76XXX


# Note

H2 data base is used and you can refer to the schema in resources : schema.sql



