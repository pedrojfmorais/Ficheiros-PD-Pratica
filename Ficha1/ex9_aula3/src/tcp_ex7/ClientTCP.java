package tcp_ex7;

import udp_ex5.ServerCurrentTime;

import java.io.*;
import java.net.Socket;

public class ClientTCP {

    private static final String MSG = "HORA";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket cliSocket = new Socket("127.0.0.1", 9001);

        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                + ":" + cliSocket.getPort());

//        InputStream is = cliSocket.getInputStream();
//        OutputStream os = cliSocket.getOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());

//        os.write(MSG.getBytes());
//        os.flush();

        oos.writeObject(MSG);

//        byte[] msgBuffer = new byte[256];
//        int nBytes = is.read(msgBuffer);

        try {
//        if(nBytes > -1){
//            String serverTime = new String(msgBuffer, 0, nBytes);

//            String serverTime = (String) ois.readObject();
            ServerCurrentTime serverTime = (ServerCurrentTime) ois.readObject();

            System.out.println("Current Server Time: " + serverTime);

        }catch(EOFException e){

//        }else
            System.out.println("Socket is Closed");

        }
        cliSocket.close();
    }
}
