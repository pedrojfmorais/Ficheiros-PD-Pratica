import java.io.*;
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

        Msg myMessage;
        boolean keepGoing = true;

        while (keepGoing){

            String msg = sc.nextLine();

            if(msg.equals("exit"))
                keepGoing = false;


            myMessage = new Msg(username, msg);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(myMessage);
            byte[] myMessageBytes = baos.toByteArray();

            DatagramPacket dp = new DatagramPacket(
                    myMessageBytes, myMessageBytes.length,
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

                    ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    Object myMsg = ois.readObject();

                    if(myMsg instanceof String){
                        String msg = (String) myMsg;

                        if(msg.contains("LIST")){

                            System.out.println("String: " + msg);

                            dp.setData(username.getBytes());
                            dp.setLength(username.getBytes().length);
                            ms.send(dp);
                        }
                    } else if(myMsg instanceof Msg){
                        Msg msg = (Msg) myMsg;
                        System.out.println("[" + msg.getNickname()+"] " + msg.getMsg());
                    } else
                        System.out.println("ERRO");
                }
            } catch (IOException ignored) { }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
