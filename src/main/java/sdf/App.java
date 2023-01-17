package sdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        Socket socket = new Socket("localhost",7000);
        
        try(OutputStream os = socket.getOutputStream()){
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console con = System.console();
            String readinput ="", receivedMsg = "";

            try(InputStream is = socket.getInputStream()){
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);
                
                while(!readinput.equalsIgnoreCase("close")){
                    readinput= con.readLine("Enter command: ");
                    dos.writeUTF(readinput);
                    dos.flush();

                    receivedMsg= dis.readUTF();
                    System.out.println(receivedMsg);
    
                }

                dis.close();
                bis.close();
                is.close();

            }
            
           
        }
        catch(EOFException e){
            socket.close();
        }

    }
}
