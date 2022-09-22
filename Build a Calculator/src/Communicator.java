import java.util.ArrayList;

public class Communicator {
	public static void main(String args[]) {
		System.out.println(getNum("(1 + 2) + 3 * 5 / (2 + 1 * (1 + 4)) * (7 + 2) * 3"));
	}
	public static double getNum(String eq) {
		
		eq = eq.replace("(", "( ");
		eq = eq.replace(")", " )");
		System.out.println(eq);
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		for (String s : a) {
			equationList.add(s);
		}
		return BackendComputation.computeArray(equationList);
	}

	


}