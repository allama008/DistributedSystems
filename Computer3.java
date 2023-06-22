/*
1. Show a message in the terminal asking the user to input a file name
2. Establish a connection with P1 and ask if the file with filename exists (socket connection establish function)
3. If 'YES' is received then
    3.1 Ask P1 to send the contents of the file.
    3.2 Append the information into the file with file name provided by the user
 */

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Computer3
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;

    private static String requestFile()
    {
        
        return "";
    }

    private static String establishConnection(int serverPorts, String serverIP, String fileName)
    {
        String transferStatus = "";
        try(Socket socket = new Socket(serverIP, serverPorts))
        {
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(fileName);
            String receivedMsg = "";
            try
            {
                receivedMsg = inputStream.readUTF();
            }
            catch(IOException ioErrMsg)
            {
                System.out.println(ioErrMsg);
            }
            if(receivedMsg == "YES")
                transferStatus = requestFile();
            else
                transferStatus = "NO";
            socket.close();
            inputStream.close();
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return transferStatus;
    }

    public static void main(String[] args)
    {
        Scanner inputObj = new Scanner(System.in);
        System.out.println("Kindly input a file name: ");
        String inputFileName = inputObj.nextLine();
        inputObj.close();

        String serverIPAddresses = new String("127.0.0.1");
        int serverPortNumber[] = new int[] {1600, 1601};

        String status = establishConnection(serverPortNumber[0], serverIPAddresses, inputFileName);
        if(status == "NO")
            status = establishConnection(serverPortNumber[1], serverIPAddresses, inputFileName);
        
        if(status == "NO")
            System.out.println("File transfer failure");
        
        System.out.println(status);

    }
}