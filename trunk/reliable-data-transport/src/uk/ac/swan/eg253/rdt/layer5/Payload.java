/*
 * An Application Method
 */
package uk.ac.swan.eg253.rdt.layer5;

import java.util.ArrayList;
import java.util.List;

/**
 * The Playload carried by a Datagram Packet.
 *
 *
 * @author Dr Chris P. Jobling
 * @version 1.0
 *
 */
class Payload {

	/**
	 * Maximum size of payload in bytes.
	 * Larger messages will be truncated
	 */
	public static final int PAYLOAD_SIZE = 256;

	/** The message */
	byte[] message = new byte[PAYLOAD_SIZE];

	/** Size of the message */private int size;

	/**
	 * Build a message from a String.
	 * @param theMessage the message as a string;
	 */
	Payload (String theMessage) {
		this(theMessage.getBytes());
	}

	/**
	 * Build a message from an array of characters.
	 * @param theMessage the message as an aray of <code>char</code>;
	 */
	Payload (char [] theMessage) {
		this(new String(theMessage).getBytes());
	}

	/**
	 * Build a message from an array of bytes.
	 * @param theMessage the message as an array of bytes;
	 */
	Payload (byte [] theMessage) {
		if (theMessage.length >= PAYLOAD_SIZE)
		{
			for (int i = 0; i < PAYLOAD_SIZE; i++)
			{
				message[i] = theMessage[i];
			}
			size = PAYLOAD_SIZE;
		}
		else
		{
			for (int i = 0; i < theMessage.length; i++)
			{
				message[i] = theMessage[i];
			}
			size = theMessage.length;
			// pad the rest of the data with 0
			for (int i = theMessage.length; i < PAYLOAD_SIZE; i++)
			{
				message[i] = (byte)0;
			}
		}
	}

	/**
	 * Return complete data as a string.
	 * @return the data as a string. Padding is not removed.
	 */
	public String getMessageString() {
		return new String(message);
	}

	/**
	 * Return the complete payload as a byte array.
	 * @return the data as a byte array. Padding is removed.
	 */
	public byte[] getMessageAsBytes()
	{
		return this.message;
	}

	/**
	 * Get complete payload data as a character array.
	 * @return data as a character array. Padding is removed.
	 */
	public char[] getMessageAsCharArray() {
		return new String(this.message).toCharArray();
	}

	/**
	 * Get size of the data.
	 * @return data size.
	 */
	int getSize() {
		return size;
	}

	public boolean equals(Object other) {
		if (other == null) return false;
		if (! (other instanceof Payload) ) return false;
		byte [] bytes = ((Payload)other).getMessageAsBytes();
		for (int i = 0; i < PAYLOAD_SIZE; i ++) {
			if (bytes[i] != this.message[i]) return false;
		}
		return true;
	}

	/**
	 * Return data of message as a string.
	 * @return data as a string. Padding removed.
	 */
	public String toString() {
		return new String(truncateMessage());
	}

	/**
	 * Return data of message as a byte array.
	 * @return data as a byte array. Padding removed.
	 */
	public byte[] toBytes() {
		return truncateMessage();
	}

	/**
	 * Convert a long message into an list of Payload messages.
	 * @param longMessage a string representing a long message.
	 * @return an array of Payload objects: if longMessage is null,
	 *   methid returns null; if message is empty string, or the message
	 *   is less than PAYLOAD_SIZE, the an array of size 1 will returned,
	 *   and the message data will be padded with zeros. If it is less than
	 *   PAYLOAD_SIZE bytes in length, the last message
	 *   will be padded with zeros also. The size of the returned array
	 *   should equal longMessage.length() / PAYLOAD_SIZE.
	 */
	public static List<Payload> packMessage(String longMessage)
	{
		if (longMessage == null) return null;
		else {
			List<Payload> l = new ArrayList<Payload>();
			int messageSize = longMessage.length();
			if (messageSize <= PAYLOAD_SIZE) {
				l.add(new Payload(longMessage));
			}
			else {
				// Break message into bytes
				byte [] message = longMessage.getBytes();
				byte [] subMessage = new byte[PAYLOAD_SIZE];
				// How many messages will there be?
				int n = message.length / PAYLOAD_SIZE;
				int index = 0;
				for (int packetNumber = 0; packetNumber < n; packetNumber++) {
					for (int i = 0; i < PAYLOAD_SIZE; i++) {
						subMessage[i] = message[index];
						index++;

					}
					System.out.println("index is now: " + index);
					l.add(new Payload(subMessage));
				}
				byte [] lastMessage = new byte[message.length - index];
				// Pack last message
				for (int i = 0; i < lastMessage.length; i++) {
					lastMessage[i] = message[index];
					index++;
				}
				l.add(new Payload(lastMessage));
			}
			return l;
		}
	}

	/**
	 * Return data of message as a character array.
	 * @return data as a character array. Padding removed.
	 */
	public char[] toCharArray() {
		return new String(truncateMessage()).toCharArray();
	}

	/**
         * Truncate a message if it is smaller than the size of the payload.
         * @return the truncated message as a <code>byte</code> array.
         */
         private byte[] truncateMessage() {
		if (this.getSize() == PAYLOAD_SIZE) {
			return this.message;
		}
		else {
			byte [] messageBytes = new byte[this.getSize()];
			for (int i = 0; i < messageBytes.length; i++) {
				messageBytes[i] = message[i];
			}
			return messageBytes;
		}
	}
}
