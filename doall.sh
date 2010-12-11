#!/bin/sh
#for i in $(seq 1 641)
for i in $(seq 1 641)
do
    echo "trying $i"
    python ./scrape_mongo.py "../jgames/games/game_$i.html" "$i"
done
#http://www.j-archive.com/showgame.php?game_id=
