package Agar;

import java.util.ArrayList;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import apcs.Window;



public class Game {
	
	static Firebase server = new Firebase("https://agarjava.firebaseio.com/");
	static int ballNumber = 0;

	public static void main(String[] args) {
		Window.size(800, 600);
		Window.setFrameRate(30);



		ArrayList<Player> players = new ArrayList<Player>();

		server.child("online").child("Mistri").setValue(true);
		server.child("online").child("Mistri").onDisconnect().setValue(false);

		Player p = new Player("Mistri");

		server.child("online").addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildAdded(DataSnapshot data, String _) {
				String name = data.getKey();
				players.add(new Player(name));
				
			}

			@Override
			public void onChildChanged(DataSnapshot data, String _) {
				String name = data.getKey();
				if ((Boolean) data.getValue()) {
					System.out.println(name + " is online");
				}
				else {
					System.out.println(name + " is not online");
				}
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub

			}

		});

		server.child("Mistrix").setValue(p.x);
		server.child("Mistriy").setValue(p.y);
		server.child("Mistrir").setValue(p.radius);
		server.child("Mistrin").setValue(p.name);

		ArrayList <Blob> blobs = new ArrayList <Blob> ();


		for (int i = 0 ; i < 5000 ; i++) {
			blobs.add(new Blob());
		}


		while (true) {
			Window.out.background("white");

			for (int i = 0; i < players.size(); i++) {
				players.get(i).draw(p.x, p.y);
				
				if (p.checkCollision(players.get(i))) {
					if (p.radius > players.get(i).radius) {
						p.radius = (int) Math.sqrt(p.radius * p.radius + players.get(i).radius * players.get(i).radius);
						players.remove(i);
						i--;
					}
					else if (p.radius < players.get(i).radius){
						p.x = Window.rollDice(10000);
						p.y = Window.rollDice(10000);
						p.radius = 20;
						server.child("Mistrix").setValue(p.x);
						server.child("Mistriy").setValue(p.y);
						server.child("Mistrir").setValue(p.radius);
					}
				}
			}

			p.draw();
			
			if (p.radius > 100) {
				p.radius = (int) (p.radius - (p.radius * .01));
			}

			for (int i = 0 ; i < blobs.size() ; i++) {
				blobs.get(i).draw(p.x, p.y);
				
				if (p.checkCollision(blobs.get(i))) {
					blobs.remove(i);
					p.radius += 1;
					i--;
				}
			}

			if (p.x > 9600) {
				Window.out.color("black");
				Window.out.square(10800 - p.x, 400, 800);
			}

			p.move();
			
			if (blobs.size() < 5000) {
				blobs.add(new Blob());
			}
			

			server.child("Mistrix").setValue(p.x);
			server.child("Mistriy").setValue(p.y);
			server.child("Mistrir").setValue(p.radius);

			Window.frame();
		}
	}

}
