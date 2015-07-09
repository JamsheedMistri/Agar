package Agar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import apcs.Window;

public class Player {

	int x, y, radius;
	int r, g, b;
	//int dx, dy;
	String name;

	public Player(String name) {
		x = Window.rollDice(10000);
		y = Window.rollDice(10000);
		r = Window.rollDice(256) - 1;
		g = Window.rollDice(256) - 1;
		b = Window.rollDice(256) - 1;
		this.name = name;
		radius = 20;
		addListeners();

	}

	public void addListeners() {
		Game.server.child(name+"x").addValueEventListener(new ValueEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDataChange(DataSnapshot data) {
				// TODO Auto-generated method stub
				if (data.getValue() != null) {
					long x2 = (Long) data.getValue();
					x = (int) x2;
				}
			}

		});

		Game.server.child(name+"y").addValueEventListener(new ValueEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDataChange(DataSnapshot data) {
				// TODO Auto-generated method stub
				if (data.getValue() != null) {
					long y2 = (Long) data.getValue();
					y = (int) y2;
				}
			}

		});

		Game.server.child(name+"r").addValueEventListener(new ValueEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDataChange(DataSnapshot data) {
				// TODO Auto-generated method stub
				if (data.getValue() != null) {
					long r2 = (Long) data.getValue();
					radius = (int) r2;
				}
			}

		});

		Game.server.child(name+"n").addValueEventListener(new ValueEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDataChange(DataSnapshot data) {
				// TODO Auto-generated method stub
				if (data.getValue() != null) { 
					name = (String) data.getValue();
				}
			}

		});

	}

	public void draw() {
		
		Window.out.color(r, g, b);
		Window.out.circle(Window.width() / 2, Window.height() / 2, radius);
		Window.out.color("black");
		Window.out.print(name, Window.width()/2, Window.height()/2);
		
		Window.out.print(x, Window.width()/2, Window.height()/2 + 20);
		Window.out.print(y, Window.width()/2, Window.height()/2 + 40);
		Window.out.print(radius, Window.width()/2, Window.height()/2 + 60);
	}

	public void draw(int xoffset, int yoffset) {
		Window.out.color(r, g, b);
		Window.out.circle(Window.width() / 2 + (x - xoffset), Window.height() / 2 + (y - yoffset), radius);
		Window.out.color("black");
		Window.out.print(name, Window.width() / 2 + (x - xoffset), Window.height() / 2 + (y - yoffset));
	}

	public void move() {
		// Get the raw difference i
		int dx = Window.mouse.getX() - Window.width() / 2;
		int dy = Window.mouse.getY() - Window.height() / 2;

		double magnitude = Math.sqrt(dx * dx + dy * dy);

		if (magnitude > 10) {
			dx = (int) (dx * 10 / magnitude);
			dy = (int) (dy * 10 / magnitude);
		}

		x = x + dx;
		y = y + dy;
	}

	public boolean checkCollision(Blob blob) {

		int a = x - blob.x;
		int b = y - blob.y;
		int c = radius + blob.radius;

		if (a * a + b * b < c * c) {
			return true;
		}


		return false;
	}
	
	public boolean checkCollision(Player p) {
		if (p == this) {
			return false;
		}
		
		int a = x - p.x;
		int b = y - p.y;
		int c = radius + p.radius;

		if (a * a + b * b < c * c) {
			return true;
		}


		return false;
	}
}
