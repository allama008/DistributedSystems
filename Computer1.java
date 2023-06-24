import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;

public class Computer1
{
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;

    private static void run()
    {
        try(ServerSocket srSocket = new ServerSocket(1601))
        {
            System.out.println("Computer 1 started in port 1601");
            Socket clientSocket = srSocket.accept();
            System.out.println("Connected to Computer 3");
            
            /*
            inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            try
            {
                String requestFileName = inputStream.readUTF();
                String filePath = new File("").getAbsolutePath();
                filePath.concat(requestFileName);
                File tempDir = new File(filePath);
                if(tempDir.exists())
                    outputStream.writeUTF("YES");

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            */

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