SET DEBUG_OPT=-agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n
SET JAVA_OPT=%DEBUG_OPT% 
SET JAR_PATH=.\carbon\target\carbon-0.1-SNAPSHOT.jar

pushd ..
start java %JAVA_OPT% -jar %JAR_PATH%
popd
