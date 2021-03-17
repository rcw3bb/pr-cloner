@echo off
cd /d %1
git fetch %~2 pull-requests/%~3/from:PR%~3
