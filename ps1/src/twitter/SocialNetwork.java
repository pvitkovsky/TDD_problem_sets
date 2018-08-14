/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.MultiMapFillerByTweet;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	private static MultiMapFillerByTweet filler = new MultiMapFillerByTweet();

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets
	 *            a list of tweets providing the evidence, not modified by this
	 *            method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
		if(!TweetsSentinel.areOK(tweets)) throw new IllegalArgumentException();
		
		Map<String, Set<String>> res = new HashMap<String, Set<String>>();
		Set<String> allUsers = getAllUsers(tweets);
		for (String nodeUser : allUsers) {
			res.put(nodeUser, new HashSet<String>());
			for (Tweet tweet : tweets) {
				if (Extract.getMentionedUsers(Arrays.asList(tweet)).contains(nodeUser)) {
					filler.putToIterableMap(nodeUser, res, tweet.getAuthor());
				}
			}
		}
		return res;
	}

	/**
	 * 
	 * @param tweets
	 *            a list of tweets providing the evidence, not modified by this
	 *            method.
	 * 
	 * @return a set of authors and mentioned users in the tweets;
	 */
	private static Set<String> getAllUsers(final List<Tweet> tweets) {
		Set<String> allUsers = new HashSet<String>();
		allUsers.addAll(Extract.getMentionedUsers(tweets));
		for (Tweet tweet : tweets)
			allUsers.add(tweet.getAuthor());
		return allUsers;
	}

	/**
	 * Find the people in a social network who have the greatest influence, in the
	 * sense that they have the most followers.
	 * 
	 * @param followsGraph
	 *            a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
		if(!TweetsSentinel.areOK(followsGraph)) throw new IllegalArgumentException();
		ArrayList<String> res = new ArrayList<String>(followsGraph.keySet());
		res.sort((p1, p2) -> p1.compareTo(p2));
		return res;
	}

}
