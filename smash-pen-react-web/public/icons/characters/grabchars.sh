#!/bin/bash

FILES=./*

for f in $FILES
do
	echo ${f:2:-4} 
done
