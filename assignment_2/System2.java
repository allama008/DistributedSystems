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
 * Java Code for Process 1
 * Author: Allama Hossain
 */

public class System2
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;
    private static int PORT = 1612;

    private static void run()
    {
        // Start server connection to accept incoming requests.
        try(ServerSocket srSocket = new ServerSocket(PORT))
        {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("Computer 2 started in IP Address " + host + " and Port " + PORT);

            // Accept any incoming requests from the client.
            /*Socket clientSocket = srSocket.accept();
            System.out.println("Connected to Computer 1");*/


            String serverIP = "";
            int serverPort = 1612;
            // Connect to Server 1
            Socket clientSocket = new Socket(serverIP, serverPort);
            System.out.println("\nConnected to system at IP Address: " + serverIP + " and Port: " + serverPort);
            
            inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            try
            {
                // Recieve file name from the client.
                outputStream.writeUTF("FILE_REQUEST");
                String requestFileName = inputStream.readUTF();
                System.out.println(requestFileName);
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