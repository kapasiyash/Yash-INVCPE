#!/bin/sh 
cd `dirname $0`
filename=`basename $1`
cat $1 | col -bx > /tmp/$filename
mv /tmp/$filename $1