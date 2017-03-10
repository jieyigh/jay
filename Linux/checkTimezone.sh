#!/bin/bash

#tz=`date |awk '{print $5}'`
tz=`date|grep 'GMT+4'|wc -l`
if [ $tz -eq 0 ]; then
	\cp  /usr/share/zoneinfo/Etc/GMT+4    /etc/localtime
	echo `date +'%F %T'`"Set Timezone as GMT+4" >> checkTimezone.log
fi
