package ficha1.ex17_aula7;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Server extends UnicastRemoteObject implements RemoteInterface {
    private final static String SERVER_FILES_FOLDER = "server/";
    protected Server() throws RemoteException {
    }

    @Override
    public byte[] getFileChunk(String fileName, long offset) throws RemoteException {
        byte[] chunk;
        int nBytes;
        try (
                FileInputStream fis = new FileInputStream(SERVER_FILES_FOLDER+fileName)
        ){
            fis.skip(offset);

            chunk = new byte[MAX_CHUNK_LENGTH];

            nBytes = fis.read(chunk);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nBytes == -1 ? null : Arrays.copyOf(chunk, MAX_CHUNK_LENGTH);
    }

        public static void main(String[] args) throws RemoteException {
        Server s = new Server();

        Registry r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        r.rebind("getFileChunk", s);
    }
}
