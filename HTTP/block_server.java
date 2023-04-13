import java.net.*;  
import java.io.*; 
import java.util.*;
import java.text.*;

class Solution 
{ 

  public static final String alpha = "abcdefghijklmnopqrstuvwxyz";
  public static String encrypt(String message, int shiftKey) 
  {
    message = message.toLowerCase();
    String cipherText = "";
    for (int ii = 0; ii < message.length(); ii++) {
      int charPosition = alpha.indexOf(message.charAt(ii));
      int keyVal = (shiftKey + charPosition) % 26;
      char replaceVal = alpha.charAt(keyVal);
      cipherText += replaceVal;
    }
    return cipherText;
  }

  public static void encryption() 
  {
    Scanner sc = new Scanner(System.in);
    String message = new String();
    int key = 0;
    System.out.print("Enter the ip Address for Encryption:");
    message = sc.next();

    System.out.println("\n\nEnter Shift Key:");
    key = sc.nextInt();

    String encrypted = encrypt(message,key);
    System.out.println("\nEncrpyted ipAddress:" + encrypted);

    try
    {
    FileWriter fw1 = new FileWriter("block1.txt");
    fw1.write("Client ip address is: " + encrypted);
    fw1.close();
    }
    catch(Exception e)
    {
      System.out.println(e);
    }

  } 
} 


class block_server
{  
    public static void client_proxy()throws Exception
    {  
        System.out.println("Porxy Server is waiting for client connection ....");
        ServerSocket ss=new ServerSocket(3333);  
        Socket s=ss.accept();
        System.out.println("Client connected....");

        InetAddress localhost = InetAddress.getLocalHost();
        String address = " with port no." + ss.getLocalPort();
    
        System.out.println(address);
        Solution obj = new Solution();
        obj.encryption();

        FileWriter fw2 = new FileWriter("block1.txt",true);
        fw2.write(address);
        fw2.close();  

        DataInputStream din=new DataInputStream(s.getInputStream());  
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 

        Date d1 = new Date();
        SimpleDateFormat df = new  SimpleDateFormat("MM/dd/YYYY HH:mm a");
        String formattedDate = df.format(d1);   
        
        String request  = din.readUTF();  
        System.out.println("\n\t*********************************");
        System.out.println("\t [CLIENT REQUEST]: "+ request);  
        System.out.println("\n\t*********************************");

        FileWriter fw3 = new FileWriter("block1.txt", true);
        fw3.append(" \nRequest made: " + request + "\nCurrent Time " + formattedDate);
        fw3.close();


        FileReader file = new FileReader("C:\\Users\\Hp\\Desktop\\HTTP\\block.txt");
        BufferedReader reader = new BufferedReader(file);

        String key="";
        String line = reader.readLine();
        while(line!=null)
        {
            key+=line;
            line=reader.readLine();
        }
        // System.out.println(key);
        
        dout.writeUTF(key);  
        dout.flush();  
        dout.close();

        FileWriter fw5 = new FileWriter("block1.txt", true);
        fw5.append("\n[RESPONSE]: " + key);
        fw5.close();
        
        din.close();  
        s.close();  
        ss.close();  
    }

    public static void proxy_server()throws Exception
    {  

        int port_no = 6666;
        Socket proxy = new Socket("localhost",port_no);  
        DataInputStream proxy_din = new DataInputStream(proxy.getInputStream());  
        DataOutputStream proxy_dout = new DataOutputStream(proxy.getOutputStream());  
        BufferedReader proxy_br = new BufferedReader(new InputStreamReader(System.in));

        InetAddress proxy_localhost = InetAddress.getLocalHost();
        String proxy_address = "Proxy Server connected with " + proxy_localhost.getHostAddress() +"with port no. " + port_no;

            FileWriter fw6 = new FileWriter("block2.txt");
            fw6.write(proxy_address);
            fw6.close();

            Date d2 = new Date();
            SimpleDateFormat df1 = new  SimpleDateFormat("MM/dd/YYYY HH:mm a");
            String formattedDate1 = df1.format(d2);  
        
            System.out.println("[PROXY]: Enter your request:");
            String proxy_request = proxy_br.readLine();  

            Thread.sleep(1000);
            proxy_dout.writeUTF(proxy_request);  
            proxy_dout.flush();  

            FileWriter fw7 = new FileWriter("block2.txt", true);
            fw7.append(" \nRequest made: " + proxy_request + "\nCurrent Time " + formattedDate1);
            fw7.close();

            String server_response = proxy_din.readUTF();
            Thread.sleep(3000);
            System.out.println("\n\t*********************************");  
            System.out.println("\t [SERVER RESPONSE]: "+ server_response); 
            System.out.println("\n\t*********************************");  


            FileWriter fw = new FileWriter("block.txt");
            fw.append(server_response);
            fw.close();

            FileWriter fw8 = new FileWriter("block2.txt", true);
            fw8.append("\n[RESPONSE]:" + server_response);
            fw8.close();

            proxy_dout.close();  
            proxy_din.close();  
            proxy.close();  
    }


    public static void main(String[] args) throws Exception
    {
        while(true)
        {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("==============================");
        System.out.println("1. client-proxy connection");
        System.out.println("2. proxy-server connection");
        System.out.println("3. exit");
        System.out.println("==============================");
        System.out.println("\n");

        System.out.println("Enter your choice");
        int choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    System.out.println("client - proxy");
                    block_server obj1 = new block_server();
                    obj1.client_proxy();
                    break;

                case 2:
                    System.out.println("proxy server");
                    block_server obj2 = new block_server();
                    obj2.proxy_server();
                    break;

                default:
                    System.out.println("exit!");
                    break;
            }
        }

    }
}  