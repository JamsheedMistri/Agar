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
	static int cooldown = 25;

	static Player p = new Player("North Korea");


	public static void main(String[] args) {
		Window.size(800, 600);
		Window.setFrameRate(30);



		final ArrayList<Player> players = new ArrayList<Player>();

		ArrayList <Blob> blobs = new ArrayList <Blob> ();


		for (int i = 0 ; i < 2000 ; i++) {
			blobs.add(new Blob());
		}

		server.child("online").child("North Korea").setValue(true);
		server.child("online").child("North Korea").onDisconnect().setValue(false);

		p = new Player("North Korea");

		server.child("online").addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildAdded(DataSnapshot data, String _) {
				String name = data.getKey();
				players.add(new Player(name));
				if (name.equals(p.name)) {
					p = players.get(players.size() - 1);
				}

			}

			@Override
			public void onChildChanged(DataSnapshot data, String _) {
				String name = data.getKey();
				if ((Boolean) data.getValue()) {
					System.out.println(name + " is online.");
				}
				else {
					System.out.println(name + " is no longer online.");
					server.child(name).removeValue();
				}
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot data) {
				String name = data.getKey();
			}

		});



		while (true) {
			Window.out.background(240,240,240);
			drawGrid();



			for (int i = 0; i < players.size(); i++) {
				if (!players.get(i).name.equals(p.name)) {
					players.get(i).draw(p.x, p.y, p.scale);
				}

				if (p.checkCollision(players.get(i))) {
					if (p.radius > players.get(i).radius) {
						p.radius += (int) players.get(i).radius / 4;
						players.remove(i);
						i--;
					}
					else 
						if (p.radius < players.get(i).radius){
							p.x = Window.rollDice(10000);
							p.y = Window.rollDice(10000);
							p.radius = 20;
							p.scale = 1;
							p.setValues();
							server.child("online/North Korea").removeValue();
						}
				}
			}

			p.draw();

			if (p.radius > 100 && cooldown >= 25) {
				p.radius = (int) (p.radius - (p.radius * .01));
				cooldown = 0;
			}

			for (int i = 0 ; i < blobs.size() ; i++) {
				blobs.get(i).draw(p.x, p.y, p.scale);

				if (p.checkCollision(blobs.get(i))) {
					blobs.get(i).reset();
					blobs.get(i).setValues();
					p.radius += 1;
					i--;
				}
			}

			if (p.x > 9600) {
				Window.out.color("black");
				//Window.out.square(10800 - p.x, 400, 800);
			}

			p.move();

			if (blobs.size() < 5000) {
				blobs.add(new Blob());
			}


			p.setValues();

			cooldown++;

			Window.frame();
		}
	}

	public static void drawGrid() {
		for (int i = 0; i < 10000; i += 31) {
			for (int j = 0; j < 10000; j += 31) {
				if (Math.abs(i - p.x) <= (Window.width() / 2) * p.scale + 25  &&
						Math.abs(j - p.y) <= (Window.height() / 2) * p.scale + 25) {
					Window.out.color("white");
					Window.out.square(Window.width() / 2 + (i - 2 - p.x) / p.scale,
							Window.height() / 2 + (j - 3 - p.y) / p.scale, 30 / p.scale);
				}
			}

		}
	}

}
