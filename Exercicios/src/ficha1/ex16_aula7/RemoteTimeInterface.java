package ficha1.ex16_aula7;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteTimeInterface extends Remote {
    Hora getHora() throws RemoteException;
}
