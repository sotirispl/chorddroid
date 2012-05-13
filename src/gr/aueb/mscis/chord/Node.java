/*
 * antiproswpevei ton komvo tou Chord
 */
package gr.aueb.mscis.chord;

import gr.aueb.mscis.Activities.ContentNodesActivity;
import gr.aueb.mscis.protocol.NodeIdentifier;
import gr.aueb.mscis.protocol.ProtocolType;
import gr.aueb.mscis.protocol.RemoteMessage;

import java.io.Serializable;

import android.os.Message;

public class Node implements Runnable,Serializable { 

	private static final long serialVersionUID = 1L;
	private NodeIdentifier successor;
	private NodeIdentifier predecessor;
	private NodeIdentifier nodeId;
	private RemoteMessageDaemon rDaemon;
	private String key; /* to hash tou komvou.De to exw xrisimopoiisei akoma */
	public Node() {}
	
	public void NodeRun() {
		if(register()){

		// threads edw gia ta upoloipa.RemoteDaemon, Agent DHT klp..
		rDaemon = new RemoteMessageDaemon();
		Thread rDeamonThread = new Thread(rDaemon);
		rDeamonThread.start();
		}		
		
		else {}

	}

	public NodeIdentifier getNodeId() {
		return nodeId;
	}

	public void setNodeId(NodeIdentifier nodeId) {
		this.nodeId = nodeId;
	}

	/* register tou node sto tracker */
	private boolean register() {
		RemoteMessageAgent connect = new RemoteMessageAgent();
		RemoteMessage response = connect.register();
		
		if(response == null){
			ContentNodesActivity.statusText.setText("Couldn't connect"); 		
			return false;
		}
		
		/* emfanizei ston node ti minima elave apo ton tracker */
		if (response.getProtocolType().equals(ProtocolType.CREATE_RING)) {
			//ContentNodesActivity.statusText.setText("Received: create");
			    Message msg = ContentNodesActivity.handlerGlobal.obtainMessage();
			   // msg.what = UPDATE_IMAGE;
			    msg.obj = "Received: create";
			   // msg.arg1 = index;
			    ContentNodesActivity.handlerGlobal.sendMessage(msg);
			/*
			 * o tracker apantaei ston komvo na kanei create kai tou stelnei ta
			 * stoixeia tou komvou
			 */
			nodeId = response.getNodeId();
			key = nodeId.getHash();
		/* dimiourgia ring */
			create(); 
			return true;
			
		} else if (response.getProtocolType().equals(ProtocolType.JOIN_RING)) {
			 Message msg = ContentNodesActivity.handlerGlobal.obtainMessage();
			   // msg.what = UPDATE_IMAGE;
			    msg.obj = "Received: join";
			   // msg.arg1 = index;
			    ContentNodesActivity.handlerGlobal.sendMessage(msg);
			/* join sto ring apo ton komvo pou 8a apantisei o tracker. Exei bre8ei o tuxaios komvos, o 
			 * opoios 8a apantisei pisw to succesor pou tou neou komvou gia eisagwgi sto ring */
			
			joinRing(response); 
			return true;
			
		} else if (response.getProtocolType().equals(
				ProtocolType.ALREADY_IN_RING)) {
			//ContentNodesActivity.statusText.setText("Received: already in ring");
			 Message msg = ContentNodesActivity.handlerGlobal.obtainMessage();
			   // msg.what = UPDATE_IMAGE;
			    msg.obj = "Received:already in ring";
			   // msg.arg1 = index;
			    ContentNodesActivity.handlerGlobal.sendMessage(msg);
			/* do nothing */ return false;
		}
		return false;
	}

	/* dimiourgia ring */
	private void create() {
		successor = this.getNodeId();
		predecessor = null;
	}

	/* join sto ring apo ton komvo pou 8a apantisei o tracker. Exei bre8ei o tuxaios komvos, o 
	 * opoios 8a apantisei pisw to succesor pou tou neou komvou gia eisagwgi sto ring
	 * Ara stelnw mnm ston komvo pou apantise o tracker gia na mou kanei find_succesor(key_mou) */
	private void joinRing(RemoteMessage rm) {
		RemoteMessageAgent connect = new RemoteMessageAgent();
		RemoteMessage response = connect.findSuccessor(rm);
		if(response == null){
			//ContentNodesActivity.statusText.setText("Couldn't connect to:" + response.getNodeId().getAddr() +":"+ response.getNodeId().getPort());
			 Message msg = ContentNodesActivity.handlerGlobal.obtainMessage();
			   // msg.what = UPDATE_IMAGE;
			    msg.obj = "Couldn't connect to:" + response.getNodeId().getAddr() +":"+ response.getNodeId().getPort();
			   // msg.arg1 = index;
			    ContentNodesActivity.handlerGlobal.sendMessage(msg);
		}
		else if(response.getProtocolType().equals(ProtocolType.FIND_SUCCESSOR_REPLY)){
			 Message msg = ContentNodesActivity.handlerGlobal.obtainMessage();
			   // msg.what = UPDATE_IMAGE;
			    msg.obj = "received successor";
			   // msg.arg1 = index; 
			    ContentNodesActivity.handlerGlobal.sendMessage(msg);
			
		}
			

	}

	public void run() {
		NodeRun();
		
		
	}
	
	public void stabilize() {
                NodeIdentifier x = null, actual_successor = this.getSuccessor();
                Boolean foundSuccessor = false;

                try {
                        // actual_successor = dummy_successor;
                        foundSuccessor = true;

                        // MSG to get Predecessor
                        //x = actual_successor.getPredecessor();
                }// try
                catch (NullPointerException npe) {
                        // An exei pesei o successor, pairnei ton epomeno apo th lista twn
                        // triwn
                        find_new_successor();
                }
                if (x != null) {
                        if (isBetween(x.getHash(), nodeId.getHash(), actual_successor.getHash())) {
                                this.setSuccessor(new NodeIdentifier(x.getAddr(), x.getPort()));
                        }
                        // else successor did not change
                }
                // else our successor doesn't have a predecessor

                try {
                        // MSG to notify
                        //actual_successor.notify(this.getNodeId());
                }
                catch (NullPointerException npe) {
                        // epese o successor, psaxnoume gia kainourio apo th lista me tous
                        // treis
                        find_new_successor();
                }
        }

        public NodeIdentifier getSuccessor() {
                return this.successor;
        }
        
        public void setSuccessor(NodeIdentifier newSuccessor)
        {
                this.successor=newSuccessor;
        }

        public NodeIdentifier getPredecessor() {
                return this.predecessor;
        }
        
        private void find_new_successor()
        {
        }
        
        public static boolean isBetween(String kID, String xID, String yID)
        {
                if(xID.equals(yID)) return true;
                
                return ( (((xID.compareTo(kID)) > 0) 
                                        && ((yID.compareTo(kID)) > 0) 
                                        && ((xID.compareTo(yID)) > 0))
                                
                                 ||
                                
                                 (((xID.compareTo(kID)) < 0) 
                                        && ((yID.compareTo(kID)) > 0) 
                                        && ((xID.compareTo(yID)) < 0))
                                
                                 ||
                                        
                                 (((xID.compareTo(kID)) < 0) 
                                        && ((yID.compareTo(kID)) < 0) 
                                        && ((xID.compareTo(yID)) > 0))
                                );
        }
}
