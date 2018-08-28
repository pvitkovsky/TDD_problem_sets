package graph;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import com.util.randString.randString;

/**
 * Keeps a set of random strings and a unique string;
 * Unique string is guaranteed not to be present in the set
 */
public class VerticeSetGenerator {
	static final int BUFFERSIZE = 5;
	static final int BUFFERNAMELENGTH = 6;
	static final Set<String> EXTRASTRINGS = randString.randomSet(BUFFERSIZE, BUFFERNAMELENGTH);
	static final String EXTRAFIRST = EXTRASTRINGS.iterator().next();

	public static Set<String> getExtraStrings() {
		assertTrue(EXTRASTRINGS.remove(EXTRAFIRST));
		assertTrue(EXTRASTRINGS.size() > 0);
		return EXTRASTRINGS;
	}

	public static String getUniqueString() {
		assertTrue(EXTRAFIRST != null);
		return EXTRAFIRST;
	}

}
