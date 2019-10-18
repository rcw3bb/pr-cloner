@echo off
cd /d %1
git fetch %~2 pull/%~3/head:PR%~3
