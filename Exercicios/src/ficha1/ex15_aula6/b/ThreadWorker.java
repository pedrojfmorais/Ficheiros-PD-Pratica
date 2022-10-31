package ficha1.ex15_aula6.b;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadWorker extends Thread{

    private final Socket masterSocket;


    public ThreadWorker(Socket masterSocket) {
        this.masterSocket = masterSocket;
    }

    @Override
    public void run() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(masterSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(masterSocket.getInputStream());

            MSG_Workers msg = (MSG_Workers) ois.readObject();

            Double res = PiCalculator.getPartialPiValue(msg.getIndiceWorker(), msg.getnWorkers(), msg.getnIntervalos());

            oos.writeUnshared(res);

            masterSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
