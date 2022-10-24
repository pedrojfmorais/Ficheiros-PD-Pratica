package ficha1.ex14_aula5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class master {

    public static ArrayList<String[]> readFileWorkers(String filename) throws IOException {
        ArrayList<String[]> result = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String st;
        while ((st = br.readLine()) != null){
            String[] split = st.split(" ");

            if(split.length != 2)
                continue;

            try {
                Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                InetAddress.getByName(split[0]);
            } catch (UnknownHostException e) {
                continue;
            }

            result.add(split);
        }
        return result;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 2)
            return;

        int nIntervalos = Integer.parseInt(args[1]);

        ArrayList<String[]> workers = readFileWorkers(args[0]);
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
            System.out.println("Worker #" + i+1 + ", res: " + res[i]);
            sum += res[i];
        }

        System.out.println("Final res: " + sum);

    }
}
