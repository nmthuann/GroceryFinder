FROM openjdk:24-ea-14-jdk-oraclelinux9

WORKDIR /nmthuann/grocery-finder-spring-boot

COPY target/*.jar /nmthuann/grocery-finder-spring-boot/app.jar

EXPOSE 3333

ENTRYPOINT ["java", "-jar", "app.jar"]