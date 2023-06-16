# Base Image
FROM maven:3.8.3-openjdk-17

ENV TAG @regression
WORKDIR /home/bookApiStudyCase

# Copy source code, pom.xml and test runner file to the working directory
COPY src /home/bookApiStudyCase/src
COPY pom.xml /home/bookApiStudyCase


# Run tests
CMD mvn test -Dcucumber.filter.tags="${TAG}"

