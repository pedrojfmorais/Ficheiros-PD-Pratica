package ficha1.ex18_aula8;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Server extends UnicastRemoteObject implements ServerRemoteInterface {
    private final static String SERVER_FILES_FOLDER = "server/";
    protected Server() throws RemoteException {
    }

    @Override
    public void getFile(String fileName, GetRemoteFileClientInterface cliRef) throws IOException {
        byte[] chunk;
        int nBytes = 0;
        try (
                FileInputStream fis = new FileInputStream(SERVER_FILES_FOLDER+fileName)
        ){

            while(nBytes != -1){
                chunk = new byte[MAX_CHUNK_LENGTH];

                nBytes = fis.read(chunk);

                cliRef.writeFileChunk(chunk, nBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RemoteException {
        Server s = new Server();

        Registry r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        r.rebind("getFile", s);
    }
}
