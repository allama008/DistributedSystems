import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;

/*
 * Java Code for Process 2
 * Author: Allama Hossain
 */

public class Computer2
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;
    private static int PORT = 1612;

    /*
     * Function to send file to the requesting process 
     */
    private static String sendFile(String fileName) throws Exception
    {
        System.out.println("Initiating file transfer...");
        int bytes = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[100];
        // Reads the file in chunks of 100 bytes and sends the contents using messages of 100 bytes.        
        while((bytes = fis.read(buffer)) != -1)
        {
            outputStream.write(buffer, 0, bytes);
            outputStream.flush();
        }
        fis.close();
        System.out.println("File Successfully transferred");
        return "File Successfully returned";
    }

    /*
     * Function to create socket server connection and listen to incoming requests.
     * If connection is successful then take file name from the client (P3) as input
     * and check if such a file exists in the server system of P1. The system responds
     * with YES if the file is present and NO otherwise.
     */
    private static void run()
    {
        try(ServerSocket srSocket = new ServerSocket(PORT))
        {
            // Start server connection to accept incoming requests.
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("Computer 2 started in IP Address " + host + " and Port " + PORT);
            
            // Accept any incoming requests from the client.
            Socket clientSocket = srSocket.accept();
            System.out.println("Connected to Computer 3");
            String status = "";
            
            inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            try
            {
                // Recieve file name from the client.
                String requestFileName = inputStream.readUTF();

                // In case the client has already found the file in another server. Terminate all socket connections and return.
                if(requestFileName.compareTo("TERMINATE_CONNECTION") == 0)
                {
                    System.out.println("Client has found the file. Closing IO stream and client-server socket connections.");
                    outputStream.writeUTF("NO");
                    outputStream.close();
                    inputStream.close();
                    srSocket.close();
                    clientSocket.close();
                    return;
                }
                System.out.println("File name: " + requestFileName + " requested by Computer 3.");
                
                // Prepare the file name by appending it with the absolute directory path.
                String filePath = new File("").getAbsolutePath();
                filePath = filePath + "/" + requestFileName;
                File tempDir = new File(filePath);

                /*
                 * Check if the file is present or not and send YES or NO accordingly.
                 * If file is present then listen for incoming FILE_REQUEST command
                 * and send file if the command is received.
                 */
                if(tempDir.exists())
                {
                    System.out.println("File name: " + requestFileName + " exists in our system.");
                    outputStream.writeUTF("YES");
                    if(inputStream.readUTF().compareTo("FILE_REQUEST") == 0)
                        status = sendFile(filePath);
                }    
                else
                {
                    System.out.println("File name: " + requestFileName + " does not exist in our system.");
                    outputStream.writeUTF("NO");
                }
                System.out.println("Closing IO stream and client-server socket connections.");
                outputStream.close();
                inputStream.close();
                srSocket.close();
                clientSocket.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        run();
    }
}