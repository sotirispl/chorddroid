package gr.aueb.mscis.chord;

import gr.aueb.mscis.configuration.Config;
import java.io.IOException;
import java.net.ServerSocket;

public class RemoteMessageDaemon extends Thread {

	ServerSocket serverSocket = null;
	boolean listening = true;
	private int serverPort = Config.listeningPort;

	public void run() {
				
		try {
			serverSocket = new ServerSocket(serverPort);
			// System.out.println("Tracker Started");
		} catch (IOException e) {
		//	System.err.println("Could not listen on port: " + serverPort + ".");
		//	System.exit(-1);
		}

		while (listening) {
			try {
				new RemoteMessageDaemonThread(serverSocket.accept()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("closed");
	}
}
