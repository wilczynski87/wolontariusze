FROM eclipse-temurin:17-jdk-jammy
LABEL author="wilczynski"

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
# EXPOSE 20327
COPY src ./src

CMD ["./mvnw", "spring-boot:run"]