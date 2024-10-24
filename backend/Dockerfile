# Dockerfile для бэкенда (Spring Boot)

# Шаг 1: Собрать приложение с помощью Maven
FROM maven:3.8.4-openjdk-8 AS builder

# Установить рабочую директорию
WORKDIR /app

# Скопировать pom.xml и исходный код в рабочую директорию
COPY pom.xml .
COPY src ./src

# Собрать приложение
RUN mvn clean package -DskipTests

# Шаг 2: Создать минимальный Docker-образ для запуска собранного приложения Spring Boot
FROM openjdk:17-jdk-slim

# Установить рабочую директорию в новом контейнере
WORKDIR /app

# Скопировать собранный JAR-файл из стадии сборки
COPY --from=builder /app/target/web-store-1.0-SNAPSHOT.jar ./web-store-1.0-SNAPSHOT.jar

# Открыть порт приложения (при необходимости настройте в соответствии с вашей конфигурацией)
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "web-store-1.0-SNAPSHOT.jar"]
