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
import java.net.InetAddress;

public class Computer3
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;

    private static String requestFile(String fileName) throws Exception
    {
        System.out.println("Beginning file transfer.");
        int bytes = 0;
        FileOutputStream fos = new FileOutputStream(fileName, true);
        byte buffer[] = new byte[100];
        int counter = 3;
        while ((bytes = inputStream.read(buffer, 0, buffer.length)) != -1 && counter > 0) 
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
        System.out.println("File Transfer Successful");
        return "SUCCESS";
    }

    private static String establishConnection(String serverIP, int serverPort, String fileName)
    {
        String transferStatus = "";
        try
        {
            Socket socket = new Socket(serverIP, serverPort);
            System.out.println("\nConnected to system at IP Address: " + serverIP + " and Port: " + serverPort);

            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Inquiring about file: " + fileName);
            outputStream.writeUTF(fileName);
            try
            {
                transferStatus = inputStream.readUTF();
            }
            catch(IOException ioErrMsg)
            {
                System.out.println(ioErrMsg);
            }
            
            if(transferStatus.compareTo("YES") == 0)
            {
                System.out.println("File available at the server. Sending request for file transfer");
                outputStream.writeUTF("FILE_REQUEST");
                transferStatus = requestFile(fileName);
            }
            else if(transferStatus.compareTo("NO") == 0)
                System.out.println("File is not available at the server.");
            System.out.println("Closing IO stream and socket connections.");
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

        try
        {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("Computer 3 IP Address: " + host);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Scanner inputObj = new Scanner(System.in);
        System.out.println("Kindly input a file name: ");
        String inputFileName = inputObj.nextLine();
        inputObj.close();
        String serverIPAddresses[] = new String[] {"10.176.69.32", "10.176.69.33"};

        for(int idx = 0; idx < serverIPAddresses.length; idx++)
        {
            status = establishConnection(serverIPAddresses[idx], 1612, inputFileName);
            //System.out.println(status);
            if(status == "SUCCESS")
            {
                //status = status + (int)(idx + 1);
                System.out.println("\nFile successfully transferred from server " + (int)(idx + 1)) ;
                break;
            }
        }
        if(status.compareTo("NO") == 0 || status.compareTo("SUCCESS") != 0)
            System.out.println("\nFile transfer failure");
        
    }
}