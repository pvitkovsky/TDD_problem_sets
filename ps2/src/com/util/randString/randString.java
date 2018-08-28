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
	public static Set<String> randomSet(int len, int size) {
		Set<String> res = new HashSet<String>();
		while (res.size() < size) {
			res.add(randomString(len));
		}
		return res;
	}
}