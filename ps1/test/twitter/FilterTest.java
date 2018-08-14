/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FilterTest {

	/*
	 * Strategy: Partition our methods as following: Filter.writtenBy: Author is
	 * contained: true, false; By size of tweet list: 0, 1, >1; Repetition of
	 * tweets: yes, no Case sensivity check: yes, no
	 * 
	 * Filter.inTimespan: Tweet is contained: yes, no Timespan size: 0, >0 By size
	 * of tweet list: 0, 1, >1; Repetition of tweets: yes, no;
	 *
	 * Filter.containing: By size of tweet list: 0, 1, >1; By word count: 0, 1, >1;
	 * Repetition of tweets: yes, no; Repetition of words: yes, no; Case insensivity
	 * check: yes, no;
	 * 
	 * Exhaustive Cartesian coverage of partitions; Checking that the order of
	 * tweets is unchanged; Ensuring the implicit precondition (not null) is handled
	 * in a fail-fast manner;
	 * 
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final String userNameOne = "alyssa";
	private static final String userNameTwo = "bbitdiddle";

	private static final List<String> WORDS1 = Arrays.asList("rivest");
	private static final List<String> WORDS3CASEINSEN = Arrays.asList("hacker", "minutes", "much", "hAcKEr");

	private static final Tweet tweetEarly_U1 = new Tweet(1, userNameOne,
			"is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetEarly_U2 = new Tweet(2, userNameTwo, "rivest talk in 30 minutes #hype", d1);
	private static final Tweet tweetMid_U1 = new Tweet(3, userNameOne, "#TDD rules", d2);
	private static final Tweet tweetLate_U2 = new Tweet(4, userNameTwo, "She is way above me as a hacker", d3);

	private static final List<Tweet> LSTWEETU1 = Arrays.asList(tweetEarly_U1);
	private static final List<Tweet> LSTWEETU1U2NOSPAN = Arrays.asList(tweetEarly_U1, tweetEarly_U2);
	private static final List<Tweet> LSTWEETU1U2SPAN = Arrays.asList(tweetMid_U1, tweetLate_U2);
	private static final List<Tweet> LSTWEETSIX_3U1_3U2 = Arrays.asList(tweetEarly_U1, tweetEarly_U2, tweetLate_U2,
			tweetMid_U1, tweetEarly_U1, tweetLate_U2);

	private static final Timespan TSEarlyInstant = new Timespan(d1, d1);
	private static final Timespan TSMidInstant = new Timespan(d2, d2);
	private static final Timespan TSMidLate = new Timespan(d2, d3);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// fail-fast parameter 1 null
	@Test(expected = IllegalArgumentException.class)
	public void writtenByNoTweets() {
		assertEquals(Filter.writtenBy(null, userNameOne), null);
	}

	// fail-fast parameter 2 null
	@Test(expected = IllegalArgumentException.class)
	public void writtenByNullAuthors() {
		assertEquals(Filter.writtenBy(Arrays.asList(tweetEarly_U1), null), null);
	}

	// tweet list size: 1, author present: yes
	@Test
	public void writtenByOneTweetAuthorFound() {
		List<Tweet> expectedList = Filter.writtenBy(LSTWEETU1, userNameOne);
		assertEquals(expectedList, LSTWEETU1);
	}

	// tweet list size: 1, author present: no, letter case test: yes;
	@Test
	public void writtenByOneTweetAuthorNotFound() {
		List<Tweet> filtered = Filter.writtenBy(LSTWEETU1, userNameOne.toUpperCase());
		  assertTrue("expected empty list", filtered.isEmpty());
	}

	// tweet list size: 6, author present: 3 yes, 3 no, tweet repetition: yes;
	@Test
	public void writtenBySixTweetAuthorFound() {
		List<Tweet> actualList = Filter.writtenBy(LSTWEETSIX_3U1_3U2, userNameOne);
		List<Tweet> expectedList = Arrays.asList(tweetEarly_U1, tweetMid_U1);
		assertEquals(actualList, expectedList);
	}

	// tweet list size: 6, author present: 3 yes, 3 no, tweet repetition: yes;
	@Test
	public void writtenBySixTweetAuthorFoundShufflec() {
		Collections.shuffle(LSTWEETSIX_3U1_3U2);
		List<Tweet> actualList = Filter.writtenBy(LSTWEETSIX_3U1_3U2, userNameOne);
		List<Tweet> expectedList = Arrays.asList(tweetEarly_U1, tweetMid_U1);
		assertEquals(actualList, expectedList);
	}

	// fail-fast: parameter 1 is null
	@Test(expected = IllegalArgumentException.class)
	public void timeSpanNullTweets() {
		assertEquals(Filter.inTimespan(null, TSEarlyInstant), null);
	}

	// fail-fast: parameter 2 is null
	@Test(expected = IllegalArgumentException.class)
	public void timeSpanNullTS() {
		assertEquals(Filter.inTimespan(LSTWEETU1, null), null);
	}

	// fail-fast: tweet list: 0
	@Test
	public void timeSpanZeroEmptyTweets() {
		assertTrue("Expected empty list", Filter.inTimespan(Arrays.asList(), TSEarlyInstant).isEmpty());
	}

	// timespan size: 0, tweet found: yes / no, tweet list: 2, tweet repetition: no
	@Test
	public void timeSpanZeroTweetsOneFoundOneNot() {
		List<Tweet> expectedList = Arrays.asList(tweetMid_U1);
		assertEquals(Filter.inTimespan(LSTWEETU1U2SPAN, TSMidInstant), expectedList);
	}

	// timespan size: 0, tweet found: yes, tweet list: 1, tweet repetition: no
	@Test
	public void timeSpanZeroTweetFound() {
		assertEquals(Filter.inTimespan(LSTWEETU1, TSEarlyInstant), LSTWEETU1);
	}

	// timespan size: > 0; tweet found: yes/no, tweet list: 6, tweet repetition: yes
	@Test
	public void timeSpanTweetsShuffled() {
		List<Tweet> expectedList = Arrays.asList(tweetMid_U1, tweetLate_U2);
		Collections.shuffle(LSTWEETSIX_3U1_3U2);
		assertEquals(Filter.inTimespan(LSTWEETSIX_3U1_3U2, TSMidLate), expectedList);
	}

	// timespan size: > 0; tweet found: yes/no, tweet list: 6, tweet repetition: yes
	@Test
	public void timeSpanTweets() {
		List<Tweet> expectedList = Arrays.asList(tweetMid_U1, tweetLate_U2);
		assertEquals(Filter.inTimespan(LSTWEETSIX_3U1_3U2, TSMidLate), expectedList);
	}

	// fail-fast: parameter 1 is null
	@Test(expected = IllegalArgumentException.class)
	public void containingNullTweets() {
		assertEquals(Filter.containing(null, WORDS1), null);
	}

	// fail-fast: parameter 2 is null
	@Test(expected = IllegalArgumentException.class)
	public void containingNullWords() {
		assertEquals(Filter.containing(LSTWEETU1, null), null);
	}

	// fail-fast: tweet list size: 0
	@Test
	public void containingNoTweets() {
		assertTrue("Expected empty list", Filter.containing(Arrays.asList(), WORDS1).isEmpty());
	}

	// tweet list size: 1, word count: 0,
	@Test
	public void containingNoWords() {
		assertEquals(Filter.containing(LSTWEETU1, Arrays.asList()), Arrays.asList());
	}

	// tweet list size: 6, word count: 1, tweet repetition: yes, case insensivity
	// check: no
	@Test
	public void containingRepeatingTweetsOneWord() {
		List<Tweet> expectedList = LSTWEETU1U2NOSPAN;
		assertEquals(Filter.containing(LSTWEETSIX_3U1_3U2, WORDS1), expectedList);
	}

	// tweet list size: 4, word count: 3, tweet repetition: no, word repetition:
	// yes, case insensivity check: yes
	@Test
	public void containingTweetsRepeatingWordsVariedCase() {
		List<Tweet> expectedList = Arrays.asList(tweetEarly_U1, tweetEarly_U2, tweetLate_U2);
		assertEquals(Filter.containing(LSTWEETSIX_3U1_3U2, WORDS3CASEINSEN), expectedList);
	}

	// tweet list size: 4, word count: 3, tweet repetition: no, word repetition:
	// yes, case insensivity check: yes
	// shuffled
	@Test
	public void containingTweetsRepeatingWordsVariedCaseShuffled() {
		List<Tweet> expectedList = Arrays.asList(tweetEarly_U1, tweetEarly_U2, tweetLate_U2);
		Collections.shuffle(LSTWEETSIX_3U1_3U2);
		assertEquals(Filter.containing(LSTWEETSIX_3U1_3U2, WORDS3CASEINSEN), expectedList);
	}

	/*
	 * Warning: all the tests you write here must be runnable against any Filter
	 * class that follows the spec. It will be run against several staff
	 * implementations of Filter, which will be done by overwriting (temporarily)
	 * your version of Filter with the staff's version. DO NOT strengthen the spec
	 * of Filter or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Filter, because that means you're testing a stronger spec
	 * than Filter says. If you need such helper methods, define them in a different
	 * class. If you only need them in this test class, then keep them in this test
	 * class.
	 */

	// @Test
	// public void testWrittenByMultipleTweetsSingleResult() {
	// List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2),
	// "alyssa");
	//
	// assertEquals("expected singleton list", 1, writtenBy.size());
	// assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
	// }
	//
	// @Test
	// public void testInTimespanMultipleTweetsMultipleResults() {
	// Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
	// Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
	//
	// List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new
	// Timespan(testStart, testEnd));
	//
	// assertFalse("expected non-empty list", inTimespan.isEmpty());
	// assertTrue("expected list to contain tweets",
	// inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
	// assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
	// }
	//
	// @Test
	// public void testContaining() {
	// List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2),
	// Arrays.asList("talk"));
	//
	// assertFalse("expected non-empty list", containing.isEmpty());
	// assertTrue("expected list to contain tweets",
	// containing.containsAll(Arrays.asList(tweet1, tweet2)));
	// assertEquals("expected same order", 0, containing.indexOf(tweet1));
	// }

}
