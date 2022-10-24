package ficha1.ex9_aula3.tcp_ex7;

import ficha1.ex9_aula3.udp_ex5.ServerCurrentTime;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServerTCP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        boolean keepGoing = true;

        ServerSocket ss = new ServerSocket(9001);

//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        while(keepGoing) {

            Socket cliSocket = ss.accept();

            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                    + ":" + cliSocket.getPort());

//            InputStream is = cliSocket.getInputStream();
//            OutputStream os = cliSocket.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());

//            byte[] msgBuffer = new byte[256];
//            int nBytes = is.read(msgBuffer);
//
//            String msgRec = new String(msgBuffer, 0, nBytes);

            String msgRec = (String) ois.readObject();

            System.out.println("Received '" + msgRec + "' ...");

            if (msgRec.equals("HORA")) {

//                String curTime = sdf.format(new Date());
                Calendar cal = Calendar.getInstance();

                ServerCurrentTime sct = new ServerCurrentTime(
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        cal.get(Calendar.SECOND)
                );
//                byte[] curTimeBytes = curTime.getBytes();
//                os.write(curTimeBytes);
//                os.flush();

//                oos.writeObject(curTime);

//                oos.writeObject(sct);
                oos.writeUnshared(sct); // não utiliza o mecanismo de cache

            }else
                keepGoing = false;

            cliSocket.close();
        }

        ss.close();
    }
}
