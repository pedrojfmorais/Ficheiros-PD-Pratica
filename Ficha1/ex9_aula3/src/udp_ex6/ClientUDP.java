package udp_ex6;

import udp_ex5.ServerCurrentTime;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        DatagramSocket ds = new DatagramSocket();
//        ds.setSoTimeout(3 * 1000);

        String ipServerText = "127.0.0.1";

//        String filename = "isec.jpg";
        String filename = "landscape.jpg";
//        byte[] msgBytes = filename.getBytes();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(filename);
        byte[] msgBytes = baos.toByteArray();

        InetAddress ipServer = InetAddress.getByName(ipServerText);
        int portServer = 9001;

        DatagramPacket dpSend = new DatagramPacket(
                msgBytes,
                msgBytes.length,
                ipServer,
                portServer
        );

        ds.send(dpSend);

        DatagramPacket dpRec = new DatagramPacket(new byte[40000], 40000);

        FileOutputStream fos = new FileOutputStream("client/" + filename);

        do{
            ds.receive(dpRec);

            ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);

            PartialMessage pm = (PartialMessage) ois.readObject();

            fos.write(pm.getMsg(), 0, pm.getMsgSize());
            System.out.println(pm.getMsgSize());

            if(pm.isLastPacket())
                break;

        } while(true);

        fos.close();
        ds.close();
    }
}
