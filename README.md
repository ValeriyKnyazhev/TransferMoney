# TransferMoney

### Build service

```./gradlew clean build```

### Run service

##### With java

`java -jar ./build/libs/TransferMoney.jar`

 If you want specify port for service, add parameter `-p N`
 
 For instance, `java -jar ./build/libs/TransferMoney.jar -p 8081`

or

##### With gradle

`./gradlew runJar`

If you want specify port for service, add parameter `-Pport=N`
 
For instance, `./gradlew runJar -Pport=8081`
 
### Test service
 
##### Create new account
```
POST http://localhost:8080/accounts
{}
```

##### Find account by identifier
```
GET http://localhost:8080/accounts/0
```

 
##### Find all accounts
```
GET http://localhost:8080/accounts
```
 
##### Put money to account 1
```
POST http://localhost:8080/accounts/1
{
  "amount": 1000
}
```

##### Take money from account 1
```
POST http://localhost:8080/accounts/1
{
  "amount": 1000
}
```

##### Transfer money from account 1 to account 2
```
POST http://localhost:8080/accounts/
{
  "amount": 1000,
  "fromAccountId": 1,
  "toAccountId": 2
}
```