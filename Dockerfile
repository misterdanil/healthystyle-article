FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /article

COPY ../util/pom.xml /util/pom.xml
WORKDIR /util
RUN mvn dependency:go-offline -B

COPY ../util .
RUN mvn -f /util/pom.xml clean install -DskipTests

WORKDIR /article

COPY ./article/pom.xml .
COPY ./article/article-model/pom.xml ./article-model/pom.xml
COPY ./article/article-repository/pom.xml ./article-repository/pom.xml
COPY ./article/article-service/pom.xml ./article-service/pom.xml
COPY ./article/article-web/pom.xml ./article-web/pom.xml
COPY ./article/article-app/pom.xml ./article-app/pom.xml

RUN mvn dependency:go-offline -B

COPY ./article .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /article
COPY --from=build /article/article-app/target/*.jar article.jar
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "article.jar"]
