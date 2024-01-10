FROM tomcat:10-jdk17
COPY /hibernate-envers/target/ROOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]