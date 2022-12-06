package ficha1.ex19_aula8;

import java.io.IOException;
import java.rmi.Remote;

public interface ServerRemoteInterface extends Remote {
    int MAX_CHUNK_LENGTH = 512;

    void getFile(String fileName, GetRemoteFileClientInterface cliRef) throws IOException;
    void addObserver(GetRemoteFileObserverInterface cliRef) throws IOException;
}
