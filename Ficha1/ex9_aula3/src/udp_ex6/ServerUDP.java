package udp_ex6;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerUDP {

    private static final int chunkSize = 4000;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        DatagramSocket ds = new DatagramSocket(9001);

        while (true) {

            DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);

            ds.receive(dpRec);

//            String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());
            ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);

            String msgRec = (String) ois.readObject();

            System.out.println("Received \"" + msgRec + "\" from " + dpRec.getAddress().getHostAddress()
                    + ":" + dpRec.getPort());

            if(msgRec.equals("fim"))
                break;


            InetAddress ipClient = dpRec.getAddress();
            int portClient = dpRec.getPort();

            FileInputStream fis = new FileInputStream("server/" + msgRec);
            byte[] chunk = new byte[chunkSize];
            int nBytes;

            PartialMessage pm = new PartialMessage();
            pm.setLastPacket(false);

            while (!pm.isLastPacket()){

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);

                nBytes = fis.read(chunk);

                pm.setMsg(chunk);
                pm.setMsgSize(nBytes);

                if(nBytes == -1) {
                    pm.setMsg(new byte[4000]);
                    pm.setMsgSize(0);
                    pm.setLastPacket(true);
                }

                System.out.println(nBytes);

                oos.writeUnshared(pm);
                byte[] msgBytes = baos.toByteArray();

                DatagramPacket dpSend = new DatagramPacket(
                        msgBytes,
                        msgBytes.length,
                        ipClient,
                        portClient
                );

                ds.send(dpSend);
                System.out.println("send");
                Thread.sleep(100);
            }

//            System.out.println(nBytes);
//
//            oos.writeObject(pm);
//            byte[] msgBytes = baos.toByteArray();
//
//            DatagramPacket dpSend = new DatagramPacket(
//                    msgBytes,
//                    msgBytes.length,
//                    ipClient,
//                    portClient
//            );
//
//            ds.send(dpSend);
        }

        ds.close();
    }
}
