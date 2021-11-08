FROM openjdk:11
VOLUME /tmp
EXPOSE 8013
ADD ./target/savingaccount-0.0.1-SNAPSHOT.jar savingaccount.jar
ENTRYPOINT ["java","-jar","/savingaccount.jar"]