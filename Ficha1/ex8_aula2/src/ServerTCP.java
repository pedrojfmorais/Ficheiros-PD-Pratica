import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) throws IOException {

        boolean keepGoing = true;

        ServerSocket ss = new ServerSocket(9001);

        while(keepGoing) {

            Socket cliSocket = ss.accept();

            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                    + ":" + cliSocket.getPort());

            InputStream is = cliSocket.getInputStream();
            OutputStream os = cliSocket.getOutputStream();

            byte[] msgBuffer = new byte[4000];
            int nBytes = is.read(msgBuffer);

            String msgRec = new String(msgBuffer, 0, nBytes);

            System.out.println("Received '" + msgRec + "' ...");

            FileInputStream fis = new FileInputStream("server/" + msgRec);

            do {

                nBytes = fis.read(msgBuffer);

                os.write(msgBuffer, 0, msgBuffer.length);
                os.flush();

            }while(nBytes > -1);

            cliSocket.close();
        }

        System.out.println("Server is closing!");
        ss.close();
    }
}
