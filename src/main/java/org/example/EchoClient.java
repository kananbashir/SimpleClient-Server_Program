package org.example;

import javax.tools.DiagnosticListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
       try {
           while (true) {
               Socket socket = new Socket("localhost",4958);
               DataInputStream input = new DataInputStream(socket.getInputStream());
               DataOutputStream output = new DataOutputStream(socket.getOutputStream());

               Scanner sc = new Scanner(System.in);
               System.out.print("\nPlease enter file name (path will be: C:\\Users\\ASUS\\Desktop\\Kenan\\Java\\photos\\....png) : ");
               String fileName = sc.nextLine();

               output.writeBoolean(true);
               output.writeUTF(fileName);

               FileInputStream fis = new FileInputStream("C:\\Users\\ASUS\\Desktop\\Kenan\\Java\\photos\\" + fileName + ".png");

               byte[] data = new byte[fis.available()];
               fis.read(data);

               output.writeInt(data.length);
               output.write(data);

               String tempFileName = input.readUTF();

               System.out.println("File \"" + fileName + ".png\" copied to desktop -- " + tempFileName + ".png");

               output.writeBoolean(false);

               socket.close();
               input.close();
               output.close();
               fis.close();
           }

       } catch (Exception ex){
           ex.printStackTrace();
       }
    }
}
