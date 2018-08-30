package com.util.randString;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class randString {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	/**
	 * Generates a set of random strings
	 * 
	 * @param len
	 *            length of strings to generate
	 * @param size
	 *            size of set to return
	 * @return a set of random strings of size size
	 */
	public static Set<String> randomSet(int len, long size) {
		Set<String> res = new HashSet<String>();
		while (res.size() < size) {
			res.add(randomString(len));
		}
		return res;
	}

	/**
	 * Relatively to the set given, returns a unique random string.
	 * @param set a set of strings that we can't repeat
	 * @return a string that is not contained in the set
	 */
	public static String uniqueString(int len, final Set<String> set) {
		String res;
		do {
			res = randomString(len);
		} while (set.contains(res));
		return res;
	}
}