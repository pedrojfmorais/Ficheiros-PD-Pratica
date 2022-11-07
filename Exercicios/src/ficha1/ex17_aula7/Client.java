package ficha1.ex17_aula7;

import ficha1.ex16_aula7.RemoteTimeInterface;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static ficha1.ex17_aula7.RemoteInterface.MAX_CHUNK_LENGTH;

public class Client {


    private final static String CLIENT_FILES_FOLDER = "client/";
    private final static String FILENAME = "landscape.jpg";

    public static void main(String[] args) throws IOException, NotBoundException {
        Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
        Remote remoteRef = r.lookup("getFileChunk");

        RemoteInterface ri = (RemoteInterface) remoteRef;

        byte[] fileChunk;
        FileOutputStream fos = new FileOutputStream(CLIENT_FILES_FOLDER + FILENAME);
        long offset = 0;

        while (true){

            fileChunk = ri.getFileChunk(FILENAME, offset);

            if (fileChunk == null)
                break;

            fos.write(fileChunk, 0, fileChunk.length);

            offset+=MAX_CHUNK_LENGTH;
        }

        fos.close();
    }
}
