package ficha1.ex16_aula7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TimeClient {
    public static void main(String[] args) throws IOException, NotBoundException {

        // Com registry externo
//        Remote remoteRef = Naming.lookup("rmi://127.0.0.1:1099/timeserver");

        // Com registry interno
        Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
        Remote remoteRef = r.lookup("timeserver");

        RemoteTimeInterface rti = (RemoteTimeInterface) remoteRef;

        System.out.println("Current Server Time: " + rti.getHora());
    }
}
