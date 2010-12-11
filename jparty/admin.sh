#/bin/sh
if [ "$1" = "runserver" ]; then
java -Dlog4j.debug -cp lib/*:WebContent/WEB-INF/classes/:./conf/ com.mob.bootstrap.Jetty
elif [ "$1" = "models" ]; then
java -cp lib/*:./build com.mob.orm.DbInspector ./conf/settings.properties ./src/jparty/models jparty.models
fi
