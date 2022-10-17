package tcp_ex7;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTCP {

    private static final String MSG = "HORA";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket cliSocket = new Socket("127.0.0.1", 9001);

        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                + ":" + cliSocket.getPort());

        ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());

        oos.writeObject(MSG);

        try {
            ServerCurrentTime serverTime = (ServerCurrentTime) ois.readObject();

            System.out.println("Current Server Time: " + serverTime);

        }catch(EOFException e){
            System.out.println("Socket is Closed");
        }
        cliSocket.close();
    }
}
