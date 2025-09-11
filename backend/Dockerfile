# Imagem base com Maven e JDK
FROM maven:3.9.5-eclipse-temurin-17
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar cache do Docker
COPY projetoacer/pom.xml .

# Instala dependências sem compilar o código ainda
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY projetoacer/src ./src

# Porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação em modo desenvolvimento
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=dev"]
