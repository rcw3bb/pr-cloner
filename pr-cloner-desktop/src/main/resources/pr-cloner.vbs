Set oShell = CreateObject("Wscript.Shell")
Dim strArgs
strArgs = "cmd /c cd bin & pr-cloner.bat"
oShell.Run strArgs, 0, false