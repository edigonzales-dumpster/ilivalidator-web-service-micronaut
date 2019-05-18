# ilivalidator-web-service-micronaut

```
mn create-app --features=graal-native-image --features file-watch ch.so.agi.ilivalidator 
mn create-controller Main
./gradlew eclipse
./gradlew run --continuous
```

```
./gradlew assemble
./docker-build.sh
docker run --memory=1024M -p 8080:8080 sogis/ilivalidator-web-service-micronaut
```

