import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.net.InetAddress;

public class Computer1
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;
    private static int PORT = 7090;

    private static void run()
    {
        try(ServerSocket srSocket = new ServerSocket(PORT))
        {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println(host);
            System.out.println("Computer 1 started in port " + PORT);
            Socket clientSocket = srSocket.accept();
            System.out.println("Connected to Computer 3");
            
            inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            try
            {
                String requestFileName = inputStream.readUTF();
                System.out.println(requestFileName);
                String filePath = new File("").getAbsolutePath();
                System.out.println(filePath);
                filePath = filePath + "/" + requestFileName;
                System.out.println(filePath);
                File tempDir = new File(filePath);
                if(tempDir.exists())
                    outputStream.writeUTF("YES");
                else
                    outputStream.writeUTF("NO");
                

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