package tcp_ex7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

//public class ThreadCLientP1 implements Runnable {
public class ThreadCLientP1 extends Thread{

    private final Socket cliSocket;
    ArrayList<String> logs;

    public ThreadCLientP1(Socket cliSocket, ArrayList<String> logs) {
        this.cliSocket = cliSocket;
        this.logs = logs;
    }

    @Override
    public void run() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());

            String msgRec = (String) ois.readObject();

            System.out.println("Received '" + msgRec + "' ...");

            if (msgRec.equals("HORA")) {

                Calendar cal = Calendar.getInstance();

                ServerCurrentTime sct = new ServerCurrentTime(
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        cal.get(Calendar.SECOND)
                );

                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                oos.writeUnshared(sct); // não utiliza o mecanismo de cache

                synchronized (logs) {
                    logs.add(sct.toString());
                }
            }
            cliSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
