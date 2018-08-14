/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

	/*
	 * Testing strategy for getTimespan:
	 *
	 * Partition the inputs as follows: tweets: 0, 1, 2, > 2; time in tweets: equal,
	 * later first, earlier first, unordered
	 */

	/*
	 * Testing strategy for getMentionedUsers:
	 *
	 * Partition the inputs as follows: mentions: 0, 1, 2; duplicate entries: yes,
	 * no
	 */
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T10:30:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T11:00:00Z");

	private static final String mentionOne = "@alyssa_1337";
	private static final String mentionTwo = "@b-bit_diddle";

	private static final Tweet tweetNull = null;
	private static final Tweet tweetEarly_noMentions = new Tweet(1, "alyssa",
			"is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetLate_noMentions = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d3);
	private static final Tweet tweetMid_oneMention = new Tweet(3, "bbitdiddle", mentionOne + " #TDD rules", d2);
	private static final Tweet tweetMid_twoMentions = new Tweet(4, "bbitdiddle",
			mentionOne + " is way above " + mentionTwo + " as hacker", d2);

	public static void main(String[] args) {
		// Set<String> mentionedUsers =
		// Extract.getMentionedUsers(Arrays.asList(tweetMid_twoMentions,
		// tweetMid_twoMentions));
		// System.out.println(mentionedUsers.toString());
		// // System.out.println(Extract.getTimespan(Arrays.asList()));
		// System.out.println(Extract.getTimespan(Arrays.asList(tweet1)));
		// System.out.println(Extract.getTimespan(Arrays.asList(tweet1, tweet2)));
	}

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// covers tweets == null,
	// earlier first;
	@Test(expected = IllegalArgumentException.class)
	public void testTweetsNull() {
		Extract.getTimespan(null);
	}

	// covers empty tweets list,
	// earlier first;
	@Test(expected = IllegalArgumentException.class)
	public void testTimeZero() {
		Extract.getTimespan(Arrays.asList());
	}

	// covers null tweet in tweets,
	// earlier first;
	@Test(expected = IllegalArgumentException.class)
	public void testTweetNull() {
		ArrayList<Tweet> tweets = new ArrayList<>();
		tweets.add(tweetNull);
		Extract.getTimespan(tweets);
	}

	// covers tweets = 1,
	@Test
	public void testTimeOne() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetEarly_noMentions));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d1, timespan.getEnd());
	}

	// covers tweets = 2,
	// earlier first;
	@Test
	public void testTimeTwoOrdered() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetEarly_noMentions, tweetLate_noMentions));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d3, timespan.getEnd());
	}

	// covers tweets = 2,
	// later first;
	@Test
	public void testTimeTwoUnOrdered() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetLate_noMentions, tweetEarly_noMentions));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d3, timespan.getEnd());
	}

	// covers tweets = 2,
	// equal time;
	@Test
	public void testTimeTwoEqual() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetEarly_noMentions, tweetEarly_noMentions));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d1, timespan.getEnd());
	}

	// covers tweets = > 2,
	// unordered time;
	@Test
	public void testTimeMulti() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweetEarly_noMentions, tweetLate_noMentions,
				tweetLate_noMentions, tweetEarly_noMentions, tweetMid_twoMentions));
		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d3, timespan.getEnd());
	}

	// covers mentions: 0;
	// duplicates: no
	@Test
	public void testGetMentionedUsersNoMention() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetEarly_noMentions));
		assertTrue("expected empty set", mentionedUsers.isEmpty());
	}

	// covers mentions: 1;
	// duplicates: no
	@Test
	public void testGetMentionedUsersOneMention() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetMid_oneMention));
		assertTrue("expected mention one", mentionedUsers.contains(mentionOne) && mentionedUsers.size() == 1);
	}

	// covers mentions: 1;
	// duplicates: yes
	@Test
	public void testGetMentionedUsersOneMentionDouble() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetMid_oneMention, tweetMid_oneMention));
		assertTrue("expected mention one", mentionedUsers.contains(mentionOne) && mentionedUsers.size() == 1);
	}

	// covers mentions: 2;
	// duplicates: no
	@Test
	public void testGetMentionedUserTwoMentions() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetMid_twoMentions));
		assertTrue("expected mention one", mentionedUsers.contains(mentionOne) && mentionedUsers.contains(mentionTwo)
				&& mentionedUsers.size() == 2);
	}

	// covers mentions: 2;
	// duplicates: yes
	@Test
	public void testGetMentionedUsersTwoMentionsDouble() {
		Set<String> mentionedUsers = Extract
				.getMentionedUsers(Arrays.asList(tweetMid_twoMentions, tweetMid_twoMentions));
		assertTrue("expected empty set", mentionedUsers.contains(mentionOne) && mentionedUsers.contains(mentionTwo)
				&& mentionedUsers.size() == 2);
	}

	/*
	 * Warning: all the tests you write here must be runnable against any Extract
	 * class that follows the spec. It will be run against several staff
	 * implementations of Extract, which will be done by overwriting (temporarily)
	 * your version of Extract with the staff's version. DO NOT strengthen the spec
	 * of Extract or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Extract, because that means you're testing a stronger spec
	 * than Extract says. If you need such helper methods, define them in a
	 * different class. If you only need them in this test class, then keep them in
	 * this test class.
	 */

}
