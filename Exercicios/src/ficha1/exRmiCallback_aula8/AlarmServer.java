package ficha1.exRmiCallback_aula8;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmServer extends UnicastRemoteObject implements AlarmServerRemoteInterface
{
    public AlarmServer() throws RemoteException { }

    @Override
    public void createAlarmClock(AlarmClientRemoteInterface clientRef, Date alarmDate) throws RemoteException
    {
        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    clientRef.triggerAlarm();
                }
                catch (RemoteException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }, alarmDate);

        System.out.println("A new alarm is scheduled!");
    }

    public static void main(String[] args) throws RemoteException
    {
        Registry r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        r.rebind(REGISTRY_BIND_NAME, new AlarmServer());
    }
}
