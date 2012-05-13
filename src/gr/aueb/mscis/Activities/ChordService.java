package gr.aueb.mscis.Activities;

import gr.aueb.mscis.chord.Node;
import gr.aueb.mscis.chord.RemoteMessageAgent;
import gr.aueb.mscis.protocol.ProtocolType;
import gr.aueb.mscis.protocol.RemoteMessage;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/*pros to paron to service kanei tin sundesi me ton tracker*/
public class ChordService extends Service {
	
	private static Node node;
	private Thread t1;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDestroy() {
		//t1.stop();
		setNode(null);
	}

	@Override
	public void onStart(Intent intent, int startid) {
		//setNode(new Node());
		Thread t1 = new Thread(setNode(new Node()));
        t1.start();

	}
	

    public static Runnable setNode(Node node) {
           return  ChordService.node = node;
    }

    public static Node getNode() {
            return node;
    }
}
