package ficha1.ex9_aula3.tcp_ex8;

import ficha1.ex9_aula3.udp_ex6.PartialMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket ss = new ServerSocket(9001);

        while(true) {

            Socket cliSocket = ss.accept();

            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                    + ":" + cliSocket.getPort());

//            InputStream is = cliSocket.getInputStream();
//            OutputStream os = cliSocket.getOutputStream();


            ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());
//
//            byte[] msgBuffer = new byte[4000];
//            int nBytes = is.read(msgBuffer);

//            String msgRec = new String(msgBuffer, 0, nBytes);


            String msgRec = (String) ois.readObject();

            System.out.println("Received '" + msgRec + "' ...");

            if(msgRec.equals("fim"))
                break;

            FileInputStream fis = new FileInputStream("server/" + msgRec);

            byte[] chunk = new byte[4000];
            int nBytes;

            do {

                nBytes = fis.read(chunk);

                PartialMessage pm = new PartialMessage();

                pm.setMsg(chunk);
                pm.setMsgSize(nBytes);

                if (nBytes == -1){
                    pm.setMsg(new byte[4000]);
                    pm.setMsgSize(0);
                    pm.setLastPacket(true);
                }else
                    pm.setLastPacket(false);

//                nBytes = fis.read(msgBuffer);

//                os.write(msgBuffer, 0, msgBuffer.length);
//                os.flush();

                oos.reset();
                oos.writeUnshared(pm);

            }while(nBytes != -1);

            cliSocket.close();
        }

        System.out.println("Server is closing!");
        ss.close();
    }
}
