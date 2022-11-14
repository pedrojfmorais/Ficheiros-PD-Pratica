package ficha1.ex18_aula8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static ficha1.ex17_aula7.RemoteInterface.MAX_CHUNK_LENGTH;

public class Client extends UnicastRemoteObject implements GetRemoteFileClientInterface{


    private final static String CLIENT_FILES_FOLDER = "client/";
    private final static String FILENAME = "landscape.jpg";

    protected Client() throws RemoteException {
    }

    @Override
    public void writeFileChunk(byte [] fileChunk, int nbytes) throws IOException {

        if(nbytes == -1){
            UnicastRemoteObject.unexportObject(this, true);
            return;
        }

        FileOutputStream fos = new FileOutputStream(CLIENT_FILES_FOLDER + FILENAME, true);

        fos.write(fileChunk, 0, fileChunk.length);

        fos.close();
    }


    public static void main(String[] args) throws IOException, NotBoundException {
        Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
        Remote remoteRef = r.lookup("getFile");

        ServerRemoteInterface ri = (ServerRemoteInterface) remoteRef;

        Client cli = new Client();

        ri.getFile(FILENAME, cli);

    }
}
