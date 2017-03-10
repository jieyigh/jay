#!/bin/sh  
Process=`ps aux | grep /data/caipiao-tomcat/ |grep java | grep -v grep | grep -v retomcat | awk '{print $2}'`
/data/apache-tomcat-7.0.68/bin/shutdown.sh   
kill -9 $Process
/data/apache-tomcat-7.0.68/bin/startup.sh