package twitter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TweetsSentinel {
	/**
	 * Check that tweets list is valid
	 * 
	 * @param tweets
	 * @return true if tweets are not null and not empty; and the first
	 */
	public static boolean areOK(List<Tweet> tweets) {
		if (tweets == null)
			throw new IllegalArgumentException();
		if (tweets.size() == 0) return true; // empty lists are allowed;
		boolean res = false;
		for (Tweet tw : tweets) {
				res = (tw != null) || res;
		}
		return res;
	}
	/**
	 * Check that influencers map is valid
	 * 
	 * @param followsGraph
	 * @return true if followsGraph is not null and is no empty; 
	 * 	 */
	public static boolean areOK(Map<String, Set<String>> followsGraph) {
		if (followsGraph == null)
			throw new IllegalArgumentException();
		if (followsGraph.size() == 0) return true; // empty maps are allowed;
		boolean res = false;
		for (Entry <String, Set<String>> entry : followsGraph.entrySet()) {
				res = (entry != null) || res;
		}
		return res;
	}
}
