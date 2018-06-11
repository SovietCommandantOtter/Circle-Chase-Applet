package Game.net;

	import java.io.IOException;
	import java.net.DatagramPacket;
	import java.net.DatagramSocket;
	import java.net.InetAddress;
	import java.net.SocketException;
	import java.net.UnknownHostException;

	import Game.GameGame;

	public class GameServer extends Thread {

		private DatagramSocket socket;
		private GameGame game;

		public GameServer(GameGame game) {
			this.game = game;
			try {
				this.socket = new DatagramSocket(7777);
			} catch (SocketException e) {
				e.printStackTrace();
			}

		}

		public void run() {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			if (message.equalsIgnoreCase("ping")) {
			System.out.println("CLIENT >" + message );
			sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}

		public void sendData(byte[] data, InetAddress ipAddress, int port) 
		{
			DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
}
