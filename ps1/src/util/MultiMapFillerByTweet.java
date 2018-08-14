package util;

import java.util.HashSet;
import java.util.Set;

import util.MultiMap;

/**
 * Concrete class for putting things into multimap.
 */
public class MultiMapFillerByTweet extends MultiMap<String, Set<String>, String> {
	
	@Override
	public Set<String> createMapCollection() {
		return new HashSet<String>();
	}
}
