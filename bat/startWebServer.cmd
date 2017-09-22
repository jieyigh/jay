set TITLE=Code Builder

call setJDK

call setServer

set Path=%JAVA_HOME%/bin;%Path%

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MaxPermSize=256m

%CATALINA_HOME%/bin/startup.bat
