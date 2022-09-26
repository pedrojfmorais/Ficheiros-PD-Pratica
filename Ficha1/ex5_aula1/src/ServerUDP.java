import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerUDP {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket(9001);
        DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);

        ds.receive(dpRec);
    }
}
