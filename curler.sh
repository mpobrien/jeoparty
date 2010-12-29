#!/bin/sh
for i in $(seq 726 3329)
do
    echo "fetching $i"
    curl -s "http://www.j-archive.com/showgame.php?game_id=$i" > "./games2/game_$i.html"
    sleep 3
done
#http://www.j-archive.com/showgame.php?game_id=
