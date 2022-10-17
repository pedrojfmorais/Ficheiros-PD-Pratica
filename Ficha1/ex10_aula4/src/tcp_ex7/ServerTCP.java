package tcp_ex7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

public class ServerTCP {
    public static void main(String[] args) throws IOException, InterruptedException {

        boolean keepGoing = true;

        ServerSocket ss = new ServerSocket(9001);
        ArrayList<Thread> allThreads = new ArrayList<>();

        ArrayList<String> logs = new ArrayList<>();

        while(keepGoing) {

            Socket cliSocket = ss.accept();

            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                    + ":" + cliSocket.getPort());

//            ThreadCLientP1 tc = new ThreadCLientP1(cliSocket); // implements Runnable
//            Thread t = new Thread(tc);

            ThreadCLientP1 t = new ThreadCLientP1(cliSocket, logs); // extends Thread

            t.start();

            allThreads.add(t);
        }

        for (Thread t : allThreads) {
            t.join();
        }

        ss.close();
    }
}
