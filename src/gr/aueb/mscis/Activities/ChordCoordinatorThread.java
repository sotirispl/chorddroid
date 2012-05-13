package gr.aueb.mscis.Activities;

import gr.aueb.mscis.chord.Node;
import gr.aueb.mscis.chord.RemoteMessageAgent;
import gr.aueb.mscis.protocol.ProtocolType;
import gr.aueb.mscis.protocol.RemoteMessage;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/*pros to paron to service kanei tin sundesi me ton tracker*/
public class ChordCoordinatorThread extends Thread {
	
	//private static Node node;
	public static Thread t1;
 
	
	

//	@Override
//	public void onStart(Intent intent, int startid) {
//		//setNode(new Node());
//		Thread t1 = new Thread(setNode(new Node()));
//        t1.start();
//
//	}
	

//    public static Runnable setNode(Node node) {
//           return  ChordCoordinatorThread.node = node;
//    }
//
//    public static Node getNode() {
//            return node;
//    }
//    
    
    public void run() {
    	// Create the object with the run() method
    	//node = new Node();
    	Runnable runnable = new Node();
    	// Create the thread supplying it with the runnable object
    	Thread thread = new Thread(runnable);
    	// Start the thread
    	thread.start();
    }
    
}
