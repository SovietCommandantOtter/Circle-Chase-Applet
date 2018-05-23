package Game;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;

	public static void main(String[] args) throws Exception {
		System.out.println("Connecting");
		socket = new Socket("localhost", 7777);
		System.out.println("Connection Established");
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		Input input = new Input(in);
		Thread thread = new Thread(input);
		thread.start();
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			String SendMessage = sc.nextLine();
			out.writeUTF(SendMessage);
		}
	}
}

class Input implements Runnable {
	DataInputStream in;

	public Input(DataInputStream in) {
		this.in = in;

	}

	public void run() {
		while (true) {
			String message;
			try {
				message = in.readUTF();
				System.out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
