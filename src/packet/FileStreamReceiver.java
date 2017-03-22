package packet;

import support.Gremlin;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedHashMap;

public class FileStreamReceiver extends StreamReceiver {
	private RandomAccessFile randomAccessFile;

	private Gremlin gremlin;

	public FileStreamReceiver(File file, Gremlin gremlin) throws FileNotFoundException {
		this(file, gremlin, DEFAULT_SIZE);
	}

	public FileStreamReceiver(File file, Gremlin gremlin, int size) throws FileNotFoundException {
		super(size);

		//noinspection ResultOfMethodCallIgnored
		file.delete();

		this.gremlin = gremlin;
		this.randomAccessFile = new RandomAccessFile(file, "rw");
	}

	public InputStream receivePackets(DatagramSocket socket) throws IOException {
		byte[] bytes = new byte[this.packetSize];

		this.invalidPackets = new LinkedHashMap<>();

		for (; ; ) {
			DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);

			socket.receive(receivePacket);

			if (receivePacket.getLength() == 1 && bytes[0] == 0) {
				break;
			}

			gremlin.damagePacket(bytes);

			int sequenceNumber = this.readPacketSequenceNumber(bytes);

			if (!this.validatePacket(bytes, receivePacket.getLength())) {
				this.invalidPackets.put(sequenceNumber, this.readChecksum(bytes));
			}

			int length = receivePacket.getLength() - this.packetSizeHeader;

			this.randomAccessFile.seek(sequenceNumber * this.packetSizeData);
			this.randomAccessFile.write(bytes, this.packetSizeHeader, length);
		}

		this.randomAccessFile.seek(0);
		return new FileInputStream(this.randomAccessFile.getFD());
	}
}
