package packet;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class StreamReceiver {
	protected static final int DEFAULT_SIZE = 128;

	protected int packetSize;

	protected int packetSizeChecksum;
	protected int packetSizeSequence;
	protected int packetSizeHeader;
	protected int packetSizeData;

	protected Map<Integer, Long> invalidPackets;

	public StreamReceiver() {
		this(DEFAULT_SIZE);
	}

	public StreamReceiver(int size) {
		this.packetSize = size;

		int sizeChecksum = (int) (Math.log(Math.pow(2, Byte.SIZE) * this.packetSize) / Math.log(2));
		this.packetSizeChecksum = (int) (Math.ceil((double) sizeChecksum / Byte.SIZE));

		this.packetSizeSequence = Integer.SIZE / Byte.SIZE;

		this.packetSizeHeader = this.packetSizeChecksum + this.packetSizeSequence;
		this.packetSizeData = this.packetSize - this.packetSizeHeader;
	}

	public InputStream receivePackets(DatagramSocket socket) throws IOException {
		byte[] bytes = new byte[this.packetSize];

		this.invalidPackets = new LinkedHashMap<>();

		Map<Integer, String> integerStringMap = new HashMap<>();

		for (; ; ) {
			DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);

			socket.receive(receivePacket);

			if (receivePacket.getLength() == 1 && bytes[0] == 0) {
				break;
			}

			int sequenceNumber = this.readPacketSequenceNumber(bytes);

			if (!this.validatePacket(bytes, receivePacket.getLength())) {
				this.invalidPackets.put(sequenceNumber, this.readChecksum(bytes));

				continue;
			}

			int length = receivePacket.getLength() - this.packetSizeHeader;

			integerStringMap.put(sequenceNumber, (new String(bytes)).substring(this.packetSizeHeader, length));
		}

		String string = "";

		for (int i = 0; i < integerStringMap.size(); i++) {
			string += integerStringMap.get(i);
		}

		return new ByteArrayInputStream(string.getBytes());
	}

	public DatagramPacket receiveSinglePacketAsString(DatagramSocket socket) throws IOException {
		byte[] bytes = new byte[this.packetSize];

		DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);

		socket.receive(receivePacket);

		return receivePacket;
	}

	public String datagramPacketToString(DatagramPacket datagramPacket) {
		byte[] bytes = datagramPacket.getData();

		String value = new String(bytes);

		return value.substring(this.packetSizeHeader, datagramPacket.getLength());
	}

	protected int readPacketSequenceNumber(byte[] input) {
		int sequenceNumber = 0;

		for (int i = this.packetSizeSequence - 1; i >= 0; i--) {
			sequenceNumber <<= Byte.SIZE;
			sequenceNumber |= Byte.toUnsignedInt(input[i + this.packetSizeChecksum]);
		}

		return sequenceNumber;
	}

	public int getInvalidPacketCount() {
		return invalidPackets.size();
	}

	protected boolean validatePacket(byte[] input, int length) {
		long checksum = readChecksum(input);

		long checksumCalculated = 0;

		for (int i = this.packetSizeHeader; i < length; i++) {
			byte b = input[i];
			checksumCalculated += b;
		}

		return checksum == checksumCalculated;
	}

	protected long readChecksum(byte[] input) {
		long checksum = 0;

		for (int i = this.packetSizeChecksum - 1; i >= 0; i--) {
			checksum <<= Byte.SIZE;
			checksum |= Byte.toUnsignedInt(input[i]);
		}

		return checksum;
	}

	public Iterator<Map.Entry<Integer, Long>> getInvalidPacketsIterator() {
		return this.invalidPackets.entrySet().iterator();
	}
}
