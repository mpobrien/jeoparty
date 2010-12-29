#/bin/sh
if [ "$1" = "runserver" ]; then
java -Dlog4j.debug -cp lib/*:WebContent/WEB-INF/classes/:./conf/ com.mob.bootstrap.Jetty
elif [ "$1" = "models" ]; then
java -cp ./lib/guiceydatagenerator-0.1.2.jar com.lowereast.guiceymongo.data.generator.GuiceyDataGenerator -p jparty.models -s ./src ./data/jeoparty.models
fi
