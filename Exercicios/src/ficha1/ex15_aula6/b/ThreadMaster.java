package ficha1.ex15_aula6.b;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadMaster extends Thread{

    private final Socket workerSocket;
    MSG_Workers msg;
    Double[] res;

    public ThreadMaster(Socket workerSocket, MSG_Workers msg, Double[] res) {
        this.workerSocket = workerSocket;
        this.msg = msg;
        this.res = res;
    }

    @Override
    public void run() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(workerSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(workerSocket.getInputStream());

            oos.writeUnshared(msg);

            Double resultado = (Double) ois.readObject();

            synchronized (res) {
                res[msg.getIndiceWorker()-1] = resultado;
            }

            workerSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
