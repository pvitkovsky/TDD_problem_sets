package graph;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import com.util.randString.randString;

/**
 * Keeps a set of random strings and a unique string; Unique string is
 * guaranteed not to be present in the set
 */
public class VerticeSetGenerator {
	static final int BUFFERSIZE = 5;
	static final int BUFFERNAMELENGTH = 6;
	static final Set<String> SET = randString.randomSet(BUFFERSIZE, BUFFERNAMELENGTH);
	static final String UNIQUE = SET.iterator().next();

	public static Set<String> getExtraStrings() {
		Set<String> res = new HashSet<String>(SET);
		res.remove(UNIQUE);
		assertTrue(SET.size() - res.size() == 1);
		return res;
	}

	public static String getUniqueString() {
		assertTrue(UNIQUE != null);
		return UNIQUE;
	}

}
