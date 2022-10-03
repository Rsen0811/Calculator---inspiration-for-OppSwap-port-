import java.util.ArrayList;

public class Communicator {
	public static void main(String args[]) {
		// testing code
		System.out.println(getNum("-(3 ^ -(4 + 5)) * 0.2! * 3(1)"));
		
	}
	public static double getNum(String eq) {
		// formats string for easier reading of processor
		eq = eq.replace("log","log "); // "log(" --> "log ("
		eq = eq.replace("ln","ln "); // "ln(" --> "ln ("
		eq = eq.replace("!", " !"); // "x!" --> "x !"
		eq = eq.replace(")(", ") * ("); // adds * for implicit multiplication
		// find every instance of ")x" - change to ") * x" for easier processing
		for (int i = 0; i < eq.length() - 1; i++) {
			if (eq.charAt(i) == ')' && ((eq.charAt(i+1) >= 48 && eq.charAt(i+1) <= 57)
					|| eq.charAt(i+1) == '-')) {
				eq = eq.substring(0, i + 1) + " * " + eq.substring(i + 1, eq.length());
			}
		}
		
		// find every instance of "x(" - change to "x * (" for easier processing
		for (int i = 1; i < eq.length(); i++) {
			if (eq.charAt(i) == '(' && eq.charAt(i-1) >= 48 && eq.charAt(i-1) <= 57) {
				eq = eq.substring(0, i) + " * " + eq.substring(i, eq.length());
			}
		}
		
		// change "(x" into "( x" for easier processing
		eq = eq.replace("(", "( ");
		// change "x)" into "x )" for easier processing
		eq = eq.replace(")", " )");
		// remove surrounding empty characters produced by input
		eq = eq.trim();
		
		// convert string to arraylist - split by " " 
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		for (String s : a) {
			equationList.add(s);
		}
		
		// compute array of equation elements - if error thrown, return NaN
		try {
			return BackendComputation.computeArray(equationList);
		} catch (IllegalArgumentException e) {
			return Double.NaN;
		}
	}

	


}