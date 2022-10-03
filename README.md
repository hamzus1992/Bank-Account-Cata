# Bank-account-kata

### A SpringBoot application offering Rest Endpoints in order to interract with a basic bank account

## Overview:

- The application offers the possibility to have multiple accounts. For each account multiple operations.
- A bank account has a unique identifier. It's represented by a balance and a list of operations which could be a deposit or a withdrawal.
- An operation is categorised(deposit or a withdrawal), have an amount, occurence date and a unique ID. Each operation is linked to a specific account.


## This application offers 4 Endpoints allowing:
1. Deposit of a given amount of money on an account
 ```json
 PUT /api/accounts/{accountId}/deposit
 ```
Payload is as:

 ```json
 {
   "amount":10
  }
 ```
Response preview:
  ```json
  {
   "firstName": "HAMZA",
   "lastName": "RAKROUKI",
   "balance": 150
}
   ```

2. Withdrawal of a given amount of money from an account:

 ```json 
 PUT /api/accounts/{accountId}/withdrawal
 ```
payload is as :

 ```json
   {
    "amount":40
   }
   ```
Response preview :

 ```json
{
   "firstName": "HAMZA",
   "lastName": "RAKROUKI",
   "balance": 110
}
  ```

3. All operations (debit, credit) listing:

   ```json  
   GET /api/accounts/{accountId}/history
   ```

Response preview:

   ```json
   [
   {
        "date": "2022-10-04T13:46:59.722Z",
        "type": "DEPOSIT",
        "amount": 20
    },
    {
        "date": "2022-10-04T13:46:59.722Z",
        "type": "WITHDRAWAL",
        "amount": 20000
    }
   ]
   ```
4. Account balance :

   ```json 
    GET /api/accounts/{accountId} 
   ```  
Response preview:
```json
{
"firstName": "HAMZA",
"lastName": "RAKROUKI",
"balance": 150
}
``` 

## Application Endpoints could be accessed using Swagger2 Ui integrated into the Application with API documentation:
To do so, visit http://localhost:8080/swagger-ui/index.html#/ once the application in launched.

## This application is configured to run with a H2 Database:
Setup database name,username and password in the properties.yml file

## Testing:
The application is tested using Junit and assertJ.
1. Unit tests mainly for the services
2. Integration test for the restControllers allowing for lightweight end-to-end testing