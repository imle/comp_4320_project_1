import enums.Method;
import packet.FileStreamReceiver;
import packet.StreamReceiver;
import packet.StreamSender;
import support.Gremlin;
import support.header.HttpRequestHeader;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

class UDPClient extends UDPBase {
	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			throw new IllegalArgumentException("UDPClient takes exactly 2 arguments.");
		}

		String hostname = args[0];
		float probabilityOfDamage = Float.parseFloat(args[1]);

		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(hostname);

		HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Method.GET, new URI("TestFile.html"));

		// Send header.
		StreamSender streamSender = new StreamSender();
		streamSender.addInputStream(new ByteArrayInputStream(httpRequestHeader.toString().getBytes()));
		streamSender.sendStreams(clientSocket, IPAddress, PORT_NUMBER);

		// Prepare receiver dependencies.
		File outputFile = new File("output.tmp");
		Gremlin gremlin = new Gremlin(probabilityOfDamage);

		// Receive packets.
		StreamReceiver streamReceiver = new FileStreamReceiver(outputFile, gremlin);
		InputStream inputStream = streamReceiver.receivePackets(clientSocket);

		// Print damage stats to STDOUT.
		System.out.printf("TOTAL DAMAGED PACKETS: %d%n%n", streamReceiver.getInvalidPacketCount());

		for (Iterator<Map.Entry<Integer, Long>> it = streamReceiver.getInvalidPacketsIterator(); it.hasNext(); ) {
			Map.Entry<Integer, Long> damagedPacket = it.next();

			System.out.printf("DAMAGED PACKET: %d (%d)%n", damagedPacket.getKey(), damagedPacket.getValue());
		}

		System.out.println();

		// Read file and print to STDOUT.
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();

		System.out.println();

		clientSocket.close();
	}
}
