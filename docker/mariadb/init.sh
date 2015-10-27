#!/bin/bash
/etc/init.d/mysql start
mysql -e "DELETE FROM mysql.user WHERE user =''"
mysql -e "GRANT ALL PRIVILEGES ON *.* TO rcdev@'%'"
mysql -e "SET PASSWORD FOR rcdev@'%' = '*77074D8AC9603904375ED54F1D2E14CDACB7842F'"
mysql -e "SET PASSWORD FOR 'root'@'localhost' = '*77074D8AC9603904375ED54F1D2E14CDACB7842F'"
/etc/init.d/mysql stop
