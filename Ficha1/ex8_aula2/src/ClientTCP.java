import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTCP {
    public static void main(String[] args) throws IOException {

        Socket cliSocket = new Socket("127.0.0.1", 9001);

        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress()
                + ":" + cliSocket.getPort());

        InputStream is = cliSocket.getInputStream();
        OutputStream os = cliSocket.getOutputStream();

//        String filename = "isec.jpg";
        String filename = "landscape.jpg";
        byte[] msgBytes = filename.getBytes();

        os.write(msgBytes);
        os.flush();

        byte[] msgBuffer = new byte[4000];

        int nBytes;
        FileOutputStream fos = new FileOutputStream("client/" + filename);

        do{

            nBytes = is.read(msgBuffer);
            fos.write(msgBuffer);

        }while(nBytes > -1);

        System.out.println("Socket is Closed");

        cliSocket.close();
    }
}
