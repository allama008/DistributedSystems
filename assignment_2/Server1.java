import java.io.*;
import java.net.*;
import java.util.*;


public class Server1
{
    private static int PORT = 1612;
    private List<Socket> connectedServers;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
    Thread mainConnectThread;
    public volatile boolean isThreadRunning = true;
    Connect t1;
    
    public Server1()
    {
        connectedServers = new ArrayList<>();
    }

    public void start()
    {
        new Thread(new userAction()).start(); 
        // mainConnectThread = new Thread(new Server1().new Connect());
        // mainConnectThread.start();
        t1 = new Connect();
        // new Thread(new Connect()).start();
    }

    private class Connect implements Runnable
    {
        Thread t;
        ServerSocket serverSocket;

        Connect()
        {
            t = new Thread(this);
            t.start();
        }
        @Override
        public void run()
        {
            try
            {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Listening for incoming connection requests.");
                while(!Thread.interrupted())
                {
                    Socket newServer = serverSocket.accept();
                    connectedServers.add(newServer);
                    new Thread(() -> incomingRequest(newServer)).start();
                }
                //System.out.println("Am I out?");
                //serverSocket.close();
            }
            catch(SocketException se)
            {
                System.out.println("This connection is now closed.");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private void incomingRequest(Socket newServer)
        {
            try 
            {
                inputStream = new DataInputStream(new BufferedInputStream(newServer.getInputStream()));
                outputStream = new DataOutputStream(newServer.getOutputStream());    
                String incomingRequestString = inputStream.readUTF();
                System.out.println(incomingRequestString);
                if(incomingRequestString.equalsIgnoreCase("FILE_REQUEST"))
                    outputStream.writeUTF("REQUEST_ACKNOWLEDGE");
                else
                    outputStream.writeUTF("UNKNOWN_REQUEST_ACKNOWLEDGE");

            } 
            catch (Exception e) 
            {
                // TODO: handle exception
                e.printStackTrace();
            }
            

        }
    }

    private class Message
    {
        String fileName;
        Socket messageSource;
        int hopCount;

        public Message(String fileName, Socket messageSource, int hopCount)
        {
            this.fileName = fileName;
            this.messageSource = messageSource;
            this.hopCount = hopCount;
        }
    }

    //Separate thread only to listen to user request.
    private class userAction implements Runnable
    {
        private void depart()
        {

            try 
            {
                // inputStream.close();
                // outputStream.close();
                System.out.println("I reached here!");
                //isThreadRunning = false;
                //control.flag = true;
                //mainConnectThread.interrupt();
                //t1.t.interrupt();
                t1.serverSocket.close();
            } 
            catch (Exception e) 
            {
                // TODO: handle exception
                e.printStackTrace();
            }
        } 

        @Override
        public void run()
        {
            int ch;
            Scanner sc = new Scanner(System.in);
            do
            {
                System.out.println("1. Search and download a new file.");
                System.out.println("2. Depart from the P2P network.");
                System.out.println("Please enter your input [1-2].");
                ch = sc.nextInt();
                switch(ch)
                {
                    case 1:
                        System.out.println("Inside the part for search and download.");
                        //Call the function to initiate file search and download.
                        break;
                    case 2:
                        System.out.println("Inside the part for departure.");
                        //Call the function to depart from the network.
                        depart();
                        break;
                    default:
                        System.out.println("Wrong input. Please enter input between [1-2].");
                }
            }while(ch != 2);
            sc.close();
        }

    }

    public static void main(String[] args)
    {
        Server1 serverObj = new Server1();
        serverObj.start();
    }
}