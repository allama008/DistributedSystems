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

public class Computer2
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;
    private static int PORT = 1612;

    private static String sendFile(String fileName) throws Exception
    {
        System.out.println("Initiating file transfer...");
        int bytes = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[100];
        while((bytes = fis.read(buffer)) != -1)
        {
            outputStream.write(buffer, 0, bytes);
            outputStream.flush();
        }
        fis.close();
        System.out.println("File Successfully transferred");
        return "File Successfully returned";
    }

    private static void run()
    {
        try(ServerSocket srSocket = new ServerSocket(PORT))
        {
            InetAddress host = InetAddress.getLocalHost();
            //System.out.println(host);
            System.out.println("Computer 2 started in IP Address " + host + " and Port " + PORT);
            Socket clientSocket = srSocket.accept();
            System.out.println("Connected to Computer 3");
            String status = "";
            
            inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            try
            {
                String requestFileName = inputStream.readUTF();
                System.out.println("File name: " + requestFileName + " requested by Computer 3.");
                String filePath = new File("").getAbsolutePath();
                filePath = filePath + "/" + requestFileName;
                File tempDir = new File(filePath);
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