FROM quay.io/wildfly/wildfly:23.0.2.Final

RUN mkdir -p /tmp/s3poc
ADD S3PoC-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080
EXPOSE 9990
EXPOSE 8787