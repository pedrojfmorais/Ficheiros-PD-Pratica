package ficha1.ex15_aula6.c;

import ficha1.ex15_aula6.c.MSG_Workers;
import ficha1.ex15_aula6.c.ThreadMaster;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class master {

    //args = "workers.txt 100000"
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 1)
            return;

        int nIntervalos = Integer.parseInt(args[0]);

        ArrayList<Thread> allThreads = new ArrayList<>();
        ArrayList<Socket> allSocketsWorkers = new ArrayList<>();

        ServerSocket ss = new ServerSocket(0);

        int tcpPort = ss.getLocalPort();

        DatagramSocket ds = new DatagramSocket();

        byte[] msg = Integer.toString(tcpPort).getBytes();

        DatagramPacket dpSend = new DatagramPacket(
                msg,
                msg.length,
                InetAddress.getByName("255.255.255.255"),
                5001
        );

        ds.send(dpSend);

        ss.setSoTimeout(5 * 1000);

        while(true){

            Socket cliSocket = null;

            try {

                cliSocket = ss.accept();

            }catch(SocketTimeoutException e){
                break;
            }

            allSocketsWorkers.add(cliSocket);
        }

        Double[] res = new Double[allSocketsWorkers.size()];

        for (int i = 0; i < allSocketsWorkers.size(); i++) {


            MSG_Workers msgWorker = new MSG_Workers(nIntervalos, allSocketsWorkers.size(), i+1);

            ThreadMaster t = new ThreadMaster(allSocketsWorkers.get(i), msgWorker, res); // extends Thread

            t.start();

            allThreads.add(t);
        }

        for (Thread t : allThreads) {
            t.join();
        }

        double sum = 0.0;

        for (int i = 0; i < res.length; i++) {
            System.out.println("Worker #" + (i+1) + ", res: " + res[i]);
            sum += res[i];
        }

        System.out.println("Final res: " + sum);

    }
}
