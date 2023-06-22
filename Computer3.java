/*
1. Show a message in the terminal asking the user to input a file name
2. Establish a connection with P1 and ask if the file with filename exists (socket connection establish function)
3. If 'YES' is received then
    3.1 Ask P1 to send the contents of the file.
    3.2 Append the information into the file with file name provided by the user
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Computer3
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;

    private static void establishConnection(int serverPorts[], String serverIP)
    {
        try(Socket socket = new Socket(serverIP, serverPorts[0]))
        {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Scanner inputObj = new Scanner(System.in);
        System.out.println("Kindly input a file name: ");
        String inputFileName = inputObj.nextLine();

        String serverIPAddresses = new String("127.0.0.1");
        int serverPortNumber[] = new int[] {1600, 1601};

    }
}