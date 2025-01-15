FROM gradle:jdk11 as GRADLE_BUILD
COPY . /planets/
WORKDIR /planets/
RUN gradle bootJar

FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=/planets/build/libs/*.jar
COPY --from=GRADLE_BUILD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
