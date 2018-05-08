/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcat;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author konch
 */
class ServerObject extends Thread
{
    public MessageObject mo;
    protected Socket link;
    protected HashMap usercred;
        public static HashMap actsess;
    static
    {
        actsess = new HashMap();
    }
    
    ServerObject()
    {
//        actsess=new HashMap();
//        actsess.put("gaurav","");
//        actsess.put("gitanshi","");
//        actsess.put("jennifer","");
//        actsess.put("nikhil","");
        
    }
    ServerObject(MessageObject m, Socket lnk, HashMap cred)
    {
        this.mo = m;
        this.link = lnk;
        this.usercred = cred;
        
         
    }
    
    
    public void run()
    {
        switch(mo.flag){
            case (0): {
                        //Authentication
                        System.out.println(mo.username+" + "+mo.hashpasswd);
                        
                        if (usercred.containsKey(""+mo.username) && usercred.get(mo.username).equals(mo.hashpasswd))
                        {
                            System.out.println("Valid User");
                            actsess.put(""+mo.username,"TRUE");
                            
                           Set s1= actsess.entrySet();
                           System.out.println(Arrays.toString(s1.toArray())+"\nSize:"+actsess.size());
//                        
                        
                        //int clientPort = link.getPort();
                        
                        try {
                            Socket sock = new Socket(link.getInetAddress(),mo.portval);
                            System.out.println(link.getPort());
                            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                            mo.flag = 1;
                            oos.writeObject(mo);
                            link.close();
                            
                        } catch (IOException ex) {
                            Logger.getLogger(ServerObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        break;
                        
                       }
            case (1): {
                        //COnnect with Client
                         //For Next phase
                        String username = mo.username;
                        if(actsess.containsKey(username))
                        {
                            System.out.println(username+" is active. Connecting now....");
                            mo.flag=2;
                        }
                        else
                        {
                            System.out.println(username+" is an invalid user.");
                            mo.flag=1;
                        }
                        try {
                            Socket sock = new Socket(link.getInetAddress(),mo.portval);
                            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                            oos.writeObject(mo);
                            link.close();
                        }catch (IOException ex)
                        {
                            Logger.getLogger(ServerObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        }
                        break;
            case (2): {
                        //Something I have not thought of
                        break;
                        }
            case (-1):{
                        if(actsess.containsKey(mo.username))
                            actsess.remove(mo.username);
                        break;
                        }
            
            }
        }
    }
    

public class ChatAuthServer 
{
    private static ServerSocket servSock;
    private static final int PORT = 5455;

    //protected HashMap clientsup, nodelist, actsess;
    public static void main(String[] args)
    {
        HashMap clientsup, nodelist;
        //clientsup - Credentials
        //nodelist - Nodes from where Clents will connect
        //actsess - Actve Session Keys.
        
        clientsup=new HashMap();
        clientsup.put("gaurav","98afd4bf4966776fde22da615bb33d59"); //kon25#ch4d9
        clientsup.put("gitanshi","86dd8e6784c2fdd908c037c4c7004536"); //Gitanshi123
        clientsup.put("jennifer","8e9d895f211d5d988d72dcb1f630501c"); //WeR0n1b@k
        clientsup.put("nikhil","7b0d61e90c39356d9dcb039ee7c1082e");//N1k3h#5tn28
        
        
        nodelist=new HashMap();
        nodelist.put("gaurav","");
        nodelist.put("gitanshi","");
        nodelist.put("jennifer","");
        nodelist.put("nikhil","");
        
        

        
        
        
        System.out.println("Opening port...\n");
        try
        {
            servSock = new ServerSocket(PORT); //Step 1.
            
        }
        catch(IOException ioEx)
        {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        do
        {
            handleClient(clientsup);
        }while (true);
    }
    private static void handleClient(HashMap clientsup)
    {
        Socket link = null; //Step 2.
    try
    {
        System.out.println("Waiting for Requests");
        link = servSock.accept(); //Step 2.
        //Scanner input = new Scanner(link.getInputStream());//Step 3.
        ObjectInputStream ois = new ObjectInputStream(link.getInputStream());
        InetAddress cli = link.getLocalAddress();
        
        MessageObject mobj= (MessageObject)ois.readObject();
        
        ServerObject so = new ServerObject(mobj,link,clientsup);
        so.start();
        

    }
    catch(IOException ioEx)
    {
        ioEx.printStackTrace();
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(ChatAuthServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    finally
    {
        try
        {
            System.out.println("\n* Closing connection... *");
            link.close(); //Step 5.
            
        }
        catch(IOException ioEx)
        {
        System.out.println("Unable to disconnect!");
        System.exit(1);
    }
    }
    }



}