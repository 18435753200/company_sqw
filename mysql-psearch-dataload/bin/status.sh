#!/bin/bash

cd `dirname $0`
cd ..
BASE=`pwd`
#修改这里
APP_MAINCLASS="com.mall.dataload.ProductLoadMain"

PROCESS=`jps -l | grep $APP_MAINCLASS | grep -v grep |awk '{print $1}'`
if [ ! -z "$PROCESS" ]; then
    echo started
else
    echo stoped
fi

