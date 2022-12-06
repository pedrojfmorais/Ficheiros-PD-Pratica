package ficha1.ex19_aula8;

import java.io.IOException;
import java.rmi.Remote;

public interface GetRemoteFileClientInterface extends Remote {

    void writeFileChunk(byte [] fileChunk, int nbytes) throws IOException;
}
