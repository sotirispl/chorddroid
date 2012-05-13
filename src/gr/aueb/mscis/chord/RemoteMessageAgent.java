package gr.aueb.mscis.chord;

import gr.aueb.mscis.Activities.ContentNodesActivity;
import gr.aueb.mscis.configuration.Config;
import gr.aueb.mscis.protocol.NodeIdentifier;
import gr.aueb.mscis.protocol.ProtocolType;
import gr.aueb.mscis.protocol.RemoteMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class RemoteMessageAgent {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	/* oi times autes fortwnontai apo tin Config class */
	// private String trackerAddr = "192.168.1.3";
	// private String trackerAddr = "localhost";
	// private int trackerPort = 8081;

	public RemoteMessage register() {
		Socket s = null;
		RemoteMessage response = null;
		try {
			s = new Socket(Config.trackerAddr, Config.trackerPort);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			/*
			 * elegxos gia to an to port pou exei eisax8ei einai egguro, alliws
			 * epilegetai to default automata
			 */
			if (Config.listeningPort > 65535 || Config.listeningPort < 1000) {
				Config.listeningPort = Config.defaultPort;
			}

			/*
			 * dimiourgia node indentifier pou 8a exei mesa to port pou 8a
			 * akouei o kombos.Tin IP tha tin parei o tracker apo to socket
			 */
			/*
			 * to port to exei eisagei o xristis. To listening port einai global
			 * metabliti pou brisketai stin class RemoteMessageServer
			 */
			NodeIdentifier node = new NodeIdentifier(Config.listeningPort);
			// outgoing stream redirect to socket
			oos = new ObjectOutputStream(s.getOutputStream());
			/* mazi me to register stelnetai kai to port pou 8a akouei o kombos */
			oos.writeObject(new RemoteMessage(ProtocolType.REGISTER, null, node));

			ois = new ObjectInputStream(s.getInputStream());
			response = (RemoteMessage) ois.readObject();

			s.close();

		} catch (SocketException e) {
			ContentNodesActivity.statusText.setText("error register");
			Log.v("register", e.getMessage());

			// System.out.println("Node " + s.getInetAddress() + ":" +
			// s.getPort()
			// + " disconnected");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	
	
	/*sundesi ston komvo pou apantise o tracker gia na parei pisw o komvos apantisi gia to poios einai o succesor tou*/
	public RemoteMessage findSuccessor(RemoteMessage rm){
		
		Socket s = null;
		int port = rm.getNodeId().getPort();
		InetAddress ipAddr = rm.getNodeId().getAddr();
		RemoteMessage response = null;
		try {
			Log.v("ip:port", ipAddr +":"+port);
			s = new Socket(ipAddr.toString(), port);
			s.setSoTimeout(3000);
		} catch (SocketException e) {
			ContentNodesActivity.statusText.setText("1Couldn't connect to: "+ipAddr.toString()+":"+port);
			return response;
			//Log.v("register", e.getMessage());

			// System.out.println("Node " + s.getInetAddress() + ":" +
			// s.getPort()
			// + " disconnected");.ConnectException
		}  catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return response;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return response;
		}
		
		// outgoing stream redirect to socket
		try{
		oos = new ObjectOutputStream(s.getOutputStream());
		/* mazi me to register stelnetai kai to port pou 8a akouei o kombos */
		oos.writeObject(new RemoteMessage(ProtocolType.FIND_SUCCESSOR, null, rm.getNodeId()));

		ois = new ObjectInputStream(s.getInputStream());
		response = (RemoteMessage) ois.readObject();
		
		if (response.getProtocolType().equals(ProtocolType.FIND_SUCCESSOR_REPLY)) {
			ContentNodesActivity.statusText.setText("connect to: "+ response.getNodeId().getAddr());
		}

		s.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ContentNodesActivity.statusText.setText("2Couldn't connect to: "+ipAddr.toString()+":"+port);
//		} catch (SocketException e) {
//			ContentNodesActivity.statusText.setText("error");
//			//Log.v("register", e.getMessage());
//
//			// System.out.println("Node " + s.getInetAddress() + ":" +
//			// s.getPort()
//			// + " disconnected");
//		} catch (InterruptedIOException iioe)
//		{ ContentNodesActivity.statusText.setText("Timeout to: "+ response.getNodeId().getAddr());
//		}catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return response;
		
	}
	
}
