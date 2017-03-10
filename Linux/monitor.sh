#!/bin/bash  
pid=$(ps aux | grep caipiao-tomcat | grep -v grep | grep -v retomcat | awk '{print $2}')
echo $pid
if [-n "$pid"]
then
{
kill -9 $pid
/data/caipiao-tomcat/bin/startup.sh
}