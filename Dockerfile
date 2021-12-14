FROM registry.access.redhat.com/ubi8/openjdk-11

COPY target/*.jar /deployments/

EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "/deployments/salesforce-camel-spring-boot-demo-0.0.1-SNAPSHOT.jar", "--server.port=8081" ]
