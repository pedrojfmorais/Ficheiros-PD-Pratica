package ficha1.ex15_aula6.c;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ThreadWorkerResponseUDP extends Thread{

    private final int myPort;

    public ThreadWorkerResponseUDP(int myPort) {
        this.myPort = myPort;
    }

    @Override
    public void run() {
        try {
            MulticastSocket ms = new MulticastSocket(5001);

            while(true) {
                DatagramPacket dpRec = new DatagramPacket(new byte[256], 0, 256);

                ms.receive(dpRec);

                byte[] msg = Integer.toString(myPort).getBytes();

                DatagramPacket dpSend = new DatagramPacket(
                        msg,
                        msg.length,
                        dpRec.getAddress(),
                        dpRec.getPort()
                );

                ms.send(dpSend);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
