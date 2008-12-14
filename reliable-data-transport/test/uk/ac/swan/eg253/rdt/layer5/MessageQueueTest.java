package uk.ac.swan.eg253.rdt.layer5;

import junit.framework.TestCase;

public class MessageQueueTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMessageBufferString() {
		fail("Not yet implemented");
	}

	public void testMessageBuffer() {
		fail("Not yet implemented");
	}

	public void testMessageBufferPayloadArray() {
		fail("Not yet implemented");
	}

	public void testAdd() {
		MessageQueue mb = new MessageQueue();
		String sentence = "Now is the time for all good men to come to the aid of the party";
		String[] words = sentence.split(" ", -1);
		Payload[] messages = new Payload[words.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = new Payload(words[i]);
		}
		for (Payload msg : messages) {
			mb.offer(msg);
		}
		Payload p = mb.poll();
		int i = 0;
		while (p != null) {
			assertEquals("Message is as expected", words[i], p.toString());
			p = mb.poll();
			i++;
		}

	}

	public void testPoll() {
		fail("Not yet implemented");
	}

	public void testGetMessage() {
		fail("Not yet implemented");
	}

	public void testBasicQueueOperation() {
		MessageQueue mb = new MessageQueue();
		assertEquals("Buffer is initially empty", 0, mb.size());
		Payload p = new Payload("Now is the time for all good friends to come to the aid of the party!");
		mb.offer(p);
		assertEquals("Buffer now has one element", 1, mb.size());
		Payload newP = mb.poll();
		assertNotNull("Element should not be null", newP);
		assertEquals("Element should be equal to original", p, newP);
		assertEquals("Queue should now be empty", 0, mb.size());
		Payload anotherP = mb.poll();
		assertNull("Empty queue returns NULL", anotherP);
	}

	public void testPollReturnsNullWhenQueueIsEmpty()
	{
		MessageQueue mb = new MessageQueue();
		assertNull("Empty Q returns null", mb.poll());
	}

	public void testOfferReturnsTrue()
	{
		MessageQueue mb = new MessageQueue();
		assertTrue("Offer returns true", mb.offer(new Payload("Hello World")));
	}

	public void testQueueCapacity() {
		MessageQueue mb = new MessageQueue();
		int i = 0;
		Payload p = new Payload("This is message " + i);
		boolean ok = true;
		while (ok) {
			ok = mb.offer(p);
			i++;
		}
		// Buffer is now full
		assertEquals("Message should be one more than capacity", MessageQueue.MAX_BUFFER_SIZE + 1, i);
		assertEquals("Buffer size is now capacity", MessageQueue.MAX_BUFFER_SIZE, mb.size());
		// remove head of queue
		Payload firstMessage = mb.poll();
		assertNotNull("The head of the queue should be a first message", firstMessage);
		assertEquals("First message was 0",
				"This is message 0", firstMessage.toString());
		// We should be able to add one more element
		Payload lastMessage = new Payload("This is the End");
		ok = mb.offer(lastMessage);
		assertTrue("Successfully added last message",ok);
		// Try one more
		Payload lastStraw = new Payload("Just one wafer thin mint!");
		ok = mb.offer(lastStraw);
		assertFalse("Can't add anymore", ok);
		p = mb.poll();
		Payload lastOut = null;
		int count = 0;
		while (p != null) {
			lastOut = p;
			p = mb.poll();
			count ++;
		}
		assertEquals("Last out is last in", "This is the End", lastOut.toString());
		assertTrue("Queue is now empty", mb.size() == 0);
		assertEquals("We extracted " + MessageQueue.MAX_BUFFER_SIZE + " messages",
				count, MessageQueue.MAX_BUFFER_SIZE);
	}

}
