package udp_ex5;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;

public class ServerUDP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

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

            if (msgRec.equals("HORA")) {
//
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//                String curTime = sdf.format(new Date());

                Calendar cal = Calendar.getInstance();

                ServerCurrentTime sct = new ServerCurrentTime(
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        cal.get(Calendar.SECOND)
                );

//                byte[] curTimeBytes = curTime.getBytes();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
//                oos.writeObject(curTime);
                oos.writeObject(sct);
                byte[] curTimeBytes = baos.toByteArray();

                InetAddress ipClient = dpRec.getAddress();
                int portClient = dpRec.getPort();

                DatagramPacket dpSend = new DatagramPacket(
                        curTimeBytes,
                        curTimeBytes.length,
                        ipClient,
                        portClient
                );

                ds.send(dpSend);

            }else
                break;
        }

        ds.close();
    }
}
