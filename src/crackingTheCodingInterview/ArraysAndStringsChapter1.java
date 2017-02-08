package crackingTheCodingInterview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArraysAndStringsChapter1 {
	public static void runAll() {
		isUniqueRunner();
		isUniqueNoStructuresRunner();
		isPermutationRunner();
		urlifyRunner();
		isPalindromePermutationRunner();
		isOneAwayRunner();
		compressRunner();
		rotateImgRunner();
		makeZeroRunner();
		isRotationRunner();	
	}
	
	public static boolean isUnique(String s) {
		HashSet<Character> used = new HashSet<Character>();
		for (int i = 0; i<s.length(); i++ ){
			Character c = new Character(s.charAt(i));
			if (used.contains(c)){
				return false;
			}
			used.add(c);
		}
		return true;
	}
	
	public static void isUniqueRunner() {
		String[] input = {
				"Bob",
				"Joey",
			    "Sammy",
			};
		for(String s: input) {
			System.out.println(isUnique(s));
		}
	}
	
	public static boolean isUniqueNoStructures(String s) {
		for(int i = 0; i<s.length(); i++) { 
			for(int j = i + 1; j<s.length(); j++) {
				if(s.charAt(i) == s.charAt(j)) { 
					return false;
				}
			}
		}
		return true;
	}
	
	public static void isUniqueNoStructuresRunner() {
		String[] input = {
				"Bob",
				"Joey",
			    "Sammy",
			};
		for(String s: input) {
			System.out.println(isUniqueNoStructures(s));
		}
	}
	
	public static boolean isPermutation(String a, String b) {
		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length(); i++){
			char c = Character.toLowerCase(a.charAt(i));
			if(!map.containsKey(c)) {
				map.put(c, 1);
			} else {
				map.put(c, map.get(c) + 1);
			}
		}
		for(int i = 0; i < b.length(); i++) {
			char c = Character.toLowerCase(b.charAt(i));
			if(!map.containsKey(c)) {
				return false;
			} else {
				if(map.get(c) > 1) {
					map.put(c, map.get(c) - 1);
				} else {
					map.remove(c);
				}
			}
		}
		if(!map.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public static void isPermutationRunner() {
		String[][] input = {
				{"Bob", "Bob"},
				{"Bob", "bob"},
				{"asdf", "fdsa"},
				{"ASDF", "fdsa"},
				{"3dfh", "fd3h"},
				{"ddd", "dd"},
		};
		for (String[] pair : input) {
			System.out.println(isPermutation(pair[0], pair[1]));
		}
	}
	
	public static char[] urlify(char[] old, int x) {
		int j = 0;
		char[] ret = new char[old.length];
		for(int i = 0; i < x; i++) {
			if(old[i] == ' ') {
				ret[j] = '%';
				ret[++j] = '2';
				ret[++j] = '0';
			} else {
				ret[j] = old[i];
			}
			j++;
		}
		return ret;
	}
	
	public static void urlifyRunner() {
		System.out.println(urlify("Mr John Smith         ".toCharArray(), 13));
		System.out.println(urlify("A B        ".toCharArray(), 3));
	}
	
	public static boolean isPalindromePermutation(String s) {
		HashMap<Character, Integer> map = new HashMap<>();
		for(int i = 0; i < s.length(); i++) {
			char c = Character.toLowerCase(s.charAt(i));
			if (c == ' ') {
				continue;
			}
			if(!map.containsKey(c)) {
				map.put(c, 1);
			} else {
				map.put(c, map.get(c) + 1);
			}
		}
		int odd = 0;
		for(Map.Entry<Character, Integer> p : map.entrySet()) {
			if (p.getValue() % 2 ==1) {
				odd ++;
			}
			if(odd > 1) {
				return false;
			}
		}
		return true;
	}
	
	public static void isPalindromePermutationRunner() {
		String[] input = {
				"bob",
				"jojo",
				"BOB ",
				"bobk",
				"boB",
				"Tact Coa",
		};
		
		for(String x : input) {
			System.out.println(isPalindromePermutation(x));
		}
	}
	
	public static boolean isOneAway(String a, String b) {
		int i = 0;
		int j = 0;
		while(i < a.length() && j < b.length() && a.charAt(i) == b.charAt(j)) {
			i++;
			j++;
		}
		int[][] options = {
				{i + 1, j},
				{i, j + 1},
				{i + 1, j + 1},
		};
		for( int[] pair : options) {
			int x = pair[0];
			int y = pair[1]; 
			while(x < a.length() && y < b.length() && a.charAt(x) == b.charAt(y)) {
				x++;
				y++;
			}
			if(x == a.length() && y == b.length()) {
				return true;
			}
		}
		return false;
	}
	
	public static void isOneAwayRunner() {
		String[][] input = {
				{"pale", "ple"},
				{"pales", "pale"},
				{"pale", "bale"},
				{"pale", "bake"},
			};
			for(String[] x : input) {
				System.out.println(isOneAway(x[0], x[1]));
			}
	}
	
	public static String compress(String s) {
		int i = 0;
		StringBuilder str = new StringBuilder();
		while(i < s.length()) {
			char c = s.charAt(i);
			int x = 0;
			while(i < s.length() && s.charAt(i) == c) {
				x++;
				i++;
			}
			str.append(c);
			str.append(x);
		}
		String newStr = str.toString();
		if(s.length() <= newStr.length()) {
			return s;
		} else {
			return newStr;
		}
	}
	
	public static void compressRunner() {
		String[] input = {
				"aabccccaaa",
				"abc",
				"aaaaa",
		};
		for (String x : input) {
			System.out.println(compress(x));
		}
	}
	
	public static int[][] rotateImg( int[][] img) {
		boolean odd = (img.length % 2 == 1 ? true : false);
		
		for (int i = 0; i < (odd ? img.length/2 + 1 : img.length/2); i++) {
			for(int j = 0; j < img.length/2; j++) {
				int temp = img[i][j];
				img[i][j] = img[j][img.length - i - 1];
				img[j][img.length - i - 1] = img[img.length - i - 1][img.length - j - 1];
				img[img.length - i - 1][img.length - j - 1] = img[img.length - j - 1][i];
				img[img.length - j - 1][i] = temp;
			}
		}
		return img;
	}
	
	public static void rotateImgRunner() {
		int[][][]  input = {
				{
					{1, 2},
					{3, 4}
				},
				{
					{1, 2, 3},
					{4, 5, 6},
					{7, 8, 9}
				},
				{
					{1, 2, 3, 4},
					{5, 6, 7, 8},
					{9, 10, 11, 12},
					{13, 14, 15, 16}
				},
				{
					{1, 2, 3, 4, 5},
					{6, 7, 8, 9, 10},
					{11, 12, 13, 14, 15},
					{16, 17, 18, 19, 20},
					{21, 22, 23, 24, 25}
				}
		};
		for(int[][] img : input) {
			System.out.println("input: ");
			for(int[] row : img) {
				System.out.println(Arrays.toString(row));
			}
			int[][] rotated = rotateImg(img);
			
			System.out.println("output: ");
			for(int[] row : rotated) {
				System.out.println(Arrays.toString(row));
			}
			System.out.println();
		}
	}
	
	public static int[][] makeZero(int[][] m) {
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < m.length; i++) {
			boolean containsZero = false;
			for(int j = 0; j < m[i].length; j++) {
				if(m[i][j] == 0) {
					containsZero = true;
					set.add(j);;
				}
			}
			if(containsZero) {
				m[i] = new int[m[i].length];
			}
		}
		for(int i = 0; i< m.length; i++) {
			for (int j: set) {
				m[i][j] = 0;
			}
		}
		return m;
	}
	
	public static void makeZeroRunner() {
		int[][][] input = {
				{
					{1, 2},
					{3, 4},
				},
				{
					{0, 1},
					{2, 3}
				},
				{
					{1, 2, 3, 4},
					{3, 4, 0, 2},
					{2, 3, 5, 9},
					{2, 4, 0, 3},
				},
				{
					{1, 2, 3},
					{2, 0, 3},
				},
				{
					{1, 2},
					{3, 5},
					{0, 1},
				}
		};
		for(int[][] m : input) {
			System.out.println("input: ");
			for(int[] row : m) {
				System.out.println(Arrays.toString(row));
			}
			int[][] zeroed = makeZero(m);
			
			System.out.println("output: ");
			for(int[] row : zeroed) {
				System.out.println(Arrays.toString(row));
			}
			System.out.println();
		}
	}
	
	public static boolean isRotation(String a, String b) {
		if(a.length() != b.length()) return false;
		for(int j = 0; j < b.length(); j++) {
			if(a.charAt(0) == b.charAt(j)) {
				int x = 0;
				int y = j;
				while(x < a.length() && a.charAt(x) == b.charAt(y)) {
					x++;
					y++;
					if(y == b.length()) {
						y = 0;
					}
				}
				if(x == a.length()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void isRotationRunner() {
		String[][] input = {
				{"Shower", "Shower"},
				{"waterbottle", "erbottlewat"},
				{"ack", "cak"},
				{"oodle", "odleo"},
				{"oodle", "dleoo"},
		};
		for (String[] x : input) {
			System.out.println(isRotation(x[0], x[1]));
		}
	}
}
