package Game;

import java.net.*;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Game extends Applet implements Runnable, KeyListener {
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;

	boolean up;
	boolean down;
	boolean left;
	boolean right;

	int playerid;
	int playerx;
	int playery;
	int[] x = new int[4];
	int[] y = new int[4];

	public void init() {
		setSize(150, 150);
		try {
			System.out.println("Connecting");
			addKeyListener(this);
			socket = new Socket("localhost", 7777);
			System.out.println("Connected");
			in = new DataInputStream(socket.getInputStream());
			playerid = in.readInt();
			out = new DataOutputStream(socket.getOutputStream());
			Input input = new Input(in, this);
			Thread thread = new Thread(input);
			thread.start();
			Thread thread2 = new Thread(this);
			thread2.start();
		} catch (Exception e) {
			System.out.println("Cannot Connect Due To Errors");
		}

	}

	public void updateCoordinates(int pid, int x, int y) {
		this.x[pid] = x;
		this.y[pid] = y;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < 4; i++) {
			g.drawOval(x[i], y[i], 10, 10);
			repaint();
			
		}
	}

	public void run() {
		while (true) {
			if (right == true) {
				playerx += 10;
			}
			if (left == true) {
				playerx -= 10;
			}
			if (up == true) {
				playery -= 10;
			}
			if (down == true) {
				playery += 10;
			}
			
			if(right || left || up || down) 
			{
				try {
				out.writeInt(playerid);
				out.writeInt(playerx);
				out.writeInt(playery);
				}catch(Exception e) {
					System.out.println("Error Sending Coordinates");
				}
			}
			
			
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			left = true;
		}
		if (e.getKeyCode() == 38) {
			up = true;
		}
		if (e.getKeyCode() == 39) {
			right = true;
		}
		if (e.getKeyCode() == 40) {
			down = true;
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			left = false;
		}
		if (e.getKeyCode() == 38) {
			up = false;
		}
		if (e.getKeyCode() == 39) {
			right = false;
		}
		if (e.getKeyCode() == 40) {
			down = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}
}