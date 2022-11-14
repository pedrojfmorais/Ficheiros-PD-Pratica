package ficha1.ex16_aula7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

public class TimeServer extends UnicastRemoteObject implements RemoteTimeInterface {

    protected TimeServer() throws RemoteException {
    }

    @Override
    public Hora getHora() throws IOException {
        throw new IOException("Server Crashed");
//        Calendar cal = Calendar.getInstance();
//
//        return new Hora(
//                cal.get(Calendar.HOUR_OF_DAY),
//                cal.get(Calendar.MINUTE),
//                cal.get(Calendar.SECOND)
//        );
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        TimeServer ts = new TimeServer();

        // Com registry externo
//        Naming.rebind("rmi://127.0.0.1:1099/timeserver", ts);

        // Com registry interno
        Registry r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        r.rebind("timeserver", ts);
    }
}
