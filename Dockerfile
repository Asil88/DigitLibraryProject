FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/DigitLibraryProject-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT java -jar /app/DigitLibraryProject-0.0.1-SNAPSHOT.jar