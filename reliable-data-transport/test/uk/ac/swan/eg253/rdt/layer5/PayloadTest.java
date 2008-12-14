package uk.ac.swan.eg253.rdt.layer5;

import uk.ac.swan.eg253.rdt.layer5.Payload;
import junit.framework.TestCase;

import java.util.List;
import java.util.Random;

public class PayloadTest extends TestCase {

	private char[] testMessage;
	private String longMessage, testString;
	private byte[] shortMessage = new byte[Payload.PAYLOAD_SIZE/2];
	private byte[] fullMessage = new byte[Payload.PAYLOAD_SIZE];
	private int maxMessageSizeInBytes = Payload.PAYLOAD_SIZE;
	Random generator = new Random();

	protected void setUp() throws Exception {
		testMessage = new char[] {
				'H', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'};
		testString = new String(testMessage);
		longMessage = new String("Now is the winter of our discontent\n" +
				"Made glorious summer by this sun of York\n" +
				"And all the clouds that loured upon our house\n" +
				"In the deep bosom of the ocean buried.\n" +
				"Now are our brows bound with victorious wreaths;\n" +
				"Our bruised arms hung up for monuments;\n" +
				"Our stern alarums changed to merry meetings;" +
				"Our dreadful marches to delightful measures.");
		for (int i = 0; i < shortMessage.length; i++)
		{
			shortMessage[i] = (byte)i;
		}
		for (int i = -maxMessageSizeInBytes/2; i < maxMessageSizeInBytes/2; i++)
		{
			fullMessage[i + maxMessageSizeInBytes/2] = (byte)i;
		}

	}

	protected void tearDown() throws Exception {
	}

	public void testCharArrayConstructor() {
		Payload msg = new Payload(testMessage);
		testShortMessage(msg);
	}

	public void testStringConstructor() {
		String s = new String(testMessage);
		Payload shortMsg = new Payload(s);
		testShortMessage(shortMsg);
		Payload longMsg = new Payload(longMessage);
		testLongMessage(longMsg);
	}

	public void testLongMessageIsTruncated() {
		Payload msg = new Payload(longMessage.toCharArray());
		testLongMessage(msg);
	}

	public void testShortMessageIsPadded() {
		Payload msg = new Payload(testMessage);
		testShortMessageIsPadded(msg);
	}

	public void testGetBytes() {
		Payload msg = new Payload(fullMessage);
		byte [] theBytes = msg.toBytes();
		for (int i = 0; i < fullMessage.length; i++)
		{
			assertEquals("Bytes in equals bytes out", fullMessage[i], theBytes[i]);
		}
	}

	public void testGetShortlengthBytes() {
		Payload msg = new Payload(shortMessage);
		byte [] theBytes = msg.toBytes();
		for (int i = 0; i < shortMessage.length; i++)
		{
			assertEquals("Bytes in equals bytes out", shortMessage[i], theBytes[i]);
		}
	}

	public void testPadding() {
		Payload msg = new Payload(shortMessage);
		byte [] theBytes = msg.getMessageAsBytes();
		for (int i = shortMessage.length; i < Payload.PAYLOAD_SIZE; i++)
		{
			assertEquals("Unused bytes are padded", (byte)0, theBytes[i]);
		}
	}

	public void testToCharArray() {
		char[] in = longMessage.toCharArray();
		Payload msg = new Payload(in);
		char[] out = msg.toCharArray();
		for (int i = 0; i < Payload.PAYLOAD_SIZE; i++){
			assertEquals("Character array out is same as string in",
					in[i], out[i]);
		}
	}

	public void testToCharArrayForTruncatedString() {
		char[] in = testMessage;
		Payload msg = new Payload(in);
		char[] out = msg.getMessageAsCharArray();
		for (int i = 0; i < testMessage.length; i++) {
			assertEquals("Character array out is same as string in",
					in[i], out[i]);
		}
		for (int i = testMessage.length; i < Payload.PAYLOAD_SIZE; i++) {
			assertEquals("Message is padded with null char",
					'\u0000', out[i]);
		}
	}

	public void testGetSize() {
		String s = new String(testMessage);
		Payload shortMsg = new Payload(s);
		assertEquals("size of message", testMessage.length, shortMsg.getSize());
		Payload longMsg = new Payload(longMessage);
		assertEquals("size of message", Payload.PAYLOAD_SIZE, longMsg.getSize());
	}

	public void testEquals() {
		Payload p = new Payload(testMessage);
		assertFalse("Should not be equal: other is not a Payload object", p.equals(new String("Hello World")));
		assertFalse("Should not be equal: other's message is different", p.equals(new Payload("Not this message")));
		assertFalse("Should not be equal: other is null", p.equals(null));
		assertTrue("Should be equal", p.equals(new Payload(testMessage)));
		p = new Payload(shortMessage);
		assertTrue("Should be equal", p.equals(new Payload(shortMessage)));
		p = new Payload(longMessage);
		assertTrue("Should be equal", p.equals(new Payload(longMessage)));
		p = new Payload(fullMessage);
		assertTrue("Should be equal", p.equals(new Payload(fullMessage)));
		for (int i = 1; i < 100; i++) {
			byte[] aMessage = randomMessage();
			p = new Payload(aMessage);
			assertTrue("Should be equal", p.equals(new Payload(aMessage)));
		}
	}

	public void testGetShortMessageToCharArray() {
		Payload p = new Payload(testMessage);
		assertEquals("Message matches", new String(testMessage), new String(p.toCharArray()));
	}

	public void testGetShortToBytes() {
		Payload p = new Payload(shortMessage);
		byte[] theData = p.toBytes();
		assertTrue("Size in equals size out", shortMessage.length == theData.length);
		for (int i = 0; i < theData.length; i++) {
			assertEquals("Data matches", shortMessage[i], theData[i]);
		}
	}

	public void testGetLongMessageAsString() {
		Payload p = new Payload(longMessage);
		assertTrue("Message matches", longMessage.startsWith(p.toString()));
		assertTrue("Message is truncated", p.toString().
				endsWith("Our bruised arms hung up for monuments;\nOur s"));
	}

	public void testGetEmptyMessageAsString() {
		Payload p = new Payload("");
		assertEquals("Message matches", "", p.toString());
	}

	public void testGetLongMessageAsCharArray() {
		Payload p = new Payload(longMessage.toCharArray());
		assertTrue("Message matches", new String(longMessage).
				startsWith(new String(p.getMessageAsCharArray())));
		char[] data = p.getMessageAsCharArray();
		assertTrue("Message is truncated", new String(p.getMessageAsCharArray()).
				endsWith(new String(data)));
	}

	public void testGetLongMessageAsByteArray() {
		Payload p = new Payload(fullMessage);
		byte[] theData = p.getMessageAsBytes();
		assertTrue("Size in equals size out", fullMessage.length == theData.length);
		for (int i = 0; i < theData.length; i++) {
			assertEquals("Data matches", fullMessage[i], theData[i]);
		}
	}

	public void testGetShortMessageAsString() {
		Payload p = new Payload(testString);
		assertEquals("Message matches", testString, p.toString());
	}

	public void testPackageMessageNullArg() {
		String s =  null;
		List<Payload> pkgs = Payload.packMessage(s);
		assertNull("Called with null string should return null", pkgs);
	}

	public void testPackageMessageEmptyArg() {
		String s =  "";
		List<Payload> pkgs = Payload.packMessage(s);
		assertEquals("Called with empty string should return 1 element", 1, pkgs.size());
	}

	public void testPackageMessageEmptyPayload() {
		String s =  "";
		List<Payload> pkgs = Payload.packMessage(s);
		Payload message = pkgs.get(0);
		assertTrue("Payload after called with empty string should be just padding", dataFieldIsEmpty(message));
	}

	public void testPackageMessageLongMessage() {
		String s =  longMessage;
		List<Payload> pkgs = Payload.packMessage(s);
		assertEquals("There should be two messages", 2, pkgs.size());
		Payload p0 = pkgs.get(0);
		Payload p1 = pkgs.get(1);
		assertEquals("Message is intact", longMessage, p0.toString() + p1.toString());
	}

	private boolean dataFieldIsEmpty(Payload message) {
		byte[] b = message.getMessageAsBytes();
		for (int i = 0; i < b.length; i++) {
			if (b[i] != (byte)0) return false;
		}
		return true;
	}

	private byte[] randomMessage() {
		byte[] randomMessage = new byte[generator.nextInt(maxMessageSizeInBytes/2)];
		for (int i = 0; i < randomMessage.length; i++)
		{
			randomMessage[i] = (byte)generator.nextInt(Byte.MAX_VALUE);
		}
		return randomMessage;
	}

	private void testLongMessage(Payload msg) {
		assertEquals("Payload is correct length",
				Payload.PAYLOAD_SIZE, msg.toString().length());
		assertTrue("Payload was truncated",
				longMessage.startsWith(msg.toString()));
	}

	private void testShortMessage(Payload msg) {
		assertEquals("Payload should be Hello world",
				"Hello world", msg.toString());
	}

	private void testShortMessageIsPadded(Payload msg) {
		char zero = '\u0000';
		String s = msg.getMessageString();
		int endOfMessage = s.indexOf(zero);
		assertTrue("Padding starts", endOfMessage < Payload.PAYLOAD_SIZE);
		for (int i = endOfMessage; i < Payload.PAYLOAD_SIZE; i++) {
			assertEquals("Char[" + i + "] should be NULL",
					zero, s.charAt(i));
		}
	}
}
