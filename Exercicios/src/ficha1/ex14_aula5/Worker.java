package ficha1.ex14_aula5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Worker {
    // 3 workers diferentes, args = "5001", args = "5002", args = "5003"
    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length != 1)
            return;

        boolean keepGoing = true;

        ServerSocket workerSocket = new ServerSocket(Integer.parseInt(args[0]));
        ArrayList<Thread> allThreads = new ArrayList<>();

        while(keepGoing) {
            Socket masterSocket = workerSocket.accept();

            System.out.println("Connected to " + masterSocket.getInetAddress().getHostAddress()
                    + ":" + masterSocket.getPort());

            ThreadWorker t = new ThreadWorker(masterSocket); // extends Thread

            t.start();

            allThreads.add(t);

        }

        for (Thread t : allThreads) {
            t.join();
        }

        workerSocket.close();
    }
}
