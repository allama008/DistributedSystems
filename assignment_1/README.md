# Project Description
***
In this project, you will use socket connections to emulate communication between three network-connected computers,
say C1, C2 and C3, and implement the following:
* Process P1, running on C1, has access to a text file, F1, of size 300 bytes. Process P2, running on C2, has access
to a text file, F2, also of size 300 bytes. The contents of these two text files are different. Process P3 is running
on C3.
* P3 operates as the client, while P1 and P2, act as servers. P3 establishes a stream socket connection with P1,
and another stream socket connection with P2.
* Once the connections are established, P3 does the following (these are the project requirements):
    – P3 shows a message in its terminal window asking the user to input a file name, and waits for the user to
type in a file name.
    – Once P3 has obtained a file name, it sends a message to P1 asking if P1 has a file of that name.
    – If P1 has a file by that name, it replies with a YES message. Otherwise, it replies with a NO message.
    – If P3 receives a YES message from P1:
        * P3 sends a request message to P1 to send the contents of the named file.
        * On receiving this request, P1 should send the contents of the file to P3 using three messages, each
containing 100 bytes of F1.
        * P3 should append the information received from P1 into a newly created file with the name provided
by the user.
    – If P3 receives a NO message from P1, then it sends a message to message to P2 asking if P2 has the file
of that name.
    – The subsequent interaction between P3 and P2 is similar to what has been described for P3 and P1 earlier.
    – If neither P1, nor P2 has the file, then P3 should output ”File transfer failure” on its terminal. Otherwise,
it should output ”File successfully fetched from < server_name >” on its terminal.
    – After this is done, the socket connections should be terminated and the corresponding processes should
exit.

Your code should run for the following scenarios:
1. When the user inputs file name F1, that file is fetched by P3 from P1.
2. When the user inputs file name F2, the search at P1 is unsuccessful, and then it is successfully fetched by P3
from P2.
3. When the user inputs some other file name, the file is not found at P1 or P2, and a ”File transfer failure” message
is shown to the user.
