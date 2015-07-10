package Agar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import apcs.Window;

public class Player {

	int x, y, radius;
	int r, g, b;
	int scale = 1;
	String name;

	public Player(String name) {
		x = Window.rollDice(10000);
		y = Window.rollDice(10000);
		r = Window.rollDice(256) - 1;
		g = Window.rollDice(256) - 1;
		b = Window.rollDice(256) - 1;
		this.name = name;
		radius = 20;
		setValues();
		addListeners();

	}
	
	public void setValues() {
		Game.server.child(name).child(name+"x").setValue(x);
		Game.server.child(name).child(name+"y").setValue(y);
		Game.server.child(name).child(name+"r").setValue(radius);
		Game.server.child(name).child(name+"n").setValue(name);
	}

	public void addListeners() {
		Game.server.child(name).child(name+"x").addValueEventListener(new ValueEventListener() {

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

		Game.server.child(name).child(name+"y").addValueEventListener(new ValueEventListener() {

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

		Game.server.child(name).child(name+"r").addValueEventListener(new ValueEventListener() {

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

		Game.server.child(name).child(name+"n").addValueEventListener(new ValueEventListener() {

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
		Window.out.circle(Window.width() / 2, Window.height() / 2, radius / scale);
		Window.out.color("black");
		Window.out.font("monospaced", 20);
		Window.out.print(name, Window.width()/2 - 40, Window.height()/2 - 20);
		
		Window.out.print("x: " + x, 20, 20);
		Window.out.print("y: " + y, 20, 40);
		Window.out.print("radius: " + radius, 20, 60);
	}

	public void draw(int xoffset, int yoffset) {
		Window.out.color(r, g, b);
		Window.out.circle(Window.width() / 2 + (x - xoffset), Window.height() / 2 + (y - yoffset), radius / scale);
		Window.out.color("black");
		Window.out.print(name, Window.width() / 2 + (x - xoffset) - 40, Window.height() / 2 + (y - yoffset) - 20);
	}

	public void move() {
		if (radius > 500) {
			radius = 500;
		}
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
		
		checkBorders();
	}

	public void checkBorders() {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x > 10000) {
			x = 10000;
		}
		if (y > 10000) {
			y = 10000;
		}
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
		if (name.equals(p.name)) {
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
