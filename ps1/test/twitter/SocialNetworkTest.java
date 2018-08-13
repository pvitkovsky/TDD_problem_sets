/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

	/*
	 * Testing strategy:
	 * Partition as following:
	 * 3 users where user N has N-1 followers; 
	 * Example:
	 *   Alyssa: Ben, Eva
	 *   Ben: Eva
	 *   Eva: 
	 * Make sure duplicate @mentions are treated as one; 
	 * Make sure duplicate usernames are treated as one
	 * Therefore:
	 * 4 tweets; 3 users; 
	 * Tweet1: Eva, mentions Ben, Alyssa
	 * Tweet2: Ben, mentions Alyssa twice,
	 * Tweet3: Ben again, mentions Alyssa once;
	 * Tweet4: Alyssa, mentions no one.
	 * 
	 * Maybe add border cases?
	 */
	
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");

	private static final String userNameOne = "alyssa";
	private static final String userNameTwo = "bbitdiddle";
	private static final String userNameThree = "evaluator";
	
	private static final Tweet tweet1E_BA = new Tweet(1, userNameThree, "@"+userNameOne+"talks, @"+userNameTwo+"agrees", d1);
	private static final Tweet tweet2B_AA = new Tweet(2, userNameTwo, "@"+userNameOne+"smartest rivest talker. @"+userNameOne+", I agree", d1);
	private static final Tweet tweet3B_A = new Tweet(3, userNameTwo, "@"+userNameOne+"#TDD rules", d1);
	private static final Tweet tweet4A = new Tweet(4, userNameOne, "Hack and test #hype", d1);

	private static final List<Tweet> LSTWEETU1 = Arrays.asList(tweet1E_BA, tweet2B_AA, tweet3B_A, tweet4A);
	private static Map<String, Set<String>> EXPMAPU1 = new HashMap<String, Set<String>>();
	private void fillExpMapU1() {
		EXPMAPU1.put(userNameOne, new HashSet<>(Arrays.asList(userNameTwo, userNameThree)));	
		EXPMAPU1.put(userNameTwo, new HashSet<>(Arrays.asList(userNameThree)));	
		EXPMAPU1.put(userNameThree, new HashSet<>());	
	}
	
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //fail fast: arg0 is null
    @Test
    public void testGuessArg0Null() {
    	assertEquals(SocialNetwork.guessFollowsGraph(null), null);
    }
    
    //fail fast: arg1 is empty
    @Test
    public void testGuessArg0Empty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    //three users, four tweets, 2-1-0 map
    @Test
    public void testGuessThreeFour() {
    	fillExpMapU1();
    	assertEquals(SocialNetwork.guessFollowsGraph(LSTWEETU1), EXPMAPU1);
    }
    
    //three users, four tweets, 2-1-0 map
    //shuffled
    @Test
    public void testGuessThreeFourShuffled() {
    	Collections.shuffle(LSTWEETU1);
    	fillExpMapU1();
    	assertEquals(SocialNetwork.guessFollowsGraph(LSTWEETU1), EXPMAPU1);
    }
   
    
   
    
    //fail fast: arg0 is null
    @Test
    public void testInfArg0Null() {
    	assertEquals(SocialNetwork.influencers(null), null);
    }
    
    //fail fast: arg0 is empty
    @Test
    public void testInfArg0Empty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected empty list", influencers.isEmpty());
    }
   
    //three users, four tweets, 2-1-0 map
    @Test
    public void testInfThreeFour() {
    	assertEquals(SocialNetwork.influencers(EXPMAPU1), Arrays.asList(userNameOne, userNameTwo,userNameThree));
    }
    


    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
