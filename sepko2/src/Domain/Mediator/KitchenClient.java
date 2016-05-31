package Domain.Mediator;
/**
* @author Adam Minarik, Leonard Merva, Marek Dvoracek, Denis Drga, Marius Ungurean
*/
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;

import Domain.Model.Item;
import Domain.Model.Order;
import View.KitchenGUI;

public class KitchenClient extends UnicastRemoteObject implements
		KitchenClientInterface, Serializable {

	private static final long serialVersionUID = 1L;
	private static RmiServerInterface remoteService;
	private static KitchenGUI gui;

	protected KitchenClient(RmiServerInterface remoteService)
			throws RemoteException {
		super();
		KitchenClient.remoteService = remoteService;
	}

	public static void main(String[] args) throws Exception {
		String ip = ReadIP.getReadIP("ServerIPaID").getIP();
		ip = "//" + ip + ":1099";
		remoteService = (RmiServerInterface) Naming.lookup(ip + "/RmiService");
		KitchenClient client = new KitchenClient(remoteService);
		remoteService.registerKitchenForCallBack(client);
		gui = new KitchenGUI(remoteService, remoteService.getController());
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gui.setVisible(true);
	}

	@Override
	public void updateKitchen(Order order) {
		gui.updateKitchen(order);
	}
	public void updateKitchenRemoveItem(Item item) {
		gui.updateKitchenRemoveItem(item);
	}

}
