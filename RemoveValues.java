package Agar;

import com.firebase.client.Firebase;

public class RemoveValues {

	static Firebase server = new Firebase("https://agarjava.firebaseio.com/");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		server.removeValue();
		
		while(true);
	}

}
