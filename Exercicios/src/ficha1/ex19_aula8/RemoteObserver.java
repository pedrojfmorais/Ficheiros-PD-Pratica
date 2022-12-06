package ficha1.ex19_aula8;

import ficha1.ex19_aula8.ServerRemoteInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObserver extends UnicastRemoteObject implements GetRemoteFileObserverInterface {


    protected RemoteObserver() throws RemoteException {
    }

    @Override
    public void notifyNewOperationConcluded(String description) throws RemoteException {
        System.out.println("Server: " + description);
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
        Remote remoteRef = r.lookup("addObserver");

        ServerRemoteInterface ri = (ServerRemoteInterface) remoteRef;

        RemoteObserver rObserver = new RemoteObserver();

        ri.addObserver(rObserver);
    }
}
