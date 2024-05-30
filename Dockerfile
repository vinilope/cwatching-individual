FROM openjdk:17
WORKDIR /cwatching
RUN mkdir -p ./logs
COPY ./target/cwatching-1.0-SNAPSHOT-jar-with-dependencies.jar /cwatching/
CMD ["java","-jar","cwatching-1.0-SNAPSHOT-jar-with-dependencies.jar"]
