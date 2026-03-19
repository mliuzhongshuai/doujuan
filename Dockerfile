# Use OpenJDK 21 base image
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/library/openjdk:21-slim


# Set application JAR as a variable
ARG JAR_FILE=target/doujuan-0.0.1-SNAPSHOT.jar

# Copy the JAR to the container
COPY ${JAR_FILE} app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","/app.jar"]