#!/bin/sh
#for i in $(seq 1 641)
for i in $(seq 3330 5745)
do
    echo "trying $i"
    python ./scrape_mongo2.py "/home/mpobrien/downloads/games3/game_$i.html" "$i"
done
#http://www.j-archive.com/showgame.php?game_id=
