import java.net.*;
import java.io.*;
import java.util.*;



public class System1 
{
    private static int PORT = 1612;
    private static void connect()
    {
        String serverIPAddresses[] = new String[] {"10.176.69.33", "10.176.69.34"};
        try(ServerSocket serverSocket = new ServerSocket(PORT))
        {
            for(int idx = 0; idx < serverIPAddresses.length; idx++)
            {
                Socket socket = new Socket(serverIPAddresses[idx], 1612);
                System.out.println("\nConnected to system at IP Address: " + serverIPAddresses[idx] + " and Port: 1612" );
                new ClientThread(socket).start();
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public System1()
    {
        //Read from the file, the list of systems present in the P2P network
        /*String hostAddress = "";
        File sysIni = new File("/home/013/m/ma/mah200001/Project2/SystemInfo.txt");
        if(sysIni.length() == 0)
            System.out.println("No system present in the P2P network.");
        else
        {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(sysIni)))
            {
                String line = bufferedReader.readLine();
                while(line != null)
                {
                    System.out.println(line);
                    line = bufferedReader.readLine();
                }
            }
            catch(FileNotFoundException e)
            {
                
            }
            catch(IOException e)
            {

            }
        }

        try 
        {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaceEnumeration.hasMoreElements())
            {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress())
                        hostAddress = interfaceAddress.getAddress().getHostAddress();
            }
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        }

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sysIni, true)))
        {
            bufferedWriter.newLine();
            bufferedWriter.write(hostAddress);
        }
        catch(IOException e)
        {

        }*/
    }
    
    public static void main(String[] args)
    {
        connect();
        /*System1 obj = new System1();
        System.out.println("Hi");*/
    }

}
