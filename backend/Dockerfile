# Etapa 1: Build do projeto
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e as fontes
COPY projetoacer/pom.xml .
COPY projetoacer/src ./src

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o JAR gerado para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expondo a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]