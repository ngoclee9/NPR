package UDPDemo;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) throws IOException{
        int port = 6789;
        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[1024];
        try{
            while(true){
                DatagramPacket  receivePacket = new DatagramPacket (receiveData, receiveData.length);
                ServiceThread thread = new ServiceThread (receivePacket, serverSocket);
                thread.start();
            }
        } finally {
            serverSocket.close();
        }
    }
    private static class ServiceThread extends Thread {
        private DatagramSocket serverSocket;
        private DatagramPacket receivePacket;
        public ServiceThread (DatagramPacket receivePacket, DatagramSocket serverSocket){
            this.receivePacket = receivePacket;
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            try{
                serverSocket.receive(receivePacket);
                String clientMessage = new String (receivePacket.getData());
                int port = receivePacket.getPort();
                InetAddress IPAdress = receivePacket.getAddress();
                String capitalizedSentence = clientMessage.toUpperCase();
                byte[] sendData = new byte[1024];
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, port);
                serverSocket.send( sendPacket);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
