package gr.aueb.mscis.chord;

import gr.aueb.mscis.protocol.ProtocolType;
import gr.aueb.mscis.protocol.RemoteMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class RemoteMessageDaemonThread extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private Socket socket = null;

	public RemoteMessageDaemonThread(Socket socket) {
		super("RemoteMessageDaemonThread");
		this.socket = socket;
	}

	public void run() {
		listen();
	}

	public void listen() {
		
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (StreamCorruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// while (true) {
			RemoteMessage message = (RemoteMessage) ois.readObject();
			RemoteMessage reply = null;

			// respond to synchronous calls
			if (message.getProtocolType().equals(ProtocolType.FIND_SUCCESSOR)) {
				oos = new ObjectOutputStream(socket.getOutputStream());
				/*8elei ulopoiisi ayti.8a prepei apo ta finger tables na psaxnei 
				 * to ton komvo pou zitaei gia successor o aitoumenos*/
				//reply = findSuccessor(message);
				
				/*prokataskevasmeni apantisi gia testing*/
				RemoteMessage rm = new RemoteMessage(ProtocolType.FIND_SUCCESSOR_REPLY, null, null);
				oos.writeObject(rm);
				oos.close();
			}
			// else if allo minima klp
			// }
			if (socket != null) {
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
