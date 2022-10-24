package ficha1.ex6_aula1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket();
//        ds.setSoTimeout(3 * 1000);

        String ipServerText = "127.0.0.1";

//        String filename = "isec.jpg";
        String filename = "landscape.jpg";
        byte[] msgBytes = filename.getBytes();


        InetAddress ipServer = InetAddress.getByName(ipServerText);
        int portServer = 9001;

        DatagramPacket dpSend = new DatagramPacket(
                msgBytes,
                msgBytes.length,
                ipServer,
                portServer
        );

        ds.send(dpSend);

        DatagramPacket dpRec = new DatagramPacket(new byte[4000], 4000);

        FileOutputStream fos = new FileOutputStream("client/" + filename);

        do{

            ds.receive(dpRec);

            fos.write(dpRec.getData(), 0, dpRec.getLength());

        } while(dpRec.getLength() > 0);

        ds.close();
    }
}
