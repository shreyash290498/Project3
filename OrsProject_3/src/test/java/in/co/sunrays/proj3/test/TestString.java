package in.co.sunrays.proj3.test;

import java.util.Arrays;

public class TestString {
	public static void main(String[] args) {
		/*
		 * String p = "rahul"; String p1 = "harul"; char[] j = p.toCharArray();
		 * char[] k = p1.toCharArray(); Arrays.sort(j); Arrays.sort(k); Boolean
		 * result1 = Arrays.equals(j, k); System.out.println(result1);
		 */

		String x = "JAVA";
		String y = "avaj";
		x = x.replace(" ", "");
		y = y.replace(" ", "");
		x = x.toLowerCase();
		y = y.toLowerCase();
		char[] a = x.toCharArray();
		char[] b = y.toCharArray();
		Arrays.sort(a);
		Arrays.sort(b);
		Boolean result = Arrays.equals(a, b);
		if (result == true) {
			System.out.println("Strin are Anagram");
		} else {
			System.out.println("Strin are not Anagram");
		}
	}
}
