package gr.aueb.mscis.protocol;

public enum ProtocolType {
	JOIN_RING, 
	CREATE_RING, 
	REGISTER, 
	ALREADY_IN_RING,  //gia na mi mporei enas kombos na prospa8ei synexeia na synde8ei ston tracker kai na mpainei allou sto ring
	FIND_SUCCESSOR   /*o komvos 8a kanei find successor gia na apantsei sto neo komvo se poion na paei na balei successor tou*/, 
	FIND_SUCCESSOR_REPLY   /*apantisi me ton komvo successor tou arxikou aitountos*/
        
}
