package ficha1.exRmiCallback_aula8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AlarmClientRemoteInterface extends Remote
{
    void triggerAlarm() throws RemoteException;
}
