package ficha1.ex9_aula3.tcp_ex8;

import ficha1.ex9_aula3.udp_ex5.ServerCurrentTime;
import ficha1.ex9_aula3.udp_ex6.PartialMessage;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientTCP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket cliSocket = new Socket("127.0.0.1", 9001);

        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                + ":" + cliSocket.getPort());

//        InputStream is = cliSocket.getInputStream();
//        OutputStream os = cliSocket.getOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());

        String filename = "isec.jpg";
//        String filename = "landscape.jpg";
//        byte[] msgBytes = filename.getBytes();

        oos.writeObject(filename);

//        os.write(msgBytes);
//        os.flush();
//
//        byte[] msgBuffer = new byte[4000];
//
//        int nBytes;
        FileOutputStream fos = new FileOutputStream("client/" + filename);

        PartialMessage pm;
        do{

//            nBytes = is.read(msgBuffer);
            pm = (PartialMessage) ois.readObject();

            fos.write(pm.getMsg(), 0, pm.getMsgSize());
            System.out.println(pm.getMsgSize() + " " + Arrays.toString(pm.getMsg()));

        }while(!pm.isLastPacket());

        System.out.println("Socket is Closed");

        cliSocket.close();
    }
}
