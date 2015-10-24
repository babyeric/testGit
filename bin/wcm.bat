SET DEBUG_OPT=-agentlib:jdwp=transport=dt_socket,server=y,address=8002,suspend=n
SET JAVA_OPT=%DEBUG_OPT% 
SET JAR_PATH=.\wcm\target\wcm-0.1-SNAPSHOT.jar

pushd ..
start java %JAVA_OPT% -jar %JAR_PATH%
popd
