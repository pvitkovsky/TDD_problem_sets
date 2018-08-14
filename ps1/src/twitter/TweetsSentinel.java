package twitter;

import java.util.List;

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
}
