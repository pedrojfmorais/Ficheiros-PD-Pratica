package ficha1.ex7_aula2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerTCP {
    public static void main(String[] args) throws IOException {

        boolean keepGoing = true;

        ServerSocket ss = new ServerSocket(9001);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        while(keepGoing) {

            Socket cliSocket = ss.accept();

            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                    + ":" + cliSocket.getPort());

            InputStream is = cliSocket.getInputStream();
            OutputStream os = cliSocket.getOutputStream();

            byte[] msgBuffer = new byte[256];
            int nBytes = is.read(msgBuffer);

            String msgRec = new String(msgBuffer, 0, nBytes);

            System.out.println("Received '" + msgRec + "' ...");

            if (msgRec.equals("HORA")) {

                String curTime = sdf.format(new Date());

                byte[] curTimeBytes = curTime.getBytes();
                os.write(curTimeBytes);
                os.flush();
            }else
                keepGoing = false;

            cliSocket.close();
        }

        ss.close();
    }
}
