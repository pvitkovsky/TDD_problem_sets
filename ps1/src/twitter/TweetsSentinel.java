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
		if (tweets == null || tweets.size() == 0)
			throw new IllegalArgumentException();
		for (Tweet tw : tweets) {
			if (tw != null)
				return true;
		}
		return false;
	}
}
