/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

	/*
	 * Testing strategy
	 *
	 * Partition the inputs as follows:
	 * tweets: 0, 1, 2, > 2;
	 * time in tweets: equal, later first, earlier first 
	 */
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

	public static void main(String[] args){
		//		System.out.println(Extract.getTimespan(Arrays.asList()));
		//		System.out.println(Extract.getTimespan(Arrays.asList(tweet1)));
		//		System.out.println(Extract.getTimespan(Arrays.asList(tweet1, tweet2)));
	}

	@Test(expected=AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// covers tweets = 0,
	//  earlier first;
	@Test
	public void testZero() {
		Timespan timespan = Extract.getTimespan(Arrays.asList());
		// undefined behaviour. should we test only contracted inputs?
	}

	// covers tweets = 1,
	@Test
	public void testOne() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d1, timespan.getEnd());
	}

	// covers tweets = 2,
	//  earlier first;
	@Test
	public void testTwoOrdered() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d2, timespan.getEnd());
	}

	// covers tweets = 2,
	//  later first;
	@Test
	public void testTwoUnOrdered() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d2, timespan.getEnd());
	}

	// covers tweets = 2,
	//  equal time;
	@Test
	public void testTwoEqual() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet1));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d1, timespan.getEnd());
	}

	@Test
	public void testGetMentionedUsersNoMention() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));

		assertTrue("expected empty set", mentionedUsers.isEmpty());
	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * Extract class that follows the spec. It will be run against several staff
	 * implementations of Extract, which will be done by overwriting
	 * (temporarily) your version of Extract with the staff's version.
	 * DO NOT strengthen the spec of Extract or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own
	 * that you have put in Extract, because that means you're testing a
	 * stronger spec than Extract says. If you need such helper methods, define
	 * them in a different class. If you only need them in this test class, then
	 * keep them in this test class.
	 */

}
