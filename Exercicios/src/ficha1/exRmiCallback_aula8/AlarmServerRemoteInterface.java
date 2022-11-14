package ficha1.exRmiCallback_aula8;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface AlarmServerRemoteInterface extends Remote
{
    String REGISTRY_BIND_NAME = "alarmClockService";
    void createAlarmClock(AlarmClientRemoteInterface clientRef, Date alarmDate) throws RemoteException;
}
