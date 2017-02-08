import java.util.regex.Pattern;


public class regex {
	public static void main(String args[]) {
		System.out.println("hello world".matches(".*\\s.*"));
		System.out.println("hello world".matches("\\s"));
		
		/*System.out.println(Pattern.matches("bob", "bob"));
		System.out.println(Pattern.matches(".*bob.*", "silllybobby"));
		
		System.out.println(Pattern.matches("add artist \\S+", "add artist tttt"));
		System.out.println(Pattern.matches("add artist \".+\"", "add artist \"silly bob\""));
		
		System.out.println(Pattern.matches("add artist (t|a)", "add artist a"));
		
		
		
		System.out.println(Pattern.matches("add artist (\\S+)|(\".+\")", "add artist bob"));
		System.out.println(Pattern.matches("add artist (\\S+|\".+\")", "add artist \"sillly bob\""));
		System.out.println(Pattern.matches("add artist (\\S+|\".+\")", "add artist sally"));*/

	}
}
