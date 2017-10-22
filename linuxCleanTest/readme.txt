
TO RUN GAME FROM COMPILED JAR FILES (Proper Method)

1) Run "TroubleServer.sh"

2) Run "Trouble.jar"

TO CLEAN BUILD AND RUN GAME (If asked to build project from scratch)

1) All source code is in the src folder in packages

2) Compile source code by running "compile_linux.sh"

3) After compiling the source code, the server and client can be run with debug windows by running "Run Server.sh" and "Run Client.sh". Be sure to run the server first so the client has a server to connect to

4) The client will try to connect to localhost by default, to change the IP address it attempts to connect to, edit the client.txt file and replace localhost:4321 with <desired ip>:4321
