package NPR;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        try{
            serverSocket = new ServerSocket(1153);
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
                DataInputStream input = new DataInputStream(connectionSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());
                int clientStudentID = Integer.parseInt(input.readUTF());
                output.writeUTF(factorial(clientStudentID).toString());

                while(true){
                    String clientInput = input.readUTF();
                    if( isNumeric(clientInput) ) {
                        if(Integer.parseInt( clientInput)>0 ){
                            output.writeUTF(String.valueOf(Math.pow(clientStudentID, Integer.parseInt(clientInput))));
                        }
                        else output.writeUTF(clientInput);

                    }
                    else{
                        output.writeUTF(clientInput);

                    }


                }
            }catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }

        public static BigInteger factorial(int N)
        {
            BigInteger f = new BigInteger("1");
            for (int i = 2; i <= N; i++)
                f = f.multiply(BigInteger.valueOf(i));
            return f;
        }
        public static boolean isNumeric(String str) {
            return str != null && str.matches("[-+]?\\d*\\.?\\d+");
        }


    }
}
