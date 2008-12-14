package uk.ac.swan.eg253.rdt.layer5;

import junit.framework.TestCase;

public class PacketTest extends TestCase {

	private Payload msg = new Payload(
		"Now is the winter of our discontent\nMade glorious summer by this sun of York;");
	private Packet pkt;
	private int src = 0;
	private int dst = 1;
	private int seqNumber = 0;

	protected void setUp() throws Exception {
		super.setUp();
		pkt = new Packet(src, dst, seqNumber, Packet.NOT_USED, msg);
	}

	protected void tearDown() throws Exception {
		pkt = null;
		super.tearDown();
	}

	public void testIsNak() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			Packet nak = thePkt.makeNak();
			assertTrue("" + i + "the returned message is a NAK", nak.isNak());
			assertEquals("Source is original destination",
					dst,nak.getSource());
			assertEquals("Destination is original source",
					src,nak.getDestination());
		}
	}

	public void testIsAck() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			Packet ack = thePkt.makeAck();
			assertTrue("Packet is ACK(" + i + ")", ack.isAck(i));
			assertEquals("Source is original destination",
					dst,ack.getSource());
			assertEquals("Destination is original source",
					src,ack.getDestination());
		}
	}

	public void testAckHasNoPayload()
	{
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			Packet ack = thePkt.makeAck();
			assertEquals("ACK has no payload", 0, ack.getDataSize());
		}
	}

	public void testNakHasNoPayload()
	{
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			Packet nak = thePkt.makeNak();
			assertEquals("NAK has no payload", 0, nak.getDataSize());
		}
	}

	public void testGetSource() {
		assertEquals("Source address", src, pkt.getSource());
	}

	public void testGetDestination() {
		assertEquals("Source address", dst, pkt.getDestination());
	}

	public void testPacketChecksum() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			assertFalse("Packet is corrupt", thePkt.isCorrupt());
		}
	}

	public void testNakChecksum() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			Packet nak = thePkt.makeNak();
			assertFalse("NAK packet is corrupt", nak.isCorrupt());
		}
	}

	public void testAckChecksum() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			Packet ack = thePkt.makeAck();
			assertFalse("ACK packet is corrupt", ack.isCorrupt());
		}
	}

	public void testCorrupt() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			thePkt.corrupt();
			assertTrue("Packet is corrupt", thePkt.isCorrupt());
		}
	}

	public void testGetPayload() {
		for (int i = 0; i < 100; i++) {
			Packet thePkt = new Packet(src, dst, i, Packet.NOT_USED, msg);
			assertEquals("Retrieved message equals sent message", msg, thePkt.getPayload());
			Packet nak = thePkt.makeNak();
			assertNull("NAK has no payload", nak.getPayload());
			Packet ack = thePkt.makeAck();
			assertNull("NAK has no payload", ack.getPayload());
		}
	}


}
