# 1. Build 단계
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Gradle Wrapper와 소스 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# 빌드 실행
RUN ./gradlew clean build -x test

# 2. 실행 단계
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 빌드 산출물 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 설정
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java","-jar","app.jar"]
