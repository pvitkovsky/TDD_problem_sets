package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.util.randString.randString;

/**
 * Keeps a set of random strings and a unique string; Unique string is
 * guaranteed not to be present in the set
 */
public class TestDataGenerator {
	static final long DATASIZE = 5;
	static final int STRLEN = 6;
	static final Set<String> GENSTRINGS = randString.randomSet(STRLEN, DATASIZE);
	static final String UNIQUE = randString.uniqueString(STRLEN, GENSTRINGS);
	static Random gen = new Random();
	
	/**
	 * @return
	 */
	public static Set<String> getExtraStrings() {
		return GENSTRINGS;
	}
	
	public static Map<String, Integer> getStringIntMap() {
		Map<String, Integer> res = new HashMap<>();
		for (String str : GENSTRINGS) {
			res.put(str, gen.nextInt());
		}
		return res;
	}

	public static String getUniqueString() {
		return UNIQUE;
	}

	public static int getRandomNumber() {
		return gen.nextInt();
	}
}
