package ficha1.ex15_aula6.c;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Worker {
    // 3 workers diferentes, args = "5001", args = "5002", args = "5003"
    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length != 1)
            return;

        boolean keepGoing = true;

        MulticastSocket ms = new MulticastSocket(5001);
        ArrayList<Thread> allThreads = new ArrayList<>();

        while(keepGoing) {

            DatagramPacket dpRec = new DatagramPacket(new byte[4000], 4000);

            ms.receive(dpRec);

            Socket masterSocket = new Socket(
                    dpRec.getAddress().getHostAddress(),
                    Integer.parseInt(new String(dpRec.getData(), 0, dpRec.getLength()))
            );

            System.out.println("Connected to " + masterSocket.getInetAddress().getHostAddress()
                    + ":" + masterSocket.getPort());

            ThreadWorker t = new ThreadWorker(masterSocket); // extends Thread

            t.start();

            allThreads.add(t);

        }

        for (Thread t : allThreads) {
            t.join();
        }

        ms.close();
    }
}
