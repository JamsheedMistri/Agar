package Agar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Events implements ValueEventListener{

	long data;

	public Events() {
		
	}

	@Override
	public void onCancelled(FirebaseError arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDataChange(DataSnapshot arg0) {
		data = (Long) arg0.getValue();

	}

}
