package uk.ac.swan.eg253.rdt.layer5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A message buffer
 * @author Dr Chris P. Jobling
 * @version 1.0
 *
 */

class MessageQueue
{
	private Queue<Payload> buffer;
	static final int MAX_BUFFER_SIZE = 50;

	MessageQueue(String msg) {
		this();
		// Make a buffer from a string, creating payload objects as necessary
	}
	MessageQueue() {
		buffer = new LinkedList<Payload>();
	}
	MessageQueue(Payload[] messages) {
		this();
		// Create a message buffer an prepopulate it with messages
	}

	/**
	 * @return the Payload at the head of the buffer or null if empty.
	 *
	 */
	public Payload poll() {
		return buffer.poll();
	}

	String getMessage() {
		// return the message stored in the buffer as a String, removing each
		// message as you do so
		return "";
	}

	public int size() {
		return buffer.size();
	}

	/**
	 * Add new Payload to message buffer.
	 * @param p Payload object to add.
	 * @return true if payload added successfully, false if capacity exceeded.
	 */
	public boolean offer(Payload p) {
		// Limited capacity buffer
		if (buffer.size() == MAX_BUFFER_SIZE)
		{
			return false;
		}
		else {
			return buffer.offer(p);
		}
	}

	public Payload peek() {
		// TODO Auto-generated method stub
		return buffer.peek();
	}

}

