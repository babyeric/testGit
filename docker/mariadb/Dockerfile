#
# MariaDB Dockerfile
#
#

FROM centos:centos6

COPY MariaDB.repo /etc/yum.repos.d/
COPY server.cnf init.sh /opt/temp/ 

RUN \
    yum -y install MariaDB-Galera-server MariaDB-client galera && \
	yes | cp -f /opt/temp/server.cnf /etc/my.cnf.d/server.cnf && \
	bash /opt/temp/init.sh && \
	rm -f -r /opt/temp/

WORKDIR /opt
ENTRYPOINT ["mysqld_safe"]
EXPOSE 3306
