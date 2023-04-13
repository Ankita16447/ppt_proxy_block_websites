import java.net.*;  
import java.io.*; 
import java.util.*;
import java.awt.Desktop;
import java.net.URI;


class block_main_server
{  
    public static void main(String args[])throws Exception
    {  

        System.out.println("Server is waiting for proxy server....");
        ServerSocket ss=new ServerSocket(6666);  
        Socket s=ss.accept();  
        System.out.println("Proxy server connected....");

        DataInputStream din=new DataInputStream(s.getInputStream());  
        DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
        DataOutputStream dout1=new DataOutputStream(s.getOutputStream());  

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
        
        String proxy_request  = din.readUTF();  

        System.out.println("\n\t*******************************");
        System.out.println("\t[PROXY REQUEST]: "+ proxy_request); 
        System.out.println("\n\t*******************************");

        ArrayList<String> list = new ArrayList<String>();
        list.add("www.yahoo.com");
        list.add("www.xyz.com");

        for(int i=0 ; i<list.size();i++)
        {
            if(list.contains(proxy_request))
            {
                String server_response = "Request to access "+ proxy_request + " denied ";
                dout.writeUTF(server_response);
                dout.flush();
            }
            else
            {
                String server_response1 = "Request to access "+ proxy_request + " accepted ";
                dout1.writeUTF(server_response1);
                dout1.flush();
                // Desktop desk = Desktop.getDesktop();
                // desk.browse(new URI("https://www.google.com"));

            }
        }


        dout.close();
        dout1.close();
        
        din.close();  
        s.close();  
        ss.close();  
    }
}  