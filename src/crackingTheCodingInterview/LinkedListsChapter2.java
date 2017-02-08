package crackingTheCodingInterview;
import java.util.*;

public class LinkedListsChapter2 {
	static class Node<E extends Comparable<E>> {
		Node<E> next;
		Node<E> prev;
		E data;
		public Node(E data) {
			this.data = data;
		}
		public Node(E[] data) {
			Node<E> n = this;
			n.data = data[0];
			for (int i = 1; i < data.length; i++) {
				n.next = new Node<>(data[i]);
				n.next.prev = n;
				n = n.next;
			}
		}
		public Node(Node<E> next) {
			this.next = next;
		}
		public String toString() {
			StringBuilder str = new StringBuilder();
			Node<E> n = this;
			while(n != null) {
				str.append(n.data.toString() + "->");
				n = n.next;
			}
			return str.toString();
		}
	}
	
	public static void main(String[] args) {
		//removeDupsRunner();
		//removeDupsNoBufferRunner();
		//kthToLastRunner();
		//deleteNodeRunner();
		//partitionRunner();
		runAddLists();
	}
	
	public static List<Node<Integer>> testIntegerLists() {
		List<Node<Integer>> lists = new ArrayList<>();
		lists.add(new Node<Integer>(new Integer[]{1,3,5}));
		lists.add(new Node<Integer>(new Integer[]{1,3, 3, 5}));
		lists.add(new Node<Integer>(new Integer[]{1,3,3,3,5}));
		lists.add(new Node<Integer>(new Integer[]{1,3,3,3,3,3,5,5,5,5,6,6,2,4,3,5}));
		lists.add(new Node<Integer>(new Integer[]{1,1,1,1,1,1}));
		lists.add(new Node<Integer>(new Integer[]{0,1,1,1,1,1}));
		lists.add(new Node<Integer>(new Integer[]{0, 0,1,1,1,1,1}));
		return lists;
	}
	
	public static List<Node<String>> testStringLists() {
		List<Node<String>> strs = new ArrayList<>();
		strs.add(new Node<String>(new String[]{"Hello", "World"}));
		strs.add(new Node<String>(new String[]{"Alexander", "Hamilton"}));
		strs.add(new Node<String>(new String[]{"Bob", "Bob"}));
		strs.add(new Node<String>(new String[]{"Alexander", "Hamilton", "Hamilton", "Alexander"}));
		strs.add(new Node<String>(new String[]{"Bob", "Joe", "Bob", "Joe"}));
		return strs;
	}
	
	public static void removeDupsRunner() {
		List<Node<Integer>> lists = testIntegerLists();
		List<Node<String>> strs = testStringLists();
		
		for(Node<Integer> ls : lists) { 
			System.out.println("input:  " + ls.toString());
			removeDups(ls);
			System.out.println("output: " + ls.toString());
			System.out.println();
		}
		for(Node<String> str : strs) {
			System.out.println("input:  " + str.toString());
			removeDups(str);
			System.out.println("output: " + str.toString());
			System.out.println();
		}
	}
	
	public static void removeDupsNoBufferRunner() {
		List<Node<Integer>> lists = testIntegerLists();
		List<Node<String>> strs = testStringLists();
		
		for(Node<Integer> ls : lists) { 
			System.out.println("input:  " + ls.toString());
			removeDupsNoBuffer(ls);
			System.out.println("output: " + ls.toString());
			System.out.println();
		}
		for(Node<String> str : strs) {
			System.out.println("input:  " + str.toString());
			removeDupsNoBuffer(str);
			System.out.println("output: " + str.toString());
			System.out.println();
		}
	}
	
	public static <E extends Comparable<E>> void removeDups(Node<E> head) {
		Node<E> n = head;
		HashSet<E> set = new HashSet<>();
		if(n!=null){
			set.add((E)n.data);
		}
		while(n != null && n.next != null) {
			if(set.contains(n.next.data)) {
				n.next = n.next.next;
			} else {
				set.add((E)n.next.data);
				n = n.next;
			}
		}
	}
	
	public static <E extends Comparable<E>> void removeDupsNoBuffer(Node<E> head) {
		head = new Node<>(head);
		Node<E> n = head;
		while(n.next != null) {
			Node<E> p = n.next;
			while(p.next != null) {
				if(p.next.data.equals(n.next.data)) {
					p.next = p.next.next;
				} else {
					p = p.next;
				}
			}
			n = n.next;
		}
		head = head.next;
	}
	
	public static <E extends Comparable<E>> Node<E> kthToLast(Node<E> head, int k) {
		int x = 0;
		Node<E> n = head;
		while(n!= null) {
			x++;
			n = n.next;
		}
		System.out.println("x: " + x);
		n = head;
		for(int i = 0; i < x-k; i++) {
			n = n.next;
		}
		return n;
	}
	
	public static void kthToLastRunner() {
		List<Node<Integer>> lists = testIntegerLists();
		List<Node<String>> strs = testStringLists();
		
		for(Node<Integer> ls : lists) { 
			System.out.println("input:  " + ls.toString());
			Node<Integer> kth = kthToLast(ls, 3);
			System.out.println("output: " + kth.toString());
			System.out.println();
		}
		for(Node<String> str : strs) {
			System.out.println("input:  " + str.toString());
			Node<String> kth = kthToLast(str, 2);
			System.out.println("output: " + kth.toString());
			System.out.println();
		}
	}
	
	public static <E extends Comparable<E>> void deleteNode(Node<E> n) {
		n.data = (E)n.next.data;
		n.next = n.next.next;
	}
	
	public static void deleteNodeRunner() {
		Node<Integer> n = new Node<Integer>(new Integer[]{1,3,3,3,3,3,5,5,5,5,6,6,2,4,3,5});
		Node<Integer> n2 = new Node<Integer>(new Integer[]{1,2,3,4,5,6,7,8,9});
		
		System.out.println("input:  " + n.toString());
		deleteNode(n);
		System.out.println("output: " + n.toString());
		
		Node<Integer> x = n2;
		for(int i = 0; i < 5; i++) {
			x = x.next;
		}
		System.out.println("input:  " + n2.toString());
		deleteNode(x);
		System.out.println("output: " + n2.toString());
	}
	
	public static <E extends Comparable<E>> void partition(Node<E> head, E pivot) {
		Node<E> i = head;
		while(i != null) {
			if(i.data.compareTo(pivot) < 0){
				i = i.next;
			} else {
				Node<E> j = i;
				while(j.next != null && j.data.compareTo(pivot) >= 0) {
					j = j.next;
				}
				E temp = i.data;
				i.data = j.data;
				j.data = temp;
				i = i.next;
			}
		}
	}
	
	public static void partitionRunner() {
		Node<Integer> n = new Node<Integer>(new Integer[]{5,3,9,4,5,1,1,0,9,2});
		int pivot = 5;
		System.out.println("pivot: " + pivot);
		System.out.println(n.toString());
		partition(n,pivot);
		System.out.println(n.toString());
		System.out.println();
		
		n = new Node<Integer>(new Integer[]{5,3,9,4,5,1,1,0,9,2});
		pivot = 4;
		System.out.println("pivot: " + pivot);
		System.out.println(n.toString());
		partition(n,pivot);
		System.out.println(n.toString());
		System.out.println();

		n = new Node<Integer>(new Integer[]{5,3,9,4,5,1,1,0,9,2});
		pivot = 0;
		System.out.println("pivot: " + pivot);
		System.out.println(n.toString());
		partition(n,pivot);
		System.out.println(n.toString());
		System.out.println();
		
		n = new Node<Integer>(new Integer[]{5,3,9,4,5,1,1,0,9,2});
		pivot = 1;
		System.out.println("pivot: " + pivot);
		System.out.println(n.toString());
		partition(n,pivot);
		System.out.println(n.toString());
		System.out.println();
		
		n = new Node<Integer>(new Integer[]{5,3,9,4,5,1,1,0,9,2});
		pivot = 9;
		System.out.println("pivot: " + pivot);
		System.out.println(n.toString());
		partition(n,pivot);
		System.out.println(n.toString());
		System.out.println();
	}
	
	public static Node<Integer> addLists(Node<Integer> a, Node<Integer> b) {
		int carry = 0;
		Node<Integer> out = null;
		Node<Integer> outEnd = null;
		while (a != null || b != null || carry != 0) {
			int c = (a != null ? a.data : 0) + (b != null ? b.data : 0) + carry;
			carry = 0;
			if (c >= 10) {
				c%=10;
				carry = 1;
			}
			if(out == null) {
				out = new Node<>(c);
				outEnd = out;
			} else {
				outEnd.next = new Node<>(c);
				outEnd = outEnd.next;
			}
			if(a != null) a = a.next;
			if(b != null) b = b.next;
		}
		return out;
	}
	
	public static void runAddLists() {
		Node<Integer> n = new Node<>(new Integer[]{7, 1, 6});
		Node<Integer> n2 = new Node<>(new Integer[]{5, 9, 2});
		Node<Integer> out = addLists(n, n2);
		System.out.println("in: " + n.toString());
		System.out.println("in: " + n2.toString());
		System.out.println("out: " + out.toString());
		System.out.println();
		
		n = new Node<>(new Integer[]{7, 1, 6, 3});
		n2 = new Node<>(new Integer[]{5, 9, 2});
		out = addLists(n, n2);
		System.out.println("in: " + n.toString());
		System.out.println("in: " + n2.toString());
		System.out.println("out: " + out.toString());
		System.out.println();
		
		n = new Node<>(new Integer[]{7, 1, 9});
		n2 = new Node<>(new Integer[]{5, 9, 2});
		out = addLists(n, n2);
		System.out.println("in: " + n.toString());
		System.out.println("in: " + n2.toString());
		System.out.println("out: " + out.toString());
		System.out.println();
	}
	
	public static Node<Integer> addListsInOrder(Node<Integer> a, Node<Integer> b) {
		Node<Integer> aEnd;
		Node<Integer> bEnd;
		while()
	}
}
