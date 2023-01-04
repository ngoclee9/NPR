package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        try{
            serverSocket = new ServerSocket(7777);
        } catch (IOException e){
            System.out.println(e);
            System.exit(1);
        }
        try {
            while (true){
                Socket connectionSocket = serverSocket.accept();
                new ServiceThread (connectionSocket, clientNumber++).start();
            }
        } finally{
            serverSocket.close();
        }
    }
    private static void log(String message){
        System.out.println(message);
    }
    private static class ServiceThread extends Thread{
        private  int clientNumber;
        private Socket connectionSocket;
        public ServiceThread(Socket connectionSocket, int clientNumber){
            this.clientNumber = clientNumber;
            this.connectionSocket = connectionSocket;
            log("New connection with client# "+ this.clientNumber + " at "+ connectionSocket);
        }
        @Override
        public void run(){
            try{
                DataInputStream  input = new DataInputStream(connectionSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());
                while(true){
                    String clientInput = input.readUTF();
                    output.writeUTF(clientInput.toUpperCase());
                }
            }catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }

    }

}
