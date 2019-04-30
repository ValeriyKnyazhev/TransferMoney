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
