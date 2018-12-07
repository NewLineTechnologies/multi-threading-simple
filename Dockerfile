FROM gradle:4.10-slim as gradle-build
WORKDIR /app
COPY src ./src
COPY build.gradle .

USER root
RUN chown -Rv gradle .
USER gradle

RUN gradle build
RUN find ./build/libs -name '*.jar' -exec mv {} ./app.jar \;

FROM openjdk:8-jre-alpine
WORKDIR /app

COPY --from=gradle-build /app/app.jar .
EXPOSE 8080
ENV JAVA_OPTS ""
COPY entrypoint.sh /entrypoint.sh
RUN chmod 755 /entrypoint.sh
ENTRYPOINT /entrypoint.sh