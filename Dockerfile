# Use uma imagem com Maven para build
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /src

# Copie apenas o pom.xml primeiro (para cache de dependências)
COPY pom.xml ./

# Baixe as dependências
RUN mvn dependency:go-offline

# Copie o código fonte
COPY src ./src

# Compile e gere o JAR
RUN mvn clean package -DskipTests

# Imagem final menor (multi-stage build)
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copie o JAR da etapa de build
COPY --from=build /src/target/*.jar app.jar

EXPOSE 8080

# Execute a aplicação
CMD ["java", "-jar", "app.jar"]