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
		
		Game.server.child("ballx"+Game.ballNumber).setValue(x);
		Game.server.child("bally"+Game.ballNumber).setValue(y);
		Game.server.child("ballr"+Game.ballNumber).setValue(radius);
	}
	
	public void draw(int xoffset, int yoffset) {
		Window.out.color(r, g, b);
		Window.out.circle(Window.width() / 2 + (x - xoffset), Window.height() / 2 + (y - yoffset), radius);
	}
}
