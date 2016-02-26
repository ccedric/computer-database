FROM tomcat:8-jre8

RUN apt-get update && \
    apt-get install -y mysql-client git

# adds /opt/cdb/etc to tomcat classpath
RUN sed -i 's/^shared\.loader=/shared\.loader=\/opt\/cdb\/etc/' $CATALINA_HOME/conf/catalina.properties

RUN git clone https://github.com/ccedric/computer-database
