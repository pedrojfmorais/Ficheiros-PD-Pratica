import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerUDP {
    public static void main(String[] args) throws IOException, InterruptedException {

        DatagramSocket ds = new DatagramSocket(9001);

        while (true) {

            DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);

            ds.receive(dpRec);

            String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());

            System.out.println("Received \"" + msgRec + "\" from " + dpRec.getAddress().getHostAddress()
                    + ":" + dpRec.getPort());

            if(msgRec.equals("fim"))
                break;


            InetAddress ipClient = dpRec.getAddress();
            int portClient = dpRec.getPort();

            FileInputStream fis = new FileInputStream("server/" + msgRec);
            byte[] chunk = new byte[4000];
            int nBytes;

            while (true){

                nBytes = fis.read(chunk);

                if(nBytes == -1)
                    break;

                System.out.println(nBytes);

                DatagramPacket dpSend = new DatagramPacket(
                        chunk,
                        chunk.length,
                        ipClient,
                        portClient
                );

                ds.send(dpSend);
                Thread.sleep(300);

            }

            DatagramPacket dpSend = new DatagramPacket(
                    chunk,
                    0,
                    ipClient,
                    portClient
            );

            ds.send(dpSend);
        }

        ds.close();
    }
}
