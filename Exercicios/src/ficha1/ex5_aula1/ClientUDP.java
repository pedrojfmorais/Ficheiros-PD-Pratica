package ficha1.ex5_aula1;

import java.io.IOException;
import java.net.*;

public class ClientUDP {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(3 * 1000);

        byte[] msgBytes = "HORA".getBytes();

        InetAddress ipServer = InetAddress.getByName("127.0.0.1");
//        InetAddress ipServer = InetAddress.getLocalHost(); //usa o ip de uma das placas de rede
        int portServer = 9001;

        DatagramPacket dpSend = new DatagramPacket(
                msgBytes,
                msgBytes.length,
                ipServer,
                portServer
        );

        ds.send(dpSend);

        DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);

        ds.receive(dpRec);

        String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());

        System.out.println("[" + dpRec.getAddress().getHostAddress() + ":" + dpRec.getPort()
                + "] Server Time: " + msgRec);

        ds.close();
    }
}
