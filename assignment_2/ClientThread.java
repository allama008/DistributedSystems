import java.net.*;
import java.io.*;
import java.util.*;

public class ClientThread extends Thread
{
    private Socket socket;
    String transferStatus = "";
    private static DataInputStream inputStream = null;
    private static DataOutputStream outputStream = null;

    public ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());


            // Send an inquiry message to the server to check if file is present or not.
            outputStream.writeUTF("Hello! How are you?");
            try
            {
                transferStatus = inputStream.readUTF();
            }
            catch(IOException ioErrMsg)
            {
                System.out.println(ioErrMsg);
            }
            
            System.out.println("Closing IO stream and socket connections.");
            socket.close();
            inputStream.close();
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(transferStatus);
    }
}