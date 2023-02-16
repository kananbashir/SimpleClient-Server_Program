
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try {
            boolean ready;
            String tempFileName = "";
            int count = 1;
            while (true) {

                ServerSocket server = new ServerSocket(4958);
                System.out.println("\nServer created and litening to " + server.getLocalPort() + " port...");
                Socket clientSoc = server.accept();
                DataInputStream input = new DataInputStream(clientSoc.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSoc.getOutputStream());
                ready = input.readBoolean();
                String fileName = input.readUTF() + "_copy";

                if (fileName.equals(tempFileName)) {
                    count++;
                } else {
                    count = 1;
                }

                tempFileName = fileName;

                if (count > 1){
                    fileName = fileName + "_" + count;
                }

                while (ready) {
                    FileOutputStream fos = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\" + fileName + ".png");

                    int msgLen = input.readInt();
                    byte[] msg = new byte[msgLen];
                    input.readFully(msg);

                    fos.write(msg);
                    fos.flush();
                    fos.close();

                    ready = false;
                }
                output.writeUTF(fileName);
                server.close();
                clientSoc.close();
                input.close();
            }
        } catch (Exception ex) {
            System.out.println("\nConnection killed!");
        }

    }
}