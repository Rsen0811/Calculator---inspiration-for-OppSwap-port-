import java.util.ArrayList;

public class Communicator {
	
	private static double getNum(String eq) {
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		for (String s : a) {
			equationList.add(s);
		}
		return BackendComputation.computeArray(equationList);
	}

	


}