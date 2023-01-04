package UDPDemo;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends JFrame implements ActionListener {
    private JButton btn1;
    private JTextField tf1, tf2, tf3, tf4, tf5;
    private Container container;
    private  JPanel panel1, panel2;
    public  Client(String s ){
        super(s);
        container = this.getContentPane();
        JLabel name = new JLabel ("Name");
        tf1 = new JTextField();
        JLabel className = new JLabel ("Class");
        tf2 = new JTextField();
        JLabel message = new JLabel ("Message");
        tf3 = new JTextField();
        JLabel Client = new JLabel ("Client");
        tf4 = new JTextField();

        tf5 = new JTextField();

        panel1 = new JPanel();
        btn1 = new JButton("Send");
        panel1.setLayout(new GridLayout(4,2));
        panel1.add(name);
        panel1.add(tf1);
        panel1.add(className);
        panel1.add(tf2);
        panel1.add(message);
        panel1.add(tf3);
        panel1.add(btn1);
        container.add(panel1);
        panel2 = new JPanel();
        panel2.add(tf4);
        panel2.add(tf5);

        panel2.setLayout(new GridLayout(2,1));
        container.add(panel2,"South");


        btn1.addActionListener(this);
        this.setSize(350, 200);
        this.setVisible(true);

        }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Send") {

            final int port = 6789;
            final String serverHost = "localhost";
            try{
                InetAddress IPAddress = InetAddress.getByName(serverHost);
                DatagramSocket clientSocket = new DatagramSocket();
                byte[] sendData;
               sendData = tf1.getText().getBytes();
                tf4.setText("FROM "+ tf1.getText());
                tf5.setText("Message sent: " + tf3.getText()) ;
                System.out.println("Hi, "+ tf1.getText());
                    sendData = tf3.getText().getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    clientSocket.send(sendPacket);
                    byte[] receiveData = new byte[sendData.length];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String modifiedSentence = new String(receivePacket.getData());
                    System.out.println("FROM SERVER: "+ modifiedSentence);

            } catch (IOException er){
                er.printStackTrace();
            }

        }
    }



        public static void main(String[] args) throws IOException {
        Client cl = new Client( "SimpleChat");

    }


}



