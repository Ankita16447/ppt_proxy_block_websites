import java.net.*;  
import java.io.*; 
import java.util.*; 



class block_client
{

    public static void main(String args[])throws Exception
    {  

       Scanner sc = new Scanner(System.in);
        System.out.println("Enter your ip address...");
        String ipAddress = sc.nextLine();  
        Socket client = new Socket(ipAddress,3333);  

        DataInputStream din = new DataInputStream(client.getInputStream());  
        DataOutputStream dout = new DataOutputStream(client.getOutputStream()); 

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        
            System.out.println("Enter your request:");
            String request = br.readLine();  

            dout.writeUTF(request);  
            dout.flush(); 







        Thread.sleep(24000);

        String response = din.readUTF();  
        System.out.println("\n\t*********************************");  
        System.out.println("[PROXY RESPONSE]: "+ response);  
        System.out.println("\n\t*********************************");  

        
        
        dout.close();  
        client.close();  
    }
}
