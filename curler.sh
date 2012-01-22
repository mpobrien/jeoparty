#!/bin/sh
for i in $(seq 1 3810)
do
    echo "fetching $i"
    curl -s "http://www.j-archive.com/showgame.php?game_id=$i" > "./games2/game_$i.html"
    sleep 1
done
#http://www.j-archive.com/showgame.php?game_id=
