package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        int port = 9876;
        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[1024];
        int clientNumber = 0;
        try{

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
                ServiceThread serviceThread = new ServiceThread(receivePacket, serverSocket);
                serviceThread.start();
            }
        }  finally{
        serverSocket.close();
         }


    }
    private static void log(String message){
        System.out.println(message);
    }
    private static  class ServiceThread extends Thread {
        private DatagramPacket receivePacket;
        private DatagramSocket serverSocket;
      //  private int clientNumber;
        public ServiceThread(DatagramPacket receivePacket, DatagramSocket serverSocket){
            this.receivePacket = receivePacket;
            this.serverSocket = serverSocket;
           // this.clientNumber = clientNumber;
            //log("New connection with client# "+ " at "+ serverSocket);
        }
        @Override
        public void run() {
            try{
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                int  clientPort = receivePacket.getPort();
                InetAddress IPAddress = receivePacket.getAddress();
                String captializedSentence = sentence.toUpperCase();
                byte[] sendData = new byte[1024];
                sendData = captializedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, clientPort);
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }


        }



    }

}
