package ficha1.ex19_aula8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GetRemoteFileObserverInterface extends Remote {
    void notifyNewOperationConcluded(String description) throws RemoteException;
}
