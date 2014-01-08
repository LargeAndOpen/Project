#!/bin/sh

# 命令變數
var="";

# 切換到Project目錄
var="${var}cd /home/kevin/git/Large/TEST1 && ";

# 執行ANT
var="${var}sudo /home/kevin/桌面/eclipse/plugins/org.apache.ant_1.8.4.v201303080030/bin/ant"

# 執行命令
eval "gnome-terminal -x sh -c '$var'"
