package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import compute.Compute;
import compute.Task;

public class ComputeEngine implements Compute {

	public ComputeEngine() {
		super();
	}

	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		return t.execute();
	}
	
	public static void main(String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready.");

		} catch (Exception e) {
			System.out.println("Exception starting RMI registry:");
			e.printStackTrace();
		}
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "Compute";
			Compute engine = new ComputeEngine();
			Compute stub = (Compute) UnicastRemoteObject
					.exportObject(engine, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("ComputeEngine bound");
		} catch (Exception e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}
	}
	
	
	
}
