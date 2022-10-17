import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    private static final int PORT = 9001;

    public static void main(String[] args) throws IOException, InterruptedException {

        ChatClient cc = new ChatClient();
        cc.startChat();
    }

    void startChat() throws IOException, InterruptedException {
        MulticastSocket ms = new MulticastSocket(PORT);
        InetAddress ipGroup = InetAddress.getByName("224.1.2.3");

        SocketAddress sa = new InetSocketAddress(ipGroup, PORT);
        NetworkInterface ni = NetworkInterface.getByName("wlan1");

        ms.joinGroup(sa, ni);

        System.out.println("Welcome to the chat!");
        Scanner sc = new Scanner(System.in);
        System.out.print("Insert your username: ");
        String username = sc.nextLine();

        ReceiveMessages rm = new ReceiveMessages(ms, username);
        rm.start();

        boolean keepGoing = true;

        while (keepGoing){

            String msg = sc.nextLine();

            if(msg.equals("exit"))
                keepGoing = false;

            msg = "[" + username + "] " + msg;
            DatagramPacket dp = new DatagramPacket(
                    msg.getBytes(), msg.getBytes().length,
                    ipGroup, PORT
            );

            ms.send(dp);
        }

        ms.leaveGroup(sa, ni);
        ms.close();
        rm.join();
    }

    class ReceiveMessages extends Thread{

        private MulticastSocket ms;
        private final String username;

        public ReceiveMessages(MulticastSocket ms, String username) {
            this.ms = ms;
            this.username = username;
        }

        @Override
        public void run() {
            try{
                while(true){
                    DatagramPacket dp = new DatagramPacket(new byte[256], 256);

                    ms.receive(dp);
                    String msg = new String(dp.getData(), 0, dp.getLength());

                    System.out.println(msg);

                    if(msg.contains("LIST")){
                        dp.setData(username.getBytes());
                        dp.setLength(username.getBytes().length);
                        ms.send(dp);
                    }

                }
            } catch (IOException ignored) { }
        }
    }
}
