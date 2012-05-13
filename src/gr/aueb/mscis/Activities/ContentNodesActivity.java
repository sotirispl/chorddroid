package gr.aueb.mscis.Activities;

import gr.aueb.mscis.configuration.Config;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class ContentNodesActivity extends Activity implements OnClickListener {

	Button buttonReg;
	Button buttonLeave;
	private EditText edittext;
	String port = null;
	Intent serviceIntent;
	public static Handler handlerGlobal;

	public static TextView statusText; /*
										 * to xrisimopoiw gia na allazw timi apo
										 * to service sto text
										 */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_nodes_activity_layout);

		buttonReg = (Button) findViewById(R.id.register);
		buttonReg.setOnClickListener(this);

		buttonLeave = (Button) findViewById(R.id.leave);
		buttonLeave.setOnClickListener(this);
		buttonLeave.setEnabled(false);

		statusText = (TextView) findViewById(R.id.text_status);
		serviceIntent = new Intent(this, ChordCoordinatorThread.class);
	}

	public void onClick(View arg0) {

		if (arg0 == buttonReg) {
			buttonReg.setEnabled(false);
			buttonLeave.setEnabled(true);

			/* diabasma tou ari8mou tou port pou 8a akouei o node */
			edittext = (EditText) findViewById(R.id.editText);
			port = edittext.getText().toString();
			/* elegxos an to port den eisax8ei, eisagetai to default */
			if (port.equals("")) {
				port = Integer.toString(Config.defaultPort);
			}
			edittext.setEnabled(false);
			edittext.setText("port " + port + " selected");
			Config.listeningPort = Integer.parseInt(port);
			/* start tou service pou 8a trexei apo pisw. */
			
			
			
			
			final Handler handler = new Handler(){
		    	  @Override
		    	  public void handleMessage(Message msg) {
		    		  ContentNodesActivity.statusText.setText("idle" + msg.obj);
		    	    super.handleMessage(msg);
		    	  }
		    	};
		    	handlerGlobal = handler;
			
//////////////////////////////////////////////////////////////////////////////////			
/**/			/*ekkinisi tou coordinator thread*/
/**/		    ChordCoordinatorThread cct = new ChordCoordinatorThread();
/**/		    cct.start();
///////////////////////////////////////////////////////////////////////////////		        
			/*klisi tou thread anti tou service*/
			//startService(serviceIntent);
		} else if (arg0 == buttonLeave) {
			buttonReg.setEnabled(true);
			buttonLeave.setEnabled(false);

			edittext.setEnabled(true);
			edittext.setText(port);
			edittext.setFocusable(true);
			ChordCoordinatorThread.t1.stop();
			//stopService(serviceIntent);
			ContentNodesActivity.statusText.setText("idle"); 
		}
	}

}