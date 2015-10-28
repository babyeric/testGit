#
# MariaDB Dockerfile
#
#

FROM centos:centos6

COPY carbon*.jar /opt/carbon/
COPY carbon.sh /opt/carbon/


RUN mkdir /opt/temp
RUN yum -y install wget tar
RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/7u51-b13/jdk-7u51-linux-x64.tar.gz" -O /opt/temp/jdk-7-linux-x64.tar.gz
RUN mkdir /usr/java
RUN (cd /usr/java; tar xzf /opt/temp/jdk-7-linux-x64.tar.gz)
RUN update-alternatives --install /usr/bin/java java /usr/java/jdk1.7.0_51/jre/bin/java 20000; update-alternatives --install /usr/bin/jar jar /usr/java/jdk1.7.0_51/bin/jar 20000; update-alternatives --install /usr/bin/javac javac /usr/java/jdk1.7.0_51/bin/javac 20000; update-alternatives --install /usr/bin/javaws javaws /usr/java/jdk1.7.0_51/jre/bin/javaws 20000; update-alternatives --set java /usr/java/jdk1.7.0_51/jre/bin/java; update-alternatives --set javaws /usr/java/jdk1.7.0_51/jre/bin/javaws; update-alternatives --set javac /usr/java/jdk1.7.0_51/bin/javac; update-alternatives --set jar /usr/java/jdk1.7.0_51/bin/jar;
RUN	rm -f -r /opt/temp/
RUN	ln -s /opt/carbon/carbon*.jar carbon.jar

VOLUME ["/opt/carbon/log"]
WORKDIR /opt/carbon
ENTRYPOINT ["/opt/carbon/carbon.sh"]
EXPOSE 8090

