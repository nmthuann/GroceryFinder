FROM openjdk:24-ea-14-jdk-oraclelinux9

WORKDIR /nmthuann/ecommerce-spring-boot-be

COPY target/*.jar /nmthuann/ecommerce-spring-boot-be/app.jar

EXPOSE 3333

ENTRYPOINT ["java", "-jar", "app.jar"]