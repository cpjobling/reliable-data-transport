/**
 * A packet
 */
package uk.ac.swan.eg253.rdt.layer5;

import java.util.Random;

class Packet {
	int seqNum;
	int ackNum;
	int checkSum;
	int dataSize;
	int source;
	int destination;
	Payload payload;
	private static Random generator = new Random(); // used to simulate bit errors
	static int NOT_USED = Integer.MIN_VALUE;
	static int NAK_VALUE = -1;


	Packet(int source, int destination, int seqNum, int ackNum, Payload payload) {
		this.source = source;
		this.destination = destination;
		this.seqNum = seqNum;
		this.ackNum = ackNum;
		this.payload = payload;
		this.checkSum = computeCheckSum();
	}

	int getSource() {
		return this.source;
	}

	int getDestination() {
		return this.destination;
	}

	int getDataSize() {
		if (payload != null) {
			dataSize = payload.getSize();
		}
		else
		{
			dataSize = 0;
		}
		return dataSize;
	}

	Payload getPayload() {
		return this.payload;
	}

	Packet makeNak() {
		return new Packet(destination, source, NOT_USED, NAK_VALUE, null);
	}

	boolean isNak() {
		return this.ackNum == NAK_VALUE;
	}

	Packet makeAck() {
		return new Packet(destination, source, NOT_USED, this.seqNum, null);
	}

	boolean isAck(int seqNumber) {
		return this.ackNum == seqNumber;
	}

	/**
	 * Corrupt the packet to simulate transmission bit errors;
	 *
	 */
	void corrupt()
	{
		int corruptWhat = generator.nextInt(5);
		switch (corruptWhat) {
			case 0 : // corrupt source address
				source += 1 + generator.nextInt(100);
				break;
			case 1 : // corrupt destination address
				destination += 1 + generator.nextInt(100);
				break;
			case 2 : // corrupt sequence number
				seqNum += 1 + generator.nextInt(5);
				break;
			case 3 : // corrupt ACK number
				ackNum += 1 + generator.nextInt(5);
				break;
			case 4: // corrupt checksum
				checkSum += 1 + generator.nextInt(500);
				break;
			default : corruptPayload();
		}
	}

	boolean isCorrupt() {
		int newSum = this.computeCheckSum();
		boolean isCorrupt = this.checkSum != newSum;
		return isCorrupt;
	}

	private int computeCheckSum()
	{
		int sum = 0;
		sum += this.source;
		sum += this.destination;
		sum += this.seqNum;
		sum += this.ackNum;
		if (payload != null) {
			byte[] bytes = payload.getMessageAsBytes();
			for (int i = 0; i < dataSize; i++) {
				sum += bytes[i];
			}
		}
		return sum;
	}

	private void corruptPayload() {
		System.out.println("Corrupting payload");
		if (this.payload == null) {
			this.payload =  new Payload("New Payload");
		}
		else
		{
			byte[] bytes = this.payload.getMessageAsBytes();
			bytes[generator.nextInt(Payload.PAYLOAD_SIZE)] = (byte)generator.nextInt();
			this.payload = new Payload(bytes);
		}
	}
}
