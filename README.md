[![Build Status](https://travis-ci.org/edigonzales/ilivalidator-web-service-micronaut.svg?branch=master)](https://travis-ci.org/edigonzales/ilivalidator-web-service-micronaut)

# ilivalidator-web-service-micronaut 

```
mn create-app --features=graal-native-image --features file-watch ch.so.agi.ilivalidator 
mn create-controller Main
./gradlew eclipse
./gradlew run --continuous
```

```
./gradlew assemble test
./docker-build.sh $TRAVIS_COMMIT $TRAVIS_BUILD_NUMBER
docker run --memory=1024M -p 8080:8080 sogis/ilivalidator-web-service-micronaut
```

