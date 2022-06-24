# First line is "FROM", defines base image
FROM tomcat:8.5-jre8
LABEL maintainer="Cameron Seibel"
ADD target/employee-servlet-app.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]