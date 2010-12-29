#!/bin/sh
#for i in $(seq 1 641)
for i in $(seq 1 3329)
do
    echo "trying $i"
    python ./scrape_mongo2.py "/home/mike/projects/jeoparty/games/game_$i.html" "$i"
done
#http://www.j-archive.com/showgame.php?game_id=
