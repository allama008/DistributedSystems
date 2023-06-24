/*
1. Show a message in the terminal asking the user to input a file name
2. Establish a connection with P1 and ask if the file with filename exists (socket connection establish function)
3. If 'YES' is received then
    3.1 Ask P1 to send the contents of the file.
    3.2 Append the information into the file with file name provided by the user
4. Ask P2 for file if NO message received
5. Output result of file transfer
6. Close connections.
 */

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Computer3
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;

    private static String requestFile(String fileName) throws Exception
    {
        int bytes = 0;
        FileOutputStream fos = new FileOutputStream(fileName, true);
        byte buffer[] = new byte[100];
        int counter = 3;
        while (counter > 0 && (bytes = inputStream.read(buffer, 0, 100)) != -1) 
        {
            counter--;
            fos.write(buffer, 0, bytes);
        }
        /*
         * counter = 0 && bytes == -1 correct condition
         * counter > 0 && bytes == -1 file size smaller than 300KB
         * counter = 0 && bytes > 0 file size greater than 300KB
         */
        if((counter > 0 && bytes == -1) || counter == 0 && bytes > 0)
            System.out.println("File size not 300KB");
        fos.close();
        return "File successfully fetched from server ";
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
            /* 
            if(receivedMsg == "YES")
                transferStatus = requestFile(fileName);
            else
                transferStatus = "NO";
            */
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
        String status = "";
        Scanner inputObj = new Scanner(System.in);
        System.out.println("Kindly input a file name: ");
        String inputFileName = inputObj.nextLine();
        inputObj.close();

        String serverIPAddresses = new String("127.0.0.1");
        int serverPortNumber[] = new int[] {1600, 1601};

        for(int idx = 0; idx < serverPortNumber.length; idx++)
        {
            status = establishConnection(serverPortNumber[idx], serverIPAddresses, inputFileName);
            if(status == "File successfully fetched from server ")
            {
                status = status + (int)(idx + 1);
                break;
            }
        }

        if(status == "NO")
            System.out.println("File transfer failure");
        
        System.out.println(status);

    }
}