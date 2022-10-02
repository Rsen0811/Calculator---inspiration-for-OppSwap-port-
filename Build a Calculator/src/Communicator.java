import java.util.ArrayList;

public class Communicator {
	public static void main(String args[]) {
		System.out.println(getNum("3 ^ -ln(5) ^ 4"));
		
	}
	public static double getNum(String eq) {
		eq = eq.replace("log","log ");
		eq = eq.replace("ln","ln ");
		eq = eq.replace("!", " !");
		eq = eq.replace(")(", ") * (");
		for (int i = 0; i < eq.length() - 1; i++) {
			if (eq.charAt(i) == ')' && ((eq.charAt(i+1) >= 48 && eq.charAt(i+1) <= 57) || eq.charAt(i+1) == '-')) {
				eq = eq.substring(0, i + 1) + " * " + eq.substring(i + 1, eq.length());
			}
		}
		
		for (int i = 1; i < eq.length(); i++) {
			if (eq.charAt(i) == '(' && eq.charAt(i-1) >= 48 && eq.charAt(i-1) <= 57) {
				eq = eq.substring(0, i) + " * " + eq.substring(i, eq.length());
			}
		}
		
		
		System.out.println(eq);
		
		eq = eq.replace("(", "( ");
		eq = eq.replace(")", " )");
		eq = eq.trim();
		System.out.println(eq);
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		
		for (String s : a) {
			equationList.add(s);
		}
		try {
			return BackendComputation.computeArray(equationList);
		} catch (NumberFormatException e) {
			return Double.NaN;
		}
	}

	


}