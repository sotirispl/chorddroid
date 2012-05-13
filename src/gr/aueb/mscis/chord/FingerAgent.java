package gr.aueb.mscis.chord;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FingerAgent extends Thread{
	public void run(){
		int counter = 0;
		while(counter<6){
			
			BigInteger addition = (new BigDecimal(String.valueOf(Math.pow(2, (counter-1))))).toBigInteger(); //how much we will add to our ID.
			String targetID = Node.nodeId.getHash().toString((new BigInteger(Node.nodeId.getHash().getByteArray())).add(addition).toByteArray()); // this.ID() + addition.
			
			int kID = Integer.parseInt(Node.nodeId.getHash());
			Node.isBetween(targetID, Node.nodeId.getHash(), Node.successor.getHash());
			
			counter++;
		}
	}
}
