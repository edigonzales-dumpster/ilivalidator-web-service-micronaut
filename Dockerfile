FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/ilivalidator
WORKDIR /home/app/ilivalidator
#RUN native-image --no-server --verbose -cp build/libs/ilivalidator-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
#COPY --from=graalvm /home/app/ilivalidator .
COPY --from=graalvm /opt/graalvm-ce-1.0.0-rc15/jre/lib/amd64/libsunec.so .
ENTRYPOINT ["./ilivalidator-web-service"]
