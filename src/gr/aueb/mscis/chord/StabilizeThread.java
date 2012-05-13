package gr.aueb.mscis.chord;

public class StabilizeThread extends Thread {

	Node node;
	
	public StabilizeThread(Node thisnode)
	{
		node = thisnode;
	}
	
	public void run()
	{
		while (!this.interrupted())
		{
			node.stabilize();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
}
