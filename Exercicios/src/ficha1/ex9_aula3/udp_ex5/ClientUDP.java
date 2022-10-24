package ficha1.ex9_aula3.udp_ex5;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {

    private static final String MSG = "HORA";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(3 * 1000);

//        byte[] msgBytes = MSG.getBytes();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(MSG);
        byte[] msgBytes = baos.toByteArray();

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

//        String serverTime = new String(dpRec.getData(), 0, dpRec.getLength());

        ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);

//        String serverTime = (String) ois.readObject();

        ServerCurrentTime serverTime = (ServerCurrentTime) ois.readObject();

        System.out.println("[" + dpRec.getAddress().getHostAddress() + ":" + dpRec.getPort()
                + "] Server Time: " + serverTime);

        ds.close();
    }
}
