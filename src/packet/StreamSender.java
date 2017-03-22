package packet;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamSender {
	private static final int DEFAULT_SIZE = 128;

	List<InputStream> streams;

	private int packetSize;

	private int packetSizeChecksum;
	private int packetSizeSequence;
	private int packetSizeHeader;
	private int packetSizeData;

	private boolean enableLogging;

	public StreamSender() {
		this(false, DEFAULT_SIZE);
	}

	public StreamSender(boolean enableLogging) {
		this(enableLogging, DEFAULT_SIZE);
	}

	public StreamSender(boolean enableLogging, int size) {
		this.streams = new ArrayList<>();
		this.packetSize = size;
		this.enableLogging = enableLogging;

		int sizeChecksum = (int) (Math.log(Math.pow(2, Byte.SIZE) * this.packetSize) / Math.log(2));
		this.packetSizeChecksum = (int) (Math.ceil((double) sizeChecksum / Byte.SIZE));

		this.packetSizeSequence = Integer.SIZE / Byte.SIZE;

		this.packetSizeHeader = this.packetSizeChecksum + this.packetSizeSequence;
		this.packetSizeData = this.packetSize - this.packetSizeHeader;
	}

	public void addInputStream(InputStream inputStream) {
		this.streams.add(inputStream);
	}

	public void sendStreams(DatagramSocket socket, InetAddress IPAddress, int port) throws IOException {
		byte[] bytes = new byte[this.packetSize];

		Iterator<InputStream> inputStreamIterator = this.streams.iterator();
		InputStream inputStream = inputStreamIterator.next();

		int sequenceNumber = 0;
		int length;

		while (inputStream != null) {
			length = inputStream.read(bytes, this.packetSizeHeader, this.packetSizeData);

			if (length == -1) {
				if (!inputStreamIterator.hasNext()) {
					break;
				}

				inputStream = inputStreamIterator.next();
				length = inputStream.read(bytes, this.packetSizeHeader, this.packetSizeData);
			}

			if (length < this.packetSizeData) {
				if (inputStreamIterator.hasNext()) {
					inputStream = inputStreamIterator.next();
					length += inputStream.read(bytes, this.packetSizeHeader + length, this.packetSizeData - length);
				}
				else {
					inputStream = null;
				}
			}

			this.fillHeader(bytes, length, sequenceNumber++);

			int totalLength = this.packetSizeHeader + length;

			DatagramPacket sendPacket = new DatagramPacket(bytes, totalLength, IPAddress, port);
			socket.send(sendPacket);

			if (this.enableLogging) {
				System.out.print("\rSENT PACKETS: " + sequenceNumber);

				try {
					Thread.sleep(0, 10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		if (this.enableLogging) {
			System.out.println();
		}
	}

	private void fillHeader(byte[] bytes, int length, int sequenceNumber) {
		long checksum = this.calculateChecksum(bytes, length);

		for (int i = 0; i < this.packetSizeChecksum; i++) {
			bytes[i] = (byte) (checksum >> (Byte.SIZE * i) & 0xFF);
		}

		for (int i = 0; i < this.packetSizeSequence; i++) {
			bytes[i + this.packetSizeChecksum] = (byte) (sequenceNumber >> (Byte.SIZE * i) & 0xFF);
		}
	}

	private long calculateChecksum(byte[] bytes, int length) {
		long checksum = 0;

		for (int i = 0; i < length; i++) {
			byte b = bytes[i + this.packetSizeHeader];
			checksum += b;
		}

		return checksum;
	}
}
