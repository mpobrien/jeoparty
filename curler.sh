#!/bin/sh
for i in $(seq 3329)
do
    curl "http://www.j-archive.com/showgame.php?game_id=$i" > "./games2/game_$i.html"
    sleep 3
done
#http://www.j-archive.com/showgame.php?game_id=
