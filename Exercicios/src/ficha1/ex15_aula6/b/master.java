package ficha1.ex15_aula6.b;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class master {

    public static ArrayList<String[]> getWorkers() throws IOException {

        ArrayList<String[]> workers = new ArrayList<>();

        DatagramSocket ds = new DatagramSocket();
        ds.setBroadcast(true);

        byte[] msg = new byte[0];

        DatagramPacket dpSend = new DatagramPacket(
                msg,
                msg.length,
                InetAddress.getByName("255.255.255.255"),
                5001
        );

        ds.send(dpSend);

        ds.setSoTimeout(2 * 1000);

        while(true){

            DatagramPacket dpRec = new DatagramPacket(new byte[4000], 4000);
            try {
                ds.receive(dpRec);
            } catch (SocketTimeoutException e) {
                break;
            }

            String[] temp = new String[2];
            temp[0] = dpRec.getAddress().toString().substring(1);
            temp[1] = new String(dpRec.getData(), 0, dpRec.getLength());

            workers.add(temp);
        }

        return workers;
    }

    //args = "workers.txt 100000"
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 1)
            return;

        int nIntervalos = Integer.parseInt(args[0]);

        ArrayList<String[]> workers = getWorkers();
        Double[] res = new Double[workers.size()];

        ArrayList<Thread> allThreads = new ArrayList<>();

        for (int i = 0; i < workers.size(); i++) {

            Socket cliSocket = new Socket(workers.get(i)[0], Integer.parseInt(workers.get(i)[1]));

            MSG_Workers msgWorker = new MSG_Workers(nIntervalos, workers.size(), i+1);

            ThreadMaster t = new ThreadMaster(cliSocket, msgWorker, res); // extends Thread

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
