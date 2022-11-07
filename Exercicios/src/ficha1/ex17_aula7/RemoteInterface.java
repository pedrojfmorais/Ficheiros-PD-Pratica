package ficha1.ex17_aula7;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    int MAX_CHUNK_LENGTH = 512;
    byte [] getFileChunk(String fileName, long offset) throws RemoteException;
}
