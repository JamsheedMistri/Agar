package Agar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import apcs.Window;

public class Blob {
	int x;
	int y;

	int r;
	int g;
	int b;

	int radius;

	public Blob() {
		x = Window.rollDice(10000);
		y = Window.rollDice(10000);
		radius = 5;

		r = Window.rollDice(255);
		g = Window.rollDice(255);
		b = Window.rollDice(255);

		setValues();
		Game.ballNumber++;
	}

	public void setValues() {

		Game.server.child("ball").child("ballx"+Game.ballNumber).setValue(x);
		Game.server.child("ball").child("bally"+Game.ballNumber).setValue(y);
		Game.server.child("ball").child("ballr"+Game.ballNumber).setValue(radius);
	}
	
	public void reset() {
		x = Window.rollDice(10000);
		y = Window.rollDice(10000);
	}

	public void draw(int xoffset, int yoffset, int scale) {
		if (Math.abs(x - xoffset) <= (Window.width() / 2) * scale && Math.abs(y - yoffset) <= (Window.height() / 2) * scale) {
			Window.out.color(r, g, b);
			int viewRadius = radius / scale;
			if (viewRadius <= 0) {
				viewRadius = 1;
			}
			Window.out.circle(Window.width() / 2 + (x - xoffset)/scale
					, Window.height() / 2 + (y - yoffset)/scale, radius/scale);
		}
	}
}
