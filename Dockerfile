FROM openjdk:11
COPY ./target/TinkoffStockService-0.0.1-SNAPSHOT.jar .
EXPOSE 8088
CMD ["java", "-jar", "TinkoffStockService-0.0.1-SNAPSHOT.jar"]