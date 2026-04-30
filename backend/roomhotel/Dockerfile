# ── Etapa 1: BUILD ────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# copiamos el pom desde la subcarpeta correcta del monorepo
COPY backend/roomhotel/pom.xml .

RUN mvn dependency:go-offline -B

# copiamos el src desde la subcarpeta correcta del monorepo
COPY backend/roomhotel/src ./src

RUN mvn package -DskipTests

# ── Etapa 2: RUN ──────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/roomhotel-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]