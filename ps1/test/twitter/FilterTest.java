/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Strategy:
     * Partition our methods as following:
     * Filter.writtenBy:
     *   Author is contained: true, false;
     *   By size of tweet list: 0, 1, >1; 
     *   Repetition of tweets: yes, no  
     *   Case sensivity check: yes, no
     *  
     * Filter.inTimespan:
     *   Tweet is contained: yes, no
     *   Timespan size: 0, >0
     *   By size of tweet list: 0, 1, >1;
     *   Repetition of tweets: yes, no;
     *
     * Filter.containing:
     *   By size of tweet list: 0, 1, >1;
     *   By word count: 0, 1, >1;
     *   Repetition of tweets: yes, no;
     *   Repetition of words: yes, no;
     *   Case insensivity check: yes, no;
     *   
     *   Exhaustive Cartesian coverage of partitions;
     *   Checking that the order of tweets is unchanged;
     *   Ensuring the implicit precondition (not null) is handled in a fail-fast manner;
     *   
     */
    
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final Tweet tweetEarly_A = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweetEarly_B = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d1);
	private static final Tweet tweetMid_A = new Tweet(3, "alyssa", "#TDD rules", d2);
	private static final Tweet tweetLate_B = new Tweet(4, "bbitdiddle", "She is way above me as a hacker", d3);
	

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
	// fail-fast parameter 1
	@Test
	public void writtenByNoTweets() {
	}

	// fail-fast parameter 2
	@Test
	public void writtenByNullAuthors() {
	}

	// tweet list size: 1, author present: yes
	@Test
	public void writtenByOneTweetAuthorFound() {
	}

	// tweet list size: 1, author present: no, letter case test: yes;
	@Test
	public void writtenByOneTweetAuthorNotFound() {
	}

	// tweet list size: 6, author present: 3 yes, 3 no, tweet repetition: yes;
	@Test
	public void writtenByFourTweetAuthorFound() {
	}

	// fail-fast: tweet list is null
	@Test
	public void timeSpanNullTweets() {
	}

	// timespan size: 0, tweet found: no, tweet list: 0
	@Test
	public void timeSpanZeroEmptyTweets() {
	}

	// timespan size: 0, tweet found: yes / no, tweet list: 2, tweet repetition: no
	@Test
	public void timeSpanZeroTweetsOneFoundOneNot() {
	}

	// timespan size: 0, tweet found: yes, tweet list: 1, tweet repetition: no
	@Test
	public void timeSpanZeroTweetFound() {
	}

	// timespan size: > 0; tweet found: yes/no, tweet list: 6, tweet repetition: yes
	@Test
	public void timeSpanTweets() {
	}
   
	
	// fail-fast: null tweets
	@Test
	public void containingNullTweets() {
	}

	// tweet list size: 0
	@Test
	public void containingNoTweets() {
	}
	
	// tweet list size: 1, word count: 0, 
	@Test
	public void containingNoWords() {
	}
	
	// tweet list size: 6, word count: 1, tweet repetition: yes, case insensivity check: no
	@Test
	public void containingRepeatingTweetsOneWord() {
	}
		
	// tweet list size: 4, word count: 3, tweet repetition: no, word repetition: yes, case insensivity check: yes
	@Test
	public void containingTweetsRepeatingWordsVariedCase() {
	}
	
     /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */
	
//	@Test
//    public void testWrittenByMultipleTweetsSingleResult() {
//        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
//        
//        assertEquals("expected singleton list", 1, writtenBy.size());
//        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
//    }
//    
//    @Test
//    public void testInTimespanMultipleTweetsMultipleResults() {
//        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
//        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
//        
//        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
//        
//        assertFalse("expected non-empty list", inTimespan.isEmpty());
//        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
//        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
//    }
//    
//    @Test
//    public void testContaining() {
//        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
//        
//        assertFalse("expected non-empty list", containing.isEmpty());
//        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
//        assertEquals("expected same order", 0, containing.indexOf(tweet1));
//    }

}
