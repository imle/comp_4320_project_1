import enums.ContentType;
import enums.Method;
import packet.StreamReceiver;
import packet.StreamSender;
import support.header.HttpRequestHeader;
import support.header.HttpResponseHeader;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


class UDPServer extends UDPBase {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER);

		//noinspection InfiniteLoopStatement
		while (true) {
			StreamReceiver streamReceiver = new StreamReceiver();
			DatagramPacket receivePacket = streamReceiver.receiveSinglePacketAsString(serverSocket);
			String requestLine = streamReceiver.datagramPacketToString(receivePacket);

			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			HttpRequestHeader httpRequestHeader = HttpRequestHeader.fromString(requestLine);

			System.out.println("SERVER RECEIVED:\n" + httpRequestHeader.toString().trim());

			if (httpRequestHeader.getMethod() == Method.GET) {
				StreamSender streamSender = new StreamSender(true);

				File file = new File(httpRequestHeader.getUri().getPath());

				try {
					FileInputStream fileInputStream = new FileInputStream(file);

					HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
					httpResponseHeader.setContentType(ContentType.TEXT_HTML);
					httpResponseHeader.setContentLength(file.length());

					String response = httpResponseHeader.toString();

					streamSender.addInputStream(new ByteArrayInputStream(response.getBytes()));
					streamSender.addInputStream(fileInputStream);
					streamSender.sendStreams(serverSocket, IPAddress, port);
				} catch (FileNotFoundException ignored) {
					HttpResponseHeader httpResponseHeader = new HttpResponseHeader(HttpResponseHeader.Status.NOT_FOUND);

					String response = httpResponseHeader.toString();

					streamSender.addInputStream(new ByteArrayInputStream(response.getBytes()));
					streamSender.sendStreams(serverSocket, IPAddress, port);
				}

				try {
					Thread.sleep(0, 50);
				} catch (InterruptedException ignored) {
				}

				byte[] sendData = new byte[1];
				sendData[0] = 0;

				serverSocket.send(new DatagramPacket(sendData, sendData.length, IPAddress, port));
			}

			System.out.println("SERVER PROCESSED");
		}
	}
}
