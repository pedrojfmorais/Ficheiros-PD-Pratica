package ficha1.exRmiCallback_aula8;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

public class AlarmClient extends UnicastRemoteObject implements AlarmClientRemoteInterface
{
    public AlarmClient() throws RemoteException { }

    @Override
    public void triggerAlarm() throws RemoteException
    {
        System.out.println("The alarm was triggered!");
        UnicastRemoteObject.unexportObject(this, true);
    }

    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        if (args.length != 1)
        {
            System.out.println("The RMI Registry IP is missing from the command line arguments.");
            return;
        }

        Registry r = LocateRegistry.getRegistry(args[0], Registry.REGISTRY_PORT);
        AlarmServerRemoteInterface remoteRef = (AlarmServerRemoteInterface)
                r.lookup(AlarmServerRemoteInterface.REGISTRY_BIND_NAME);

        // We will set the alarm for 10 seconds from now...
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);

        AlarmClient alarmClient = new AlarmClient();
        remoteRef.createAlarmClock(alarmClient, calendar.getTime());

        System.out.println("The alarm is scheduled for 10 seconds from now!");
    }
}
